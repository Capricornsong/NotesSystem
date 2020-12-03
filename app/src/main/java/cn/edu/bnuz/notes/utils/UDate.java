package cn.edu.bnuz.notes.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDate {
    public static Date TfStringtoDate(String sdate){
        sdate.replace('T' , ' ');
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date finalDate = null;
        try {
            finalDate = dateFormat.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }
}
