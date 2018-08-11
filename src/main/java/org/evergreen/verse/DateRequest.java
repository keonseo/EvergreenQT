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

    public void setYear(final String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(final String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public DateTime toDateTime() {
        return new DateTime(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0, 0);
    }
}
