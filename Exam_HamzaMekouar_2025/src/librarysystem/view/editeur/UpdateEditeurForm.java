package librarysystem.view.editeur;

import librarysystem.controller.EditeurController;
import librarysystem.model.Editeur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Formulaire moderne pour modifier un éditeur existant.
 * Hamza Mekouar
 */
public class UpdateEditeurForm extends JDialog {

    private JTextField txtNom;
    private JButton btnModifier;

    private EditeurController controller;
    private Editeur editeur;

    public UpdateEditeurForm(JFrame parent, EditeurController controller, Editeur editeur) {
        super(parent, "✏️ Modifier un Éditeur", true);
        this.controller = controller;
        this.editeur = editeur;

        setSize(400, 180);
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
        formPanel.add(new JLabel("Nom de l'Éditeur:"), gbc);

        gbc.gridx = 1;
        txtNom = new JTextField(editeur.getNom());
        txtNom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNom, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnModifier = new JButton("💾 Modifier");
        btnModifier.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnModifier.setFocusPainted(false);
        formPanel.add(btnModifier, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnModifier.addActionListener((ActionEvent e) -> {
            String nom = txtNom.getText().trim();

            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer le nom de l'éditeur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            editeur.setNom(nom);
            controller.updateEditeur(editeur);
            dispose();
        });
    }
}
