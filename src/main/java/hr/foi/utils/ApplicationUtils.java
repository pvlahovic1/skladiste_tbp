package hr.foi.utils;

import java.util.regex.Pattern;

public class ApplicationUtils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isInteger(String number) {
        return number.matches("\\d+");
    }

    public static boolean isDecimal(String number) {
        return number.matches("\\d+(\\.\\d+)*");
    }

    public static boolean isNumberAndBiggerThen(String number, double num) {
        if (isInteger(number) || isDecimal(number)) {
            double value = Double.valueOf(number);
            return value > num;
        }

        return false;
    }

    public static boolean isIntegerAndBiggerThen(String number, double num) {
        if (isInteger(number)) {
            int value = Integer.valueOf(number);
            return value > num;
        }

        return false;
    }
}
