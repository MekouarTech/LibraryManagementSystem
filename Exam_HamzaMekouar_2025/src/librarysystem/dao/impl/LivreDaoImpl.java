package librarysystem.dao.impl;

import librarysystem.dao.LivreDao;
import librarysystem.model.Livre;
import librarysystem.model.Editeur;
import librarysystem.model.Auteur;
import librarysystem.model.Categorie;
import librarysystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Hamza Mekouar
 */
public class LivreDaoImpl implements LivreDao {
	
	
	/**
     * Retrieves all Books from the database.
     *
     * @return a list of all Livre objects
     */
	@Override
	public List<Livre> getAllLivres() {
	    List<Livre> livres = new ArrayList<>();
	    String sql = "SELECT l.id, l.titre, l.annee_publication, l.nbr_exemplaires, e.id AS editeur_id, e.nom AS editeur_nom " +
	                 "FROM Livre l LEFT JOIN Editeur e ON l.editeur_id = e.id";

	    try (
             Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)
             ) {

	        // First: Extract all base livre info
	        while (rs.next()) {

                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                int anneePublication = rs.getInt("annee_publication");
                int nbrExemplaires = rs.getInt("nbr_exemplaires");
                int editeurId = rs.getInt("editeur_id");
                String editeurNom = rs.getString("editeur_nom");

                // Create Editeur object if exists
                Editeur editeur = null;
                if (editeurId != 0) {
                    editeur = new Editeur(editeurId, editeurNom);
                }
                
                Livre livre = new Livre(id, titre, anneePublication, nbrExemplaires, editeur);

	            livres.add(livre);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return livres;
	    }

	    for (Livre livre : livres) {
	        livre.setAuteurs(getAuteursByLivreId(livre.getId()));
	        livre.setCategories(getCategoriesByLivreId(livre.getId()));
	    }

	    return livres;
	}


    @Override
    public Livre getLivreById(int id) {
        Livre livre = null;
        String sql = "SELECT l.id, l.titre, l.annee_publication, l.nbr_exemplaires, e.id AS editeur_id, e.nom AS editeur_nom " +
                     "FROM Livre l LEFT JOIN Editeur e ON l.editeur_id = e.id WHERE l.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    livre = new Livre();
                    livre.setId(rs.getInt("id"));
                    livre.setTitre(rs.getString("titre"));
                    livre.setAnneePublication(rs.getInt("annee_publication"));
                    livre.setNbrExemplaires(rs.getInt("nbr_exemplaires"));

                    Editeur editeur = new Editeur();
                    editeur.setId(rs.getInt("editeur_id"));
                    editeur.setNom(rs.getString("editeur_nom"));
                    livre.setEditeur(editeur);

                    livre.setAuteurs(getAuteursByLivreId(livre.getId()));
                    livre.setCategories(getCategoriesByLivreId(livre.getId()));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livre;
    }

    @Override
    public void addLivre(Livre livre) {
        String sqlLivre = "INSERT INTO Livre (titre, annee_publication, nbr_exemplaires, editeur_id) VALUES (?, ?, ?, ?)";
        String sqlLivreAuteur = "INSERT INTO Livre_Auteur (livre_id, auteur_id) VALUES (?, ?)";
        String sqlLivreCategorie = "INSERT INTO Livre_Categorie (livre_id, categorie_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtLivre = conn.prepareStatement(sqlLivre, Statement.RETURN_GENERATED_KEYS)) {
                pstmtLivre.setString(1, livre.getTitre());
                pstmtLivre.setInt(2, livre.getAnneePublication());
                pstmtLivre.setInt(3, livre.getNbrExemplaires());
                if (livre.getEditeur() != null && livre.getEditeur().getId() != 0) {
                    pstmtLivre.setInt(4, livre.getEditeur().getId());
                } else {
                    pstmtLivre.setNull(4, java.sql.Types.INTEGER);
                }


                pstmtLivre.executeUpdate();

                try (ResultSet generatedKeys = pstmtLivre.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int livreId = generatedKeys.getInt(1);
                        livre.setId(livreId);

                        // Insert Livre_Auteur relations
                        if (livre.getAuteurs() != null) {
                            try (PreparedStatement pstmtLivreAuteur = conn.prepareStatement(sqlLivreAuteur)) {
                                for (Auteur auteur : livre.getAuteurs()) {
                                    pstmtLivreAuteur.setInt(1, livreId);
                                    pstmtLivreAuteur.setInt(2, auteur.getId());
                                    pstmtLivreAuteur.addBatch();
                                }
                                pstmtLivreAuteur.executeBatch();
                            }
                        }

                        // Insert Livre_Categorie relations
                        if (livre.getCategories() != null) {
                            try (PreparedStatement pstmtLivreCategorie = conn.prepareStatement(sqlLivreCategorie)) {
                                for (Categorie categorie : livre.getCategories()) {
                                    pstmtLivreCategorie.setInt(1, livreId);
                                    pstmtLivreCategorie.setInt(2, categorie.getId());
                                    pstmtLivreCategorie.addBatch();
                                }
                                pstmtLivreCategorie.executeBatch();
                            }
                        }
                    } else {
                        throw new SQLException("Creating livre failed, no ID obtained.");
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLivre(Livre livre) {
        String sqlUpdateLivre = "UPDATE Livre SET titre = ?, annee_publication = ?, nbr_exemplaires = ?, editeur_id = ? WHERE id = ?";
        String sqlDeleteLivreAuteur = "DELETE FROM Livre_Auteur WHERE livre_id = ?";
        String sqlDeleteLivreCategorie = "DELETE FROM Livre_Categorie WHERE livre_id = ?";
        String sqlInsertLivreAuteur = "INSERT INTO Livre_Auteur (livre_id, auteur_id) VALUES (?, ?)";
        String sqlInsertLivreCategorie = "INSERT INTO Livre_Categorie (livre_id, categorie_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtUpdateLivre = conn.prepareStatement(sqlUpdateLivre)) {
                pstmtUpdateLivre.setString(1, livre.getTitre());
                pstmtUpdateLivre.setInt(2, livre.getAnneePublication());
                pstmtUpdateLivre.setInt(3, livre.getNbrExemplaires());
                pstmtUpdateLivre.setInt(4, livre.getEditeur() != null ? livre.getEditeur().getId() : null);
                pstmtUpdateLivre.setInt(5, livre.getId());
                pstmtUpdateLivre.executeUpdate();

                // Delete existing associations
                try (PreparedStatement pstmtDeleteAuteur = conn.prepareStatement(sqlDeleteLivreAuteur);
                     PreparedStatement pstmtDeleteCategorie = conn.prepareStatement(sqlDeleteLivreCategorie)) {

                    pstmtDeleteAuteur.setInt(1, livre.getId());
                    pstmtDeleteAuteur.executeUpdate();

                    pstmtDeleteCategorie.setInt(1, livre.getId());
                    pstmtDeleteCategorie.executeUpdate();
                }

                // Re-insert associations
                if (livre.getAuteurs() != null) {
                    try (PreparedStatement pstmtInsertAuteur = conn.prepareStatement(sqlInsertLivreAuteur)) {
                        for (Auteur auteur : livre.getAuteurs()) {
                            pstmtInsertAuteur.setInt(1, livre.getId());
                            pstmtInsertAuteur.setInt(2, auteur.getId());
                            pstmtInsertAuteur.addBatch();
                        }
                        pstmtInsertAuteur.executeBatch();
                    }
                }

                if (livre.getCategories() != null) {
                    try (PreparedStatement pstmtInsertCategorie = conn.prepareStatement(sqlInsertLivreCategorie)) {
                        for (Categorie categorie : livre.getCategories()) {
                            pstmtInsertCategorie.setInt(1, livre.getId());
                            pstmtInsertCategorie.setInt(2, categorie.getId());
                            pstmtInsertCategorie.addBatch();
                        }
                        pstmtInsertCategorie.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLivre(int id) {
        String sql = "DELETE FROM Livre WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Livre> searchLivres(String titre, String editeur, String auteur, String categorie) {
        List<Livre> livres = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT l.id, l.titre, l.annee_publication, l.nbr_exemplaires, " +
            "e.id AS editeur_id, e.nom AS editeur_nom " +
            "FROM Livre l " +
            "LEFT JOIN Editeur e ON l.editeur_id = e.id " +
            "LEFT JOIN Livre_Auteur la ON l.id = la.livre_id " +
            "LEFT JOIN Auteur a ON la.auteur_id = a.id " +
            "LEFT JOIN Livre_Categorie lc ON l.id = lc.livre_id " +
            "LEFT JOIN Categorie c ON lc.categorie_id = c.id " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (titre != null && !titre.trim().isEmpty()) {
            sql.append("AND l.titre LIKE ? ");
            params.add("%" + titre + "%");
        }
        if (editeur != null && !editeur.trim().isEmpty()) {
            sql.append("AND e.nom LIKE ? ");
            params.add("%" + editeur + "%");
        }
        if (auteur != null && !auteur.trim().isEmpty()) {
            sql.append("AND (a.nom LIKE ? OR a.prenom LIKE ?) ");
            params.add("%" + auteur + "%");
            params.add("%" + auteur + "%");
        }
        if (categorie != null && !categorie.trim().isEmpty()) {
            sql.append("AND c.nom LIKE ? ");
            params.add("%" + categorie + "%");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // set parameters
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            Map<Integer, Livre> livreMap = new HashMap<>();

            // Step 1: Load books
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int livreId = rs.getInt("id");
                    if (!livreMap.containsKey(livreId)) {
                        Livre livre = new Livre();
                        livre.setId(livreId);
                        livre.setTitre(rs.getString("titre"));
                        livre.setAnneePublication(rs.getInt("annee_publication"));
                        livre.setNbrExemplaires(rs.getInt("nbr_exemplaires"));

                        Editeur editeurObj = new Editeur();
                        editeurObj.setId(rs.getInt("editeur_id"));
                        editeurObj.setNom(rs.getString("editeur_nom"));
                        livre.setEditeur(editeurObj);

                        livreMap.put(livreId, livre);
                    }
                }
            }

            // Step 2: Enrich books
            for (Livre livre : livreMap.values()) {
                livre.setAuteurs(getAuteursByLivreId(livre.getId()));
                livre.setCategories(getCategoriesByLivreId(livre.getId()));
                livres.add(livre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }


    // Helper methods to get associated authors and categories

    private List<Auteur> getAuteursByLivreId(int livreId) {
        List<Auteur> auteurs = new ArrayList<>();
        String sql = "SELECT a.id, a.nom, a.prenom, a.nationalite, a.dateNaissance FROM Auteur a " +
                     "INNER JOIN Livre_Auteur la ON a.id = la.auteur_id WHERE la.livre_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, livreId);

            try (ResultSet rs = pstmt.executeQuery()) {
            	while (rs.next()) {
            	    Auteur auteur = new Auteur();
            	    auteur.setId(rs.getInt("id"));
            	    auteur.setNom(rs.getString("nom"));
            	    auteur.setPrenom(rs.getString("prenom"));
            	    auteur.setNationalite(rs.getString("nationalite"));

            	    java.sql.Date sqlDate = rs.getDate("dateNaissance");
            	    if (sqlDate != null) {
            	        auteur.setDateNaissance(sqlDate.toLocalDate());
            	    } else {
            	        auteur.setDateNaissance(null);
            	    }

            	    auteurs.add(auteur);
            	}

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auteurs;
    }

    private List<Categorie> getCategoriesByLivreId(int livreId) {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT c.id, c.nom FROM Categorie c " +
                     "INNER JOIN Livre_Categorie lc ON c.id = lc.categorie_id WHERE lc.livre_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, livreId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Categorie categorie = new Categorie();
                    categorie.setId(rs.getInt("id"));
                    categorie.setNom(rs.getString("nom"));
                    categories.add(categorie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
