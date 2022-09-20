package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Bill;
import model.BillTableModel;
import model.Item;
import model.ItemTableModel;
import test.view.BillDialog;
import test.view.BillForm;
import test.view.ItemDialog;

public class ActionController implements ActionListener, ListSelectionListener{
    
    private BillForm billForm; 
    private BillDialog billDialog;
    private ItemDialog itemDialog;
    
    public ActionController(BillForm billForm){
        this.billForm = billForm;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        System.out.println("Action " +action);
        
        switch (action) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Add Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
             case "createBillCancel":
                createBillCancel();
                break;
            case "createBillOK":
                createBillOK();
                break;
            case "createItemOk":
                createItemOK();
                break;
            case "createItemCancel":
                createItemCancel();
                break;
    }
    
}

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(billForm);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Bills have been read");
                
                List<Bill> bills = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] headerParts = headerLine.split(",");
                    int billNumber = Integer.parseInt(headerParts[0]);
                    String billDate = headerParts[1];
                    String customerName = headerParts[2];

                    Bill bill = new Bill(billNumber, billDate, customerName);
                    bills.add(bill);
                    
                    System.out.println("bills Size "+bills.toString());
                }

                result = fc.showOpenDialog(billForm);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File itemFile = fc.getSelectedFile();
                    Path itemPath = Paths.get(itemFile.getAbsolutePath());
                    List<String> items = Files.readAllLines(itemPath);
                    
                    for (String item : items) {
                        String lineParts[] = item.split(",");
                        int billNumber = Integer.parseInt(lineParts[0]);
                        String itemName = lineParts[1];
                        double itemPrice = Double.parseDouble(lineParts[2]);
                        int count = Integer.parseInt(lineParts[3]);
                        Bill bill = null;
                        for (Bill mBill : bills) {
                            if (mBill.getNum() == billNumber) {
                                bill = mBill;
                                break;
                            }
                        }

                        Item line = new Item(itemName, itemPrice, count, bill);
                        bill.getItems().add(line);
                    }
                    System.out.println("Check point "+bills.toString());
                }
                billForm.setBills(bills);
                
                BillTableModel billTableModel = new BillTableModel(bills); 
                billForm.setBillTableModel(billTableModel);
                billForm.getBillTable().setModel(billTableModel);
                billForm.getBillTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
       int selectedIndex = billForm.getBillTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            Bill currentBill = billForm.getBills().get(selectedIndex);
            billForm.getBillNumber().setText("" + currentBill.getNum());
            billForm.getBillDate().setText(currentBill.getDate());
            billForm.getBillCustomerName().setText(currentBill.getCustomer());
            billForm.getBillTotal().setText("" + currentBill.getBillTotal());
            ItemTableModel itemTableModel = new ItemTableModel(currentBill.getItems());
            billForm.getItemTable().setModel(itemTableModel);
            itemTableModel.fireTableDataChanged();
        }
    }

    private void deleteInvoice() {
         int selectedRow = billForm.getBillTable().getSelectedRow();
        if (selectedRow != -1) {
            billForm.getBills().remove(selectedRow);
            billForm.getBillTableModel().fireTableDataChanged();
        }
    }

    private void deleteItem() {
         int selectedRow = billForm.getItemTable().getSelectedRow();

        if (selectedRow != -1) {
            ItemTableModel itemTableModel = (ItemTableModel) billForm.getItemTable().getModel();
            itemTableModel.getItems().remove(selectedRow);
            itemTableModel.fireTableDataChanged();
            billForm.getBillTableModel().fireTableDataChanged();
        }
    }

    private void saveFile() {
        List<Bill> bills = billForm.getBills();
        String headers = "";
        String lines = "";
        for (Bill bill : bills) {
            String invCSV = bill.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (Item item : bill.getItems()) {
                String lineCSV = item.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(billForm);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(billForm);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvoice() {
        billDialog = new BillDialog(billForm);
        billDialog.setVisible(true);
    }

    private void createNewItem() {
        itemDialog = new ItemDialog(billForm);
        itemDialog.setVisible(true);
    }

    private void createBillCancel() {
        billDialog.setVisible(false);
        billDialog.dispose();
        billDialog = null;
    }

    private void createBillOK() {
        String date = billDialog.getInvDateField().getText();
        String customer = billDialog.getCustNameField().getText();
        int num = billForm.getNextBillNumber();

        Bill bill = new Bill(num, date, customer);
        billForm.getBills().add(bill);
        billForm.getBillTableModel().fireTableDataChanged();
        billDialog.setVisible(false);
        billDialog.dispose();
        billDialog = null;
    }

    private void createItemOK() {
           String itemName = itemDialog.getItemNameField().getText();
        String countStr = itemDialog.getItemCountField().getText();
        String priceStr = itemDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = billForm.getBillTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Bill bill = billForm.getBills().get(selectedInvoice);
            Item item = new Item(itemName, price, count, bill);
            bill.getItems().add(item);
            ItemTableModel itemTableModel = (ItemTableModel) billForm.getItemTable().getModel();
            //linesTableModel.getLines().add(line);
            itemTableModel.fireTableDataChanged();
            billForm.getBillTableModel().fireTableDataChanged();
        }
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

    private void createItemCancel() {
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }
}
