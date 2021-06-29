package sample;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sample.model.*;

public class DOMxmlReader {
    private static File xmlFile;
    private static Document document;

    public static void setXmlFile(File file) {
        xmlFile = file;
    }

    public static void importXML(){
        List<?> importDataSet = parseXML();
        assert importDataSet != null;
        switch (document.getDocumentElement().getNodeName()) {
            case "vehicles":
                Vehicles vehicles = new Vehicles(HomePageController.getConn());
                for (Object o : importDataSet) vehicles.insert((Vehicle) o);
                break;
            case "drivers":
                Drivers drivers = new Drivers(HomePageController.getConn());
                for (Object o : importDataSet) drivers.insert((Driver) o);
                break;
            case "tariffs":
                Tariffs tariffs = new Tariffs(HomePageController.getConn());
                for (Object o : importDataSet) tariffs.insert((Tariff) o);
                break;
            case "orders":
                Orders orders = new Orders(HomePageController.getConn());
                for (Object o : importDataSet) orders.insert((Order) o);
                break;
            case "paymentInfo":
                PaymentInfo paymentInfo = new PaymentInfo(HomePageController.getConn());
                for (Object o : importDataSet) paymentInfo.insert((Payment) o);
                break;
        }
    }

    public static List<?> parseXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList;
            switch (document.getDocumentElement().getNodeName()) {
                case "vehicles":
                    nodeList = document.getElementsByTagName("vehicle");
                    List<Vehicle> vehicleList = new ArrayList<>();
                    for (int i = 0; i < nodeList.getLength(); i++)
                        vehicleList.add(getVehicle(nodeList.item(i)));
                    return vehicleList;
                case "drivers":
                    nodeList = document.getElementsByTagName("driver");
                    List<Driver> driverList = new ArrayList<>();
                    for (int i = 0; i < nodeList.getLength(); i++)
                        driverList.add(getDriver(nodeList.item(i)));
                    return driverList;
                case "tariffs":
                    nodeList = document.getElementsByTagName("tariff");
                    List<Tariff> tariffList = new ArrayList<>();
                    for (int i = 0; i < nodeList.getLength(); i++)
                        tariffList.add(getTariff(nodeList.item(i)));
                    return tariffList;
                case "orders":
                    nodeList = document.getElementsByTagName("order");
                    List<Order> orderList = new ArrayList<>();
                    for (int i = 0; i < nodeList.getLength(); i++)
                        orderList.add(getOrder(nodeList.item(i)));
                    return orderList;
                case "paymentInfo":
                    nodeList = document.getElementsByTagName("payment");
                    List<Payment> paymentList = new ArrayList<>();
                    for (int i = 0; i < nodeList.getLength(); i++)
                        paymentList.add(getPayment(nodeList.item(i)));
                    return paymentList;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private static Vehicle getVehicle(Node node) {
        Vehicle vehicle = new Vehicle(0, null, null, null, null, null, null);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            vehicle.setIdVehicles(Integer.parseInt(getTagValue("idVehicles", element)));
            vehicle.setVehicleNum(getTagValue("vehicleNum", element));
            vehicle.setBrand(getTagValue("brand", element));
            vehicle.setType(getTagValue("type", element));
            vehicle.setYear(new Date(Integer.parseInt(getTagValue("year", element)), 1, 1));
            vehicle.setColor(getTagValue("color", element));
            vehicle.setVehicleClass(getTagValue("vehicleClass", element));
        }
        return vehicle;
    }

    private static Driver getDriver(Node node) {
        Driver driver = new Driver(0, null, null, null, null, null, null , 0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            driver.setIdDrivers(Integer.parseInt(getTagValue("idDrivers", element)));
            driver.setSurname(getTagValue("surname", element));
            driver.setName(getTagValue("name", element));
            driver.setSecondName(getTagValue("secondName", element));
            driver.setBirthday(Date.valueOf(getTagValue("birthday", element)));
            driver.setPassSeries(getTagValue("passSeries", element));
            driver.setPassNumber(getTagValue("passNumber", element));
            driver.setVehicle(Integer.parseInt(getTagValue("vehicle", element)));
        }
        return driver;
    }

    private static Tariff getTariff(Node node) {
        Tariff tariff = new Tariff(0, null, null, 0, 0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            tariff.setIdTariffs(Integer.parseInt(getTagValue("idTariffs", element)));
            tariff.setName(getTagValue("name", element));
            tariff.setTimeOfDay(getTagValue("timeOfDay", element));
            tariff.setDistanceFromCityCenter(Integer.parseInt(getTagValue("distanceFromCityCenter", element)));
            tariff.setPricePerKM(Integer.parseInt(getTagValue("pricePerKM", element)));
        }
        return tariff;
    }

    private static Order getOrder(Node node) {
        Order order = new Order(0, 0, 0, 0, null, null, null,null, null);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            order.setIdOrders(Integer.parseInt(getTagValue("idOrders", element)));
            order.setIdDrivers(Integer.parseInt(getTagValue("idDrivers", element)));
            order.setLength(Float.parseFloat(getTagValue("length", element)));
            order.setPassengerCount(Integer.parseInt(getTagValue("passengerCount", element)));
            order.setAddressSrc(getTagValue("addressSrc", element));
            order.setAddressDst(getTagValue("addressDst", element));
            order.setDate(Date.valueOf(getTagValue("date", element)));
            order.setTime(Time.valueOf(getTagValue("time", element)));
            order.setStatus(getTagValue("status", element));
        }
        return order;
    }

    private static Payment getPayment(Node node) {
        Payment payment = new Payment(0, 0, 0, 0);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            payment.setIdOrders(Integer.parseInt(getTagValue("idOrders", element)));
            payment.setIdTariffs(Integer.parseInt(getTagValue("idTariffs", element)));
            payment.setMileage(Float.parseFloat(getTagValue("mileage", element)));
            payment.setPrice(Float.parseFloat(getTagValue("price", element)));
        }
        return payment;
    }
}