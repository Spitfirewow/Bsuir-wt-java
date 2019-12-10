package service.migration;

import com.ctc.wstx.io.CharsetNames;
import entity.User;
import org.apache.logging.log4j.Level;
import service.exception.ServiceException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserMigrationService extends Migration<User> {
    public static final UserMigrationService INSTANCE = new UserMigrationService();

    private final String TABLE_NAME = "users";

    private UserMigrationService() {}

    @Override
    public int migrate(List<User> users) throws ServiceException {
        Connection connection = retrieveConnection();

        LOGGER.log(Level.INFO, String.format(START_MIGRATION_LOG_FORMAT, TABLE_NAME));

        int migratedEntitiesCount = 0;
        for (User user : users) {
            migratedEntitiesCount += migrate(user, connection) ? 1 : 0;
        }

        LOGGER.log(Level.INFO, String.format(COMPLETE_MIGRATION_LOG_FORMAT,
                TABLE_NAME, migratedEntitiesCount, users.size()));

        return migratedEntitiesCount;
    }

    @Override
    protected boolean migrate(User user, Connection connection) {
        final String INSERT_QUERY = "insert into " + TABLE_NAME
                + "(id, login, password_hash, password_salt, admin) values (?, ?, ?, ?, ?)";
        boolean migrated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, new String(user.getPasswordHash(), StandardCharsets.UTF_8));
            preparedStatement.setString(4, new String(user.getPasswordSalt(), StandardCharsets.UTF_8));
            preparedStatement.setBoolean(5, user.isAdmin());
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, String.format(SUCCESSFUL_MIGRATION_LOG_FORMAT, user));
            migrated = true;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        return migrated;
    }
}
