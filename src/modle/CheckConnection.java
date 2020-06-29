package modle;


import conn.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class CheckConnection {


    public static boolean checkInternet() {
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Connection Successful");
            System.gc();
            return true;
        } catch (Exception e) {
            System.out.println("Internet Not Connected");
            return false;
        }
    }


//    public static boolean checkLocal() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection c = DriverManager.getConnection(DB.DBPATH, DB.USER, DB.PASS);
//            c = null;
//            System.gc();
//
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
