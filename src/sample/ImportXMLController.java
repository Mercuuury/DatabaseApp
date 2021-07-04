package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import java.io.File;

public class ImportXMLController {

    @FXML
    private VBox vBox;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label rdyLbl;

    @FXML
    void initialize() {

        vBox.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != vBox && dragEvent.getDragboard().hasFiles())
                dragEvent.acceptTransferModes(TransferMode.COPY);

            dragEvent.consume();
        });

        vBox.setOnDragDropped(dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            boolean success = false;

            if (db.hasFiles()) {
                if(getFileExtension(db.getFiles().get(0)).equals("xml")) {
                    DOMxmlReader.setXmlFile(db.getFiles().get(0));
                    success = true;
                    progressBar.setProgress(0.3);
                } else {
                    rdyLbl.setText("Ошибка");
                    rdyLbl.setStyle("-fx-text-fill: red;");
                }
            }

            dragEvent.setDropCompleted(success);
            dragEvent.consume();
            if(success){
                vBox.setDisable(true);
                progressBar.setProgress(0.7);

                DOMxmlReader.importXML();
                progressBar.setProgress(1.0);
                rdyLbl.setText("Готово");
                rdyLbl.setStyle("-fx-text-fill: white;");
            }
        });
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else
            return "";
    }
}
