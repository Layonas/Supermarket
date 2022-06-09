package Interfaces;

import Models.Product;
import Registers.CashRegister;
import Registers.ProductStorage;

import java.util.Map;

public interface SupermarketService {

    public void run();

    /**
     * Sends a buy offer into the console with all available products and their prices.
     * @return null if exit or cancel is given, product valid item name is given
     */
    public Product buyOffer();

    /**
     * Starts the transaction of the item you want to buy
     * @param product the item you want to buy
     * @return on success return the value of change needed to be handed, on cancel or exit returns -1
     */
    public double transaction(Product product);

    /**
     * Gives the user a change from the amount they over-payed
     * @param amount the amount of change needed
     */
    public void giveChange(double amount);


    /**
     * Updates the cash register with the values that have been given as change to the customer
     * @param shopMoneySpent a map containing the value that has been given as a change and how many times it has
     */
    public void updateCashRegister(Map<Double, Integer> shopMoneySpent);

    /**
     * Updates the product storage with the item that has been bought by the customer
     * @param product the product that has been bought by a customer
     */
    public void updateProductStorage(Product product);

}
