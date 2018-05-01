package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 25/10/17.
 */

public class StudentDetails {

    private String student_id;
    private String student_name;
    private String last_assessment;
    private String avg;
    private String total;

    public StudentDetails(String student_id, String student_name, String last_assessment, String avg, String total) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.last_assessment = last_assessment;
        this.avg = avg;
        this.total = total;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getLast_assessment() {
        return last_assessment;
    }

    public void setLast_assessment(String last_assessment) {
        this.last_assessment = last_assessment;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
