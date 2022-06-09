package Registers;

import Utilities.Utils;
import Models.Currency;

import java.util.List;

public class CashRegister{
    private List<Currency> storedMoney;

    public CashRegister(){
        this.storedMoney = Utils.getCurrency();
    }

    public CashRegister(List<Currency> storedMoney) {
        this.storedMoney = storedMoney;
    }

    public List<Currency> getStoredMoney() {
        return storedMoney;
    }

    public void setStoredMoney(List<Currency> storedMoney) {
        this.storedMoney = storedMoney;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        storedMoney.stream().forEach(c -> builder.append("\n" + c.getValue() + " Quantity: " + c.getQuantity()));

        return builder.toString();
    }

    public boolean acceptsValue(Double p) {
        return this.storedMoney.stream().anyMatch(c -> c.getValue() == p);
    }

    public void updateValue(double key, int value) {
        for (Currency c : this.storedMoney)
            if(Double.compare(c.getValue(), key) == 0){
                int val = c.getQuantity();
                c.setQuantity(val - value);
                break;
            }
    }
}
