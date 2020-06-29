package modle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author RM.LasanthaRanga@gmail.com
 */
public class Round {

    public static void main(String[] args) {
//        double v = Maths.round2(2000.34467);
//        System.out.println(v);
//
//        BigDecimal rawValue = new BigDecimal(2000.0007);
//        BigDecimal value = rawValue.setScale(0, RoundingMode.UP);
//        System.out.println(value);
//
//        System.out.println(round(2000.34467));

        System.out.println(roundFormat(12345.10));
    }

    public static double round(Double value) {
        return Math.round(value * 100.00) / 100.00;
    }

    public static String roundToString(double value) {
        return new DecimalFormat("0.00").format(value);
    }

    public static String roundFormat(double value) {
        return new DecimalFormat("#,###.00").format(value);
    }

}
