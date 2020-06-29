package modle;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

public final class AmounToWord {

    private static final double MAX_LIMIT = 999999.99;
    private static final String OUT_OF_BOUNDS_INPUT = "Value must be greater than 0 "
            + "and lower or equal to " + String.valueOf(MAX_LIMIT);
    private static final String INVALID_INPUT = "Unknow number pattern informed";
    private static final String ZERO = "zero dollars";

    private static final String[] oneToNineteenNames = {
            "", // sentinel value
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen"
    };

    private static final String[] tenToNinetyNames = {
            "", // sentinel value
            "ten",
            "twenty",
            "thirty",
            "forty",
            "fifty",
            "sixty",
            "seventy",
            "eighty",
            "ninety"
    };


    private AmounToWord() {/*deliberately empty*/}


    public static String convert(String value) throws IllegalArgumentException {
        double number;
        try {
            number = Double.parseDouble(getUSPatternNumber(value));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (number < 0.0 || number > (int) MAX_LIMIT) {
            throw new IllegalArgumentException(OUT_OF_BOUNDS_INPUT);
        }
        if (number == 0.0) {
            return ZERO;
        }

        return capitalize(translate(value));
    }


    private enum NUM_TYPE {
        INTEGER,
        FLOATING_POINT
    }


    private static String getHundredsTensOnes(int value) {
        StringBuilder returnValue = new StringBuilder();
        if (value % 100 < 20) {
            returnValue.append(oneToNineteenNames[value % 100]);
            value /= 100;
        } else {
            returnValue.append(oneToNineteenNames[value % 10]);
            value /= 10;
            returnValue.insert(0, tenToNinetyNames[value % 10] + " ");
            value /= 10;
        }
        if (value > 0) {
            returnValue.insert(0, oneToNineteenNames[value] + " hundred ");
        }
        return returnValue.toString();
    }


    private static String translateDollars(String dollars) throws IllegalArgumentException, ParseException {
        StringBuilder dollarsStrBuilder = new StringBuilder(256);
        String[] values = dollars.split(",");
        int valuesIndex = 0;
        if (getIntValue(dollars) == 0) {
            // value is lower than one dollar, so print only cents
            return "";
        }
        switch (values.length) {

            case 2: // thousands
                dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex++])));
                dollarsStrBuilder.append(" thousand ");
            case 1: // hundreds or less
                dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex])));
                break;
        }
        return dollarsStrBuilder.append(" rupees ").toString();
    }


    private static String translateCents(String cents) throws ParseException {
        if (cents.equals("00")) {
            return "";
        }
        return getHundredsTensOnes(getIntValue(cents)) + " cent";
    }


    private static String translate(String number) throws IllegalArgumentException {
        StringBuilder numberInWords = new StringBuilder(256);
        try {
            switch (identifyType(number)) {
                case INTEGER:
                    numberInWords.append(translateDollars(number));
                    break;
                case FLOATING_POINT:
                    int cents_position = number.length()-2;
                    boolean isMoreThanOneDollar = true;
                    String cents = "";

                    numberInWords.append(translateDollars(number.substring(0, cents_position-1)));
                    if (numberInWords.toString().equals("")) {
                        isMoreThanOneDollar = false;
                    }
                    cents = translateCents(number.substring(cents_position));
                    if (isMoreThanOneDollar && !cents.equals("")) {
                        // gets the "and zero cents" case
                        numberInWords.append(" and ");
                    }
                    numberInWords.append(cents);
                    break;
                default:
                    throw new IllegalArgumentException(INVALID_INPUT);
            }
        } catch (IllegalArgumentException | ParseException ex) {
            System.err.println(ex);
            throw new IllegalArgumentException(ex);
        }
        return numberInWords.toString();
    }


    private static NUM_TYPE identifyType(String number) {
        if (isFloatingPoint(number)) {
            return NUM_TYPE.FLOATING_POINT;
        }
        return NUM_TYPE.INTEGER;
    }


    private static boolean isFloatingPoint(String number) throws IllegalArgumentException {
        if (!isValid(number)) {
            throw new IllegalArgumentException(INVALID_INPUT);
        }
        return Pattern.matches(".*(\\.\\d\\d)$", number);
    }


    private static String getUSPatternNumber(String value) throws ParseException {
        return NumberFormat.getNumberInstance(Locale.US).parse(value).toString();
    }


    private static String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }


    private static int getIntValue(String s) throws ParseException {
        return Math.abs(Integer.valueOf(getUSPatternNumber(s)));
    }


    private static boolean isValid(String number) {
        if (!Pattern.matches("^\\d{1,3}(,\\d{3})*(\\.\\d\\d)?$", number)) {
            return false;
        }
        return true;
    }






}


