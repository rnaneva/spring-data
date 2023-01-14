package exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;

@Component
public class XmlParserImpl implements XmlParser {

    @Override
    public <T> T fromFile(Class<T> tClass, Path path) throws JAXBException, FileNotFoundException {

        FileReader fileReader = new FileReader(path.toFile());
        JAXBContext context = JAXBContext.newInstance(tClass);
        return (T) context.createUnmarshaller().unmarshal(fileReader);

    }
}
