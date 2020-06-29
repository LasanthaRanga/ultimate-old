package conn;


import com.jcraft.jsch.JSch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

//import static conn.DB.lport;

public class SessionFactorys {


    static String sshHost, sshAccount, sshPassword;
    static int sshPort = 22;
    static int localPort = 3399, remotePort = 3306;
    static String remoteHost = "localhost";

    public static void main(String[] args) throws Exception {

        Properties config = new Properties();//
        config.put("StrictHostKeyChecking", "no");//
        JSch jsch = new JSch();//
        com.jcraft.jsch.Session session = jsch.getSession("kgmckhrd", "server184.web-hosting.com", 21098);
        session.setPassword("Cat2020cpanel");//
        session.setConfig(config);
        System.out.println("Start Connecting ");
        session.connect();
        int assinged_port = session.setPortForwardingL(21098, "127.0.0.1", 3306);



        System.out.println("Connect Hibernate");
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory factory = configuration.buildSessionFactory(builder.build());
        Session sessions = factory.openSession();
        Transaction tx = sessions.beginTransaction();
//
        System.out.println("Close Hibernate Connection");
        tx.commit();
        sessions.close();
        factory.close();
//
        System.out.println("Closing SSH Connection");
        session.disconnect();

    }
}
