package sample.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;

@XmlRootElement(name = "driver")
@XmlType(propOrder = {"idDrivers", "surname", "name", "secondName", "birthday", "passSeries", "passNumber", "vehicle"})
public class Driver {
    private int idDrivers;
    private String surname;
    private String name;
    private String secondName;
    private Date birthday;
    private String passSeries;
    private String passNumber;
    private int vehicle;

    public Driver() { }
    public Driver(int idDrivers, String surname, String name, String secondName, Date birthday, String passSeries, String passNumber, int vehicle) {
        this.idDrivers = idDrivers;
        this.surname = surname;
        this.name = name;
        this.secondName = secondName;
        this.birthday = birthday;
        this.passSeries = passSeries;
        this.passNumber = passNumber;
        this.vehicle = vehicle;
    }

    @XmlElement(name = "idDrivers")
    public int getIdDrivers() {
        return idDrivers;
    }

    public void setIdDrivers(int idDrivers) {
        this.idDrivers = idDrivers;
    }

    @XmlElement(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "secondName")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @XmlElement(name = "passSeries")
    public String getPassSeries() {
        return passSeries;
    }

    public void setPassSeries(String passSeries) {
        this.passSeries = passSeries;
    }

    @XmlElement(name = "passNumber")
    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }

    @XmlElement(name = "vehicle")
    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }
}
