package sample.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;

@XmlRootElement(name = "vehicle")
@XmlType(propOrder = {"idVehicles", "vehicleNum", "brand", "type", "year", "color", "vehicleClass"})
public class Vehicle {
    private int idVehicles;
    private String vehicleNum;
    private String brand;
    private String type;
    private Date year;
    private String color;
    private String vehicleClass;

    public Vehicle() { }
    public Vehicle(int idVehicles, String vehicleNum, String brand, String type, Date year, String color, String vehicleClass) {
        this.idVehicles = idVehicles;
        this.vehicleNum = vehicleNum;
        this.brand = brand;
        this.type = type;
        this.year = year;
        this.color = color;
        this.vehicleClass = vehicleClass;
    }

    @XmlElement(name = "idVehicles")
    public int getIdVehicles() {
        return idVehicles;
    }

    public void setIdVehicles(int idVehicles) {
        this.idVehicles = idVehicles;
    }

    @XmlElement(name = "vehicleNum")
    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    @XmlElement(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @XmlElement(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "year")
    @XmlJavaTypeAdapter(YearAdapter.class)
    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    @XmlElement(name = "color")
    public String getColor() { return color; }

    public void setColor(String color) {
        this.color = color;
    }

    @XmlElement(name = "vehicleClass")
    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }
}
