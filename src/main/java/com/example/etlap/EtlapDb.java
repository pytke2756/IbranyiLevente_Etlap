package com.example.etlap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EtlapDb {
    Connection connection;

    public EtlapDb() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/etlapdb", "root", "");
    }
}
