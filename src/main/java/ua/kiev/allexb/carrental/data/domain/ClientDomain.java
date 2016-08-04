package ua.kiev.allexb.carrental.data.domain;

import ua.kiev.allexb.carrental.model.Client;

import java.util.Date;

/**
 * @author allexb
 * @version 1.0 04.08.2016
 */
public class ClientDomain extends AbstractDomain {

    private String firstName;
    private String lastName;
    private Date birthday;
    private int dLNumber;
    private int lengthOfDrivingExperience;

    public ClientDomain(Long id, String firstName, String lastName, Date birthday, int dLNumber, int lengthOfDrivingExperience) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.dLNumber = dLNumber;
        this.lengthOfDrivingExperience = lengthOfDrivingExperience;
    }

    public ClientDomain(Client client) {
        this.setClient(client);
    }

    public ClientDomain() {
    }

    public Client getClient() {
        return new Client(this.getId(), this.firstName, this.lastName, this.birthday, this.dLNumber,
                this.lengthOfDrivingExperience);
    }

    public void setClient(Client client) {
        super.setId(client.getId());
        this.firstName = client.getFirstName();
        this. lastName = client.getLastName();
        this.birthday = client.getBirthday();
        this.dLNumber = client.getdLNumber();
        this.lengthOfDrivingExperience = client.getLengthOfDrivingExperience();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getdLNumber() {
        return dLNumber;
    }

    public void setdLNumber(int dLNumber) {
        this.dLNumber = dLNumber;
    }

    public int getLengthOfDrivingExperience() {
        return lengthOfDrivingExperience;
    }

    public void setLengthOfDrivingExperience(int lengthOfDrivingExperience) {
        this.lengthOfDrivingExperience = lengthOfDrivingExperience;
    }
}
