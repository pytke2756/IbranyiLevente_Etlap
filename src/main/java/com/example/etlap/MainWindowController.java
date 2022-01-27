package com.example.etlap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainWindowController extends Controller{
    @FXML
    private TableView<Etel> etelTable;
    @FXML
    private TableColumn<Etel, String> colNev;
    @FXML
    private TableColumn<Etel, Integer> colAr;
    @FXML
    private TableColumn<Etel, String> colKategoria;
    @FXML
    private TextField tfKivalasztott;

    private EtlapDb db;
    @FXML
    private Spinner<Integer> forintSpinner;
    @FXML
    private Spinner<Integer> szazalekSpinner;


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

        etelTable.setRowFactory(etelTableView -> {
            TableRow<Etel> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                Etel e = etelTable.getSelectionModel().getSelectedItem();
                String tfBe = e.getNev() + " - " + e.getKategoria() + " - " + e.getLeiras() + " - " + e.getAr() + " Ft";
                tfKivalasztott.setText(tfBe);
            });
            return row;
        });
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


    @FXML
    public void felvetelButtonClick(ActionEvent actionEvent) {
        try {
            Controller ujFelvetel = ujWindow("hozzaad_window.fxml", "Hozzáadás", 370, 270);
            ujFelvetel.getStage().setOnCloseRequest(windowEvent -> feltolt());
            ujFelvetel.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void torlesButtonCLick(ActionEvent actionEvent) throws SQLException {
        Etel e = etelTable.getSelectionModel().getSelectedItem();
        if (e != null){
            if (confirm("Biztos, hogy törölni szeretné ezt az ételt?")){
                db.etelTorlese(e.getId());
                feltolt();
                return;
            }
            return;
        }
        alert("Nincs kiválasztva étel!");
    }

    @FXML
    public void szazelekEmelesButtonClick(ActionEvent actionEvent) throws SQLException {
        Etel e = etelTable.getSelectionModel().getSelectedItem();
        if (e != null){
            String confirmUzenet = "Biztos hogy módosítja a(z) " + e.getNev() + " árát?";
            if (confirm(confirmUzenet)){
                int ertekValtozas = szazalekErtekValtozas(e.getAr());
                try{
                    db.etelModositasa(e, ertekValtozas);
                    feltolt();
                    System.out.println("Sikeres módosítás");
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
        }
        else{
            if (confirm("Biztos hogy módosítja az összes ételt?")){
                int meret = etelTable.getItems().size();
                for (int i = 0; i < meret; i++) {
                    Etel obj = etelTable.getItems().get(i);
                    int ertekValtozas = szazalekErtekValtozas(obj.getAr());
                    try {
                        db.etelModositasa(obj, ertekValtozas);
                        feltolt();
                        System.out.println("Sikeres módosítás");
                    }catch (Exception ex){
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    private int szazalekErtekValtozas(int etelJelenlegiAra){
        return (int)((szazalekSpinner.getValue() / 100.0) * etelJelenlegiAra) + etelJelenlegiAra;
    }

    @FXML
    public void forintEmelesButtonClick(ActionEvent actionEvent) {
    }
}