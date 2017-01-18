/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket.TugasAkhir;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Microsoft
 */
public class TugasAkhir {
    Statement state;
    Connection connect;
    String namaDatabase = "jdbc:mysql://localhost/db_tugasakhir";
    
    public void PBOTugasAkhir(){
        try {
            connect = DriverManager.getConnection(namaDatabase, "root", "merlinda20102011");
            state = (Statement) connect.createStatement();
        } catch (SQLException e) {
            System.err.println("" + e);
        }
    }
}
