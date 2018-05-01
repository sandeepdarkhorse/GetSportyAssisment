package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by sandeepsingh on 19/4/18.
 */

public class RefDate {
    private boolean isDate;
    private String refDate;
    private boolean isRange;
    private String startYear;
    private String endYear;

    public RefDate(boolean isDate, String refDate, boolean isRange, String startYear, String endYear) {
        this.isDate = isDate;
        this.refDate = refDate;
        this.isRange = isRange;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public boolean isDate() {
        return isDate;
    }

    public void setDate(boolean date) {
        isDate = date;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean range) {
        isRange = range;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

}
