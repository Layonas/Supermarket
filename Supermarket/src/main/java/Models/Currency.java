package Models;

public class Currency implements Comparable<Currency>{
    private double value;
    private int quantity;

    public Currency(double value, int quantity) {
        this.value = value;
        this.quantity = quantity;
    }

    public Currency() {
    }

    public double getValue() {
        return value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int compareTo(Currency o) {
        return Double.compare(this.value, o.value);
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null)
            return false;

        if(obj instanceof Currency){
            if(Double.compare(this.value, ((Currency) obj).value) == 0)
                return true;
        }

        if(obj instanceof Double){
            if(Double.compare(this.value, (double) obj) == 0)
                return true;
        }

        return false;
    }
}
