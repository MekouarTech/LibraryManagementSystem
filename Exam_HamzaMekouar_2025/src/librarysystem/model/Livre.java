package librarysystem.model;

import java.util.List;

/**
 *
 * @author Hamza Mekouar
 */
public class Livre {

    private int id;
    private String titre;
    private int anneePublication;
    private int nbrExemplaires;
    private Editeur editeur;
    private List<Auteur> auteurs;
    private List<Categorie> categories;

    public Livre() {}

    public Livre(int id, String titre, int anneePublication, int nbrExemplaires, Editeur editeur) {
        this.id = id;
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.nbrExemplaires = nbrExemplaires;
        this.editeur = editeur;
    }
    

    public Livre(String titre, int anneePublication, int nbrExemplaires, Editeur editeur) {
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.nbrExemplaires = nbrExemplaires;
        this.editeur = editeur;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public int getNbrExemplaires() {
        return nbrExemplaires;
    }

    public void setNbrExemplaires(int nbrExemplaires) {
        this.nbrExemplaires = nbrExemplaires;
    }

    public Editeur getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeur editeur) {
        this.editeur = editeur;
    }

    public List<Auteur> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
    
    @Override
    public String toString() {
        return "Livre {" +
                "id = " + id +
                "titre = '" + titre + '\'' +
                "anneePublication = " + anneePublication +
                "nbrExemplaires = " + nbrExemplaires +
                "\n\t" + (editeur != null ? editeur.toString() : "null") +
                "\n\tauteurs :" + (auteurs != null ? auteurs.toString() : "[]") +
                "\n\tcategories :" + (categories != null ? categories.toString() : "[]") +
                "\n}";
    }
}
