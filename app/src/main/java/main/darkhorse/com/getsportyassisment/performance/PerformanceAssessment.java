package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 28/03/17.
 */

public class PerformanceAssessment {

    private int id;
    private int studentId;
    private String module;
    private String submodule;
    private String question;
    private int answer;
    private int rowId;
    private int status;

//    public PerformanceAssessment(int id, String module, String submodule, String question, int answer) {
//        this.id = id;
//        this.module = module;
//        this.submodule = submodule;
//        this.question = question;
//        this.answer = answer;
//    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSubmodule() {
        return submodule;
    }

    public void setSubmodule(String submodule) {
        this.submodule = submodule;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
