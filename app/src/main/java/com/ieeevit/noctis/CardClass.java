package com.ieeevit.noctis;

/**
 * Created by Mayank on 3/25/2018.
 */

public class CardClass {

    private String FromDate,FromTime,ToDate,ToTime,Admin;

    public CardClass(String fromDate, String fromTime, String toDate, String toTime, String admin) {
        FromDate = fromDate;
        FromTime = fromTime;
        ToDate = toDate;
        ToTime = toTime;
        Admin = admin;
    }


    public CardClass() {
        super();
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String admin) {
        Admin = admin;
    }
    
}
