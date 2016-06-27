package com.orcs.boomshakalaka.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>need to be tested
 * Provide the most common regular expression methods
 *
 * @author
 */
public class RegularExpUtils {

    /**
     * Judge if the string is email address
     *
     * @param mail The email address string
     * @return True if the string is email address, false otherwise
     */
    public static boolean checkMail(String mail) {
        boolean flag;
        try {
            String check = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(mail);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Judge if the string is mobile number
     *
     * @param mobile The mobile number string
     * @return True if the string is mobile number, false otherwise
     */
    public static boolean checkMobile(String mobile) {
        return mobile.matches("^[1][2,3,4,5,7,8]+\\d{9}");
    }

}
