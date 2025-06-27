package librarysystem.view;

import javax.swing.*;

import librarysystem.controller.*;
import librarysystem.dao.*;
import librarysystem.dao.impl.*;
import librarysystem.view.auteur.ListAuteurForm;
import librarysystem.view.livre.ListLivreForm;
import librarysystem.view.categorie.ListCategorieForm;
import librarysystem.view.editeur.ListEditeurForm;

import java.awt.*;

/**
 * Main application window for the Library Management System
 * 
 * @author Hamza Mekouar
 */
public class MainWindow extends JFrame {

	private final AuteurDao auteurDao = new AuteurDaoImpl();
	private final CategorieDao categorieDao = new CategorieDaoImpl();
	private final EditeurDao editeurDao = new EditeurDaoImpl();
	private final LivreController livreController = new LivreController(new LivreDaoImpl());
	private final AuteurController auteurController = new AuteurController(new AuteurDaoImpl());
	private final CategorieController categorieController = new CategorieController(new CategorieDaoImpl());
	private final EditeurController editeurController = new EditeurController(new EditeurDaoImpl());

	
    public MainWindow() {
        setTitle("Library System | Home");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("📖 Système de Gestion de Bibliothèque", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton btnLivres = createStyledButton("📘 Gérer les Livres");
        JButton btnAuteurs = createStyledButton("✍️ Gérer les Auteurs");
        JButton btnEditeurs = createStyledButton("🏢 Gérer les Éditeurs");
        JButton btnCategories = createStyledButton("📂 Gérer les Catégories");

        buttonPanel.add(btnLivres);
        buttonPanel.add(btnAuteurs);
        buttonPanel.add(btnEditeurs);
        buttonPanel.add(btnCategories);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("© 2025 Hamza Mekouar", SwingConstants.CENTER);
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        // Add action listeners
        //  "ListLivre"
        btnLivres.addActionListener(e -> {
            ListLivreForm form = new ListLivreForm(livreController, auteurDao, categorieDao,editeurDao);
            form.setVisible(true);
        });

        //  "ListAuteur"
        btnAuteurs.addActionListener(e ->{
        	ListAuteurForm form = new ListAuteurForm(auteurController);
        	form.setVisible(true);
        });
        

        //  "ListEditeur"
        btnEditeurs.addActionListener(e -> {
        	ListEditeurForm form = new ListEditeurForm(editeurController);
        	form.setVisible(true);
        });

        //  "ListCategorie"
        btnCategories.addActionListener(e -> {
        	ListCategorieForm form = new ListCategorieForm(categorieController);
        	form.setVisible(true);
        });

        
        add(mainPanel); 
        
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(63, 81, 181));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
