# BasketDivision
API for the intelligent division of the shopping basket into as few and optimal deliveries as possible.

### Tools used
- Java 17
- JUnit 5.8.1
- JSON-Simple 1.1.1

### Library compilation
Clone the repository
```
git clone https://github.com/arturgesiarz/BasketDivision
```
Navigate to the application folder
```
cd BasketDivision
```
Programme compilation
```
mvn clean install
```
Go to this folder
```
cd target
```
Copy file
```
BasketDivision-1.0-jar-with-dependencies.jar
```

### Description of classes

#### BasketSplitter
This class has only one Split() method, which divides our basket into the most opytmally supplied

#### Basket
This static class is responsible for communication with suppliers and the products they are to supply. 
It assigns a product to a supplier, and intelligently divides the products between these suppliers.

#### Supplier
This class is responsible for modifying the products supplied by a given supplier,
allowing the addition/removal of products

#### Product
This is a record which has only one attribute, the product name, and has the equals method

#### DeliveryHandler
This static class is responsible for loading a configuration file in JSON format, 
which contains all the possible products offered by our shop and, accordingly, 
the deliveries available for the given products.

#### ConsoleDisplay
Static class allowing to display the result obtained by the Split() method in BasketSplitter

### Description of algortim
The algorithm works in such a way that we first load our data from a JSON file, then pass our shopping list to the Split() method.

This shopping list is converted into a list of delivery methods, those deliveries that can deliver these products. Each of these methods is initially assigned all deliverable purchases from our shopping list. It is clear that there may be some overlap at the outset, but we will now be wise to divide this up.

It is sufficient to calculate for each delivery method the number of products that can be delivered by it in our shopping basket.
Then in a loop:
We will sort these methods from the largest quantities available for delivery to the smallest.
We will then select the first delivery method from the top and confirm this delivery - i.e. we will remove the products that we have taken with the currently selected method.
Then, in a loop until all the products have been delivered, I will repeat this process, but with the change that each time after sorting I will take a lower method than the previously selected one.

This algorithm is corrected and distributes the shopping cart between the delivery methods 
