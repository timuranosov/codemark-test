package codemark.test.api.dto;

import codemark.test.validation.PasswordConstraint;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDTO {
    private Long id;
    @NotBlank(message = "User name is mandatory")
    private String name;
    @NotBlank(message = "User login is mandatory")
    private String login;
    @NotBlank(message = "User password is mandatory")
    @PasswordConstraint
    private String password;
    private Set<RoleDTO> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
