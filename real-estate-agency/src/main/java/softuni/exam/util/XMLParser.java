package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Component
public class XMLParser {

 public <T> T fromXMLFile(Class<T> object, Path path) throws JAXBException, FileNotFoundException {
     Unmarshaller unmarshaller = JAXBContext.newInstance(object).createUnmarshaller();
     return (T) unmarshaller.unmarshal(new FileReader(path.toFile()));
 }
}
