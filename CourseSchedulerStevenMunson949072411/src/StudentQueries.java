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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student){
            connection = DBConnection.getConnection();
            try
            {
                addStudent = connection.prepareStatement("insert into app.student (studentID, firstName, lastName) values (?, ?, ?)");
                addStudent.setString(1, student.getStudentID());
                addStudent.setString(2, student.getFirstName());
                addStudent.setString(3, student.getLastName());
                addStudent.executeUpdate();
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
            {
                getAllStudents = connection.prepareStatement("select * from app.student");
                resultSet = getAllStudents.executeQuery();

                while(resultSet.next())
                {
                    String studentID = resultSet.getString("studentID");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    StudentEntry student = new StudentEntry(studentID, firstName, lastName);
                    

                    students.add(student);
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            return students;
        
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try
        {
            getStudent = connection.prepareStatement("select * from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
                {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    student = new StudentEntry(studentID, firstName, lastName);
                }
            }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}

