package sample.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "tariff")
@XmlType(propOrder = {"idTariffs", "name", "timeOfDay", "distanceFromCityCenter", "pricePerKM"})
public class Tariff {
    private int idTariffs;
    private String name;
    private String timeOfDay;
    private int distanceFromCityCenter;
    private int pricePerKM;

    public Tariff() { }
    public Tariff(int idTariffs, String name, String timeOfDay, int distanceFromCityCenter, int pricePerKM) {
        this.idTariffs = idTariffs;
        this.name = name;
        this.timeOfDay = timeOfDay;
        this.distanceFromCityCenter = distanceFromCityCenter;
        this.pricePerKM = pricePerKM;
    }

    @XmlElement(name = "idTariffs")
    public int getIdTariffs() {
        return idTariffs;
    }

    public void setIdTariffs(int idTariffs) {
        this.idTariffs = idTariffs;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "timeOfDay")
    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    @XmlElement(name = "distanceFromCityCenter")
    public int getDistanceFromCityCenter() {
        return distanceFromCityCenter;
    }

    public void setDistanceFromCityCenter(int distanceFromCityCenter) {
        this.distanceFromCityCenter = distanceFromCityCenter;
    }

    @XmlElement(name = "pricePerKM")
    public int getPricePerKM() {
        return pricePerKM;
    }

    public void setPricePerKM(int pricePerKM) {
        this.pricePerKM = pricePerKM;
    }
}
