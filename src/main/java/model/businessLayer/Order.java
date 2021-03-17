package model.businessLayer;

public class Order {
    private int orderID;
    private DateOrder date;
    private int table;

    public Order(int id, int table) {
        this.orderID = id; //The ID is incremented automatically
        this.table = table;
        this.date = new DateOrder(); // the Date will be initialized automatically with the current date of the calendar
    }

    public int getOrderID() {
        return orderID;
    }

    public String getDate() {
        return date.toString();
    }

    public int getTable() {
        return table;
    }

    @Override
    public int hashCode() {
        int hashcode = 13;
        hashcode += hashcode*11 + 7*orderID+31*date.getCurrentDay()+5*table;
        return hashcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID &&
                table == order.table &&
                date.equals(order.date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", date=" + date +
                ", table=" + table +
                '}';
    }
}
