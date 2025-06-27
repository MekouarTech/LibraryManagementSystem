package librarysystem.controller;

import librarysystem.dao.EditeurDao;
import librarysystem.model.Editeur;

import java.util.List;

/**
 * Controller to manage "Editeurs" (Editors)
 * Encapsulates interaction between view and Editeur DAO.
 * 
 * @author Hamza Mekouar
 */
public class EditeurController {

    private final EditeurDao editeurDAO;

    /**
     * Constructor with dependency injection.
     * 
     * @param editeurDAO The DAO implementation for Editeur
     */
    public EditeurController(EditeurDao editeurDAO) {
        this.editeurDAO = editeurDAO;
    }

    /**
     * Retrieves all editors from the database.
     * 
     * @return list of all editors
     */
    public List<Editeur> getAllEditeurs() {
        return editeurDAO.getAllEditeurs();
    }

    /**
     * Adds a new editor to the database.
     * 
     * @param editeur the editor to add
     */
    public void addEditeur(Editeur editeur) {
        editeurDAO.addEditeur(editeur);
    }

    /**
     * Updates an existing editor in the database.
     * 
     * @param editeur the updated editor object
     */
    public void updateEditeur(Editeur editeur) {
        editeurDAO.updateEditeur(editeur);
    }

    /**
     * Deletes an editor by ID.
     * 
     * @param id the ID of the editor to delete
     */
    public void deleteEditeur(int id) {
        editeurDAO.deleteEditeur(id);
    }

    /**
     * Searches for editors by name (partial or full).
     * 
     * @param nom the search term
     * @return list of matching editors
     */
    public List<Editeur> searchEditeursByName(String nom) {
        return editeurDAO.searchEditeursByName(nom);
    }

    /**
     * Retrieves a single editor by ID.
     * 
     * @param id the editor's ID
     * @return the editor object, or null if not found
     */
    public Editeur getEditeurById(int id) {
        return editeurDAO.getEditeurById(id);
    }
}
