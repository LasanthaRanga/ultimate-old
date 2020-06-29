package modle.asses;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import conn.DB;

public class Quater {

    public int getPrviasQuater() {
        int currentQuater = getCurrentQuater();
        int pq = 0;
        if (currentQuater > 0 && currentQuater <= 4) {
            pq = currentQuater - 1;
            if (pq == 0) {
                pq = 4;
            }
        }
        return pq;
    }

    public int getCurrentQuater() {
        int q = 0;
        Date sd = getSystemDateByQuary();
        if (sd != null) {
            int month = Integer.parseInt(new SimpleDateFormat("MM").format(sd));
            if (month <= 3) {
                q = 1;
            } else if (month <= 6) {
                q = 2;
            } else if (month <= 9) {
                q = 3;
            } else if (month <= 12) {
                q = 4;
            }
        }
        //    System.out.println(q);
        return q;
    }


    public int getCurrentQuaterByDate(Date date) {
        int q = 0;
        if (date != null) {
            int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
            if (month <= 3) {
                q = 1;
            } else if (month <= 6) {
                q = 2;
            } else if (month <= 9) {
                q = 3;
            } else if (month <= 12) {
                q = 4;
            }
        }
        return q;
    }




    public int getCurrentMonth() {
        int m = 0;
        Date sd = getSystemDateByQuary();
        if (sd != null) {
            m = Integer.parseInt(new SimpleDateFormat("MM").format(sd));
        }
        //  System.out.println(m);
        return m;
    }

    public int getCurrentDay() {
        int d = 0;
        Date sd = getSystemDateByQuary();
        if (sd != null) {
            d = Integer.parseInt(new SimpleDateFormat("dd").format(sd));
        }
        //  System.out.println(d);
        return d;
    }

    public int getCurrentYear() {
        int y = 0;
        Date sd = getSystemDateByQuary();
        if (sd != null) {
            y = Integer.parseInt(new SimpleDateFormat("yyyy").format(sd));
        }
        // System.out.println(y);
        return y;
    }

    public Date getSystemDate() {
        String qq = "SELECT\n" +
                "systemdate.idSystemDate,\n" +
                "systemdate.systemDate,\n" +
                "systemdate.systemDate_status,\n" +
                "systemdate.change_user_id\n" +
                "FROM `systemdate`\n" +
                "WHERE\n" +
                "systemdate.systemDate_status = 1";
        try {
            ResultSet data = DB.getData(qq);
            if(data.last()){
                java.sql.Date systemDate = data.getDate("systemDate");
                Date sd = systemDate;
                return sd;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getSystemDateByQuary(){
        String qq = "SELECT\n" +
                "systemdate.idSystemDate,\n" +
                "systemdate.systemDate,\n" +
                "systemdate.systemDate_status,\n" +
                "systemdate.change_user_id\n" +
                "FROM `systemdate`\n" +
                "WHERE\n" +
                "systemdate.systemDate_status = 1";
        try {
            ResultSet data = DB.getData(qq);
            if(data.last()){
                java.sql.Date systemDate = data.getDate("systemDate");
                Date sd = systemDate;
                return sd;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSystemDateStringByQuary(){
        String qq = "SELECT\n" +
                "systemdate.idSystemDate,\n" +
                "systemdate.systemDate,\n" +
                "systemdate.systemDate_status,\n" +
                "systemdate.change_user_id\n" +
                "FROM `systemdate`\n" +
                "WHERE\n" +
                "systemdate.systemDate_status = 1";
        try {
            ResultSet data = DB.getData(qq);
            if(data.last()){
                String systemDate = data.getString("systemDate");
                return systemDate;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int cheackDiscountDate() {
        int currentDay = getCurrentDay();
        int currentMonth = getCurrentMonth();

        if (currentMonth == 1 && currentDay <= 31) {
            return 1;
        } else if (currentMonth == 4 && currentDay <= 30) {
            return 2;
        } else if (currentMonth == 7 && currentDay <= 31) {
            return 3;
        } else if (currentMonth == 10 && currentDay <= 31) {
            return 4;
        } else {
            return 0;
        }

    }

}
