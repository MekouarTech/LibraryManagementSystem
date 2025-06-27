package librarysystem.view.categorie;

import librarysystem.model.Categorie;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * TableModel to display Categorie (Category) data in a JTable.
 * 
 * @author Hamza Mekouar
 */
public class CategorieTableModel extends AbstractTableModel {

    private final String[] columnNames = {
        "ID", "Nom"
    };

    private List<Categorie> categories;

    /**
     * Sets the list of categories to display and refreshes the table.
     *
     * @param categories list of Categorie objects
     */
    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return categories != null ? categories.size() : 0;
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
        Categorie categorie = categories.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> categorie.getId();
            case 1 -> categorie.getNom();
            default -> null;
        };
    }

    /**
     * Retrieves the Categorie object for a specific row.
     *
     * @param rowIndex selected row index
     * @return the corresponding Categorie object
     */
    public Categorie getCategorieAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < categories.size()) {
            return categories.get(rowIndex);
        }
        return null;
    }
}
