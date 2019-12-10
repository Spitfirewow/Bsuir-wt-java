package service.migration;

import entity.Coffee;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CoffeeMigrationService extends Migration<Coffee> {
    public static final CoffeeMigrationService INSTANCE = new CoffeeMigrationService();

    private final String TABLE_NAME = "coffees";

    private CoffeeMigrationService() {}

    @Override
    public int migrate(List<Coffee> coffees) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (Coffee coffee : coffees) {
            migratedEntitiesCount += migrate(coffee, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, coffees.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(Coffee coffee, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME
                + "(id, name, weight_s, weight_m, weight_l) values (?, ?, ?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, coffee.getId());
            preparedStatement.setString(2, coffee.getName());
            preparedStatement.setInt(3, coffee.getWeightS());
            preparedStatement.setInt(4, coffee.getWeightM());
            preparedStatement.setInt(5, coffee.getWeightL());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, coffee));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
