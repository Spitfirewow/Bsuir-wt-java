package service.validation;

import service.exception.ServiceException;

public interface XmlValidator {
    void validate(String xmlPath, String xsdPath) throws ServiceException;
}
