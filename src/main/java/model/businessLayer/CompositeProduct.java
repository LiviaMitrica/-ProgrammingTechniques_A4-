package model.businessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

    private ArrayList<MenuItem> product = new ArrayList<>();

    public CompositeProduct(String name, ArrayList<MenuItem> product) {
        this.name=name;
        this.product=product;
    }

    @Override
    public float computePrice() {
        float price = 0;
        for (MenuItem p: product) {
            price+=p.getPrice();
        }
        this.setPrice(price);
        return price;
    }
}
