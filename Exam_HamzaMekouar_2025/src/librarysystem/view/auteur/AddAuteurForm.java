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
 * Formulaire pour ajouter un auteur.
 * Hamza Mekouar
 */
public class AddAuteurForm extends JDialog {
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtNationalite;
    private JTextField txtDateNaissance;
    private JButton btnAjouter;

    private AuteurController controller;

    public AddAuteurForm(JFrame parent, AuteurController controller) {
        super(parent, "👤 Ajouter un Auteur", true);
        this.controller = controller;

        setSize(500, 500);
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
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        txtNom = new JTextField();
        txtNom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNom, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        txtPrenom = new JTextField();
        txtPrenom.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtPrenom, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Nationalité:"), gbc);
        gbc.gridx = 1;
        txtNationalite = new JTextField();
        txtNationalite.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtNationalite, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Date de naissance (yyyy-mm-dd):"), gbc);
        gbc.gridx = 1;
        txtDateNaissance = new JTextField();
        txtDateNaissance.setPreferredSize(new Dimension(200, 30));
        formPanel.add(txtDateNaissance, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnAjouter = new JButton("➕ Ajouter");
        btnAjouter.setFocusPainted(false);
        btnAjouter.setFont(new Font("SansSerif", Font.BOLD, 13));
        formPanel.add(btnAjouter, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnAjouter.addActionListener((ActionEvent e) -> {
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
                Auteur auteur = new Auteur(nom, prenom, nationalite, dateNaissance);
                controller.addAuteur(auteur);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Format de date invalide (attendu: yyyy-mm-dd).", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
