package modle;


public class Maths {
    public static double round2(double d){
      return Math.round(d*100.0)/100.0;
    }
    
    public static String get2String(double d){
      return String.format("%.2f", d);
    }
    
    public static String rondAnd2String(double d){
        return String.format("%.2f", Math.round(d*100.0)/100.0);
    }
    
}

