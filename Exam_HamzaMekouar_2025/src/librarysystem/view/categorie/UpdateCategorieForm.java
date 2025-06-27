package librarysystem.view.categorie;

import librarysystem.controller.CategorieController;
import librarysystem.model.Categorie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Formulaire pour modifier une catégorie existante.
 * Hamza Mekouar
 */
public class UpdateCategorieForm extends JDialog {

    private JTextField txtNom;
    private JButton btnModifier;

    private final CategorieController controller;
    private final Categorie categorie;

    public UpdateCategorieForm(JFrame parent, CategorieController controller, Categorie categorie) {
        super(parent, "✏️ Modifier une Catégorie", true);
        this.controller = controller;
        this.categorie = categorie;

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

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
        txtNom = new JTextField(categorie.getNom());
        txtNom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNom, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnModifier = new JButton("💾 Modifier");
        btnModifier.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnModifier.setFocusPainted(false);
        formPanel.add(btnModifier, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnModifier.addActionListener((ActionEvent e) -> {
            String nom = txtNom.getText().trim();

            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer le nom de la catégorie.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            categorie.setNom(nom);
            controller.updateCategorie(categorie);
            dispose();
        });
    }
}
