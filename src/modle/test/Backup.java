package modle.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Backup {

    public static void main(String[] args) {
        boolean backup = Backup("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump.exe", "D:\\test", "root", "Cat2020@mysql", "ultimate2");
        System.out.println(backup);
    }



    public static boolean Backup(String dump, String backup, String uname, String pword, String db) {
        Date date = new Date();
        String format = new SimpleDateFormat("dd_MMMMM_yyyy_HH_mm_ss").format(date);
        String name = backup + format + ".sql";
        System.out.println(name);

        String u = " -u" + uname;
        String pass;
        if (pword == null) {
            pass = " ";
        } else {
            pass = " -p" + pword;
        }
        String DB = " " + db + " -r ";

        Runtime r = Runtime.getRuntime();
        boolean b = true;
        try {
            String s = dump + u + pass + DB + name;
            System.out.println(s);
            r.exec(s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
