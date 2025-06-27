package librarysystem.view.categorie;

import librarysystem.controller.CategorieController;
import librarysystem.model.Categorie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Formulaire pour ajouter une catégorie.
 * Hamza Mekouar
 */
public class AddCategorieForm extends JDialog {
    private JTextField txtNom;
    private JButton btnAjouter;

    private final CategorieController controller;

    public AddCategorieForm(JFrame parent, CategorieController controller) {
        super(parent, "🏷️ Ajouter une Catégorie", true);
        this.controller = controller;

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        initForm();
    }

    private void initForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nom de la catégorie:"), gbc);
        gbc.gridx = 1;
        txtNom = new JTextField();
        txtNom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNom, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnAjouter = new JButton("➕ Ajouter");
        btnAjouter.setFocusPainted(false);
        btnAjouter.setFont(new Font("SansSerif", Font.BOLD, 13));
        formPanel.add(btnAjouter, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnAjouter.addActionListener((ActionEvent e) -> {
            String nom = txtNom.getText().trim();

            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer le nom de la catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Categorie categorie = new Categorie(nom);
            controller.addCategorie(categorie);
            dispose();
        });
    }
}
