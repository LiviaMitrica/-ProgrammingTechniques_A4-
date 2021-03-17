package application;

import controller.dataLayer.Controller;

public class App {
    public static void main(String[] args) {
        if(args.length==1) {
            String file = args[0];
            Controller controller = new Controller(file);
            controller.start();
        }
        else
            System.out.println("Missing file restaurant.ser");
    }
}
