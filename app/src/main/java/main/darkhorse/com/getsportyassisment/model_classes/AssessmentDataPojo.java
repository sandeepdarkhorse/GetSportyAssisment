package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by shekhar on 2/8/18.
 */
public class AssessmentDataPojo {

    String admin_id;
    String name;
    String venue;
    String assessment_status;
    String assesment_category;
    String date_assessed;

    String institute_id;



    public AssessmentDataPojo(String admin_id, String name, String venue, String assessment_status, String assesment_category, String date_assessed,    String institute_id) {
        this.admin_id = admin_id;
        this.name = name;
        this.venue = venue;
        this.assessment_status = assessment_status;
        this.assesment_category = assesment_category;
        this.date_assessed = date_assessed;
        this.institute_id = institute_id;
    }





}
