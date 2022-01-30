package com.example.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtlapDb {
    Connection connection;

    public EtlapDb() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }

    public List<Etel> getEtelek() throws SQLException {
        List<Etel> etelek = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM `etlap`\n" +
                "INNER JOIN kategoriak ON etlap.kategoria_id = kategoriak.id";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            String leiras = result.getString("leiras");
            int ar = result.getInt("ar");
            String kategoria = result.getString("kategoriak.nev");
            int kategoriaId = result.getInt("kategoriak.id");
            Etel obj = new Etel(id,nev,leiras,ar, kategoria, kategoriaId);
            etelek.add(obj);
        }
        return etelek;
    }

    public int etelAdd(String nev, String leiras, int ar, int kategoria) throws SQLException {
        String sql = "INSERT INTO etlap(nev, leiras, ar, kategoria_id) VALUES(?,?,?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setString(2, leiras);
        stmt.setInt(3, ar);
        stmt.setInt(4, kategoria);
        return stmt.executeUpdate();
    }

    public boolean etelTorlese(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean etelModositasa(Etel modositando, int hozzaadottAr) throws SQLException {
        String sql = "UPDATE etlap SET " +
                "nev = ?," +
                "leiras = ?," +
                "ar = ?," +
                "kategoria_id = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, modositando.getNev());
        stmt.setString(2, modositando.getLeiras());
        stmt.setInt(3, hozzaadottAr);
        stmt.setInt(4, modositando.getKategoriaId());
        stmt.setInt(5, modositando.getId());
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public List<Kategoria> getKategoriak() throws SQLException {
        List<Kategoria> osszesKategoria = new ArrayList<>();
        String sqlKat = "SELECT * FROM kategoriak";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sqlKat);

        while (result.next()){
            int id = result.getInt("id");
            String nev = result.getString("nev");
            Kategoria obj = new Kategoria(id, nev);
            osszesKategoria.add(obj);
            System.out.println(obj);
        }
        return osszesKategoria;
    }

    public int kategoriaAdd(String ujKategoria) throws SQLException {
        String sql = "INSERT INTO kategoriak(nev) VALUES(?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, ujKategoria);
        return stmt.executeUpdate();
    }

    public boolean kategoriaTorles(int id) throws SQLException {
        String sql = "DELETE FROM kategoriak WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}
