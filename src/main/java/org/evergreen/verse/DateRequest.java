package org.evergreen.verse;

import org.joda.time.DateTime;

public class DateRequest {
    private String year;


    private String month;
    private String day;

    // Default constructor required
    public DateRequest() { }

    public DateRequest(final String year, final String month, final String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public DateTime toDateTime() {
        return new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0, 0);
    }

    public DateRequest setYear(String year) {
        this.year = year;
        return this;
    }

    public DateRequest setMonth(String month) {
        this.month = month;
        return this;
    }

    public DateRequest setDay(String day) {
        this.day = day;
        return this;
    }
}
