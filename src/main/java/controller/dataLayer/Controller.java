package controller.dataLayer;

import model.businessLayer.Restaurant;
import view.presentationLayer.ChefGUI;
import view.presentationLayer.UserInterface;

public class Controller {

    private String filename;
    public Controller(String filename) {
        this.filename = filename;
    }

    public void start(){
        Restaurant restaurant = RestaurantSerializator.deserialize(filename);
        UserInterface userInterface = new UserInterface(restaurant);
        userInterface.createGUI();
        ChefGUI chefGUI = new ChefGUI(restaurant);
    }
}
