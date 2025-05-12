/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import outils.OutilsJDBC;
/**
 *
 * @author mmunier
 */
public class SupressionBDDJoueurs {
    
     public static void main(String[] args) {

        try {

            Connection connexion =  DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/2024-2025_s2_vs1_tp2_boatrush", "etudiant", "YTDTvj9TR3CDYCmP");

            Statement statement = connexion.createStatement() ;
            statement.executeUpdate("DELETE FROM joueurs;"); 


           
            statement.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
