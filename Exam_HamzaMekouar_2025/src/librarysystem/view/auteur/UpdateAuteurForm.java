package librarysystem.view.auteur;

import librarysystem.controller.AuteurController;
import librarysystem.model.Auteur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Formulaire moderne pour modifier un auteur existant.
 * Hamza Mekouar
 */
public class UpdateAuteurForm extends JDialog {

    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtNationalite;
    private JTextField txtDateNaissance;
    private JButton btnModifier;

    private AuteurController controller;
    private Auteur auteur;

    public UpdateAuteurForm(JFrame parent, AuteurController controller, Auteur auteur) {
        super(parent, "✏️ Modifier un Auteur", true);
        this.controller = controller;
        this.auteur = auteur;

        setSize(500, 420);
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
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        txtNom = new JTextField(auteur.getNom());
        txtNom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNom, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        txtPrenom = new JTextField(auteur.getPrenom());
        txtPrenom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtPrenom, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Nationalité:"), gbc);
        gbc.gridx = 1;
        txtNationalite = new JTextField(auteur.getNationalite());
        txtNationalite.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNationalite, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Date de naissance (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        txtDateNaissance = new JTextField(String.valueOf(auteur.getDateNaissance()));
        txtDateNaissance.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtDateNaissance, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnModifier = new JButton("💾 Modifier");
        btnModifier.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnModifier.setFocusPainted(false);
        formPanel.add(btnModifier, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnModifier.addActionListener((ActionEvent e) -> {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String nationalite = txtNationalite.getText().trim();
            String dateStr = txtDateNaissance.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty() || nationalite.isEmpty() || dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate dateNaissance = LocalDate.parse(dateStr);
                auteur.setNom(nom);
                auteur.setPrenom(prenom);
                auteur.setNationalite(nationalite);
                auteur.setDateNaissance(dateNaissance);

                controller.updateAuteur(auteur);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-mm-dd.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
