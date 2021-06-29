package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import sample.model.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class exportXMLController {

    @FXML
    private TextField fileNameTextField;
    @FXML
    private TextField directoryTextField;
    @FXML
    private ComboBox<String> tablesComboBox;
    @FXML
    private Button saveBtn;

    @FXML
    void initialize(){
        tablesComboBox.getItems().addAll("Vehicles", "Drivers", "Tariffs", "Orders", "PaymentInfo");

        saveBtn.disableProperty().bind(fileNameTextField.textProperty().isEmpty()
                .or(tablesComboBox.getSelectionModel().selectedItemProperty().isNull()
                        .or(directoryTextField.textProperty().isEmpty())));
    }

    @FXML
    void handleChangeDirectoryBtnClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) directoryTextField.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    void handleSaveBtnClick(ActionEvent event) {
        String path = directoryTextField.getText() + "\\" + fileNameTextField.getText().trim() + ".xml";
        Path file = Paths.get(path);
        if (!Files.exists(file))
            createXML(path, tablesComboBox.getSelectionModel().selectedItemProperty().getValue());
    }

    private void createXML(String path, String tableType){
        JAXBContext jaxbContext;
        Marshaller jaxbMarshaller;
        try {
            switch (tableType){
                case "Vehicles":
                    Vehicles vehicles = new Vehicles(HomePageController.getConn());
                    vehicles.getAll();

                    jaxbContext = JAXBContext.newInstance(Vehicles.class);
                    jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(vehicles, new File(path));
                    break;
                case "Drivers":
                    Drivers drivers = new Drivers(HomePageController.getConn());
                    drivers.getAll();

                    jaxbContext = JAXBContext.newInstance(Drivers.class);
                    jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(drivers, new File(path));
                    break;
                case "Tariffs":
                    Tariffs tariffs = new Tariffs(HomePageController.getConn());
                    tariffs.getAll();

                    jaxbContext = JAXBContext.newInstance(Tariffs.class);
                    jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(tariffs, new File(path));
                    break;
                case "Orders":
                    Orders orders = new Orders(HomePageController.getConn());
                    orders.getAll();

                    jaxbContext = JAXBContext.newInstance(Orders.class);
                    jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(orders, new File(path));
                    break;
                case "PaymentInfo":
                    PaymentInfo paymentInfo = new PaymentInfo(HomePageController.getConn());
                    paymentInfo.getAll();

                    jaxbContext = JAXBContext.newInstance(PaymentInfo.class);
                    jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    jaxbMarshaller.marshal(paymentInfo, new File(path));
                    break;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
