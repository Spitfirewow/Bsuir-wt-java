package service.migration;

import entity.Extra;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ExtraMigrationService extends Migration<Extra> {
    public static final ExtraMigrationService INSTANCE = new ExtraMigrationService();

    private final String TABLE_NAME = "extras";

    private ExtraMigrationService() {}

    @Override
    public int migrate(List<Extra> extras) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (Extra extra : extras) {
            migratedEntitiesCount += migrate(extra, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, extras.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(Extra extra, Connection connection) {
        final String INSERT_QUERY
                = "insert into " + TABLE_NAME + "(id, name, price) values (?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, extra.getId());
            preparedStatement.setString(2, extra.getName());
            preparedStatement.setDouble(3, extra.getPrice());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, extra));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
