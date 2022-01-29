package com.example.etlap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.SQLException;
import java.util.List;

public class KategoriaWindowController extends Controller{

    private EtlapDb db;
    private List<Kategoria> kategoriak;

    @FXML
    private ListView<Kategoria> kategoriakListView;

    public void initialize() throws SQLException {
        db = new EtlapDb();
        kategoriakFeltolt();
    }

    @FXML
    public void kategoriaHozzaadasButtonClick(ActionEvent actionEvent) {
    }

    private void kategoriakFeltolt() throws SQLException {
        kategoriak = db.getKategoriak();
        kategoriakListView.getItems().clear();
        ObservableList<Kategoria> kategoriakObservedList = FXCollections.observableArrayList(kategoriak);
        kategoriakListView.setItems(kategoriakObservedList);
    }
}
