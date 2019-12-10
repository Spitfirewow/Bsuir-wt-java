package service.jdbc;

import service.exception.ServiceException;

public interface JdbcConnection {
    void establishConnection(String url, String user, String password) throws ServiceException;
}
