package exam.service;

import exam.model.entities.Laptop;

import java.io.IOException;
import java.util.Optional;

//ToDo - Implement all methods
public interface LaptopService {
    boolean areImported();

    String readLaptopsFileContent() throws IOException;

    String importLaptops() throws IOException;

    String exportBestLaptops();

    Optional<Laptop> findByMacAddress(String macAddress);

}
