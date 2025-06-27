package librarysystem.view.auteur;

import librarysystem.model.Auteur;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * TableModel to display Auteur (Author) data in a JTable.
 * 
 * @author Hamza
 */
public class AuteurTableModel extends AbstractTableModel {

    private final String[] columnNames = {
        "ID", "Nom", "Prénom", "Nationalité", "Date de Naissance"
    };

    private List<Auteur> auteurs;

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return auteurs != null ? auteurs.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Auteur auteur = auteurs.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> auteur.getId();
            case 1 -> auteur.getNom();
            case 2 -> auteur.getPrenom();
            case 3 -> auteur.getNationalite();
            case 4 -> auteur.getDateNaissance(); // Assumes LocalDate
            default -> null;
        };
    }

    /**
     * Retrieves the Auteur object for a specific row.
     *
     * @param rowIndex selected row index
     * @return the corresponding Auteur object
     */
    public Auteur getAuteurAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < auteurs.size()) {
            return auteurs.get(rowIndex);
        }
        return null;
    }
}
