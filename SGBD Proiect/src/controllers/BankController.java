package controllers;

import database.Database;
import entities.Bank;

import java.sql.*;
import java.util.ArrayList;

public class BankController {
    public void create(String city, String address, String name){
        Connection con = Database.getConnection();
        String call = "{ ? = call new_bank(?,?,?) }";
        CallableStatement statement = null;
        try {
            statement = con.prepareCall(call);
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setString(2, city);
            statement.setString(3, address);
            statement.setString(4, name);
            statement.execute();
            String output = statement.getString(1);
            System.out.println(output);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String id, String city, String address, String name){
        Connection con = Database.getConnection();
        String call = "{ ? = call update_bank(?,?,?,?) }";
        CallableStatement statement = null;
        try {
            statement = con.prepareCall(call);
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setString(2, id);
            statement.setString(3, city);
            statement.setString(4, address);
            statement.setString(5, name);
            statement.execute();
            String output = statement.getString(1);
            System.out.println(output);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findById(int id) throws SQLException{
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from BANK where id_bank = " + id + "")) {
            rs.next();
            System.out.println("Bank Id: " + rs.getString(1) + ", city: " + rs.getString(2) + ", address: " +
                    rs.getString(3) + ", name: " + rs.getString(4) + ". It has been created at: " + rs.getString(5) +
                    " and has last been updated at " + rs.getString(6) + ".");

        }
    }

    public Bank getBankById(String id){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from BANK where id_bank = " + id + "")) {
            rs.next();
            return new Bank(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getBanksIds(){
        ArrayList <String> result = new ArrayList<>();
        Connection con = Database.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT id_bank from BANK" );
            while(rs.next())
            {
                String row = rs.getString(1);
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
