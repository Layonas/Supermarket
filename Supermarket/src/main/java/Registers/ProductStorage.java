package Registers;

import Exceptions.SoldOutException;
import Models.Product;
import Utilities.Utils;
import java.util.List;
import java.util.NoSuchElementException;


public class ProductStorage {

    private List<Product> products;

    public ProductStorage(){
        this.products = Utils.getProducts();
    }

    public ProductStorage(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product getProduct(String item){
        try{
            Product product = this.products.stream().filter(p -> p.getItemName().toLowerCase().equals(item.toLowerCase())).findFirst().get();

            if(product.getQuantity() == 0)
                throw new SoldOutException();

            return product;
        } catch (NoSuchElementException e){
            System.out.println("There is no item that would correspond to: " + item + "\n");
            return null;
        } catch (SoldOutException e) {
            return null;
        }
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        products.stream().forEach(p -> builder.append("\n"+p.getItemName().toUpperCase() + " Quantity: " + p.getQuantity()));

        return builder.toString();
    }

    public void updateQuantity(Product product) {
        Product p = this.products.get(this.products.indexOf(product));
        int q = p.getQuantity();
        p.setQuantity(--q);
    }
}
