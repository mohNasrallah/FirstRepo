package model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class BillTableModel extends AbstractTableModel{
  private List<Bill> bills;
  private String[] columns = {"No.", "Date", "Customer", "Total"};

    public BillTableModel(List<Bill> bills) {
        this.bills = bills;
    }

  
    @Override
    public int getRowCount() {
           return bills.size();
    }

    @Override
    public int getColumnCount() {
            return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    @Override
    public Object getValueAt(int i, int i1) {
         Bill bill = bills.get(i);
        
        switch (i1) {
            case 0: return bill.getNum();
            case 1: return bill.getDate();
            case 2: return bill.getCustomer();
            case 3: return bill.getBillTotal();
            default : return "";
        }
    }
    
}
