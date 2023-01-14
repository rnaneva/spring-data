package exam.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface XmlParser {

    <T>T fromFile(Class<T> tClass, Path path) throws JAXBException, FileNotFoundException;
}
