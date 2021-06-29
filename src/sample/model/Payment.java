package sample.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "payment")
@XmlType(propOrder = {"idOrders", "idTariffs", "mileage", "price"})
public class Payment {
    private int idOrders;
    private int idTariffs;
    private float mileage;
    private float price;

    public Payment() { }
    public Payment(int idOrders, int idTariffs, float mileage, float price) {
        this.idOrders = idOrders;
        this.idTariffs = idTariffs;
        this.mileage = mileage;
        this.price = price;
    }

    @XmlElement(name = "idOrders")
    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }

    @XmlElement(name = "idTariffs")
    public int getIdTariffs() {
        return idTariffs;
    }

    public void setIdTariffs(int idTariffs) {
        this.idTariffs = idTariffs;
    }

    @XmlElement(name = "mileage")
    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    @XmlElement(name = "price")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
