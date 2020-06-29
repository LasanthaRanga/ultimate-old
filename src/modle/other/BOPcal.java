package modle.other;

import conn.DB;

import java.sql.ResultSet;

public class BOPcal {

//    public static void main(String[] args) {
//        System.out.println(   calBopValue(20));
//    }



    public static double calBopValue(double perch){

            double val=0.0;
        try{
            ResultSet data = DB.getData("SELECT\n" +
                    "bop_cal.id,\n" +
                    "bop_cal.min,\n" +
                    "bop_cal.max,\n" +
                    "bop_cal.`value`\n" +
                    "FROM `bop_cal`");

            while (data.next()){
                double min = data.getDouble("min");
                double max = data.getDouble("max");
                if(perch>min && perch<= max){
                    val = data.getDouble("value");
                    System.out.println("match");
                    break;
                }else{
                    System.out.println( "NO");
                }
            }

        }catch (Exception e){e.printStackTrace();}

        return val;
    }


}
