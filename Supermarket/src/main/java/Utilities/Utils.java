package Utilities;

import Registers.CashRegister;
import Registers.ProductStorage;
import com.fasterxml.jackson.databind.ObjectMapper;

import Models.Currency;
import Models.Product;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Currency> getCurrency(){

        List<Currency> storedCash = new ArrayList<>();

        try {
            storedCash = Arrays.asList(mapper.readValue(new File("currency.json"), Currency[].class));
        } catch (Exception e){
            e.printStackTrace();
        }

        return storedCash;
    }

    public static List<Product> getProducts(){
        List<Product> products = new ArrayList<>();

        try {
            products = Arrays.asList(mapper.readValue(new File("products.json"), Product[].class));
        } catch (Exception e){
            e.printStackTrace();
        }

        return products;
    }

    public static void updateCurrency(CashRegister register){
        try{
            mapper.writeValue(new File("currency.json"), register.getStoredMoney());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateProducts(ProductStorage products){
        try{
            mapper.writeValue(new File("products.json"), products.getProducts());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
