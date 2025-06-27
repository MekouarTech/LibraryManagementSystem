package librarysystem.util;

import librarysystem.model.Categorie;

/**
 * Categorie Item class for JComboBox
 * 
 * @author Hamza Mekouar
 */
public class CategorieItem {

	private final Categorie categorie;

    public CategorieItem(Categorie categorie) {
        this.categorie = categorie;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        return categorie.getNom();
    }
}
