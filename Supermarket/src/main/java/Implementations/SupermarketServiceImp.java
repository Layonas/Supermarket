package Implementations;

import Exceptions.NotEnoughChangeException;
import Exceptions.PayNotAcceptedException;
import Interfaces.SupermarketService;
import Models.Currency;
import Models.Product;
import Registers.CashRegister;
import Registers.ProductStorage;
import Utilities.Utils;

import java.util.*;

public class SupermarketServiceImp implements SupermarketService {

    private CashRegister cashRegister = new CashRegister();
    private ProductStorage storage = new ProductStorage();
    private Map<Double, Integer> customerMoney = new TreeMap<>();
    private Map<Double, Integer> shopMoney = new TreeMap<>();
    private boolean exit = false;
    private boolean cancel = false;
    private boolean gaveChange = false;
    private Scanner in = new Scanner(System.in);


    private static SupermarketServiceImp instance = null;

    private SupermarketServiceImp(){

    }

    public static SupermarketServiceImp getInstance(){
        if(instance == null){
            instance = new SupermarketServiceImp();
        }

        return instance;
    }

    @Override
    public void run() {

        if(exit)
            return;

        Collections.sort(cashRegister.getStoredMoney(), Comparator.reverseOrder());

        System.out.println("---------------------------------------------------");
        System.out.println("Initial Product Inventory:" + storage);
        System.out.println("Initial Cash Inventory:" + cashRegister);
        System.out.println("---------------------------------------------------");
        System.out.println("You can exit or cancel the transaction simple by typing EXIT or CANCEL.");

        while(!exit){

            cancel = false;

            Product product = buyOffer();

            double change = -1;
            if(product != null)
                change = transaction(product);

            if(change > 0)
                giveChange(change);

            if(cancel){
                System.out.println("Your transaction was canceled! \n");
            } else if(!exit) {
                if(gaveChange)
                    updateCashRegister(this.customerMoney);
                else
                    Collections.sort(this.cashRegister.getStoredMoney(), Comparator.reverseOrder());

                updateCashRegister(this.shopMoney);

                if(product != null) {
                    updateProductStorage(product);
                    System.out.println("Here is your product: " + product.getItemName() + "\n");
                    System.out.println("---------------------------------------------------");
                    System.out.println("Updated Product Inventory:" + storage);
                    System.out.println("Updated Cash Inventory:" + cashRegister);
                    System.out.println("---------------------------------------------------\n");
                    System.out.println("You can exit or cancel the transaction simple by typing EXIT or CANCEL.");

                    this.gaveChange = false;
                }
            }
        }

        Utils.updateCurrency(cashRegister);
        Utils.updateProducts(storage);

    }

    @Override
    public Product buyOffer() {
        System.out.println("What would you like to buy? Type in the name of the desired product.");
        storage.getProducts().stream().forEach(p -> System.out.print(p.getItemName() + " (price: " + p.getPrice() + ") "));
        System.out.println();

        String input = in.nextLine();

        if(input.toLowerCase().equals("exit")){
            exit = true;
            return null;
        }

        if(input.toLowerCase().equals("cancel")){
            cancel = true;
            return null;
        }

        return storage.getProduct(input);
    }

    @Override
    public double transaction(Product product) {

        List<Currency> cash = cashRegister.getStoredMoney();
        Collections.sort(cash);

        cash.stream().forEach(c -> this.shopMoney.put(c.getValue(), 0));

        double price  = product.getPrice();

        double pay = 0;

        String s = "Provide bill or coin (accepted values: " + cash.toString().replace("[", "").replace("]", "") + ")\n";

        while(!cancel && (double)Math.round((price-pay)*10)/10 > 0 && !exit){

            if(pay <= 0){
                System.out.println("You are trying to buy " + product.getItemName().toUpperCase() + ". You need to pay " + product.getPrice());
                System.out.print(s);
            } else {
                System.out.println("You paid " + (double)Math.round((pay)*10)/10 + " in total. You still need to pay " + (double)Math.round((price-pay)*10)/10);
                System.out.print(s);
            }

            String input = in.nextLine();

            if(input.toLowerCase().equals("exit")){
                exit = true;
                return -1;
            }

            if(input.toLowerCase().equals("cancel")){
                cancel = true;
                return -1;
            }

            try {
                Double p =  Double.parseDouble(input);
                if(!cashRegister.acceptsValue(p))
                    throw new PayNotAcceptedException();
                pay += p;
                int q = this.shopMoney.get(p);
                this.shopMoney.put(p, --q);
            }catch (PayNotAcceptedException ex){

            } catch (Exception e) {
                System.out.println("Incorrect format of the currency.");
            }

        }

        if((double)Math.round((pay-price)*10)/10 != 0){
            System.out.println("You have paid " + pay + " in total. Your change will be " + (double)Math.round((pay-price)*10)/10);
        }

        return (double)Math.round((pay-price)*10)/10;
    }

    @Override
    public void giveChange(double amount) {
        final double[] change = {amount};
        final double[] pay = {0};
        List<Currency> cash = cashRegister.getStoredMoney();
        Collections.sort(cash, Comparator.reverseOrder());
        final boolean[] valid = {false, true};
        gaveChange = true;

        cash.stream().forEach(c -> {
            customerMoney.put(c.getValue(), 0);
        });

        while((double)Math.round((amount - pay[0])*10)/10 > 0){
            valid[0] = false;
            try{
                for(Currency c : cash){
                    if((double)Math.round((change[0] - c.getValue())*10)/10 >= 0 && valid[1]){
                        pay[0] += c.getValue();
                        valid[0] = true;
                        change[0] -= c.getValue();
                        int q = customerMoney.get(c.getValue());
                        if(q+1 > c.getQuantity()){
                            valid[1] = false;
                        }

                        customerMoney.put(c.getValue(), ++q);
                        break;
                    }
                }

                if(!valid[0] && !valid[1])
                    throw new NotEnoughChangeException();
            } catch (NotEnoughChangeException e){
                cancel = true;
                return;
            }

        }

        System.out.println("Here is your change:");
        for(Map.Entry<Double, Integer> entry : this.customerMoney.entrySet()){
            if(entry.getValue() != 0)
                System.out.println("Value: " + entry.getKey() + ", quantity: " + entry.getValue());
        }
    }

    @Override
    public void updateCashRegister(Map<Double, Integer> shopMoneySpent) {
        for (Map.Entry<Double, Integer> entry : shopMoneySpent.entrySet()){
            if(entry.getValue() != 0)
                cashRegister.updateValue(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void updateProductStorage(Product product) {
        storage.updateQuantity(product);
    }
}
