package service.migration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exception.ServiceException;
import service.jdbc.JdbcConnectionService;

import java.sql.Connection;
import java.util.List;

public abstract class Migration<T> {
    final JdbcConnectionService jdbcConnectionService = JdbcConnectionService.INSTANCE;
    final Logger LOGGER = LogManager.getLogger(getClass());
    final String START_MIGRATION_LOG_FORMAT = "MIGRATION STARTED: %s.";
    final String COMPLETE_MIGRATION_LOG_FORMAT = "MIGRATION COMPLETED: %s, %d/%d.";
    final String SUCCESSFUL_MIGRATION_LOG_FORMAT = "%s migrated successfully.";

    public abstract int migrate(List<T> entities) throws ServiceException;

    protected abstract boolean migrate(T entity, Connection connection);

    protected Connection retrieveConnection() throws ServiceException{
        Connection connection;
        try {
            connection = jdbcConnectionService.getConnection();
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
            throw e;
        }
        return connection;
    }
}
