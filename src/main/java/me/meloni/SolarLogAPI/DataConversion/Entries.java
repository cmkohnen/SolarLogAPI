package me.meloni.SolarLogAPI.DataConversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This Class includes the static List<Date> entries function to list all data points stored for one day.
 * @author ChaosMelone9
 * @since 0.0.1
 */
public class Entries {
    public static List<Date> entriesPerDay(Date day) throws ParseException {
        List<Date> entries = new ArrayList<>();

        int min = 0;
        int hour = 0;

        String date = new SimpleDateFormat("yyyyMMdd").format(day);

        for(int i = 0; i <288; i++){
            min = min + 5;
            if(min == 60) {
                hour++;
                min = 0;
            }
            @SuppressWarnings("SpellCheckingInspection") DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            StringBuilder timestamp = new StringBuilder();
            timestamp.append(date);
            if(String.valueOf(hour).length() == 1) {
                timestamp.append("0").append(hour);
            } else {
                timestamp.append(hour);
            }
            if(String.valueOf(min).length() == 1) {
                timestamp.append("0").append(min);
            } else {
                timestamp.append(min);
            }
            timestamp.append("00");
            Date d = formatter.parse(timestamp.toString());
            entries.add(d);
        }
        return entries;
  }

  public static List<String> timestamps() {
        List<String> entries = new ArrayList<>();

        int min = 0;
        int hour = 0;

        for(int i = 0; i <288; i++) {
            min = min + 5;
            if (min == 60) {
                hour++;
                min = 0;
            }
            StringBuilder timestamp = new StringBuilder();
            if (String.valueOf(hour).length() == 1) {
                timestamp.append("0").append(hour).append(":");
            } else {
                timestamp.append(hour).append(":");
            }
            if (String.valueOf(min).length() == 1) {
                timestamp.append("0").append(min);
            } else {
                timestamp.append(min);
            }
            entries.add(timestamp.toString());
        }
        return entries;
      }

      public static List<Date> entriesPerMonth(YearMonth yearMonth) {
          List<Date> dateList = new ArrayList<>();

          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.MONTH, yearMonth.getMonthValue());
          cal.set(Calendar.DAY_OF_MONTH, 1);
          cal.set(Calendar.YEAR, yearMonth.getYear());
          int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
          for (int i = 1; i < maxDay; i++)
          {
              cal.set(yearMonth.getYear(),yearMonth.getMonthValue() ,i + 1,0,0,0);
              dateList.add(cal.getTime());
          }
          return dateList;
      }

      public static List<YearMonth> entriesPerYear(Year year) {
        List<YearMonth> yearMonths = new ArrayList<>();
        for(int i = 1; i < 13; i++) {
            yearMonths.add(year.atMonth(i));
        }
        return yearMonths;
      }
  }
