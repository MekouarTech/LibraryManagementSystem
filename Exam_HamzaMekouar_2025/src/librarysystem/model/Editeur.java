package librarysystem.model;

/**
 *
 * @author Hamza Mekouar
 */
public class Editeur {
	
	private int id;
	private String nom;
	
	public Editeur() {}

	public Editeur(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public Editeur(String nom) {
		this.nom = nom;
	}
	
	// Getters and Setters
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
    public String toString() {
        return "\nEditeur{" +
                "\nid = " + this.id +
                "\nnom = '" + this.nom +
                "\n}\n------------------------------";
    }

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Editeur)) return false;
	    Editeur editeur = (Editeur) o;
	    return id == editeur.id;
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(id);
	}

}
