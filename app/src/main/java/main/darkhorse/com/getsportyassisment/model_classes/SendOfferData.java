package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by sandeepsingh on 12/2/18.
 */

public class SendOfferData {

    private String emp_id;
    private String applicant_id;
    private String job_id;
    private String salary;
    private String joining_date;

    public SendOfferData(String emp_id, String applicant_id, String job_id, String salary, String joining_date) {
        this.emp_id = emp_id;
        this.applicant_id = applicant_id;
        this.job_id = job_id;
        this.salary = salary;
        this.joining_date = joining_date;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(String applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }
}
