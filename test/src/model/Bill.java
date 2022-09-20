package model;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int num; 
    private String date; 
    private String customer;
    private List<Item> items;

    public Bill(int num, String date, String customer) {
        this.num = num;
        this.date = date;
        this.customer = customer;
    }

    public Bill() {
    }

    public List<Item> getItems() {
        if(items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
     public double getBillTotal() {
        double total = 0.0;
        for (Item item : getItems()) {
            total += item.getItemTotal();
        }
        return total;
    }
    

    @Override
    public String toString() {
         return "Bill{" + "num=" + num + ", date=" + date + ", customer=" + customer + '}';
    }
 
        
    public String getAsCSV() {
        return num + "," + date + "," + customer;
    }
}

