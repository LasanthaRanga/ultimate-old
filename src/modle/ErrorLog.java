package modle;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLog {

    public static boolean writeLog(String eM, String eClass, String eMethod, String ePath) {

        try {
            String format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
            File file = new File("C:/cat/logers/" + format + "Loger.txt");
            FileWriter fw = new FileWriter(file);
            fw.write(" -Path : " + ePath);
            fw.write("\n -Class : " + eClass);
            fw.write("\n -Method : " + eMethod);
            fw.write("\n -Error Message : " + eM);
            fw.flush();
            return true;
        } catch (Exception e) {
            return true;
        }

    }

}
