package controller.dataLayer;

import model.businessLayer.MenuItem;
import model.businessLayer.Order;

import java.util.ArrayList;

public interface IRestaurantProcessing {

    /**
     * @pre menuItem != null
     * list.size@pre == list.size@post-1
     */
    public void createNewMenuItem(MenuItem menuItem);

    /**
     * @pre menuItem!=null
     * @post list.size == list.size@post + 1
     */
    public void deleteMenuItem(MenuItem menuItem);

    /**
     * @pre menuItem!=null
     * @post menuItem@pre.price != menuItem.price || ! menuItem@pre.name.equals(menuItem.name)
     */
    public void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem);

    /**
     * @pre order!=null
     * @pre menuItems.size!=0
     */
    public void createNewOrder(Order order, ArrayList<MenuItem> menuItems );

    /**
     * @pre order!=null
     */
    public float computePriceOrder(Order order);

    /**
     * @pre tableNo>0
     */
    public void generateBill(int tableNo);
}
