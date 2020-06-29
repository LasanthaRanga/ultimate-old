package conn;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DBssh {

    public static final int lport = 21098;
    public static final String rhost = "127.0.0.1";
    public static final String host = "server184.web-hosting.com";
    public static final int rport = 3306;
    public static final String user = "kgmckhrd";
    public static final String password = "Cat2020cpanel";
    public static final String dbuserName = "kgmckhrd";
    public static final String dbpassword = "Cat2020cpanel";
    public static final String url = "jdbc:mysql://localhost:" + lport + "/kgmckhrd_ultimate_mck";
    public static final String driverName = "com.mysql.jdbc.Driver";
    public static Connection c = null;
    public static Session session = null;

    public static Connection getConnection() throws Exception {
        if (c == null) {

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, lport);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();


            System.out.println("Connected");
            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
            System.out.println("Port Forwarded");

            //mysql database connectivity
            Class.forName(driverName).newInstance();
            c = DriverManager.getConnection(url, dbuserName, dbpassword);

            System.out.println("Database connection established");
            System.out.println("DONE");

        }
        return c;
    }

    public static int setData(String sql) throws Exception {
        int row = DBssh.getConnection().createStatement().executeUpdate(sql);
//       System.out.println("===============\n"+sql+"\n====================");
        return row;
    }

    public static ResultSet getData(String sql) throws Exception {
        ResultSet executeQuery = DBssh.getConnection().createStatement().executeQuery(sql);
//       System.out.println("===============\n"+sql+"\n====================");
        return executeQuery;
    }


}
