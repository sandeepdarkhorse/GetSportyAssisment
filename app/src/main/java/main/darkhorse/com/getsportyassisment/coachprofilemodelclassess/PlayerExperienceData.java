package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 7/2/17.
 */
public class PlayerExperienceData {
    private String designation;
    private String organisationName;
    private String description;
    private String dateFrom;
    private String dateTo;
    private String tillDate;
    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }



    public PlayerExperienceData(String designation, String organisationName, String description, String dateFrom, String dateTo,String tillDate) {
        this.designation = designation;
        this.organisationName = organisationName;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.tillDate = tillDate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
