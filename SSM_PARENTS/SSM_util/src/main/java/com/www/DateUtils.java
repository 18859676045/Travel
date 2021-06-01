package com.www;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {


    /**
     * ����ת���ַ���
     * @param date
     * @param patt
     * @return String
     */
    public static String date2String(Date date,String patt){

        SimpleDateFormat sdf=new SimpleDateFormat(patt);
        String format = sdf.format(date);

        return format;
    }

    /**
     * �ַ���ת������
     * @param
     * @param patt
     * @return Date
     * @throws ParseException
     */
    public static Date date2Date(String str,String patt) throws ParseException {

        SimpleDateFormat sdf=new SimpleDateFormat(patt);
        Date parse = sdf.parse(str);

        return parse;
    }



}
