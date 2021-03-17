package view.presentationLayer;

import model.businessLayer.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class ChefGUI implements Observer {
    private JPanel chefPanel;
    private JTextArea textArea;
    private Restaurant restaurant;

    public ChefGUI(Restaurant restaurant){
        JFrame jFrame = new JFrame("Restaurant Management - Chef");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(300,250));
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.add(chefPanel);
        jFrame.setVisible(true);
        this.restaurant = restaurant;

        if(restaurant!=null)
            restaurant.addObserver(this);
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        textArea.setText(arg.toString());
    }
}
