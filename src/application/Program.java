package application;

import db.DB;

import java.sql.Connection;

public class Program {
    public static void main(String[] args) {

        Connection conect = DB.getConnection();
        DB.closeConnection();

    }
}
