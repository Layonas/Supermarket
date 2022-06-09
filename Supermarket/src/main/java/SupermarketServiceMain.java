import Implementations.SupermarketServiceImp;

public class SupermarketServiceMain {
    public static void main(String[] args) {
        SupermarketServiceImp market = SupermarketServiceImp.getInstance();

        market.run();
    }
}
