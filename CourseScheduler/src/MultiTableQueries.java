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
public class MultiTableQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllClassDescriptions;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester){
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
        
        try
            {
                getAllClassDescriptions = connection.prepareStatement("select app.class.courseCode, description, seats from app.class, app.course where semester = ? and app.class.courseCode = app.course.courseCode order by app.class.courseCode");
                getAllClassDescriptions.setString(1, semester);
                resultSet = getAllClassDescriptions.executeQuery();

                while(resultSet.next())
                {
                    String courseCode = resultSet.getString("courseCode");
                    String description = resultSet.getString("description");
                    int seats = resultSet.getInt("seats");
                    
                    ClassDescription classDescription = new ClassDescription(courseCode, description, seats);
                    classDescriptions.add(classDescription);
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return classDescriptions;
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> scheduledStudents = new ArrayList<StudentEntry>();
        
        try{
            getScheduledStudentsByClass = connection.prepareStatement("select * from app.student join app.schedule on app.student.studentID = app.schedule.studentID where app.schedule.semester = ? and app.schedule.coursecode = ? and app.schedule.status = 's'");
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
                    
            ResultSet resultSet = getScheduledStudentsByClass.executeQuery();
                    
            while (resultSet.next()){
               StudentEntry student = new StudentEntry(resultSet.getString("studentID"), resultSet.getString("firstname"), resultSet.getString("lastname"));
               scheduledStudents.add(student);
               }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return scheduledStudents;
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> waitlistedStudents = new ArrayList<StudentEntry>();
        
        try{
            getWaitlistedStudentsByClass = connection.prepareStatement("select * from app.student join app.schedule on app.student.studentID = app.schedule.studentID where app.schedule.semester = ? and app.schedule.coursecode = ? and app.schedule.status = 'w' order by timestamp");
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);                    
            resultSet = getWaitlistedStudentsByClass.executeQuery();
                    
            while (resultSet.next()){
               StudentEntry student = new StudentEntry(resultSet.getString("studentID"), resultSet.getString("firstname"), resultSet.getString("lastname"));
               waitlistedStudents.add(student);
               }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return waitlistedStudents;
    }
}
