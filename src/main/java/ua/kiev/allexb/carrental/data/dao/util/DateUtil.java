package ua.kiev.allexb.carrental.data.dao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author allexb
 * @version 1.0 04.08.2016
 */
public class DateUtil {

    public static java.sql.Date getSQLFormatDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date getSimpleFormatDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
