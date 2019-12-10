package service.migration;

import entity.OrderedCoffee;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderedCoffeeMigrationService extends Migration<OrderedCoffee> {
    public static final OrderedCoffeeMigrationService INSTANCE = new OrderedCoffeeMigrationService();

    private final String TABLE_NAME = "ordered_coffees";

    private OrderedCoffeeMigrationService() {}

    @Override
    public int migrate(List<OrderedCoffee> orderedCoffees) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (OrderedCoffee orderedCoffee : orderedCoffees) {
            migratedEntitiesCount += migrate(orderedCoffee, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, orderedCoffees.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(OrderedCoffee orderedCoffee, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME
                + "(id, size, count, coffee_id, order_id) values (?, ?, ?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, orderedCoffee.getId());
            preparedStatement.setInt(2, orderedCoffee.getSize().ordinal());
            preparedStatement.setInt(3, orderedCoffee.getCount());
            preparedStatement.setInt(4, orderedCoffee.getCoffeeId());
            preparedStatement.setInt(5, orderedCoffee.getOrderId());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, orderedCoffee));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
