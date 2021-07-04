package sample;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.*;


@SuppressWarnings("ALL")
public class ViewTableController {

    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button changeBtn;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane tablePane;
    @FXML
    private TableView tableView;

    private DBConnection conn = null;
    private Vehicle selectedVehicle = null;
    private Driver selectedDriver = null;
    private Tariff selectedTariff = null;
    private Order selectedOrder = null;
    private Payment selectedPayment = null;
    private static String tableType = null;

    @FXML
    void initialize() {
        tablePane.prefHeightProperty().bind(scrollPane.heightProperty());
        tablePane.prefWidthProperty().bind(scrollPane.widthProperty());
        conn = HomePageController.getConn();
        updateTable(true);
        TableView.TableViewSelectionModel<?> selectionModel = tableView.getSelectionModel();

        mainMenuBtn.setOnAction(actionEvent -> {
            mainMenuBtn.getScene().getWindow().hide();
            loadStage("/sample/homePage.fxml");
        });

        addBtn.setOnAction(actionEvent -> {
            IACController.setIsInsert(true);
            loadStage("/sample/IAC.fxml");
        });

        deleteBtn.setOnAction(actionEvent -> {
            switch (tableType) {
                case "Vehicles":
                    selectedVehicle = (Vehicle) selectionModel.selectedItemProperty().getValue();
                    if (selectedVehicle != null) {
                        Vehicles vehicles = new Vehicles(conn);
                        vehicles.delete(selectedVehicle);
                        updateTable(false);
                    } else throwAlert();
                    break;
                case "Drivers":
                    selectedDriver = (Driver) selectionModel.selectedItemProperty().getValue();
                    if (selectedDriver != null) {
                        Drivers drivers = new Drivers(conn);
                        drivers.delete(selectedDriver);
                        updateTable(false);
                    } else throwAlert();
                    break;
                case "Tariffs":
                    selectedTariff = (Tariff) selectionModel.selectedItemProperty().getValue();
                    if (selectedTariff != null) {
                        Tariffs tariffs = new Tariffs(conn);
                        tariffs.delete(selectedTariff);
                        updateTable(false);
                    } else throwAlert();
                    break;
                case "Orders":
                    selectedOrder = (Order) selectionModel.selectedItemProperty().getValue();
                    if (selectedOrder != null) {
                        Orders orders = new Orders(conn);
                        orders.delete(selectedOrder);
                        updateTable(false);
                    } else throwAlert();
                    break;
                case "PaymentInfo":
                    selectedPayment = (Payment) selectionModel.selectedItemProperty().getValue();
                    if (selectedPayment != null) {
                        PaymentInfo paymentInfo = new PaymentInfo(conn);
                        paymentInfo.delete(selectedPayment);
                        updateTable(false);
                    } else throwAlert();
                    break;
            }
        });

        changeBtn.setOnAction(actionEvent -> {
            IACController.setIsInsert(false);
            switch (tableType) {
                case "Vehicles":
                    selectedVehicle = (Vehicle) selectionModel.selectedItemProperty().getValue();
                    if (selectedVehicle != null) loadStage("/sample/IAC.fxml");
                    else throwAlert();
                    break;
                case "Drivers":
                    selectedDriver = (Driver) selectionModel.selectedItemProperty().getValue();
                    if (selectedDriver != null) loadStage("/sample/IAC.fxml");
                    else throwAlert();
                    break;
                case "Tariffs":
                    selectedTariff = (Tariff) selectionModel.selectedItemProperty().getValue();
                    if (selectedTariff != null) loadStage("/sample/IAC.fxml");
                    else throwAlert();
                break;
                case "Orders":
                    selectedOrder = (Order) selectionModel.selectedItemProperty().getValue();
                    if (selectedOrder != null) loadStage("/sample/IAC.fxml");
                    else throwAlert();
                break;
                case "PaymentInfo":
                    selectedPayment = (Payment) selectionModel.selectedItemProperty().getValue();
                    if (selectedPayment != null) loadStage("/sample/IAC.fxml");
                    else throwAlert();
                    break;
            }
        });

    }

    private void loadStage(String location){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(location));

        if(location.equals("/sample/IAC.fxml"))
            IACController.setParent(this);

        try{
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(tableType);
        if(location.equals("/sample/homePage.fxml")) stage.setTitle("Database App");
        stage.setResizable(false);
        stage.getIcons().add(new Image("/dbIco.png"));
        stage.showAndWait();
    }

    @SuppressWarnings("unchecked")
    protected void updateTable(boolean isNew){
        try {
            if (isNew) {
                tableView.getItems().removeAll();
                tableView.getColumns().removeAll();
            }
            switch (tableType) {
                case "Vehicles":
                    Vehicles vehicles = new Vehicles(conn);
                    tableView.setItems(vehicles.getAll());
                    if (isNew)
                        createColumns(new String[]{"idVehicles", "vehicleNum", "brand", "type", "year", "color", "vehicleClass"});
                    break;
                case "Drivers":
                    Drivers drivers = new Drivers(conn);
                    tableView.setItems(drivers.getAll());
                    if (isNew)
                        createColumns(new String[]{"idDrivers", "surname", "name", "secondName", "birthday", "passSeries", "passNumber", "vehicle"});
                    break;
                case "Tariffs":
                    Tariffs tariffs = new Tariffs(conn);
                    tableView.setItems(tariffs.getAll());
                    if (isNew)
                        createColumns(new String[]{"idTariffs", "name", "timeOfDay", "distanceFromCityCenter", "pricePerKM"});
                    break;
                case "Orders":
                    Orders orders = new Orders(conn);
                    tableView.setItems(orders.getAll());
                    if (isNew)
                        createColumns(new String[]{"idOrders", "idDrivers", "length", "passengerCount", "addressSrc", "addressDst", "date", "time", "status"});
                    break;
                case "PaymentInfo":
                    PaymentInfo paymentInfo = new PaymentInfo(conn);
                    tableView.setItems(paymentInfo.getAll());
                    if (isNew)
                        createColumns(new String[]{"idOrders", "idTariffs", "mileage", "price"});
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void createColumns(String[] cols){
        for(int i = 0; i < cols.length; i++){
            switch (tableType){
                case "Vehicles":
                    if (i == 0) {
                        TableColumn<Vehicle, Integer> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else if (i == 4) {
                        TableColumn<Vehicle, Date> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellFactory(column -> {
                            TableCell<Vehicle, Date> cell = new TableCell<Vehicle, Date>() {
                                private SimpleDateFormat format = new SimpleDateFormat("yyyy");
                                @Override
                                protected void updateItem(Date item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if(empty) setText(null);
                                    else this.setText(format.format(item));
                                }
                            };
                            return cell;
                        });
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else {
                        TableColumn<Vehicle, String> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    }
                    break;
                case "Drivers":
                    if (i == 0 || i == 7) {
                        TableColumn<Driver, Integer> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else if (i == 4) {
                        TableColumn<Driver, Date> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else {
                        TableColumn<Driver, String> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    }
                    break;
                case "Tariffs":
                    if (i == 1 || i == 2){
                        TableColumn<Tariff, String> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else {
                        TableColumn<Tariff, Integer> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    }
                    break;
                case "Orders":
                    if (i == 0 || i == 1 || i == 3) {
                        TableColumn<Order, Integer> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else if (i == 2) {
                        TableColumn<Order, Float> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else if (i == 6) {
                        TableColumn<Order, Date> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else if (i == 7) {
                        TableColumn<Order, Time> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else {
                        TableColumn<Order, String> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    }
                    break;
                case "PaymentInfo":
                    if (i == 0 || i == 1) {
                        TableColumn<Payment, Integer> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    } else {
                        TableColumn<Payment, Float> tableColumn = new TableColumn<>(cols[i]);
                        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cols[i]));
                        tableView.getColumns().add(tableColumn);
                    }
                    break;
            }
        }
    }

    private void throwAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Выберите строку, чтобы выполнить это действие");
        alert.showAndWait();
    }

    public static String getTableType() {
        return tableType;
    }

    protected Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    protected Driver getSelectedDriver() {
        return selectedDriver;
    }

    protected Tariff getSelectedTariff() {
        return selectedTariff;
    }

    protected Order getSelectedOrder() {
        return selectedOrder;
    }

    protected Payment getSelectedPayment() {
        return selectedPayment;
    }

    public static void setTableType(String type){
        tableType = type;
    }
}
