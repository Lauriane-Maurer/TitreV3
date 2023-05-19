package fr.simplon.titrev3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserForm {

    @NotBlank
    @Size(min=1,max=255)
    private String login;
    @NotBlank
    @Size(min=1,max=255)
    private String password;
    @NotBlank
    @Size(min=1,max=255)
    private String confirmPassword;

    @NotNull
    @Size(min=1,max=255)
    private String firstname;


    public CreateUserForm() {
    }

    public CreateUserForm(String login, String password, String confirmPassword, String firstname) {
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstname = firstname;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
