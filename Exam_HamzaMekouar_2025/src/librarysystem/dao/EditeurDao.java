package librarysystem.dao;

import librarysystem.model.Editeur;
import java.util.List;

/**
 * DAO interface for Editeur entity
 * 
 * @author Hamza Mekouar
 */
public interface EditeurDao {
    List<Editeur> getAllEditeurs();
    Editeur getEditeurById(int id);
    void addEditeur(Editeur editeur);
    void updateEditeur(Editeur editeur);
    void deleteEditeur(int id);

    /**
     * Search editors by partial or full name.
     *
     * @param nom partial or full editor name
     * @return list of matching editors
     */
    List<Editeur> searchEditeursByName(String nom);
}
