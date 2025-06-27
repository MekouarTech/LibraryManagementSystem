package librarysystem.view.auteur;

import librarysystem.controller.AuteurController;
import librarysystem.model.Auteur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Formulaire pour afficher, rechercher et gérer les Auteurs dans un tableau.
 * CRUD avec pagination et exportation CSV.
 * @author Hamza Mekouar
 */

public class ListAuteurForm extends JFrame {
    private JTable table;
    private AuteurTableModel tableModel;
    private JTextField searchField;
    private JButton btnPrevious, btnNext, btnAdd, btnUpdate, btnDelete, btnDetails, btnExport;
    private int currentPage = 0;
    private final int PAGE_SIZE = 15;

    private final AuteurController auteurController;

    public ListAuteurForm(AuteurController controller) {
        this.auteurController = controller;

        setTitle("👤 Liste des Auteurs");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
        setVisible(true);
    }

    private void initComponents() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchPanel.setBackground(new Color(240, 248, 255));
        searchField = new JTextField(20);
        JButton btnSearch = new JButton("🔍 Rechercher");
        btnSearch.addActionListener(e -> {
            currentPage = 0;
            loadData();
        });
        searchPanel.add(new JLabel("Nom:"));
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);

        tableModel = new AuteurTableModel();
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(173, 216, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(220, 220, 220));

        TableRowSorter<AuteurTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 245));

        btnPrevious = new JButton("⬅️ Précédent");
        btnNext = new JButton("➡️ Suivant");
        btnAdd = new JButton("➕ Ajouter");
        btnUpdate = new JButton("✏️ Modifier");
        btnDelete = new JButton("❌ Supprimer");
        btnDetails = new JButton("ℹ️ Détails");
        btnExport = new JButton("📤 Exporter CSV");

        JButton[] buttons = {btnPrevious, btnNext, btnAdd, btnUpdate, btnDelete, btnDetails, btnExport};
        for (JButton btn : buttons) {
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 13));
            bottomPanel.add(btn);
        }

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnPrevious.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadData();
            }
        });

        btnNext.addActionListener(e -> {
            currentPage++;
            loadData();
        });

        btnAdd.addActionListener(e -> {
            AddAuteurForm addForm = new AddAuteurForm(this, auteurController);
            addForm.setVisible(true);
            loadData();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un auteur.");
                return;
            }
            Auteur selected = tableModel.getAuteurAt(table.convertRowIndexToModel(row));
            UpdateAuteurForm updateForm = new UpdateAuteurForm(this, auteurController, selected);
            updateForm.setVisible(true);
            loadData();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un auteur.");
                return;
            }
            Auteur selected = tableModel.getAuteurAt(table.convertRowIndexToModel(row));
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Supprimer l'auteur " + selected.getNom() + " ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                auteurController.deleteAuteur(selected.getId());
                loadData();
            }
        });

        btnDetails.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                Auteur auteur = tableModel.getAuteurAt(table.convertRowIndexToModel(row));
                JOptionPane.showMessageDialog(this, auteur.toString(), "Détails de l'Auteur", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnExport.addActionListener(e -> exportTableToCSV());
    }

    private void loadData() {
        String nom = searchField.getText().trim().toLowerCase();
        List<Auteur> allAuteurs = auteurController.getAllAuteurs();
        List<Auteur> filtered = allAuteurs.stream()
                .filter(a -> a.getNom().toLowerCase().contains(nom) || a.getPrenom().toLowerCase().contains(nom)
                		||a.getFullName().toLowerCase().contains(nom) )
                .collect(Collectors.toList());

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, filtered.size());
        if (start > end) {
            currentPage = 0;
            start = 0;
            end = Math.min(PAGE_SIZE, filtered.size());
        }

        List<Auteur> page = filtered.subList(start, end);
        tableModel.setAuteurs(page);

        btnPrevious.setEnabled(currentPage > 0);
        btnNext.setEnabled((currentPage + 1) * PAGE_SIZE < filtered.size());
    }

    private void exportTableToCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("auteurs.csv"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(chooser.getSelectedFile())) {
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.write(tableModel.getColumnName(i));
                    if (i != tableModel.getColumnCount() - 1) writer.write(",");
                }
                writer.write("\n");

                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        writer.write(String.valueOf(tableModel.getValueAt(row, col)));
                        if (col != tableModel.getColumnCount() - 1) writer.write(",");
                    }
                    writer.write("\n");
                }
                JOptionPane.showMessageDialog(this, "Exportation réussie !");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erreur d'exportation: " + e.getMessage());
            }
        }
    }
}
