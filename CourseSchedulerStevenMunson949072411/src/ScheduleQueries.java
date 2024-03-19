/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author muns1
 */
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduleStudentCount;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement getWaitlistedStudentByClass;
    private static PreparedStatement getStatus;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
            try
            {
                addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, courseCode, studentID, status, Timestamp) values (?, ?, ?, ?, ?)");
                addScheduleEntry.setString(1, entry.getSemester());
                addScheduleEntry.setString(2, entry.getCourseCode());
                addScheduleEntry.setString(3, entry.getStudentID());
                addScheduleEntry.setString(4, entry.getStatus());
                addScheduleEntry.setTimestamp(5, entry.getTimestamp());
                addScheduleEntry.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
            {
                getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and studentID = ?");
                getScheduleByStudent.setString(1, semester);
                getScheduleByStudent.setString(2, studentID);
                resultSet = getScheduleByStudent.executeQuery();
                
                while (resultSet.next()) {
                    String sem = resultSet.getString("semester");
                    String courseCode = resultSet.getString("courseCode");
                    String SID = resultSet.getString("studentID");
                    String status = resultSet.getString("status");
                    java.sql.Timestamp Timestamp = resultSet.getTimestamp("Timestamp");

                    ScheduleEntry scheduleEntry = new ScheduleEntry(sem, courseCode, SID, status, Timestamp);
                    schedule.add(scheduleEntry);
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return schedule;
    }
    
    public static int getScheduleStudentCount(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int count = 0;
        try
            {
                getScheduleStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
                getScheduleStudentCount.setString(1, semester);
                getScheduleStudentCount.setString(2, courseCode);
                resultSet = getScheduleStudentCount.executeQuery();
                
                while(resultSet.next())
                {
                    count = resultSet.getInt(1);
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return count;
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry student){
        connection = DBConnection.getConnection();
            try
            {
                updateScheduleEntry = connection.prepareStatement("update app.schedule set status = 's' where semester = ? and coursecode = ? and studentID = ?");
                updateScheduleEntry.setString(1, semester);
                updateScheduleEntry.setString(2, student.getCourseCode());
                updateScheduleEntry.setString(3, student.getStudentID());
                updateScheduleEntry.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
            try
            {
                getWaitlistedStudentByClass = connection.prepareStatement("select * from app.schedule where semester = ? and coursecode = ? and status = 'w' order by timestamp");
                getWaitlistedStudentByClass.setString(1, semester);
                getWaitlistedStudentByClass.setString(2, courseCode);
                resultSet = getWaitlistedStudentByClass.executeQuery();
                
                while (resultSet.next()){
                    String sem = resultSet.getString("semester");
                    String courseCod = resultSet.getString("courseCode");
                    String SID = resultSet.getString("studentID");
                    String status = resultSet.getString("status");
                    java.sql.Timestamp Timestamp = resultSet.getTimestamp("Timestamp");
                    
                    ScheduleEntry scheduleEntry = new ScheduleEntry(sem, courseCod, SID, status, Timestamp);
                    schedule.add(scheduleEntry);
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return schedule;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
            try
            {
                dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and courseCode = ?");
                dropStudentScheduleByCourse.setString(1, semester);
                dropStudentScheduleByCourse.setString(2, studentID);
                dropStudentScheduleByCourse.setString(3, courseCode);
                dropStudentScheduleByCourse.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
            try
            {
                dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and courseCode = ?");
                dropScheduleByCourse.setString(1, semester);
                dropScheduleByCourse.setString(2, courseCode);
                dropScheduleByCourse.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
    }
    
}
