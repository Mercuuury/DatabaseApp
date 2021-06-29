package sample.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;
import java.sql.Time;

@XmlRootElement(name = "order")
@XmlType(propOrder = {"idOrders", "idDrivers", "length", "passengerCount", "addressSrc", "addressDst", "date", "time", "status"})
public class Order {
    private int idOrders;
    private int idDrivers;
    private float length;
    private int passengerCount;
    private String addressSrc;
    private String addressDst;
    private Date date;
    private Time time;
    private String status;

    public Order() { }
    public Order(int idOrders, int idDrivers, float length, int passengerCount, String addressSrc, String addressDst, Date date, Time time, String status) {
        this.idOrders = idOrders;
        this.idDrivers = idDrivers;
        this.length = length;
        this.passengerCount = passengerCount;
        this.addressSrc = addressSrc;
        this.addressDst = addressDst;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    @XmlElement(name = "idOrders")
    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(int idOrders) {
        this.idOrders = idOrders;
    }

    @XmlElement(name = "idDrivers")
    public int getIdDrivers() {
        return idDrivers;
    }

    public void setIdDrivers(int idDrivers) {
        this.idDrivers = idDrivers;
    }

    @XmlElement(name = "length")
    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    @XmlElement(name = "passengerCount")
    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    @XmlElement(name = "addressSrc")
    public String getAddressSrc() {
        return addressSrc;
    }

    public void setAddressSrc(String addressSrc) {
        this.addressSrc = addressSrc;
    }

    @XmlElement(name = "addressDst")
    public String getAddressDst() {
        return addressDst;
    }

    public void setAddressDst(String addressDst) {
        this.addressDst = addressDst;
    }

    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlElement(name = "time")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @XmlElement(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
