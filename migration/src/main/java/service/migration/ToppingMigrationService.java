package service.migration;

import entity.Topping;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ToppingMigrationService extends Migration<Topping> {
    public static final ToppingMigrationService INSTANCE = new ToppingMigrationService();

    private final String TABLE_NAME = "toppings";

    private ToppingMigrationService() {}

    @Override
    public int migrate(List<Topping> toppings) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (Topping topping : toppings) {
            migratedEntitiesCount += migrate(topping, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, toppings.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(Topping topping, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME + "(id, name) values (?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, topping.getId());
            preparedStatement.setString(2, topping.getName());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, topping));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
