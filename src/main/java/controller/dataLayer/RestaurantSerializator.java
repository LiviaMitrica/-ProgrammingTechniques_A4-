package controller.dataLayer;

import model.businessLayer.Restaurant;

import java.io.*;

public class RestaurantSerializator {

    public static void serialize(Restaurant restaurant){
        String filename = "restaurant.ser";
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(restaurant);
            out.close();
            file.close();
            System.out.println("Object serialized successfully.");
        }catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static Restaurant deserialize(String filename){
        Restaurant restaurant = new Restaurant();
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            restaurant = (Restaurant) in.readObject();
            in.close();
            file.close();
            System.out.println("Object deserialized successfully.");
        } catch (FileNotFoundException e) {
            restaurant = new Restaurant();
            serialize(restaurant);
            return restaurant;
        } catch (IOException e) {
            restaurant = new Restaurant();
            serialize(restaurant);
            return restaurant;
        } catch (ClassNotFoundException e) {
        }
        return restaurant;
    }
}
