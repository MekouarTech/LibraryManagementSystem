package librarysystem.controller;

import librarysystem.dao.LivreDao;
import librarysystem.dao.impl.LivreDaoImpl;
import librarysystem.model.Livre;

import java.util.List;

/**
 * Controller To manage "Livres"
 * 
 * @author Hamza Mekouar
 */

public class LivreController {

    private final LivreDao livreDAO;

    /**
     * Constructeur avec injection du DAO
     * 
     * @param livreDAO Implémentation de LivreDao
     */
    public LivreController(LivreDao livreDAO) {
        this.livreDAO = livreDAO;
    }

    /**
     * Ajoute un livre dans la base de données.
     * 
     * @param livre Le livre à ajouter
     */
    public void addLivre(Livre livre) {
        livreDAO.addLivre(livre);
    }

    /**
     * Récupère la liste de tous les livres.
     * 
     * @return Liste des livres
     */
    public List<Livre> getLivres() {
        return livreDAO.getAllLivres();
    }

    /**
     * Recherche des livres selon différents critères.
     *
     * @param titre     Filtre par titre
     * @param editeur   Filtre par éditeur
     * @param auteur    Filtre par auteur
     * @param categorie Filtre par catégorie
     * @return Liste filtrée des livres
     */
    public List<Livre> searchLivres(String titre, String editeur, String auteur, String categorie) {
        return livreDAO.searchLivres(titre, editeur, auteur, categorie);
    }

    /**
     * Récupère un livre par son identifiant.
     * 
     * @param id Identifiant du livre
     * @return Livre correspondant ou null
     */
    public Livre getLivreById(int id) {
        return livreDAO.getLivreById(id);
    }

    /**
     * Met à jour un livre existant.
     * 
     * @param livre Livre avec nouvelles données
     */
    public void updateLivre(Livre livre) {
        livreDAO.updateLivre(livre);
    }

    /**
     * Supprime un livre selon son identifiant.
     * 
     * @param id Identifiant du livre à supprimer
     */
    public void deleteLivre(int id) {
        livreDAO.deleteLivre(id);
    }
}
