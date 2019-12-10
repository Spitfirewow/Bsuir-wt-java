package service.migration;

import entity.Order;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderMigrationService extends Migration<Order> {
    public static final OrderMigrationService INSTANCE = new OrderMigrationService();

    private final String TABLE_NAME = "orders";

    private OrderMigrationService() {}

    @Override
    public int migrate(List<Order> orders) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (Order order : orders) {
            migratedEntitiesCount += migrate(order, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, orders.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(Order order, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME + "(id, date_time, ready, user_id) values (?, ?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setString(2, order.getDateTime());
            preparedStatement.setBoolean(3, order.isReady());
            preparedStatement.setInt(4, order.getUserId());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, order));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
