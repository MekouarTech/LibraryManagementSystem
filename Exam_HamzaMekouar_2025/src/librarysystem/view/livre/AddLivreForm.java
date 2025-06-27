package librarysystem.view.livre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import librarysystem.controller.LivreController;
import librarysystem.dao.AuteurDao;
import librarysystem.dao.CategorieDao;
import librarysystem.dao.EditeurDao;
import librarysystem.model.Editeur;
import librarysystem.model.Livre;
import librarysystem.util.AuteurItem;
import librarysystem.util.CategorieItem;
import librarysystem.util.EditeurItem;

/**
 * Formulaire pour ajouter un nouveau Livre.
 * Gère les relations plusieurs-à-plusieurs avec auteurs et catégories.
 * 
 * Auteur : Hamza Mekouar
 */
public class AddLivreForm extends JDialog {

    private JTextField txtTitre;
    private JTextField txtAnnee;
    private JTextField txtNbrExemplaires;
    private JComboBox<EditeurItem> cmbEditeur;
    private JList<AuteurItem> listAuteurs;
    private JList<CategorieItem> listCategories;
    private JButton btnAjouter;

    private LivreController controller;
    private AuteurDao auteurDAO;
    private CategorieDao categorieDAO;
    private EditeurDao editeurDAO;

    public AddLivreForm(JFrame parent, LivreController controller, AuteurDao auteurDAO, CategorieDao categorieDAO, EditeurDao editeurDAO) {
        super(parent, "📘 Ajouter un Livre", true);
        this.controller = controller;
        this.auteurDAO = auteurDAO;
        this.categorieDAO = categorieDAO;
        this.editeurDAO = editeurDAO;

        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        initForm();
    }

    private void initForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("📘 Titre:"), gbc);
        gbc.gridx = 1;
        txtTitre = new JTextField();
        txtTitre.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtTitre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("📅 Année Publication:"), gbc);
        gbc.gridx = 1;
        txtAnnee = new JTextField();
        txtAnnee.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtAnnee, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("📦 Nombre d'exemplaires:"), gbc);
        gbc.gridx = 1;
        txtNbrExemplaires = new JTextField();
        txtNbrExemplaires.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNbrExemplaires, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("🏢 Éditeur:"), gbc);
        gbc.gridx = 1;
        cmbEditeur = new JComboBox<>();
        cmbEditeur.addItem(null);
        editeurDAO.getAllEditeurs().stream().map(EditeurItem::new).forEach(cmbEditeur::addItem);
        cmbEditeur.setPreferredSize(new Dimension(200, 30));
        formPanel.add(cmbEditeur, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("👤 Auteurs:"), gbc);
        gbc.gridx = 1;
        listAuteurs = new JList<>(auteurDAO.getAllAuteurs().stream().map(AuteurItem::new).toArray(AuteurItem[]::new));
        listAuteurs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane auteurScroll = new JScrollPane(listAuteurs);
        auteurScroll.setPreferredSize(new Dimension(200, 80));
        formPanel.add(auteurScroll, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("🏷️ Catégories:"), gbc);
        gbc.gridx = 1;
        listCategories = new JList<>(categorieDAO.getAllCategories().stream().map(CategorieItem::new).toArray(CategorieItem[]::new));
        listCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane categorieScroll = new JScrollPane(listCategories);
        categorieScroll.setPreferredSize(new Dimension(200, 80));
        formPanel.add(categorieScroll, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnAjouter = new JButton("➕ Ajouter");
        btnAjouter.setFocusPainted(false);
        btnAjouter.setFont(new Font("SansSerif", Font.BOLD, 13));
        formPanel.add(btnAjouter, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnAjouter.addActionListener((ActionEvent e) -> {
            String titre = txtTitre.getText().trim();
            String anneeStr = txtAnnee.getText().trim();
            String nbrStr = txtNbrExemplaires.getText().trim();

            if (titre.isEmpty() || anneeStr.isEmpty() || nbrStr.isEmpty() ||
                listAuteurs.getSelectedValuesList().isEmpty() || listCategories.getSelectedValuesList().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int annee = Integer.parseInt(anneeStr);
                int nbrExemplaires = Integer.parseInt(nbrStr);

                EditeurItem selectedEditeurItem = (EditeurItem) cmbEditeur.getSelectedItem();
                Editeur selectedEditeur = selectedEditeurItem != null ? selectedEditeurItem.getEditeur() : null;

                Livre livre = new Livre(titre, annee, nbrExemplaires, selectedEditeur);
                livre.setAuteurs(listAuteurs.getSelectedValuesList().stream()
                        .map(AuteurItem::getAuteur).collect(Collectors.toList()));
                livre.setCategories(listCategories.getSelectedValuesList().stream()
                        .map(CategorieItem::getCategorie).collect(Collectors.toList()));

                controller.addLivre(livre);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Année ou nombre d'exemplaires invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
