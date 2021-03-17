package model.businessLayer;

public abstract class MenuItem implements java.io.Serializable {

    protected String name;
    protected float price;

    protected MenuItem() {
    }

    public abstract float computePrice();

    public MenuItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "name= " + name + ' ' +
                ", price=" + this.computePrice();
    }
}
