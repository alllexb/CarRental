package ua.kiev.allexb.carrental.data.domain;

import ua.kiev.allexb.carrental.model.Administrator;

/**
 * @author allexb
 * @version 1.0 05.08.2016
 */
public class AdministratorDomain extends AbstractDomain {

    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;

    public AdministratorDomain(Long id, String firstName, String lastName, String email, String login, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public AdministratorDomain(Administrator administrator) {
        this.setAdministrator(administrator);
    }

    public AdministratorDomain() {
    }

    public Administrator getAdministrator() {
        return new Administrator(this.getId(), this.firstName, this.lastName, this.email, this.login, this.password);
    }

    public void setAdministrator(Administrator administrator) {
        super.setId(administrator.getId());
        this.firstName = administrator.getFirstName();
        this.lastName = administrator.getLastName();
        this.email = administrator.getEmail();
        this.login = administrator.getLogin();
        this.password = administrator.getPassword();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
