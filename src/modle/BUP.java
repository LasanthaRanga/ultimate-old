package modle;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author RM.LasanthaRanga@gmail.com
 */
public class BUP {



    final String dump = "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump";
    final String host = "localhost";
    final String port = "3306";
    final String user = "root";
    final String pass = "root";
    final String db = "ultimate2";
    final String bacuppath = "C:\\MysqlDBbackup\\";

    public boolean backupDataWithOutDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
            Date date = new Date();
            String filepath = "backup(without_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (password != "") {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);

            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
                System.out.println("OK");
                //         log.info("Backup created successfully for without DB " + database + " in " + host + ":" + port);
            } else {
                status = false;
                System.out.println("Eroor");
                //        log.info("Could not create the backup for without DB " + database + " in " + host + ":" + port);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            //      log.error(ioe, ioe.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            //      log.error(e, e.getCause());
        }
        return status;
    }

    public boolean backupDataWithDatabase(String text) {
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = text + "backup(with_DB)-" + db + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (pass != "") {
                //Backup with database
                batchCommand = dump + " -h " + host + " --port " + port + " -u " + user + " --password=" + pass + " --add-drop-database -B " + db + " -r \"" + bacuppath + "" + filepath + "\"";
            } else {
                batchCommand = dump + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + db + " -r \"" + bacuppath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
                System.out.println("OK");
            } else {
                status = false;
                System.out.println("Eroor");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
