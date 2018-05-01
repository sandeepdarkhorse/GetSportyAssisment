package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 6/2/17.
 */
public class SportEducationData {
    private String degree;
    private String stream;
    private String organisation;
    private String dateFrom;
    private String dateTo;
    private String tillDate;
    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }



   // private String courseDuration;
    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }


    public SportEducationData(String degree, String stream, String organisation, String dateFrom,String dateTo,String tillDate) {
        this.degree = degree;
        this.stream = stream;
        this.organisation = organisation;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.tillDate = tillDate;

//        this.courseDuration = courseDuration;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
//
//    public String getCourseDuration() {
//        return courseDuration;
//    }
//
//    public void setCourseDuration(String courseDuration) {
//        this.courseDuration = courseDuration;
//    }
}
