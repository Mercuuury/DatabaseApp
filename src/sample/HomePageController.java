package sample;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HomePageController {

    @FXML
    private Label connSuccessLbl;
    @FXML
    private Button driversBtn;
    @FXML
    private Button vehiclesBtn;
    @FXML
    private Button tariffsBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private Button paymentInfoBtn;
    @FXML
    private Button importXMLBtn;
    @FXML
    private Button exportXMLBtn;

    private static final DBConnection conn = new DBConnection("jdbc:mysql://std-mysql:3306/std_1598_course", "std_1598_course",
            "Ux8YRiP0", "com.mysql.cj.jdbc.Driver", "utf8");

    @FXML
    void initialize() {
        if(conn.getConnection() == null){
            connSuccessLbl.setText("Ошибка");
            connSuccessLbl.setStyle("-fx-text-fill: red;");
        }

        vehiclesBtn.setOnAction(actionEvent -> loadStage("Vehicles"));

        driversBtn.setOnAction(actionEvent -> loadStage("Drivers"));

        tariffsBtn.setOnAction(actionEvent -> loadStage("Tariffs"));

        ordersBtn.setOnAction(actionEvent -> loadStage("Orders"));

        paymentInfoBtn.setOnAction(actionEvent -> loadStage("PaymentInfo"));

        importXMLBtn.setOnAction(actionEvent -> loadStage("Import XML"));

        exportXMLBtn.setOnAction(actionEvent -> loadStage("Export XML"));
    }

    private void loadStage(String type){
        FXMLLoader loader = new FXMLLoader();

        if(type.equals("Import XML"))
            loader.setLocation(getClass().getResource("/sample/importXML.fxml"));
        else if (type.equals("Export XML"))
            loader.setLocation(getClass().getResource("/sample/exportXML.fxml"));
        else {
            ViewTableController.setTableType(type);
            connSuccessLbl.getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/sample/viewTable.fxml"));
        }

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(type);
        stage.getIcons().add(new Image("/dbIco.png"));
        if(!type.contains("XML")){
            stage.setMinWidth(450);
            stage.setMinHeight(500);
            stage.show();
        } else {
            stage.setResizable(false);
            stage.showAndWait();
        }
    }

    protected static DBConnection getConn(){
        return conn;
    }
}
