package librarysystem.view.livre;

import librarysystem.controller.LivreController;
import librarysystem.dao.*;
import librarysystem.model.*;
import librarysystem.util.AuteurItem;
import librarysystem.util.EditeurItem;
import librarysystem.util.CategorieItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Formulaire moderne pour afficher, rechercher et gérer les livres dans un tableau.
 * CRUD avec pagination et exportation CSV.
 * @author Hamza Mekouar
 */
public class ListLivreForm extends JFrame {
    private JTable table;
    private LivreTableModel tableModel;
    private JTextField searchTitreField;

    private JComboBox<AuteurItem> auteurComboBox;
    private JComboBox<CategorieItem> categorieComboBox;
    private JComboBox<EditeurItem> editeurComboBox;

    private JButton btnPrevious, btnNext, btnAdd, btnUpdate, btnDelete, btnDetails, btnExport;
    private int currentPage = 0;
    private final int PAGE_SIZE = 15;

    private final LivreController livreController;
    private final AuteurDao auteurDao;
    private final CategorieDao categorieDao;
    private final EditeurDao editeurDao;

    public ListLivreForm(LivreController controller, AuteurDao auteurDao, CategorieDao categorieDao, EditeurDao editeurDao) {
        this.livreController = controller;
        this.auteurDao = auteurDao;
        this.categorieDao = categorieDao;
        this.editeurDao = editeurDao;

        setTitle("📚 Liste des Livres");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
        setVisible(true);
    }

    private void initComponents() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        searchPanel.add(new JLabel("🔎 Titre:"), gbc);
        gbc.gridx = 1;
        searchTitreField = new JTextField(15);
        searchPanel.add(searchTitreField, gbc);

        gbc.gridx = 2;
        searchPanel.add(new JLabel("👤 Auteur:"), gbc);
        gbc.gridx = 3;
        auteurComboBox = this.<Auteur, AuteurItem>getComboItems(auteurDao.getAllAuteurs(), AuteurItem::new);
        searchPanel.add(auteurComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        searchPanel.add(new JLabel("🏷️ Catégorie:"), gbc);
        gbc.gridx = 1;
        categorieComboBox = this.<Categorie, CategorieItem>getComboItems(categorieDao.getAllCategories(), CategorieItem::new);
        searchPanel.add(categorieComboBox, gbc);

        gbc.gridx = 2;
        searchPanel.add(new JLabel("🏢 Éditeur:"), gbc);
        gbc.gridx = 3;
        editeurComboBox = this.<Editeur, EditeurItem>getComboItems(editeurDao.getAllEditeurs(), EditeurItem::new);
        searchPanel.add(editeurComboBox, gbc);

        gbc.gridx = 4;
        JButton btnSearch = new JButton("🔍 Rechercher");
        btnSearch.addActionListener(e -> {
            currentPage = 0;
            loadData();
        });
        searchPanel.add(btnSearch, gbc);

        tableModel = new LivreTableModel();
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

        TableRowSorter<LivreTableModel> sorter = new TableRowSorter<>(tableModel);
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
            AddLivreForm addForm = new AddLivreForm(this, livreController, auteurDao, categorieDao, editeurDao);
            addForm.setVisible(true);
            loadData();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre.");
                return;
            }
            Livre selected = tableModel.getLivreAt(table.convertRowIndexToModel(row));
            UpdateLivreForm updateForm = new UpdateLivreForm(this, livreController, auteurDao, categorieDao, editeurDao, selected);
            updateForm.setVisible(true);
            loadData();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre.");
                return;
            }
            Livre selected = tableModel.getLivreAt(table.convertRowIndexToModel(row));
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Supprimer le livre \"" + selected.getTitre() + "\" ?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                livreController.deleteLivre(selected.getId());
                loadData();
            }
        });

        btnDetails.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre.");
                return;
            }
            Livre selected = tableModel.getLivreAt(table.convertRowIndexToModel(row));
            JOptionPane.showMessageDialog(this, selected.toString(), "Détails du Livre", JOptionPane.INFORMATION_MESSAGE);
        });

        btnExport.addActionListener(e -> exportTableToCSV());
    }

    private void loadData() {
        String titre = searchTitreField.getText().trim().toLowerCase();
        AuteurItem selectedAuteurItem = (AuteurItem) auteurComboBox.getSelectedItem();
        CategorieItem selectedCategorieItem = (CategorieItem) categorieComboBox.getSelectedItem();
        EditeurItem selectedEditeurItem = (EditeurItem) editeurComboBox.getSelectedItem();

        List<Livre> allLivres = livreController.getLivres();
        List<Livre> filtered = allLivres.stream()
            .filter(l -> l.getTitre().toLowerCase().contains(titre))
            .filter(l -> selectedAuteurItem == null || l.getAuteurs().stream()
                .anyMatch(a -> a.getId() == selectedAuteurItem.getAuteur().getId()))
            .filter(l -> selectedCategorieItem == null || l.getCategories().stream()
                .anyMatch(c -> c.getId() == selectedCategorieItem.getCategorie().getId()))
            .filter(l -> selectedEditeurItem == null || (l.getEditeur() != null &&
                l.getEditeur().getId() == selectedEditeurItem.getEditeur().getId()))
            .collect(Collectors.toList());

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, filtered.size());
        if (start > end) {
            currentPage = 0;
            start = 0;
            end = Math.min(PAGE_SIZE, filtered.size());
        }

        List<Livre> page = filtered.subList(start, end);
        tableModel.setLivres(page);

        btnPrevious.setEnabled(currentPage > 0);
        btnNext.setEnabled((currentPage + 1) * PAGE_SIZE < filtered.size());
    }

    private void exportTableToCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("livres.csv"));
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

    private <T, U> JComboBox<U> getComboItems(List<T> items, java.util.function.Function<T, U> mapper) {
        JComboBox<U> comboBox = new JComboBox<>();
        comboBox.addItem(null);
        items.stream().map(mapper).forEach(comboBox::addItem);
        return comboBox;
    }
}
