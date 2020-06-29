package modle.asses;

import conn.DB;
import modle.GetInstans;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Process {
    public static void main(String[] args) {
        System.out.println("ELA");
    }


    public boolean anableProcessButton() {
        boolean anable = false;

        try {
            Date systemDate = GetInstans.getQuater().getSystemDate();
            // String format = new SimpleDateFormat("MM-dd").format(systemDate);
            String today = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
            int currentYear = GetInstans.getQuater().getCurrentYear();
            int currentQuater = GetInstans.getQuater().getCurrentQuater();

            String date = currentYear + "-" + "01-01";

            if (today.equals(currentYear + "-" + "01-01") || today.equals(currentYear + "-" + "04-01") || today.equals(currentYear + "-" + "07-01") || today.equals(currentYear + "-" + "10-01")) {
                System.out.println(today);
                if (!getProcessDate(today)) {
                    anable = true;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return anable;
    }


    public boolean anableProcessPayment() {
        boolean anable = false;

        try {
            Date systemDate = GetInstans.getQuater().getSystemDate();
            // String format = new SimpleDateFormat("MM-dd").format(systemDate);
            String today = new SimpleDateFormat("yyyy-MM-dd").format(systemDate);
            int currentYear = GetInstans.getQuater().getCurrentYear();
            int currentQuater = GetInstans.getQuater().getCurrentQuater();


            boolean completed = isCompleted(currentYear, currentQuater);
            return completed;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


        return anable;
    }

    public boolean getProcessDate(String date) {
        boolean bb = false;

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_process.idProcess,\n" +
                    "ass_process.process_date,\n" +
                    "ass_process.quater_number,\n" +
                    "ass_process.user_id,\n" +
                    "ass_process.start_time,\n" +
                    "ass_process.end_time\n" +
                    "FROM\n" +
                    "ass_process\n" +
                    "WHERE\n" +
                    "ass_process.process_date = '" + date + "'");

            if (data.last()) {
                bb = true;
                System.out.println("data thiyanawa   " + date);
            } else {
                System.out.println("data ne  " + date);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return bb;

    }

    public boolean isCompleted(int year, int quater) {
        boolean bb = false;

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "ass_process.idProcess,\n" +
                    "ass_process.process_date,\n" +
                    "ass_process.quater_number,\n" +
                    "ass_process.user_id,\n" +
                    "ass_process.start_time,\n" +
                    "ass_process.end_time\n" +
                    "FROM\n" +
                    "ass_process\n" +
                    "WHERE\n" +
                    "ass_process.quater_number = '" + quater + "' AND\n" +
                    "YEAR(ass_process.process_date) = " + year);

            if (data.last()) {
                String end_time = data.getString("end_time");
                System.out.println(end_time);
                if (end_time == null) {
                    bb = false;
                    System.out.println("Process Not Completed  " + year + " - Quater " + quater);
                } else {
                    bb = true;
                    System.out.println("Process Completed   " + year + " - Quater " + quater);
                }
            } else {
                bb = false;
                System.out.println("Process Not Completed  " + year + " - Quater " + quater);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return bb;

    }


    public boolean allwDateChange(Date date) {
        boolean allow = false;
        try {
            String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
            int currentQuater = GetInstans.getQuater().getCurrentQuaterByDate(date);
            ResultSet data = DB.getData("SELECT\n" +
                    "\tass_process.idProcess,\n" +
                    "\tass_process.process_date,\n" +
                    "\tass_process.quater_number,\n" +
                    "\tass_process.user_id,\n" +
                    "\tass_process.start_time,\n" +
                    "\tass_process.end_time \n" +
                    "FROM\n" +
                    "\tass_process \n" +
                    "WHERE\n" +
                    "\tass_process.process_date <= '" + format + "' \n" +
                    "\tAND ass_process.quater_number =" + currentQuater);

            if (data.last()) {
                allow = true;
                System.out.println("data thiyanawa");
            } else {
                System.out.println(" data ne ");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return allow;
    }


    public void startProcess() {


    }

}

