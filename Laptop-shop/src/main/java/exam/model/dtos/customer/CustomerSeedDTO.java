package exam.model.dtos.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerSeedDTO {

    @Size(min = 2)
    @NotNull
    private String firstName;

    @Size(min = 2)
    @NotNull
    private String lastName;

    @Email
    private String email;

    @NotNull
    private String registeredOn;

    private TownNameDTO town;

    public CustomerSeedDTO(){}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public TownNameDTO getTown() {
        return town;
    }
}
