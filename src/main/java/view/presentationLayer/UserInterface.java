package view.presentationLayer;
import controller.dataLayer.IRestaurantProcessing;
import controller.dataLayer.RestaurantSerializator;
import model.businessLayer.MenuItem;
import model.businessLayer.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserInterface extends JFrame implements IRestaurantProcessing {
    private JFrame jFrame;
    private JButton waiterButton,administratorButton ;
    private JButton doneAdministratorButton, doneChooseButton, doneCompositeProductButton, doneWaiterButton, doneBaseProductButton;
    private JPanel mainPanel, administratorPanel,waiterPanel, productTypePanel, compositeProductPanel, baseProductPanel, chooseMenuItemPanel, ordersPanel, menuItemsPanel, editItemPanel;
    private JTextField textFieldOrderedItems, currentNameTextField,newNameTextField,newPriceTextField;
    private JTextField nameTextField, priceTextField, nameCompositeProduct, compositionValue, textFieldTableNo;;
    private JButton createNewItemButton, deleteItemButton, editItemButton,createNewOrderButton;
    private JButton computePriceForOrderButton, generateBillButton, baseProductButton,compositeProductButton;
    private JList listMenuItems, listItems;
    private JButton createBaseProductButton, createCompositeProductButton, showOrdersButton;
    private JLabel newName, nameBaseProduct, priceBaseProduct;
    private JTable tableOrders;
    private JButton backButton,backEditItem, backWaiterPanel,addOrderButton, editMenuItemButton;
    private JTable tableMenuItems;
    private JPanel admMenuItemsPanel;
    private JButton showMenuItemsButton;
    private Restaurant restaurant;
    private ArrayList<Order> orders = new ArrayList<>();
    private IDgenerator id = new IDgenerator();

    public UserInterface(Restaurant restaurant) {
        this.restaurant = restaurant;
        addListenersToWaiter();
        addListenersToAdministrator();
    }

    private void setAllPanelsNull(){
        waiterPanel.setVisible(false);
        administratorPanel.setVisible(false);
        mainPanel.setVisible(false);
        productTypePanel.setVisible(false);
        compositeProductPanel.setVisible(false);
        baseProductPanel.setVisible(false);
        chooseMenuItemPanel.setVisible(false);
        ordersPanel.setVisible(false);
        menuItemsPanel.setVisible(false);
        editItemPanel.setVisible(false);
        menuItemsPanel.setVisible(false);
    }
    private void displayPanel(JPanel panel){
        jFrame.add(panel);
        setAllPanelsNull();
        panel.setVisible(true);
    }
    private void addListenersToAdministrator(){
        administratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(administratorPanel);
            }
        });
        showMenuItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemsPanel.setVisible(true);
                String columns[] = { "ID", "Name", "Price", "Type" };
                DefaultTableModel myModel = new DefaultTableModel(0, 4);
                myModel.insertRow(0, columns);
                Object[][] obj = restaurant.getMenuItemsTable();
                for (int i=0; i<obj.length; i++){
                    myModel.insertRow(i+1, obj[i]);
                }
                tableMenuItems.setPreferredScrollableViewportSize(new Dimension(400,60));
                tableMenuItems.setModel(myModel);};
        });
        createNewItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(productTypePanel);
            }
        });
        createCompositeProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<MenuItem> items = new ArrayList<>();
                ArrayList<String> strings = (ArrayList<String>) listMenuItems.getSelectedValuesList();
                for(String s:strings){
                    MenuItem menuItem = restaurant.searchMenuItemByName(s);
                    if (menuItem!=null)
                        items.add(menuItem);
                }
                String name = nameCompositeProduct.getText();
                MenuItem menuItem = new CompositeProduct(name, items);
                createNewMenuItem(menuItem);
                RestaurantSerializator.serialize(restaurant);
                JOptionPane.showMessageDialog(null, "Product added successfully");
                nameCompositeProduct.setText("");
                listMenuItems.clearSelection();
                compositionValue.setText("");
            }
        });
        createBaseProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String p = priceTextField.getText();
                float price = Float.parseFloat(p.trim()); //Float.parseFloat(priceBaseProduct.getText().trim());
                BaseProduct baseProduct = new BaseProduct(name, price);
                createNewMenuItem(baseProduct);
                RestaurantSerializator.serialize(restaurant);
                JOptionPane.showMessageDialog(null, "Product added successfully");
                nameTextField.setText("");
                priceTextField.setText("");
            }
        });
        baseProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(baseProductPanel);
            }
        });
        compositeProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(compositeProductPanel);
                chooseMenuItemPanel.setVisible(true);
                DefaultListModel<String> list = new DefaultListModel<>();
                for(MenuItem m:restaurant.getMenuItems())
                    list.addElement(m.getName());
                listMenuItems.setModel(list);
                listMenuItems.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            compositionValue.setText(listMenuItems.getSelectedValuesList().toString());
                        }
                    }
                });
            }
        });
        doneAdministratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(mainPanel);
            }
        });
        doneCompositeProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(productTypePanel);
            }
        });
        doneBaseProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(productTypePanel);
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = JOptionPane.showInputDialog("Please input name of the product you want to delete: ");
                MenuItem m = restaurant.searchMenuItemByName(text);
                if(m!=null) {
                    deleteMenuItem(m);
                    RestaurantSerializator.serialize(restaurant);
                    JOptionPane.showMessageDialog(null, "Product deleted succesfully!");
                }
                else
                    JOptionPane.showMessageDialog(null, "Product does not exist!");
            }
        });
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(editItemPanel);
            }
        });
        editMenuItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldname = currentNameTextField.getText();
                String newName = newNameTextField.getText().trim();
                String newPrice = newPriceTextField.getText();
                MenuItem oldMenuitem = restaurant.searchMenuItemByName(oldname);
                try{
                    float newP = Float.parseFloat(newPrice.trim());
                    newName = newName == "" ? oldname : newName ;
                    MenuItem newMenuItem = new BaseProduct(newName, newP);
                    editMenuItem(oldMenuitem, newMenuItem);
                    RestaurantSerializator.serialize(restaurant);
                    JOptionPane.showMessageDialog(null, "Product updated successfully");
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Bad input!");
                }
            }
        });
        backEditItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(administratorPanel);
            }
        });
        doneChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(administratorPanel);
            }
        });
    }
    private void addListenersToWaiter(){
        waiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(waiterPanel);
            }
        });
        doneWaiterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(mainPanel);
            }
        });
        showOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAllPanelsNull();
                displayPanel(ordersPanel);
                String columns[] = { "ID", "Date", "Table", "Items" };
                DefaultTableModel myModel = new DefaultTableModel(0, 4);
                myModel.insertRow(0, columns);
                if(orders!=null) {
                    Object[][] obj = restaurant.getOrdersTable();
                    for (int i=0; i<obj.length; i++){
                        myModel.insertRow(i+1, obj[i]);
                    }
                }
                tableOrders.setPreferredScrollableViewportSize(new Dimension(400,60));
                tableOrders.setModel(myModel);
            }
        });
        backWaiterPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(waiterPanel);
            }
        });
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<MenuItem> items = new ArrayList<>();
                ArrayList<String> strings = (ArrayList<String>) listItems.getSelectedValuesList();
                for(String s:strings){
                    MenuItem menuItem = restaurant.searchMenuItemByName(s);
                    if (menuItem!=null)
                        items.add(menuItem);
                }
                try{
                int tableNo = Integer.parseInt(textFieldTableNo.getText().trim());
                Order o = new Order(id.getNextID(), tableNo);
                orders.add(o);
                createNewOrder( o, items);
                restaurant.addNewOrder(o);
                JOptionPane.showMessageDialog(null, "Order added successfully");
                textFieldTableNo.setText("");
                textFieldOrderedItems.setText("");
                listItems.clearSelection();
                }
                catch (Exception excepion) {
                    JOptionPane.showMessageDialog(null, "Bad input");
                }
            }
        });
        createNewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(menuItemsPanel);
                DefaultListModel<String> list = new DefaultListModel<>();
                for(MenuItem m: restaurant.getMenuItems()){
                    list.addElement(m.getName());
                }
                listItems.setModel(list);
                listItems.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if(!e.getValueIsAdjusting()){
                            textFieldOrderedItems.setText(listItems.getSelectedValuesList().toString());
                        }
                    }
                });
            }
        });
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = JOptionPane.showInputDialog("Please input table number: ");
                try {
                    int tableNo = Integer.parseInt(text.trim());
                    generateBill(tableNo);
                }catch(Exception exception){
                    JOptionPane.showMessageDialog(null, "No order for this table!");
                }
            }
        });
        computePriceForOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = JOptionPane.showInputDialog("Please input table number: ");
                try {
                    int tableNo = Integer.parseInt(text);
                    Order order = restaurant.searchOrderByTable(tableNo);
                    if(order!=null)
                        restaurant.computePriceOrder(order);
                    else
                        JOptionPane.showMessageDialog(null, "No order for this table!");
                }catch(Exception exception){
                    JOptionPane.showMessageDialog(null, "Invalid input format!");
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel(waiterPanel);
            }
        });
    }
    private void initializeGUI() {
        jFrame = new JFrame("Restaurant Management");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(400,350));
        jFrame.setResizable(false);
        jFrame.pack();
        setAllPanelsNull();
        displayPanel(mainPanel);
    }

    @Override
    public void createNewMenuItem(MenuItem menuItem) {
        restaurant.createNewMenuItem(menuItem);
    }
    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        restaurant.deleteMenuItem(menuItem);
    }
    @Override
    public void editMenuItem(MenuItem oldMenuItem, MenuItem newMenuItem) {
        restaurant.editMenuItem(oldMenuItem, newMenuItem);
    }
    @Override
    public void createNewOrder(Order order, ArrayList<MenuItem> menuItems) {
        restaurant.createNewOrder(order, menuItems);
    }
    @Override
    public float computePriceOrder(Order order) {
        return restaurant.computePriceOrder(order);
    }
    @Override
    public void generateBill(int tableNo) {
        restaurant.generateBill(tableNo);
    }

    public void createGUI(){
        UserInterface userInterface = new UserInterface(restaurant);
        userInterface.initializeGUI();
        userInterface.jFrame.setVisible(true);
    }
}