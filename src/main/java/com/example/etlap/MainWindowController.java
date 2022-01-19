package com.example.etlap;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MainWindowController {
    @FXML
    private TableView<Etel> etelTable;
    @FXML
    private TableColumn<Etel, String> colNev;
    @FXML
    private TableColumn<Etel, Integer> colAr;
    @FXML
    private TableColumn<Etel, String> colKategoria;

    private EtlapDb db;



    public void initialize(){
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));

        try {
            db = new EtlapDb();
            feltolt();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    
    private void feltolt(){
        try {
            List<Etel> etelLista = db.getEtelek();
            etelTable.getItems().clear();
            for (Etel item :
                    etelLista) {
                etelTable.getItems().add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}