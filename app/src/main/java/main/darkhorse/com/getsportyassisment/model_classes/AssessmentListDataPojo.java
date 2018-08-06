package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by shekhar on 2/8/18.
 */
public class AssessmentListDataPojo {


    String assessment_id;

    public String getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(String assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getAssessment_name() {
        return assessment_name;
    }

    public void setAssessment_name(String assessment_name) {
        this.assessment_name = assessment_name;
    }

    public String getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(String assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getAssigned_status() {
        return assigned_status;
    }

    public void setAssigned_status(String assigned_status) {
        this.assigned_status = assigned_status;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    String assessment_name;
    String assigned_date;
    String assigned_status;
    String venue;
    String category;
    String date_created;
    String institute_id;

}
