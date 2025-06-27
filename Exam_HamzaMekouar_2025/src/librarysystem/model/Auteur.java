package librarysystem.model;

import java.time.LocalDate;

/**
 *
 * @author Hamza Mekouar
 */
public class Auteur {

    private int id;
    private String nom;
    private String prenom;
    private String nationalite;
    private LocalDate dateNaissance;

    public Auteur() {}

    public Auteur(int id, String nom, String prenom, String nationalite, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.dateNaissance = dateNaissance;
    }

    public Auteur(String nom, String prenom, String nationalite, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.dateNaissance = dateNaissance;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public String getFullName() {
    	return nom+" "+prenom;
    }

    @Override
    public String toString() {
        return "\nAuteur {" +
                "\nid = " + id +
                "\nnom = '" + nom + '\'' +
                "\nprenom = '" + prenom + '\'' +
                "\nnationalite = '" + nationalite + '\'' +
                "\ndateNaissance = " + dateNaissance +
                "\n}\n------------------------------";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auteur)) return false;
        Auteur auteur = (Auteur) o;
        return id == auteur.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
