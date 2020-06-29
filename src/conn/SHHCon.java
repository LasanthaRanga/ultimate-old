package conn;

import java.sql.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SHHCon {
    /**
     * Java Program to connect to remote database through SSH using port forwarding
     *
     * @throws SQLException
     * @author Pankaj@JournalDev
     */
    public static void main(String[] args) throws SQLException {

        int lport=21098;
        String rhost="127.0.0.1";
        String host="server184.web-hosting.com";
        int rport=3306;
        String user="kgmckhrd";
        String password="Cat2020cpanel";
        String dbuserName = "kgmckhrd";
        String dbpassword = "Cat2020cpanel";
        String url = "jdbc:mysql://localhost:"+lport+"/kgmckhrd_ultimate_mck";
        String driverName="com.mysql.jdbc.Driver";
        Connection conn = null;
        Session session= null;
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, host, lport);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();


            System.out.println("Connected");
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
            System.out.println("Port Forwarded");

            //mysql database connectivity
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection (url, dbuserName, dbpassword);

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT\n" +
                    "`user`.idUser,\n" +
                    "`user`.user_full_name,\n" +
                    "`user`.user_nic,\n" +
                    "`user`.user_date,\n" +
                    "`user`.user_birth_day,\n" +
                    "`user`.user_status,\n" +
                    "`user`.user_syn,\n" +
                    "`user`.user_question,\n" +
                    "`user`.user_answer,\n" +
                    "`user`.user_username,\n" +
                    "`user`.user_password,\n" +
                    "`user`.user_level,\n" +
                    "`user`.office_idOffice,\n" +
                    "`user`.user_name_sinhala,\n" +
                    "`user`.mobile_no\n" +
                    "FROM\n" +
                    "`user`\n");

            while (resultSet.next()){

                String user_full_name = resultSet.getString("user_full_name");
                System.out.println(user_full_name);
            }


            System.out.println ("Database connection established");
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null && !conn.isClosed()){
                System.out.println("Closing Database Connection");
                conn.close();
            }
            if(session !=null && session.isConnected()){
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        }
    }







    static Connection c = null;


    public static final String DBPATH = "jdbc:mysql://server184.web-hosting.com:3306/kgmckhrd_ultimate_mck?zeroDateTimeBehavior=convertToNull&useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=no&amp;autoReconnect=true";
    public static final String USER = "kgmckhrd";
    public static final String PASS = "Cat2020cpanel";


    public static Connection getConnection() throws Exception {
        if (c == null) {
            Class.forName("com.mysql.jdbc.Driver");
            //   DriverManager.setLoginTimeout(2);
            c = DriverManager.getConnection(DBPATH, USER, PASS);
        }
        return c;
    }

    public static int setData(String sql) throws Exception {
        int row = SHHCon.getConnection().createStatement().executeUpdate(sql);
//       System.out.println("===============\n"+sql+"\n====================");
        return row;
    }

    public static ResultSet getData(String sql) throws Exception {
        ResultSet executeQuery = SHHCon.getConnection().createStatement().executeQuery(sql);
//       System.out.println("===============\n"+sql+"\n====================");
        return executeQuery;
    }


}

