package librarysystem.view.editeur;

import librarysystem.model.Editeur;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * TableModel to display Editeur (Editor) data in a JTable.
 * 
 * @author Hamza
 */
public class EditeurTableModel extends AbstractTableModel {

    private final String[] columnNames = {
        "ID", "Nom"
    };

    private List<Editeur> editeurs;

    public void setEditeurs(List<Editeur> editeurs) {
        this.editeurs = editeurs;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return editeurs != null ? editeurs.size() : 0;
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
        Editeur editeur = editeurs.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> editeur.getId();
            case 1 -> editeur.getNom();
            default -> null;
        };
    }

    /**
     * Retrieves the Editeur object for a specific row.
     *
     * @param rowIndex selected row index
     * @return the corresponding Editeur object
     */
    public Editeur getEditeurAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < editeurs.size()) {
            return editeurs.get(rowIndex);
        }
        return null;
    }
}
