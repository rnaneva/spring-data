package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.customer.CustomerSeedDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.constants.Messages.INVALID_CUSTOMER;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static exam.constants.Paths.CUSTOMERS_JSON;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private TownService townService;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtil validationUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               TownService townService,
                               ModelMapper modelMapper,
                               Gson gson,
                               ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(CUSTOMERS_JSON);
    }

    @Override
    public String importCustomers() throws IOException {

        CustomerSeedDTO[] customerSeedDTOs =
                gson.fromJson(readCustomersFileContent(), CustomerSeedDTO[].class);

        return Arrays.stream(customerSeedDTOs)
                .map(this::importCustomer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCustomer(CustomerSeedDTO customerSeedDTO) {
        boolean isValid = validationUtil.isValid(customerSeedDTO);
        Optional<Customer> optionalCustomer = findByEmail(customerSeedDTO.getEmail());
        Optional<Town> optionalTown = townService.findByName(customerSeedDTO.getTown().getTownName());

        if(!isValid || optionalCustomer.isPresent() || optionalTown.isEmpty()){
            return INVALID_CUSTOMER;
        }

        Customer customer = modelMapper.map(customerSeedDTO, Customer.class);
        customer.setTown(optionalTown.get());
        customerRepository.save(customer);

        return SUCCESSFULLY_IMPORTED + customer.info();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
