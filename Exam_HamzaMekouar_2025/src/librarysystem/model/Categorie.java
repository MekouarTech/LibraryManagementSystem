package librarysystem.model;

/**
 *
 * @author Hamza Mekouar
 */
public class Categorie {

    private int id;
    private String nom;

    public Categorie() {}

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "\nCategorie{" +
                "\nid = " + id +
                "\nnom = '" + nom + '\'' +
                "\n}\n------------------------------";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie)) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
