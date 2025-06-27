package librarysystem.util;

import librarysystem.model.Editeur;

/**
 * Editeur Item class for JComboBox
 * 
 * @author Hamza Mekouar
 */
public class EditeurItem {
	
	private final Editeur editeur;

    public EditeurItem(Editeur editeur) {
        this.editeur = editeur;
    }

    public Editeur getEditeur() {
        return editeur;
    }

    @Override
    public String toString() {
        return editeur.getNom();
    }
}
