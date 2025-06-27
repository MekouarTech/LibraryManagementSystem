package librarysystem.view.livre;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import librarysystem.model.Livre;
import java.util.stream.Collectors;

/**
 * TableModel for display Livre Data In JTable.
 * 
 * @author Hamza Mekouar
 */
public class LivreTableModel extends AbstractTableModel {
    private final String[] columnNames = {
        "ID", "Titre", "Année", "Exemplaires", "Éditeur", "Auteurs", "Catégories"
    };

    private List<Livre> livres;

    public LivreTableModel() {
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return livres != null ? livres.size() : 0;
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
        Livre livre = livres.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> livre.getId();
            case 1 -> livre.getTitre();
            case 2 -> livre.getAnneePublication();
            case 3 -> livre.getNbrExemplaires();
            case 4 -> livre.getEditeur() != null ? livre.getEditeur().getNom() : "";
            case 5 -> livre.getAuteurs() != null
                        ? livre.getAuteurs().stream().map(a -> a.getPrenom() + " " + a.getNom()).collect(Collectors.joining(", "))
                        : "";
            case 6 -> livre.getCategories() != null
                        ? livre.getCategories().stream().map(c -> c.getNom()).collect(Collectors.joining(", "))
                        : "";
            default -> null;
        };
    }
    
    /**
     * 
     * @param rowIndex selected row
     * @return Livre object or null if its not valid
     */
    public Livre getLivreAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < livres.size()) {
            return livres.get(rowIndex);
        }
        return null;
    }
}
