package librarysystem.controller;

import librarysystem.dao.CategorieDao;
import librarysystem.model.Categorie;

import java.util.List;

/**
 * Controller to manage "Categories"
 * Encapsulates interaction between view and Categorie DAO.
 * 
 * Author: Hamza Mekouar
 */
public class CategorieController {

    private final CategorieDao categorieDAO;

    /**
     * Constructor with dependency injection.
     * 
     * @param categorieDAO The DAO implementation for Categorie
     */
    public CategorieController(CategorieDao categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    /**
     * Retrieves all categories from the database.
     * 
     * @return list of all categories
     */
    public List<Categorie> getAllCategories() {
        return categorieDAO.getAllCategories();
    }

    /**
     * Adds a new category to the database.
     * 
     * @param categorie the category to add
     */
    public void addCategorie(Categorie categorie) {
        categorieDAO.addCategorie(categorie);
    }

    /**
     * Updates an existing category in the database.
     * 
     * @param categorie the updated category object
     */
    public void updateCategorie(Categorie categorie) {
        categorieDAO.updateCategorie(categorie);
    }

    /**
     * Deletes a category by ID.
     * 
     * @param id the ID of the category to delete
     */
    public void deleteCategorie(int id) {
        categorieDAO.deleteCategorie(id);
    }

    /**
     * Searches for categories by name (partial or full).
     * 
     * @param nom the search term
     * @return list of matching categories
     */
    public List<Categorie> searchCategoriesByName(String nom) {
        return categorieDAO.searchCategoriesByName(nom);
    }

    /**
     * Retrieves a single category by ID.
     * 
     * @param id the category's ID
     * @return the category object, or null if not found
     */
    public Categorie getCategorieById(int id) {
        return categorieDAO.getCategorieById(id);
    }
}
