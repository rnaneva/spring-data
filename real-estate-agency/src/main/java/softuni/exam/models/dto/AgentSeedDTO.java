package softuni.exam.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AgentSeedDTO {

    @NotNull
    @Size(min = 2)
    private String firstName;

    @NotNull
    @Size(min = 2)
    private String lastName;
    private String town;

    @NotNull
    @Email
    private String email;

    public AgentSeedDTO() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTown() {
        return town;
    }

    public String getEmail() {
        return email;
    }
}
