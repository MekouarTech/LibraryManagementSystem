package librarysystem.dao;

import librarysystem.model.Livre;
import java.util.List;

/**
 * DAO interface for Livre entity
 * 
 * @author Hamza Mekouar
 */
public interface LivreDao {
    List<Livre> getAllLivres();
    Livre getLivreById(int id);
    void addLivre(Livre livre);
    void updateLivre(Livre livre);
    void deleteLivre(int id);

    /**
     * Search livres by multiple optional filters.
     * Any parameter can be null or empty to be ignored.
     * 
     * @param titre      title filter (partial match)
     * @param editeur    editor filter (partial match)
     * @param auteur     author filter (partial match)
     * @param categorie  category filter (partial match)
     * @return list of livres matching filters
     */
    List<Livre> searchLivres(String titre, String editeur, String auteur, String categorie);
}
