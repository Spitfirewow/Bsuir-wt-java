import entity.OrderedCoffee;
import entity.CoffeeData;
import service.exception.ServiceException;
import service.jdbc.JdbcConnectionService;
import service.migration.*;
import service.validation.XmlService;


public class App {
    private static final String XML_FILE_PATH = "src/main/resources/coffeeData.xml";
    private static final String XSD_FILE_PATH = "src/main/resources/coffeeData.xsd";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/coffee?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "55545554";

    public static void main(String[] args) {
        try {
            XmlService xmlService = XmlService.INSTANCE;
            xmlService.validate(XML_FILE_PATH, XSD_FILE_PATH);

            CoffeeData coffeeData = xmlService.read(XML_FILE_PATH);

            JdbcConnectionService.INSTANCE.establishConnection(DB_URL, DB_USER, DB_PASSWORD);

            ExtraMigrationService.INSTANCE.migrate(coffeeData.getExtras());
            CoffeeMigrationService.INSTANCE.migrate(coffeeData.getCoffees());
            ToppingMigrationService.INSTANCE.migrate(coffeeData.getToppings());
            CoffeeToppingMigrationService.INSTANCE.migrate(coffeeData.getCoffeeToppings());
            UserMigrationService.INSTANCE.migrate(coffeeData.getUsers());
            OrderMigrationService.INSTANCE.migrate(coffeeData.getOrders());
            OrderedExtraMigrationService.INSTANCE.migrate(coffeeData.getOrderedExtras());
            OrderedCoffeeMigrationService.INSTANCE.migrate(coffeeData.getOrderedCoffees());
        } catch (ServiceException e) {}
    }

}
