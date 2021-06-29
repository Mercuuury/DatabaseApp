package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.*;
import java.sql.Date;
import java.sql.Time;

public class IACController {
    @FXML
    private Button readyBtn;
    @FXML
    private AnchorPane parent_AnchorPane;

    @FXML
    private AnchorPane v_AnchorPane;
    @FXML
    private TextField v_vehicleNum;
    @FXML
    private TextField v_year;
    @FXML
    private TextField v_idVehicles;
    @FXML
    private TextField v_brand;
    @FXML
    private TextField v_type;
    @FXML
    private TextField v_color;
    @FXML
    private TextField v_vehicleClass;

    @FXML
    private AnchorPane d_AnchorPane;
    @FXML
    private TextField d_surname;
    @FXML
    private TextField d_vehicle;
    @FXML
    private TextField d_idDrivers;
    @FXML
    private TextField d_name;
    @FXML
    private TextField d_secondName;
    @FXML
    private TextField d_passSeries;
    @FXML
    private DatePicker d_birthday;
    @FXML
    private TextField d_passNumber;

    @FXML
    private AnchorPane t_AnchorPane;
    @FXML
    private TextField t_idTariffs;
    @FXML
    private TextField t_name;
    @FXML
    private TextField t_timeOfDay;
    @FXML
    private TextField t_distanceFromCC;
    @FXML
    private TextField t_pricePerKM;

    @FXML
    private AnchorPane o_AnchorPane;
    @FXML
    private TextField o_idDrivers;
    @FXML
    private TextField o_addressSrc;
    @FXML
    private TextField o_time;
    @FXML
    private TextField o_status;
    @FXML
    private TextField o_idOrders;
    @FXML
    private TextField o_length;
    @FXML
    private TextField o_passengerCount;
    @FXML
    private TextField o_addressDst;
    @FXML
    private DatePicker o_date;

    @FXML
    private AnchorPane p_AnchorPane;
    @FXML
    private TextField p_idTariffs;
    @FXML
    private TextField p_mileage;
    @FXML
    private TextField p_idOrders;
    @FXML
    private TextField p_price;

    private static ViewTableController controller;
    private static boolean isInsert;

    @FXML
    void initialize() {
        hideAllPanes();

        switch (ViewTableController.getTableType()) {
            case "Vehicles":
                v_AnchorPane.setVisible(true);
                v_AnchorPane.setManaged(true);
                processVehiclesFields();
            break;
            case "Drivers":
                d_AnchorPane.setVisible(true);
                d_AnchorPane.setManaged(true);
                processDriversFields();
            break;
            case "Tariffs":
                t_AnchorPane.setVisible(true);
                t_AnchorPane.setManaged(true);
                processTariffsFields();
                break;
            case "Orders":
                o_AnchorPane.setVisible(true);
                o_AnchorPane.setManaged(true);
                processOrdersFields();
                break;
            case "PaymentInfo":
                p_AnchorPane.setVisible(true);
                p_AnchorPane.setManaged(true);
                processPaymentInfoFields();
                break;
        }
    }

    public static void setParent (ViewTableController c){
        controller = c;
    }

    public static void setIsInsert(boolean tf){
        isInsert = tf;
    }

    private void hideAllPanes(){
        for(Node pane : parent_AnchorPane.getChildren())
            if(pane.getId().contains("_AnchorPane")){
                pane.setVisible(false);
                pane.setManaged(false);
            }
    }

    private int checkForErrors(int paneNum){
        AnchorPane[] panes = {v_AnchorPane, d_AnchorPane, t_AnchorPane, o_AnchorPane, p_AnchorPane};
        int errorFields = 0;
        for(Node i : panes[paneNum].getChildren()){
            if(i.getClass().equals(TextField.class))
                if(((TextField) i).getText().equals("")) {
                    i.setStyle("-fx-border-color: red; -fx-border-radius: 4;");
                    errorFields++;
                }
        }
        return errorFields;
    }

   private void processVehiclesFields(){
        if(!isInsert){
            v_idVehicles.setText(Integer.toString(controller.getSelectedVehicle().getIdVehicles()));
            v_vehicleNum.setText(controller.getSelectedVehicle().getVehicleNum());
            v_brand.setText(controller.getSelectedVehicle().getBrand());
            v_type.setText(controller.getSelectedVehicle().getType());
            v_year.setText(controller.getSelectedVehicle().getYear().toString().split("-")[0]);
            v_color.setText(controller.getSelectedVehicle().getColor());
            v_vehicleClass.setText(controller.getSelectedVehicle().getVehicleClass());
        } else d_idDrivers.setText("A-I");

        readyBtn.setOnAction(actionEvent -> {
            boolean ok = false;
            if(isInsert){
                if (checkForErrors(0) == 0) {
                    DBConnection conn = HomePageController.getConn();
                    Vehicles vehicles = new Vehicles(conn);
                    Vehicle vehicle = new Vehicle(0, v_vehicleNum.getText(), v_brand.getText(),
                            v_type.getText(), new Date(Integer.parseInt(v_year.getText()), 1, 1), v_color.getText(), v_vehicleClass.getText());
                    vehicles.insert(vehicle);
                    ok = true;
                }
            } else {
                DBConnection conn = HomePageController.getConn();
                Vehicles vehicles = new Vehicles(conn);
                Vehicle vehicle = new Vehicle(Integer.parseInt(v_idVehicles.getText()), v_vehicleNum.getText(), v_brand.getText(),
                        v_type.getText(), new Date(Integer.parseInt(v_year.getText()), 1, 1), v_color.getText(), v_vehicleClass.getText());
                vehicles.update(vehicle);
                ok = true;
            }
            if(ok){
                controller.updateTable(false);
                Stage stage = (Stage) readyBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void processDriversFields() {
        if(!isInsert){
            d_idDrivers.setText(Integer.toString(controller.getSelectedDriver().getIdDrivers()));
            d_surname.setText(controller.getSelectedDriver().getSurname());
            d_name.setText(controller.getSelectedDriver().getName());
            d_secondName.setText(controller.getSelectedDriver().getSecondName());
            d_birthday.setValue(controller.getSelectedDriver().getBirthday().toLocalDate());
            d_passSeries.setText(controller.getSelectedDriver().getPassSeries());
            d_passNumber.setText(controller.getSelectedDriver().getPassNumber());
            d_vehicle.setText(Integer.toString(controller.getSelectedDriver().getVehicle()));
        } else d_idDrivers.setText("A-I");

        readyBtn.setOnAction(actionEvent -> {
            boolean ok = false;
            if(isInsert){
                if (checkForErrors(1) == 0) {
                    DBConnection conn = HomePageController.getConn();
                    Drivers drivers = new Drivers(conn);
                    Driver driver = new Driver(0, d_surname.getText(), d_name.getText(), d_secondName.getText(),
                            Date.valueOf(d_birthday.getValue()), d_passSeries.getText(), d_passNumber.getText(), Integer.parseInt(d_vehicle.getText()));
                    drivers.insert(driver);
                    ok = true;
                }
            } else {
                DBConnection conn = HomePageController.getConn();
                Drivers drivers = new Drivers(conn);
                Driver driver = new Driver(Integer.parseInt(d_idDrivers.getText()), d_surname.getText(), d_name.getText(), d_secondName.getText(),
                        Date.valueOf(d_birthday.getValue()), d_passSeries.getText(), d_passNumber.getText(), Integer.parseInt(d_vehicle.getText()));
                drivers.update(driver);
                ok = true;
            }
            if(ok){
                controller.updateTable(false);
                Stage stage = (Stage) readyBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void processTariffsFields() {
        if(!isInsert){
            t_idTariffs.setText(Integer.toString(controller.getSelectedTariff().getIdTariffs()));
            t_name.setText(controller.getSelectedTariff().getName());
            t_timeOfDay.setText(controller.getSelectedTariff().getTimeOfDay());
            t_distanceFromCC.setText(Integer.toString(controller.getSelectedTariff().getDistanceFromCityCenter()));
            t_pricePerKM.setText(Integer.toString(controller.getSelectedTariff().getPricePerKM()));
        } else t_idTariffs.setText("A-I");

        readyBtn.setOnAction(actionEvent -> {
            boolean ok = false;
            if(isInsert){
                if (checkForErrors(2) == 0) {
                    DBConnection conn = HomePageController.getConn();
                    Tariffs tariffs = new Tariffs(conn);
                    Tariff tariff = new Tariff(0, t_name.getText(), t_timeOfDay.getText(),
                            Integer.parseInt(t_distanceFromCC.getText()), Integer.parseInt(t_pricePerKM.getText()));
                    tariffs.insert(tariff);
                    ok = true;
                }
            } else {
                DBConnection conn = HomePageController.getConn();
                Tariffs tariffs = new Tariffs(conn);
                Tariff tariff = new Tariff(Integer.parseInt(t_idTariffs.getText()), t_name.getText(), t_timeOfDay.getText(),
                        Integer.parseInt(t_distanceFromCC.getText()), Integer.parseInt(t_pricePerKM.getText()));
                tariffs.update(tariff);
                ok = true;
            }
            if(ok){
                controller.updateTable(false);
                Stage stage = (Stage) readyBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void processOrdersFields() {
        if(!isInsert){
            o_idOrders.setText(Integer.toString(controller.getSelectedOrder().getIdOrders()));
            o_idDrivers.setText(Integer.toString(controller.getSelectedOrder().getIdDrivers()));
            o_length.setText(Float.toString(controller.getSelectedOrder().getLength()));
            o_passengerCount.setText(Integer.toString(controller.getSelectedOrder().getPassengerCount()));
            o_addressSrc.setText(controller.getSelectedOrder().getAddressSrc());
            o_addressDst.setText(controller.getSelectedOrder().getAddressDst());
            o_date.setValue(controller.getSelectedOrder().getDate().toLocalDate());
            o_time.setText(controller.getSelectedOrder().getTime().toString());
            o_status.setText(controller.getSelectedOrder().getStatus());
        } else o_idOrders.setText("A-I");

        readyBtn.setOnAction(actionEvent -> {
            boolean ok = false;
            if(isInsert){
                if (checkForErrors(3) == 0) {
                    DBConnection conn = HomePageController.getConn();
                    Orders orders = new Orders(conn);
                    Order order = new Order(0, Integer.parseInt(o_idDrivers.getText()), Float.parseFloat(o_length.getText()),
                            Integer.parseInt(o_passengerCount.getText()), o_addressSrc.getText(), o_addressDst.getText(),
                            Date.valueOf(o_date.getValue()), Time.valueOf(o_time.getText()), o_status.getText());
                    orders.insert(order);
                    ok = true;
                }
            } else {
                DBConnection conn = HomePageController.getConn();
                Orders orders = new Orders(conn);
                Order order = new Order(Integer.parseInt(o_idOrders.getText()), Integer.parseInt(o_idDrivers.getText()),
                        Float.parseFloat(o_length.getText()), Integer.parseInt(o_passengerCount.getText()),
                        o_addressSrc.getText(), o_addressDst.getText(), Date.valueOf(o_date.getValue()),
                        Time.valueOf(o_time.getText()), o_status.getText());
                orders.update(order);
                ok = true;
            }
            if(ok){
                controller.updateTable(false);
                Stage stage = (Stage) readyBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void processPaymentInfoFields() {
        if(!isInsert){
            p_idOrders.setText(Integer.toString(controller.getSelectedPayment().getIdOrders()));
            p_idTariffs.setText(Integer.toString(controller.getSelectedPayment().getIdTariffs()));
            p_mileage.setText(Float.toString(controller.getSelectedPayment().getMileage()));
            p_price.setText(Float.toString(controller.getSelectedPayment().getPrice()));
            p_idOrders.setDisable(true);
            p_idTariffs.setDisable(true);
        }

        readyBtn.setOnAction(actionEvent -> {
            boolean ok = false;
            if(isInsert){
                if (checkForErrors(4) == 1) {
                    DBConnection conn = HomePageController.getConn();
                    PaymentInfo paymentInfo = new PaymentInfo(conn);
                    Payment payment = new Payment(Integer.parseInt(p_idOrders.getText()), Integer.parseInt(p_idTariffs.getText()),
                            Float.parseFloat(p_mileage.getText()), 0);
                    paymentInfo.insert(payment);
                    ok = true;
                }
            } else {
                DBConnection conn = HomePageController.getConn();
                PaymentInfo paymentInfo = new PaymentInfo(conn);
                Payment payment = new Payment(Integer.parseInt(p_idOrders.getText()), Integer.parseInt(p_idTariffs.getText()),
                        Float.parseFloat(p_mileage.getText()), Float.parseFloat(p_price.getText()));
                paymentInfo.update(payment);
                ok = true;
            }
            if(ok){
                controller.updateTable(false);
                Stage stage = (Stage) readyBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
}
