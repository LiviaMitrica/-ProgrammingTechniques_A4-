package model.businessLayer;

public class IDgenerator {
    private int counter = 1;

    public IDgenerator(){

    }

    public int getNextID(){
        return counter++;
    }
}
