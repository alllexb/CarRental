package ua.kiev.allexb.carrental.model;

import java.util.Date;

/**
 * @author allexb
 * @version 1.0 04.08.2016
 */
public class Client {

    private long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private int dLNumber;
    private int lengthOfDrivingExperience;

    public Client() {
    }

    public Client(long id, String firstName, String lastName, Date birthday, int dLNumber, int lengthOfDrivingExperience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.dLNumber = dLNumber;
        this.lengthOfDrivingExperience = lengthOfDrivingExperience;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthday.hashCode();
        result = 31 * result + dLNumber;
        result = 31 * result + lengthOfDrivingExperience;
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", dLNumber=" + dLNumber +
                ", lengthOfDrivingExperience=" + lengthOfDrivingExperience +
                '}';
    }
}
