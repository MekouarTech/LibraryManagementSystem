package librarysystem.dao.impl;

import librarysystem.dao.EditeurDao;
import librarysystem.model.Editeur;
import librarysystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the EditeurDao interface.
 * 
 * 
 * @author Hamza Mekouar
 */
public class EditeurDaoImpl implements EditeurDao {
    
    /**
     * Retrieve all editors from the database.
     *
     * @return list of all editors "Editeur"
     */
    @Override
    public List<Editeur> getAllEditeurs() {
        List<Editeur> editeurs = new ArrayList<>();
        String sql = "SELECT id, nom FROM Editeur";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Editeur editeur = new Editeur();
                editeur.setId(rs.getInt("id"));
                editeur.setNom(rs.getString("nom"));
                editeurs.add(editeur);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return editeurs;
    }

    /**
     * Retrieve a specific editor by its ID.
     *
     * @param id editor ID
     * @return Editeur object or null if not found
     */
    @Override
    public Editeur getEditeurById(int id) {
        String sql = "SELECT id, nom FROM Editeur WHERE id = ?";
        Editeur editeur = null;

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    editeur = new Editeur();
                    editeur.setId(rs.getInt("id"));
                    editeur.setNom(rs.getString("nom"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return editeur;
    }

    /**
     * Add a new editor to the database.
     *
     * @param editeur editor to add
     */
    @Override
    public void addEditeur(Editeur editeur) {
        String sql = "INSERT INTO Editeur (nom) VALUES (?)";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, editeur.getNom());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    editeur.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update an existing editor's information.
     *
     * @param editeur editor to update
     */
    @Override
    public void updateEditeur(Editeur editeur) {
        String sql = "UPDATE Editeur SET nom = ? WHERE id = ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, editeur.getNom());
            stmt.setInt(2, editeur.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an editor from the database.
     *
     * @param id editor ID to delete
     */
    @Override
    public void deleteEditeur(int id) {
        String sql = "DELETE FROM Editeur WHERE id = ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search editors by partial or full name.
     *
     * @param nom partial or full editor name
     * @return list of matching editors
     */
    @Override
    public List<Editeur> searchEditeursByName(String nom) {
        List<Editeur> editeurs = new ArrayList<>();
        String sql = "SELECT id, nom FROM Editeur WHERE nom LIKE ?";

        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "%" + nom + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Editeur editeur = new Editeur();
                    editeur.setId(rs.getInt("id"));
                    editeur.setNom(rs.getString("nom"));
                    editeurs.add(editeur);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return editeurs;
    }
}
