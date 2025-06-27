package librarysystem.dao.impl;

import librarysystem.dao.CategorieDao;
import librarysystem.model.Categorie;
import librarysystem.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CategorieDao interface using JDBC.
 * 
 * 
 * @author Hamza Mekouar
 */
public class CategorieDaoImpl implements CategorieDao {

    /**
     * Retrieves all categories from the database.
     * 
     * @return list of all categories
     */
    @Override
    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie";
        try (Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categorie categorie = new Categorie(
                    rs.getInt("id"),
                    rs.getString("nom")
                );
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id the category ID
     * @return Categorie object or null if not found
     */
    @Override
    public Categorie getCategorieById(int id) {
        String sql = "SELECT * FROM categorie WHERE id = ?";
        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categorie(
                        rs.getInt("id"),
                        rs.getString("nom")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new category to the database.
     * 
     * @param categorie the category to add
     */
    @Override
    public void addCategorie(Categorie categorie) {
        String sql = "INSERT INTO categorie (nom) VALUES (?)";
        
        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categorie.getNom());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categorie.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing category in the database.
     * 
     * @param categorie the category to update
     */
    @Override
    public void updateCategorie(Categorie categorie) {
        String sql = "UPDATE categorie SET nom = ? WHERE id = ?";
        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categorie.getNom());
            stmt.setInt(2, categorie.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id the category ID
     */
    @Override
    public void deleteCategorie(int id) {
        String sql = "DELETE FROM categorie WHERE id = ?";
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
     * Searches categories by name.
     * 
     * @param nom the category name
     * @return list of matching categories
     */
    @Override
    public List<Categorie> searchCategoriesByName(String nom) {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie WHERE nom LIKE ?";
        try (
        		Connection connection = DBConnection.getConnection();
        		PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categorie categorie = new Categorie(
                        rs.getInt("id"),
                        rs.getString("nom")
                    );
                    categories.add(categorie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
