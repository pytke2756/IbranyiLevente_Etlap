package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class HozzaadWindowController extends Controller{

    @FXML
    private TextArea tfLeiras;
    @FXML
    private ChoiceBox<String> cbKategoria;
    @FXML
    private Spinner<Integer> spAr;
    @FXML
    private TextField tfNev;

    private EtlapDb db;

    public void initialize() throws SQLException {
        cbKategoria.getSelectionModel().selectFirst();
        db = new EtlapDb();
    }

    @FXML
    public void etelHozzaadasButtonClick(ActionEvent actionEvent) {
        boolean isTrue = true;
        String nev = tfNev.getText().toString();
        if (nev.isEmpty()){
            alert("A név nem lehet üres!");
            return;
        }
        String leiras = tfLeiras.getText().toString();
        if (leiras.isEmpty()){
            alert("A leírás nem lehet üres!");
            return;
        }
        String kategoria = cbKategoria.getSelectionModel().getSelectedItem();
        if (kategoria == null){
            alert("Kötelező kiválasztani kategóriát!");
            return;
        }
        int ar = spAr.getValue();
        if (ar == 0 || ar < 1){
            alert("Az ár nem lehet nulla vagy annál kisebb!");
            return;
        }
        try {
            int siker = db.etelAdd(nev, leiras, ar, kategoria);
            if (siker == 1){
                alert("Étel hozzáadása sikeres!");
                tfLeiras.setText("");
                spAr.getEditor().setText("1000");
                tfNev.setText("");
            }else{
                alert("Étel hozzáadása sikertelen!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
