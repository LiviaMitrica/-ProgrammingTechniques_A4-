package model.businessLayer;

import controller.dataLayer.FileWriterBill;
import controller.dataLayer.IRestaurantProcessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Restaurant extends Observable implements IRestaurantProcessing, java.io.Serializable {

    private ArrayList<MenuItem> menuItems;
    private Map<Order, ArrayList<MenuItem>> orderInformation;
    private ArrayList<Order> orders;

    public Restaurant() {
        this.menuItems = new ArrayList<>();
        this.orderInformation = new HashMap<>();
        this.orders = new ArrayList<>();
    }

    public Map<Order, ArrayList<MenuItem>> getOrderInformation() {
        return orderInformation;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Object[][] getOrdersTable(){
        Map<Order, ArrayList<MenuItem>> myMap = getOrderInformation();
        Object[][] obj = new Object[orders.size()][4];
        int i = 0;
        for (Order o : orders) {
            Order currentOrder = o;
            obj[i][0] = o.getOrderID();
            obj[i][1] = o.getDate().toString();
            obj[i][2] = o.getTable();
            String s = new String();
            ArrayList<MenuItem> menuItems = myMap.get(currentOrder);
            for(MenuItem m: menuItems)
                s+=", "+m.getName();
            s = s.substring(2);
            obj[i][3] = s;
            i++;
        }
        return obj;
    }

    public Object[][] getMenuItemsTable(){
        Object[][] obj = new Object[menuItems.size()][4];
        int i = 0;
        for (MenuItem o: menuItems) {
           MenuItem currentMenuItem = o;
            obj[i][0] = i+1;
            obj[i][1] = o.getName();
            obj[i][2] = o.computePrice();
            obj[i][3] = o.getClass().getSimpleName();
            String s = new String();
            i++;
        }
        return obj;
    }

    public MenuItem searchMenuItemByName(String name){
        for(MenuItem m:menuItems)
            if(m.getName().equals(name))
                return m;
            return null;
    }

    public Order searchOrderByTable(int tableNo){
        Order currentOrder = null;
        for (Order o: orders)
            if(tableNo == o.getTable()){
                currentOrder = o;
            }
        return currentOrder;
    }

    public void addNewOrder(Order order){
        orders.add(order);

        String s = new String();
        s+="New Order has been created, details:\n";
        s+="ID: "+order.getOrderID()+"\nTable: "+order.getTable()+"\nDate: "+order.getDate().toString()+"\n";
        s+="Menu items:\n";
        Map<Order, ArrayList<MenuItem>> map = orderInformation;
        ArrayList<MenuItem> currentOrderMenuItems = map.get(order);
        boolean composite = false;

        for (MenuItem m: currentOrderMenuItems) {
            s+=m.toString()+"\n";
            if(m.getClass()== CompositeProduct.class)
                composite = true;
        }

        if (composite ==true){
            setChanged();
            notifyObservers(s);
        }
    }

    @Override
    public void createNewMenuItem(MenuItem menuItem) {
        assert menuItem!=null;
        int preSize = menuItems.size();
        menuItems.add(menuItem);
        int postSize = menuItems.size();
        assert preSize == postSize-1;
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        assert menuItem!=null;
        int preSize = menuItems.size();
        menuItems.remove(menuItem);
        int postSize = menuItems.size();
        assert preSize == postSize+1;
    }

    @Override
    public void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem) {
        assert !oldMenuItem.equals(newMenuItem);
        for (MenuItem m: menuItems)
            if(m.equals(oldMenuItem)) {
                int index = menuItems.indexOf(m);
                menuItems.set(index, newMenuItem);
            }
    }

    @Override
    public void createNewOrder(Order order, ArrayList<MenuItem> menuItems) {
        assert order!=null;
        assert menuItems!=null;

        Order currentOrder = order;

        orderInformation.put(order,menuItems);

        assert currentOrder.equals(order);
    }

    @Override
    public float computePriceOrder(Order order) {
        assert order!=null;
        float priceOrder =0;

        if(orders.contains(order)){
            ArrayList<MenuItem> currentOrderMenuItems = orderInformation.get(order);
            for (MenuItem m: currentOrderMenuItems){
                priceOrder+=m.computePrice();
            }
        }

        return priceOrder;
    }

    @Override
    public void generateBill(int tableNo) {
        assert tableNo>0;
        Order currentOrder = searchOrderByTable(tableNo);
        String s = new String();
        s+="Order #"+currentOrder.getOrderID()+"\n";
        s+="Date: "+currentOrder.getDate().toString()+"\n\nItems:\n";

        ArrayList<MenuItem> currentOrderMenuItems = orderInformation.get(currentOrder);
        for (MenuItem m: currentOrderMenuItems) {
            s+=m.toString()+"\n";
        }
        float price =computePriceOrder(currentOrder);
        s+="\nTotal: "+price;
        FileWriterBill fileWriterBill = new FileWriterBill();
        String filename = "bill"+currentOrder.getOrderID();
        fileWriterBill.writeFile(s, filename);
    }
}
