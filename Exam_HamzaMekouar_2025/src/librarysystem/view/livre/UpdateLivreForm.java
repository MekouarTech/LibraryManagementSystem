package librarysystem.view.livre;

import javax.swing.*;
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
 * Formulaire moderne pour modifier un "Livre" existant
 * 
 * @author Hamza Mekouar
 */
public class UpdateLivreForm extends JDialog {

    private JTextField txtTitre;
    private JTextField txtAnnee;
    private JTextField txtNbrExemplaires;
    private JComboBox<EditeurItem> cmbEditeur;
    private JList<AuteurItem> listAuteurs;
    private JList<CategorieItem> listCategories;
    private JButton btnModifier;

    private LivreController controller;
    private AuteurDao auteurDAO;
    private CategorieDao categorieDAO;
    private EditeurDao editeurDAO;
    private Livre livre;

    public UpdateLivreForm(JFrame parent, LivreController controller, AuteurDao auteurDAO,
                           CategorieDao categorieDAO, EditeurDao editeurDAO, Livre livre) {
        super(parent, "✏️ Modifier un Livre", true);
        this.controller = controller;
        this.auteurDAO = auteurDAO;
        this.categorieDAO = categorieDAO;
        this.editeurDAO = editeurDAO;
        this.livre = livre;

        setSize(520, 530);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        initForm();
    }

    private void initForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(250, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("📖 Titre:"), gbc);
        gbc.gridx = 1;
        txtTitre = new JTextField(livre.getTitre());
        txtTitre.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtTitre, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("📅 Année Publication:"), gbc);
        gbc.gridx = 1;
        txtAnnee = new JTextField(String.valueOf(livre.getAnneePublication()));
        txtAnnee.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtAnnee, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("🔢 Nombre d'exemplaires:"), gbc);
        gbc.gridx = 1;
        txtNbrExemplaires = new JTextField(String.valueOf(livre.getNbrExemplaires()));
        txtNbrExemplaires.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNbrExemplaires, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("🏢 Éditeur:"), gbc);
        gbc.gridx = 1;
        List<EditeurItem> editeurItems = editeurDAO.getAllEditeurs().stream().map(EditeurItem::new).collect(Collectors.toList());
        cmbEditeur = new JComboBox<>(editeurItems.toArray(new EditeurItem[0]));
        cmbEditeur.setPreferredSize(new Dimension(200, 30));
        for (int i = 0; i < cmbEditeur.getItemCount(); i++) {
            if (cmbEditeur.getItemAt(i).getEditeur().getId() == livre.getEditeur().getId()) {
                cmbEditeur.setSelectedIndex(i);
                break;
            }
        }
        formPanel.add(cmbEditeur, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("👤 Auteurs:"), gbc);
        gbc.gridx = 1;
        List<AuteurItem> auteurItems = auteurDAO.getAllAuteurs().stream().map(AuteurItem::new).collect(Collectors.toList());
        listAuteurs = new JList<>(auteurItems.toArray(new AuteurItem[0]));
        listAuteurs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane auteurScroll = new JScrollPane(listAuteurs);
        auteurScroll.setPreferredSize(new Dimension(200, 70));
        formPanel.add(auteurScroll, gbc);
        livre.getAuteurs().forEach(a -> {
            for (int i = 0; i < auteurItems.size(); i++) {
                if (auteurItems.get(i).getAuteur().getId() == a.getId()) {
                    listAuteurs.addSelectionInterval(i, i);
                }
            }
        });

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("🏷️ Catégories:"), gbc);
        gbc.gridx = 1;
        List<CategorieItem> categorieItems = categorieDAO.getAllCategories().stream().map(CategorieItem::new).collect(Collectors.toList());
        listCategories = new JList<>(categorieItems.toArray(new CategorieItem[0]));
        listCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane categorieScroll = new JScrollPane(listCategories);
        categorieScroll.setPreferredSize(new Dimension(200, 70));
        formPanel.add(categorieScroll, gbc);
        livre.getCategories().forEach(c -> {
            for (int i = 0; i < categorieItems.size(); i++) {
                if (categorieItems.get(i).getCategorie().getId() == c.getId()) {
                    listCategories.addSelectionInterval(i, i);
                }
            }
        });

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnModifier = new JButton("💾 Modifier");
        btnModifier.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnModifier.setFocusPainted(false);
        formPanel.add(btnModifier, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnModifier.addActionListener((ActionEvent e) -> {
            String titre = txtTitre.getText().trim();
            String anneeStr = txtAnnee.getText().trim();
            String nbrStr = txtNbrExemplaires.getText().trim();

            if (titre.isEmpty() || anneeStr.isEmpty() || nbrStr.isEmpty() ||
                    listAuteurs.getSelectedValuesList().isEmpty() || listCategories.getSelectedValuesList().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int annee = Integer.parseInt(anneeStr);
            int nbrExemplaires = Integer.parseInt(nbrStr);
            EditeurItem selectedEditeurItem = (EditeurItem) cmbEditeur.getSelectedItem();
            Editeur selectedEditeur = selectedEditeurItem != null ? selectedEditeurItem.getEditeur() : null;

            livre.setTitre(titre);
            livre.setAnneePublication(annee);
            livre.setNbrExemplaires(nbrExemplaires);
            livre.setEditeur(selectedEditeur);
            livre.setAuteurs(listAuteurs.getSelectedValuesList().stream().map(AuteurItem::getAuteur).collect(Collectors.toList()));
            livre.setCategories(listCategories.getSelectedValuesList().stream().map(CategorieItem::getCategorie).collect(Collectors.toList()));

            controller.updateLivre(livre);
            dispose();
        });
    }
}
