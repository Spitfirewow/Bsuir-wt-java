package service.jdbc;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.exception.ServiceException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionService implements JdbcConnection {
    public static final JdbcConnectionService INSTANCE = new JdbcConnectionService();

    private Connection connection;
    private final Logger LOGGER = LogManager.getLogger(getClass());

    private JdbcConnectionService() {}

    @Override
    public void establishConnection(String url, String user, String password) throws ServiceException {
        try {
            // load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // establish connection
            connection = DriverManager.getConnection(url, user, password);
            LOGGER.log(Level.INFO, "Database connection has been established successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            ServiceException serviceException = new ServiceException(e.getMessage(), e);
            LOGGER.log(Level.ERROR, serviceException.getMessage());
            throw serviceException;
        }
    }

    public Connection getConnection() throws ServiceException {
        if (connection == null) {
            ServiceException serviceException = new ServiceException("Database connection has not been established.");
            LOGGER.log(Level.ERROR, serviceException.getMessage());
            throw serviceException;
        }
        return connection;
    }
}
