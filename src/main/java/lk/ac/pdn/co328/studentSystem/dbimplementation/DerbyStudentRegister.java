/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ac.pdn.co328.studentSystem.dbimplementation;

import lk.ac.pdn.co328.studentSystem.Student;
import lk.ac.pdn.co328.studentSystem.StudentRegister;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author himesh
 */
public class DerbyStudentRegister extends StudentRegister {

    Connection connection = null;
    public DerbyStudentRegister() throws SQLException
    {

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        }catch(ClassNotFoundException e){
            System.out.println("Class not found");
        }
        String dbURL1 = "jdbc:derby:\\codejava\\studentDB.db;create=true";
            //String dbURL1 = "jdbc:derby:codejava/studentDB;create=true";
            connection = DriverManager.getConnection(dbURL1);
            if (connection != null)
            {
                String SQL_CreateTable = "CREATE TABLE Students(id INT , firstname VARCHAR(24),lastname VARCHAR(24))";
                System.out.println ( "Creating table addresses..." );
                try
                {
                    Statement stmnt = connection.createStatement();
                    stmnt.execute( SQL_CreateTable );
                    stmnt.close();
                    System.out.println("Table created");
                } catch ( SQLException e )
                {
                    System.out.println("sql");
                }
               System.out.println("Connected to database");
            }
            else
            {
                throw new SQLException("Connection Failed");
            }
    }

    @Override
    public void addStudent(Student st) throws Exception {
        if (connection != null)
        {
            String SQL_AddStudent = "INSERT INTO Students VALUES (" + st.getId() + ",'" + st.getFirstName() + "'," + st.getLastName() + ")";
            System.out.println ( "Adding the student..." + SQL_AddStudent);

            Statement stmnt = connection.createStatement();
            stmnt.execute(SQL_AddStudent );
            stmnt.close();
            System.out.println("Student Added");

        }
        else
            throw new Exception("Database Connection Error");
    }

    @Override
    public void removeStudent(int regNo) {
        String query = " DELETE FROM Students " + "WHERE id" + regNo;
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Student findStudent(int regNo) {
        String query = " SELECT firstname, lastname FROM Students WHERE id " + regNo;
        Student st = null;
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");
                String first = rs.getString("firstname");
                String last = rs.getString("lastname");

                st = new Student(id,first,last);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    return st;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        String query = " DROP TABLE Students ";
        try {
            Statement stmnt = connection.createStatement();
            //ResultSet rs = stmnt.executeQuery(query);
            //rs.close();
            stmnt.execute(query);
            stmnt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Student> findStudentsByName(String name) {
        ArrayList<Student> studentList = new ArrayList<Student>();
        String query = "SELECT firstname, lastname, id FROM Students" + " WHERE firstname = name ";
        Student st = null;
        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");
                String first = rs.getString("firstname");
                String last = rs.getString("lastname");

                System.out.print(", Id: " + id);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
                st = new Student(id,first,last);
                studentList.add(st);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public ArrayList<Integer> getAllRegistrationNumbers() {
        ArrayList<Integer> studentId = new ArrayList<Integer>();
        String query = "SELECT id FROM Students";

        try {
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("id");

                studentId.add(id);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentId;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
