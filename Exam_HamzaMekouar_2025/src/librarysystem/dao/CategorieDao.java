package librarysystem.dao;

import librarysystem.model.Categorie;
import java.util.List;

/**
 * DAO interface for Categorie entity
 * 
 * @author Hamza Mekouar
 */
public interface CategorieDao {
    List<Categorie> getAllCategories();
    Categorie getCategorieById(int id);
    void addCategorie(Categorie categorie);
    void updateCategorie(Categorie categorie);
    void deleteCategorie(int id);

    /**
     * Search categories by partial name match.
     * 
     * @param nom partial or full category name to search
     * @return list of matching categories
     */
    List<Categorie> searchCategoriesByName(String nom);
}
