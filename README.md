# Supermarket
Supermarket system for buying products

## Usage
When you run the program you will see a screen like this:
![image](https://user-images.githubusercontent.com/60541384/172863380-4b28011c-6413-444d-94b4-07a5dd5bde33.png)

In this screen you can write what you want to buy from the products listed. You can also follow the instructions written there.
![image](https://user-images.githubusercontent.com/60541384/172863857-b3d8b78c-2e6a-4523-bdfd-993ea1baf1fa.png)

You can write in lowercase or uppercase it doesn't matter. When you choose a product you then need to write what bill or coin you are paying with.
Shop only accepts the values listed.
When you finish our transaction you are given the a change if needed and the shop refreshes its stock and the cash register.

You can add new products in the products.json file with a similar structure as given in it:
```
{
  "itemName": "name",
  "price": amount,
  "quantity": amount
}
```

You can also add new currency in the currency.json file with a similar structure as given in it:
```
{
  "value": amount,
  "quantity": amount
}
```
### Note that by adding new currency or product make value/price only have a precision of 1 number after a comma
