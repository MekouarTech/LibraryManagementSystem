package librarysystem.controller;

import librarysystem.dao.AuteurDao;
import librarysystem.model.Auteur;

import java.util.List;

/**
 * Controller to manage "Auteurs"
 * Encapsulates interaction between view and Auteur DAO.
 * 
 * @author Hamza Mekouar
 */
public class AuteurController {

    private final AuteurDao auteurDAO;

    /**
     * Constructor with dependency injection.
     * 
     * @param auteurDAO The DAO implementation for Auteur
     */
    public AuteurController(AuteurDao auteurDAO) {
        this.auteurDAO = auteurDAO;
    }

    /**
     * Retrieves all authors from the database.
     * 
     * @return list of all authors
     */
    public List<Auteur> getAllAuteurs() {
        return auteurDAO.getAllAuteurs();
    }

    /**
     * Adds a new author to the database.
     * 
     * @param auteur the author to add
     */
    public void addAuteur(Auteur auteur) {
        auteurDAO.addAuteur(auteur);
    }

    /**
     * Updates an existing author in the database.
     * 
     * @param auteur the updated author object
     */
    public void updateAuteur(Auteur auteur) {
        auteurDAO.updateAuteur(auteur);
    }

    /**
     * Deletes an author by ID.
     * 
     * @param id the ID of the author to delete
     */
    public void deleteAuteur(int id) {
        auteurDAO.deleteAuteur(id);
    }

    /**
     * Searches for authors by name (partial or full).
     * 
     * @param nom the search term
     * @return list of matching authors
     */
    public List<Auteur> searchAuteursByNom(String nom) {
        return auteurDAO.searchAuteursByNom(nom);
    }

    /**
     * Retrieves a single author by ID.
     * 
     * @param id the author's ID
     * @return the author object, or null if not found
     */
    public Auteur getAuteurById(int id) {
        return auteurDAO.getAuteurById(id);
    }
}
