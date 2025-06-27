package librarysystem.util;

import librarysystem.model.Auteur;

/**
 * Item class for JComboBox
 * 
 * @author Hamza Mekouar
 */
public class AuteurItem {
    private final Auteur auteur;

    public AuteurItem(Auteur auteur) {
        this.auteur = auteur;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    @Override
    public String toString() {
        return auteur.getNom();
    }
}
