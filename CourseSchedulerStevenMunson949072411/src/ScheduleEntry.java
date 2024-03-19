
import java.util.Calendar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author muns1
 */

public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private java.sql.Timestamp Timestamp;

    public ScheduleEntry(String semester, String courseCode, String studentID, String status, java.sql.Timestamp Timestamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.Timestamp = Timestamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }
    
    public java.sql.Timestamp getTimestamp(){
        return Timestamp;
    }
}
