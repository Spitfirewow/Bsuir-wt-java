package service.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import entity.CoffeeData;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import service.exception.ServiceException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlService implements XmlValidator, XmlReader {
    public static final XmlService INSTANCE = new XmlService();

    private final Logger LOGGER = LogManager.getLogger(getClass());

    private XmlService() {}

    public void validate(String xmlPath, String xsdPath) throws ServiceException {
        try {
            File xsdFile = new File(xsdPath);
            File xmlFile = new File(xmlPath);
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            LOGGER.log(Level.INFO, "XML file validated successfully.");
        } catch (IOException | SAXException e) {
            ServiceException serviceException = new ServiceException(e.getMessage(), e);
            LOGGER.log(Level.ERROR, serviceException.getMessage());
            throw serviceException;
        }
    }

    public CoffeeData read(String xmlPath) throws ServiceException {
        CoffeeData coffeeData;
        try {
            File xmlFile = new File(xmlPath);
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            coffeeData = xmlMapper.readValue(xmlFile, CoffeeData.class);
            LOGGER.log(Level.INFO, "XML file deserialized successfully.");
        } catch (IOException e) {
            ServiceException serviceException = new ServiceException(e.getMessage(), e);
            LOGGER.log(Level.ERROR, serviceException.getMessage());
            throw serviceException;
        }
        return coffeeData;
    }
}
