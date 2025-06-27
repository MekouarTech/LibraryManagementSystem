package librarysystem.dao.impl;

import librarysystem.dao.AuteurDao;
import librarysystem.model.Auteur;
import librarysystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * 
 * @author Hamza Mekouar
 */
public class AuteurDaoImpl implements AuteurDao {
    /**
     * Retrieves all authors from the database.
     *
     * @return a list of all Auteur objects
     */
    @Override
    public List<Auteur> getAllAuteurs() {
        List<Auteur> auteurs = new ArrayList<>();
        String sql = "SELECT * FROM Auteur";

        try (Connection connection = DBConnection.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                auteurs.add(extractAuteur(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auteurs;
    }

    /**
     * Retrieves a specific author by their ID.
     *
     * @param id the ID of the author
     * @return the Auteur object if found, null otherwise
     */
    @Override
    public Auteur getAuteurById(int id) {
        Auteur auteur = null;
        String sql = "SELECT * FROM Auteur WHERE id = ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    auteur = extractAuteur(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auteur;
    }

    /**
     * Adds a new author to the database.
     *
     * @param auteur the Auteur object to add
     */
    @Override
    public void addAuteur(Auteur auteur) {
        String sql = "INSERT INTO Auteur (nom, prenom, nationalite, dateNaissance) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, auteur.getNom());
            pstmt.setString(2, auteur.getPrenom());
            pstmt.setString(3, auteur.getNationalite());
            pstmt.setDate(4, Date.valueOf(auteur.getDateNaissance()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing author's information in the database.
     *
     * @param auteur the updated Auteur object
     */
    @Override
    public void updateAuteur(Auteur auteur) {
        String sql = "UPDATE Auteur SET nom = ?, prenom = ?, nationalite = ?, dateNaissance = ? WHERE id = ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, auteur.getNom());
            pstmt.setString(2, auteur.getPrenom());
            pstmt.setString(3, auteur.getNationalite());
            pstmt.setDate(4, Date.valueOf(auteur.getDateNaissance()));
            pstmt.setInt(5, auteur.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an author from the database by their ID.
     *
     * @param id the ID of the author to delete
     */
    @Override
    public void deleteAuteur(int id) {
        String sql = "DELETE FROM Auteur WHERE id = ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for authors whose last name contains the specified text.
     *
     * @param nom partial or full name of the author
     * @return a list of matching Auteur objects
     */
    @Override
    public List<Auteur> searchAuteursByNom(String nom) {
        List<Auteur> auteurs = new ArrayList<>();
        String sql = "SELECT * FROM Auteur WHERE nom LIKE ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nom + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    auteurs.add(extractAuteur(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auteurs;
    }

    /**
     * Helper method to extract an Auteur object from a ResultSet.
     *
     * @param rs the ResultSet object
     * @return the extracted Auteur object
     * @throws SQLException if a database access error occurs
     */
    private Auteur extractAuteur(ResultSet rs) throws SQLException {
        Auteur auteur = new Auteur();
        auteur.setId(rs.getInt("id"));
        auteur.setNom(rs.getString("nom"));
        auteur.setPrenom(rs.getString("prenom"));
        auteur.setNationalite(rs.getString("nationalite"));

        Date sqlDate = rs.getDate("dateNaissance");
        auteur.setDateNaissance(sqlDate != null ? sqlDate.toLocalDate() : null);

        return auteur;
    }
}
