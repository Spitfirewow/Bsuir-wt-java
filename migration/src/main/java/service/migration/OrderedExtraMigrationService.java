package service.migration;

import entity.OrderedExtra;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderedExtraMigrationService extends Migration<OrderedExtra> {
    public static final OrderedExtraMigrationService INSTANCE = new OrderedExtraMigrationService();

    private final String TABLE_NAME = "ordered_extras";

    private OrderedExtraMigrationService() {}

    @Override
    public int migrate(List<OrderedExtra> orderedExtras) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (OrderedExtra orderedExtra : orderedExtras) {
            migratedEntitiesCount += migrate(orderedExtra, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, orderedExtras.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(OrderedExtra orderedExtra, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME + "(id, count, extra_id, order_id) values (?, ?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, orderedExtra.getId());
            preparedStatement.setInt(2, orderedExtra.getCount());
            preparedStatement.setInt(3, orderedExtra.getExtraId());
            preparedStatement.setInt(4, orderedExtra.getOrderId());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, orderedExtra));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
