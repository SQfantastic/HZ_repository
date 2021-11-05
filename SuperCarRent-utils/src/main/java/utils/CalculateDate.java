package utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算两个日期之间相差的天数
 */

public class CalculateDate {

    public int Calculate(Date beginDate,Date endDate){

        int betweenDays = (int)((endDate.getTime() - beginDate.getTime()) / (1000L*3600L*24L));

        return betweenDays;
    }

    public int Calculate(String begindate,String enddate) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = simpleDateFormat.parse(begindate);
        Date endDate = simpleDateFormat.parse(enddate);

        int betweenDays = (int)((endDate.getTime() - beginDate.getTime()) / (1000L*3600L*24L));

        return betweenDays;
    }
}
