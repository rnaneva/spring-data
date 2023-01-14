package exam.service;

import exam.model.entities.Customer;

import java.io.IOException;
import java.util.Optional;

//ToDo - Implement all methods
public interface CustomerService {

    boolean areImported();

    String readCustomersFileContent() throws IOException;

    String importCustomers() throws IOException;

    Optional<Customer> findByEmail(String email);


}
