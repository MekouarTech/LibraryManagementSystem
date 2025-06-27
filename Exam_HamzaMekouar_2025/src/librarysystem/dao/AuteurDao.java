package librarysystem.dao;

import librarysystem.model.Auteur;
import java.util.List;

/**
 * DAO interface for Auteur entity
 * 
 * @author Hamza Mekouar
 */
public interface AuteurDao {
    List<Auteur> getAllAuteurs();
    Auteur getAuteurById(int id);
    void addAuteur(Auteur auteur);
    void updateAuteur(Auteur auteur);
    void deleteAuteur(int id);

    /**
     * Search authors by partial or full last name.
     * 
     * @param nom partial or full author last name
     * @return list of matching authors
     */
    List<Auteur> searchAuteursByNom(String nom);
}
