package service.migration;

import entity.CoffeeTopping;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CoffeeToppingMigrationService extends Migration<CoffeeTopping> {
    public static final CoffeeToppingMigrationService INSTANCE = new CoffeeToppingMigrationService();

    private final String TABLE_NAME = "coffee_toppings";

    private CoffeeToppingMigrationService() {}

    @Override
    public int migrate(List<CoffeeTopping> coffeeToppings) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (CoffeeTopping coffeeTopping : coffeeToppings) {
            migratedEntitiesCount += migrate(coffeeTopping, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, coffeeToppings.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(CoffeeTopping coffeeTopping, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME + "(id, coffee_id, topping_id) values (?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, coffeeTopping.getId());
            preparedStatement.setInt(2, coffeeTopping.getCoffeeId());
            preparedStatement.setInt(3, coffeeTopping.getToppingId());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, coffeeTopping));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
