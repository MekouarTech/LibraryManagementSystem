package librarysystem;

import librarysystem.util.DBConnection;
import librarysystem.view.MainWindow;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import librarysystem.dao.LivreDao;
import librarysystem.dao.AuteurDao;
import librarysystem.dao.EditeurDao;
import librarysystem.dao.CategorieDao;

import librarysystem.dao.impl.LivreDaoImpl;
import librarysystem.dao.impl.AuteurDaoImpl;
import librarysystem.dao.impl.EditeurDaoImpl;
import librarysystem.dao.impl.CategorieDaoImpl;

import librarysystem.model.Livre;
import librarysystem.model.Auteur;
import librarysystem.model.Editeur;
import librarysystem.model.Categorie;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Hamza Mekouar
 */
public class Main {
	
	private static void TesterLivre() throws SQLException {
		LivreDao livreDao = new LivreDaoImpl();

        // 1. Test addLivre
        Livre newLivre = new Livre();
        newLivre.setTitre("Test Driven Development");
        newLivre.setAnneePublication(2020);
        newLivre.setNbrExemplaires(5);

        // Set Editeur (assuming ID 1 exists)
        Editeur editeur = new Editeur(1,"Gallimard");
        newLivre.setEditeur(editeur);


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		List<Auteur> auteurs = new ArrayList<>();
		
		Auteur auteur1 = new Auteur(1,"Saint-Exupéry","Antoine de","Française", LocalDate.parse("1900-06-29",formatter));
		
		Auteur auteur2 = new Auteur(2,"Orwell","George","Britannique", LocalDate.parse("1903-06-25",formatter));
        auteurs.add(auteur1);
        auteurs.add(auteur2);
        newLivre.setAuteurs(auteurs);

     // Set Categories (assuming ID 1 exists)
        List<Categorie> categories = new ArrayList<>();
        Categorie cat1 = new Categorie();
        cat1.setId(1);
        categories.add(cat1);
        newLivre.setCategories(categories);
        /*
        livreDao.addLivre(newLivre);
        System.out.println("Added Livre with ID: " + newLivre.getId());
*/
        // 2. Test getLivreById
        Livre foundLivre = livreDao.getLivreById(10);//newLivre.getId());
        System.out.println("Found Livre by ID: " + foundLivre.getTitre());
  /*   
        // 3. Test getAllLivres
        List<Livre> allLivres = livreDao.getAllLivres();
        System.out.println("All Livres in DB:");
        for (Livre l : allLivres) {
            System.out.println("- " + l.toString());
        }
/**/
        // 4. Test updateLivre
        foundLivre.setNbrExemplaires(100);
        livreDao.updateLivre(foundLivre);
        System.out.println("Updated Livre's nbrExemplaires to 100.");

        // 5. Test searchLivres by title and auteur
        List<Livre> searchedLivres = livreDao.searchLivres("Driven", null, "Orwell", null);
        System.out.println("Search results:");
        for (Livre l : searchedLivres) {
            System.out.println("- " + l.toString());
        }

        // 6. Test deleteLivre
        livreDao.deleteLivre(newLivre.getId());
        System.out.println("Deleted Livre with ID: " + newLivre.getId());

        // Verify delete
        Livre deletedLivre = livreDao.getLivreById(newLivre.getId());
        System.out.println("After deletion, Livre is: " + (deletedLivre == null ? "null" : "still exists"));
	}
	
	private static void TesterAuteur() throws SQLException {
	    AuteurDao auteurDao = new AuteurDaoImpl();

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    // 1. Test addAuteur
	    Auteur newAuteur = new Auteur();
	    newAuteur.setNom("Tolkien");
	    newAuteur.setPrenom("J.R.R.");
	    newAuteur.setNationalite("Britannique");
	    newAuteur.setDateNaissance(LocalDate.parse("1892-01-03", formatter));

	    auteurDao.addAuteur(newAuteur);
	    System.out.println("Added Auteur: " + newAuteur.getNom() + " " + newAuteur.getPrenom());

	    // 2. Test getAuteurById (Assume the latest ID is highest, find it manually if needed)
	    List<Auteur> allAuteurs = auteurDao.getAllAuteurs();
	    Auteur lastAuteur = allAuteurs.get(allAuteurs.size() - 1);
	    int lastId = lastAuteur.getId();
	    Auteur foundAuteur = auteurDao.getAuteurById(lastId);
	    System.out.println("Found Auteur by ID: " + foundAuteur);

	    // 3. Test updateAuteur
	    foundAuteur.setNationalite("Anglaise");
	    auteurDao.updateAuteur(foundAuteur);
	    System.out.println("Updated Auteur's nationalité to Anglaise.");

	    // 4. Test getAllAuteurs
	    allAuteurs = auteurDao.getAllAuteurs();
	    System.out.println("All Auteurs in DB:");
	    for (Auteur a : allAuteurs) {
	        System.out.println("- " + a);
	    }

	    // 5. Test searchAuteursByNom
	    List<Auteur> searched = auteurDao.searchAuteursByNom("Tol");
	    System.out.println("Search results for 'Tol':");
	    for (Auteur a : searched) {
	        System.out.println("- " + a);
	    }

	    // 6. Test deleteAuteur
	    auteurDao.deleteAuteur(lastId);
	    System.out.println("Deleted Auteur with ID: " + lastId);

	    // Verify deletion
	    Auteur deleted = auteurDao.getAuteurById(lastId);
	    System.out.println("After deletion, Auteur is: " + (deleted == null ? "null" : "still exists"));
	}
	
	private static void TesterEditeur() throws SQLException {
	    EditeurDao editeurDao = new EditeurDaoImpl();

	    // 1. Test addEditeur
	    Editeur newEditeur = new Editeur();
	    newEditeur.setNom("Hachette Livre");

	    editeurDao.addEditeur(newEditeur);
	    System.out.println("Added Editeur with ID: " + newEditeur.getId());

	    // 2. Test getEditeurById
	    Editeur foundEditeur = editeurDao.getEditeurById(newEditeur.getId());
	    if (foundEditeur != null) {
	        System.out.println("Found Editeur: " + foundEditeur.getNom());
	    } else {
	        System.out.println("Editeur not found.");
	    }

	    // 3. Test getAllEditeurs
	    List<Editeur> allEditeurs = editeurDao.getAllEditeurs();
	    System.out.println("All Editeurs in DB:");
	    for (Editeur e : allEditeurs) {
	        System.out.println("- " + e);
	    }

	    // 4. Test updateEditeur
	    foundEditeur.setNom("Hachette Updated");
	    editeurDao.updateEditeur(foundEditeur);
	    System.out.println("Updated Editeur name to: " + foundEditeur.getNom());

	    // 5. Test searchEditeursByName
	    List<Editeur> searched = editeurDao.searchEditeursByName("Hachette");
	    System.out.println("Search Results for 'Hachette':");
	    for (Editeur e : searched) {
	        System.out.println("- " + e);
	    }

	    // 6. Test deleteEditeur
	    editeurDao.deleteEditeur(newEditeur.getId());
	    System.out.println("Deleted Editeur with ID: " + newEditeur.getId());

	    // 7. Verify deletion
	    Editeur deleted = editeurDao.getEditeurById(newEditeur.getId());
	    System.out.println("After deletion, Editeur is: " + (deleted == null ? "null (deleted)" : "still exists"));
	}
	
	private static void TesterCategorie() throws SQLException {
	    CategorieDao categorieDao = new CategorieDaoImpl();

	    // 1. Test addCategorie
	    Categorie newCategorie = new Categorie();
	    newCategorie.setNom("Science Fiction");
	    categorieDao.addCategorie(newCategorie);
	    System.out.println("Added Categorie with ID: " + newCategorie.getId());

	    // 2. Test getCategorieById
	    Categorie foundCategorie = categorieDao.getCategorieById(newCategorie.getId());
	    if (foundCategorie != null) {
	        System.out.println("Found Categorie: " + foundCategorie.getNom());
	    } else {
	        System.out.println("Categorie not found by ID.");
	    }

	    // 3. Test getAllCategories
	    List<Categorie> allCategories = categorieDao.getAllCategories();
	    System.out.println("All Categories:");
	    for (Categorie c : allCategories) {
	        System.out.println("- " + c.getId() + ": " + c.getNom());
	    }

	    // 4. Test updateCategorie
	    foundCategorie.setNom("Sci-Fi Updated");
	    categorieDao.updateCategorie(foundCategorie);
	    System.out.println("Updated Categorie name to: " + foundCategorie.getNom());

	    // 5. Test searchCategoriesByName
	    List<Categorie> searchedCategories = categorieDao.searchCategoriesByName("Sci");
	    System.out.println("Search results for 'Sci':");
	    for (Categorie c : searchedCategories) {
	        System.out.println("- " + c.getId() + ": " + c.getNom());
	    }

	    // 6. Test deleteCategorie
	    categorieDao.deleteCategorie(newCategorie.getId());
	    System.out.println("Deleted Categorie with ID: " + newCategorie.getId());

	    // 7. Verify deletion
	    Categorie deletedCategorie = categorieDao.getCategorieById(newCategorie.getId());
	    System.out.println("After deletion, Categorie is: " + (deletedCategorie == null ? "null (deleted)" : "still exists"));
	}

	
    public static void main(String[] args) throws SQLException {
        try
        {
            Connection conn = DBConnection.getConnection();
            
            if (conn != null) {
                System.out.println("Connected to MySQL successfully!");
            }
            
        }
        catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        /*
        TesterLivre();
        
        System.out.println("\n--------------------------------------------------------------------------------------\n");
        
        TesterAuteur();
   
        System.out.println("\n--------------------------------------------------------------------------------------\n");
   
        TesterEditeur();

        System.out.println("\n--------------------------------------------------------------------------------------\n");
        
        TesterCategorie();
        
        */
        /*try {
            // Use system look and feel for modern appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        SwingUtilities.invokeLater(MainWindow::new);
        
    }
}
