package service.validation;

import entity.CoffeeData;
import service.exception.ServiceException;

public interface XmlReader {
    CoffeeData read(String xmlPath) throws ServiceException;
}
