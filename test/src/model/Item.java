package model;

public class Item {
//    private int itemNumber; 
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private Bill bill;

    public Item() {
    }

    public Item(String itemName, double itemPrice, int itemCount, Bill bill) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.bill = bill;
    }
    
    

    public Item(int itemNumber, String itemName, double itemPrice, int itemCount) {
//        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
    }

    public Item(int itemNumber, String itemName, double itemPrice, int itemCount, Bill bill) {
//        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.bill = bill;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    
//
//    public int getItemNumber() {
//        return itemNumber;
//    }
//
//    public void setItemNumber(int itemNumber) {
//        this.itemNumber = itemNumber;
//    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public double getItemTotal() {
        return itemPrice * itemCount;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + bill.getNum() + ", item=" + itemName + ", price=" + itemPrice + ", count=" + itemCount + '}';
    }
    
     
    public String getAsCSV() {
        return bill.getNum() + "," + itemName + "," + itemPrice + "," + itemCount;
    }
    
    
}
