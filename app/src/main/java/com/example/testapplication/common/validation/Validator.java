package com.example.testapplication.common.validation;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Validator {
    private final static String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
    private final static String DIGITS_PATTERN = "\\d+";

    private final static String PASSWORD_PATTERN = "[a-zA-Z0-9]{6,30}";

    public static boolean isEmpty(final String field) {
        return field == null || field.equals("");
    }

    public static boolean isNumber(String str) {
        return !TextUtils.isEmpty(str) && TextUtils.isDigitsOnly(str);
    }

    public static boolean isNull(final int field) {
        return field <= 0;
    }

    public static boolean isNull(final Object field) {
        return field == null;
    }

    public static boolean isNull(final long field) {
        return field <= 0;
    }

    public static boolean isNull(final Date field) {
        return field == null;
    }

    public static boolean isCorrectEmail(final String email) {
        return !isEmpty(email) && Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    public static boolean isCorrectUserName(final String userName) {
        //return Pattern.compile(USER_NAME_PATTERN).matcher(userName).matches();
        return !isEmpty(userName);
    }

    public static boolean isCorrectPassword(final String password) {
//        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
        return password != null && !password.isEmpty() && password.length() >= 6;
    }

    public static boolean isCorrectNumber(final String number) {
        return !isEmpty(number) && number.length() > 1 && isCorrectDigits(number.substring(1, number.length()));
    }

    public static boolean isCorrectDigits(final String phone) {
        return !isEmpty(phone) && Pattern.compile(DIGITS_PATTERN).matcher(phone).matches();
    }

    public static boolean isEquals(final String password, final String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
