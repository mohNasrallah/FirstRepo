package model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel{
    private List<Item> items;
    private String[] columns = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public ItemTableModel(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
    
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int x) {
        return columns[x];
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Item item = items.get(i);
        
        switch(i1) {
            case 0: return item.getBill().getNum();
            case 1: return item.getItemName();
            case 2: return item.getItemPrice();
            case 3: return item.getItemCount();
            case 4: return item.getItemTotal();
            default : return "";
        }
    }

}
