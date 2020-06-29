package modle.AmountToWord;

public class Convert {

    public static void main(String[] args) {

    }


    public static String convertToWord(double amount) {
        boolean haveCents = false;

        String dn = "";
        String cn = "";

        double desimal = amount % 1;
        System.out.println(desimal);

        if (desimal > 0) {
            haveCents = true;
            desimal = desimal * 100;
            long l = (new Double(desimal)).longValue();
            cn = EnglishNumberToWords.convert(l);
        }

        long l = (new Double(amount)).longValue();
        dn = EnglishNumberToWords.convert(l);


        if (haveCents) {
            return dn + " rupees and " + cn + " cents only";
        } else {
            return dn + " rupees only";
        }
    }


}
