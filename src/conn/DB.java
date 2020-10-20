package conn;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;

/**
 * @author Punnajee
 */
public class DB {


    static Connection c = null;

    //       local
    public static final String DBPATH = "jdbc:mysql://124.43.8.250:3306/tempory?zeroDateTimeBehavior=convertToNull";
    public static final String USER = "root";
    public static final String PASS = "CHI@#321#";

//       local
//    public static final String DBPATH = "jdbc:mysql://124.43.4.213:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "padu@#$075%$";


//    Polghawela
//    public static final String DBPATH = "jdbc:mysql://124.43.8.99:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "Pol@#522#";

    //Comment updated

//    public static final String DBPATH = "jdbc:mysql://124.43.8.250:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "CHI@#321#";


    // Panduwasnuwara local server
//    public static final String DBPATH = "jdbc:mysql://localhost:3306/kakkapalliya?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "root";

//    Kuliuc
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/wariyapolaps?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


//    Kuliuc
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/chilaw?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


    //    MCK
//    public static final String DBPATH = "jdbc:mysql://124.43.9.57:3306/atd2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "@Mck_#321";

//    public static final String DBPATH = "jdbc:mysql://MCK_SERVER:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "@Mck_#321";

    //    CHILAW
//    public static final String DBPATH = "jdbc:mysql://124.43.8.250:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "CHI@#321#";

    //    // Pannala Online
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/pannala?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


    // Ambalangoda Online
//    public static final String DBPATH = "jdbc:mysql://124.43.8.155:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "Amb@#321#";


//    // Digital Ocean
//    public static final String DBPATH = "jdbc:mysql://157.245.152.224:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "Cat2020@mysql";

//
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/kuliuc?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


//
//    public static final String DBPATH = "jdbc:mysql://mysql-do-user-6789679-0.db.ondigitalocean.com:25060/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "doadmin";
//    public static final String PASS = "a1c9pzfjz3tye8t1";


//    public static final String DBPATH = "jdbc:mysql://KULIPS_SERVER:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "Kulips@#321#";


    //    // POLPITHIGAMA PS
//    public static final String DBPATH = "jdbc:mysql://localhost:3306/polpithigamaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/ibbagamuwaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
    //  public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/ibbagamuwaps?useUnicode=yes&amp;characterEncoding=utf8mb4";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


//    public static final String DBPATH = "jdbc:mysql://192.168.1.3:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


//    // LOCAL KULI UC
//    public static final String DBPATH = "jdbc:mysql://localhost:3306/kuliuc?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


//    // LOCAL KULI UC
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/kuliuc?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


//    public static final String DBPATH = "jdbc:mysql://192.168.1.6:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";

//    public static final String DBPATH = "jdbc:mysql://localhost:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";

//Cat 2020 Local server
//    public static final String DBPATH = "jdbc:mysql://192.168.1.3:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


// LOCAL
//    public static final String DBPATH = "jdbc:mysql://127.0.0.1:3306/polgahawela?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


////    Polgahawela


    ////    Polgahawela Local
//    public static final String DBPATH = "jdbc:mysql://localhost:3306/polgahawela?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


//    // CHILAW
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/chilaw?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


//    Pannala
    //  public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/pannala?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String DBPATH = "jdbc:mysql://localhost:3306/pannala?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
    // public static final String USER = "root";
//    public static final String PASS = "root";
    // public static final String PASS = "3ta@kela#una@";

//    Pannala local
//    public static final String DBPATH = "jdbc:mysql://127.0.0.1:3306/pannala?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


    //    Karuwalagaswewa
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/karuwalagaswewaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

    //    Karuwalagaswewa Local
//    public static final String DBPATH = "jdbc:mysql://localhost:3306/karuwalagaswewaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";


    // WENNAPPUWA
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/wennappuwaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

    //=======================================================================================================================================================
//    Kuliyapitiya
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


////    MCK Online
//    public static final String DBPATH = "jdbc:mysql://124.43.9.57:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "@Mck_#321";

//    Galgamuwa
//    public static final String DBPATH = "jdbc:mysql://GPS_SERVER:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";

//    Waththegama
//    public static final String DBPATH = "jdbc:mysql://ASSESSMENT-TAX:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";

    //    Polgahawela
//    public static final String DBPATH = "jdbc:mysql://124.43.9.57:3306/polgahawela?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "@Mck_#321";

//    //Narammala
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/narammalaps?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

    //Pannala PS
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/pannala?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

    // Kuliyapitiya UC
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3306/kuliuc?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

    //  Mathale PS
//    public static final String DBPATH = "jdbc:mysql://MPS_03:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";

    // Panduwasnuwara
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/panduwasnuwaraps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";


    // Karuwalagas Wewa
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/karuwalagaswewaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

// Ibbagamuwa PS
//    public static final String DBPATH = "jdbc:mysql://124.43.11.162:3307/ibbagamuwaps?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "3ta@kela#una@";

//    public static final String DBPATH = "jdbc:mysql://PUJ_SERVER:3306/ultimate2?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
//    public static final String USER = "root";
//    public static final String PASS = "root";

//    public static final String DBPATH = "jdbc:mysql://124.43.8.99:3306/ultimate2?zeroDateTimeBehavior=convertToNull";
//    public static final String USER = "root";
//    public static final String PASS = "Pol@#522#";

    public static Connection getConnection() throws Exception {
        if (c == null) {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            //   DriverManager.setLoginTimeout(2);
            c = DriverManager.getConnection(DBPATH, USER, PASS);
            timeOut();
        }
        return c;
    }

    public static int setData(String sql) throws Exception {
        int row = DB.getConnection().createStatement().executeUpdate(sql);
//        System.out.println("===============\n" + sql + "\n====================");
        time = 300000;
        return row;
    }

    public static ResultSet getData(String sql) throws Exception {
        ResultSet executeQuery = DB.getConnection().createStatement().executeQuery(sql);
//        System.out.println("===============\n" + sql + "\n====================");
        time = 300000;
        return executeQuery;
    }

    static double time = 300000;

    public static void timeOut() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (time < 0) {

                            Class.forName("com.mysql.cj.jdbc.Driver");
                            c = DriverManager.getConnection(DBPATH, USER, PASS);
                            time = 300000;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        time = time - 5000;
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
