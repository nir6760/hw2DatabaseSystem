package HW2;

import HW2.business.*;
import HW2.data.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import HW2.data.PostgreSQLErrorCodes;

import java.util.ArrayList;

import static HW2.business.ReturnValue.*;


public class Solution {
    /************************************************************/
    /*****************************************************************************************************************/
    /**Auxiliary defines**/
    static final int NOT_NULL_VIOLATION = PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue();
    static final int FOREIGN_KEY_VIOLATION = PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue();
    static final int UNIQUE_VIOLATION = PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue();
    static final int CHECK_VIOLATION = PostgreSQLErrorCodes.CHECK_VIOLATION.getValue();




    static final String T_STUDENT = "Student";
    static final String T_TEST = "Test";
    static final String T_SUPERVISOR = "Supervisor";
    static final String T_TAKE_TEST = "TakeTest";
    static final String T_OVERSEE = "Oversee";

    static final String[] tables = {T_STUDENT, T_TEST, T_SUPERVISOR, T_TAKE_TEST, T_OVERSEE};
    /************************************************************/

    /*****************************************************************************************************************/
    /**Auxiliary functions**/
    //auxiliary close connection function - connection epilog
    private static ReturnValue connectionEpilog(Connection con, PreparedStatement pstmt) {
        try {
            pstmt.close();
        } catch (SQLException e) {
            return ERROR;
        }
        try {
            con.close();
        } catch (SQLException e) {
            return ERROR;
        }
        return OK;
    }
    // auxiliary which return the right type of error after Insert query
    private static ReturnValue typeOfErrorForInsert(int type_error) {
        if (type_error == NOT_NULL_VIOLATION || type_error == CHECK_VIOLATION)
            return BAD_PARAMS;
        else if (type_error == UNIQUE_VIOLATION)
            return ALREADY_EXISTS;
        else if (type_error == FOREIGN_KEY_VIOLATION)
            return NOT_EXISTS;
        return ERROR;
    }


    /**Auxiliary functins for basic DataBase functions **/
    //auxiliary create table function
    private static PreparedStatement createTable(String table, Connection connection){
        PreparedStatement pstmt = null;
        try{
            switch (table){
                case T_STUDENT: {
                    pstmt = connection.prepareStatement(
                            "CREATE TABLE " + table
                                    +"  (\n"
                                    +"  student_id integer NOT NULL, \n"
                                    +"  name text NOT NULL,\n"
                                    +"  faculty text NOT NULL,\n"
                                    +"  credit_points integer NOT NULL,\n"
                                    +"  PRIMARY KEY (student_id),"
                                    +"  CHECK (student_id > 0),\n"
                                    +"  CHECK (credit_points >= 0)\n"
                                    +")\n"
                    );
                    break;
                }
                case T_TEST: {
                    pstmt = connection.prepareStatement(
                            "CREATE TABLE " + table
                                    +"  (\n"
                                    +"  course_number integer NOT NULL, \n"
                                    +"  semester integer NOT NULL,\n"
                                    +"  \"time\" integer NOT NULL,\n"
                                    +"  room integer NOT NULL,\n"
                                    +"  day integer NOT NULL,\n"
                                    +"  credit_points integer NOT NULL,\n"
                                    +"  PRIMARY KEY (course_number, semester),\n"
                                    +"  CHECK (course_number > 0) NOT VALID,\n"
                                    +"  CHECK (semester > 0 AND semester < 4),\n"
                                    +"  CHECK (\"time\" > 0 AND \"time\" < 4),\n"
                                    +"  CHECK (room > 0) NOT VALID,\n"
                                    +"  CHECK (day > 0 AND day < 32),\n"
                                    +"  CHECK (credit_points > 0)"
                                    +")\n"


                    );
                    break;
                }
                case T_SUPERVISOR: {
                    pstmt = connection.prepareStatement(
                            "CREATE TABLE " + table
                                    +"  (\n"
                                    +"  supervisor_id integer NOT NULL,\n"
                                    +"  name text NOT NULL,\n"
                                    +"  salary integer NOT NULL,\n"
                                    +"  PRIMARY KEY (supervisor_id),\n"
                                    +"  CHECK (supervisor_id > 0),\n"
                                    +"  CHECK (salary >= 0)\n"
                                    +")\n"

                    );

                    break;
                }
                case T_TAKE_TEST: {
                    pstmt = connection.prepareStatement(
                            "CREATE TABLE " + table
                                    +"  (\n"
                                    +"  student_id integer NOT NULL,\n"
                                    +"  course_number integer NOT NULL,\n"
                                    +"  semester integer NOT NULL,\n"
                                    +"  UNIQUE (student_id,course_number,semester),\n"
                                    +"  FOREIGN KEY (course_number, semester)\n"
                                    +"      REFERENCES " + T_TEST + " (course_number, semester)\n"
                                    +"      ON UPDATE CASCADE\n"
                                    +"      ON DELETE CASCADE,\n"
                                    +"  FOREIGN KEY (student_id)\n"
                                    +"      REFERENCES " + T_STUDENT + " (student_id)\n"
                                    +"      ON UPDATE CASCADE\n"
                                    +"      ON DELETE CASCADE\n"
                                    +")\n"

                    );
                    break;
                }
                case T_OVERSEE: {
                    pstmt = connection.prepareStatement(
                            "CREATE TABLE " + table
                                    +"  (\n"
                                    +"  course_number integer NOT NULL,\n"
                                    +"  semester integer NOT NULL,\n"
                                    +"  supervisor_id integer,\n"
                                    +"  UNIQUE (course_number,semester,supervisor_id),\n"
                                    +"  FOREIGN KEY (course_number, semester)\n"
                                    +"      REFERENCES " + T_TEST +" (course_number, semester)\n"
                                    +"      ON UPDATE CASCADE\n"
                                    +"      ON DELETE CASCADE,\n"
                                    +"  FOREIGN KEY (supervisor_id)\n"
                                    +"      REFERENCES " + T_SUPERVISOR + " (supervisor_id)\n"
                                    +"      ON UPDATE CASCADE\n"
                                    +"      ON DELETE CASCADE\n"
                                    +")\n"
                    );
                    break;
                }
            }
        } catch (SQLException e) {}
        return pstmt;
    }
    //auxiliary clear table function
    private static PreparedStatement clearTable(String table, Connection connection){ //clear table helper
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement(
                    "DELETE FROM " + table);


        } catch (SQLException e) {}
        return pstmt;
    }
    //auxiliary drop table function
    private static PreparedStatement dropTable(String table, Connection connection){ //dropTable helper
        PreparedStatement pstmt = null;
        try{
            pstmt = connection.prepareStatement(
                    "DROP TABLE IF EXISTS " + table);

        } catch (SQLException e) {}
        return pstmt;
    }


    /**Auxiliary functions for CRUD API **/
    // convert a record of ResultSet to an instance of the
    //Test business object
    private static void instanceFromTest(ResultSet rs,Test test) throws SQLException {
        test.setId(rs.getInt("course_number"));
        test.setSemester(rs.getInt("semester"));
        test.setCreditPoints(rs.getInt("credit_points"));
        test.setDay(rs.getInt("day"));
        test.setRoom(rs.getInt("room"));
        test.setTime(rs.getInt("time"));
        //return test;
    }
    // convert a record of ResultSet to an instance of the
    //Student business object
    private static void instanceFromStudent(ResultSet rs,Student student) throws SQLException {
        student.setId(rs.getInt("student_id"));
        student.setName(rs.getString("name"));
        student.setFaculty(rs.getString("faculty"));
        student.setCreditPoints(rs.getInt("credit_points"));
        //return student;
    }
    // convert a record of ResultSet to an instance of the
    //Supervisor business object
    private static void instanceFromSupervisor(ResultSet rs,Supervisor supervisor) throws SQLException {
        supervisor.setId(rs.getInt("supervisor_id"));
        supervisor.setName(rs.getString("name"));
        supervisor.setSalary(rs.getInt("salary"));
        //return supervisor;
    }

    /*****************************************************************************************************************/
    /**4 - Basic DataBase functions**/

    public static void createTables() {
        InitialState.createInitialState();
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        for (String table : tables) {
            pstmt = createTable(table,connection);
            try {
                if (pstmt != null) pstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                connectionEpilog(connection,pstmt); //if we cant excute end connection and return
                return;
            } ///todo:

        }
        connectionEpilog(connection,pstmt); //with finally for each iteration?


    }

    public static void clearTables() {
        //clear your tables here
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        for (String table : tables) {
            pstmt = clearTable(table,connection);
            try {
                if (pstmt != null) pstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                connectionEpilog(connection,pstmt); //if we cant excute end connection and return
                return;
            } ///todo:

        }
        connectionEpilog(connection,pstmt); //with finally for each iteration?

    }

    public static void dropTables() {
        InitialState.dropInitialState();
		//drop your tables here
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        for (int i=tables.length-1; i>=0 ; i--) { //because of the dependecies we have to drop the end to the front
            pstmt = dropTable(tables[i],connection);
            try {
                if (pstmt != null) pstmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                connectionEpilog(connection,pstmt); //if we cant excute end connection and return
                return;
            } ///todo:

        }
        connectionEpilog(connection,pstmt); //with finally for each iteration?
    }

    /*****************************************************************************************************************/
    /**3.2 - CRUD API**/

    public static ReturnValue addTest(Test test) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO " + T_TEST + "(\n"
                            +"  course_number, semester, \"time\", room, day, credit_points)\n"
                            +"  VALUES (?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1,test.getId());
            pstmt.setInt(2,test.getSemester());
            pstmt.setInt(3,test.getTime());
            pstmt.setInt(4,test.getRoom());
            pstmt.setInt(5,test.getDay());
            pstmt.setInt(6,test.getCreditPoints());
            pstmt.execute();
        } catch (SQLException e){
            int vio = Integer.valueOf(e.getSQLState());
            connectionEpilog(connection,pstmt);
            return typeOfErrorForInsert(vio);
        } finally {
            ret = connectionEpilog(connection,pstmt);
        }
        return ret;
    }

    public static Test getTestProfile(Integer testID, Integer semester) {
        Test test = Test.badTest();
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT * FROM " + T_TEST
                            + " WHERE course_number = ? AND semester = ?");
            pstmt.setInt(1,testID);
            pstmt.setInt(2,semester);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                instanceFromTest(rs,test);

            }
        } catch (SQLException e) {
            //test = Test.badTest();
        } finally {
            connectionEpilog(connection, pstmt);
        }
        return test;
    }

    public static ReturnValue deleteTest(Integer testID, Integer semester) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        boolean del = false;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM " + T_TEST
                            + " WHERE course_number = ? AND semester = ?");

            pstmt.setInt(1,testID);
            pstmt.setInt(2,semester);

            del = pstmt.execute();
        } catch (SQLException e) {
            connectionEpilog(connection, pstmt);
            return ERROR;
        } finally {
            ret = connectionEpilog(connection, pstmt);
        }
        if (del) { return NOT_EXISTS; }    // del is true if the first result is a ResultSet object;
                                            // false if the first result is an update count or there is no result
        return ret;
    }

    public static ReturnValue addStudent(Student student) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO " + T_STUDENT + "(\n"
                            +"   student_id, name, faculty, credit_points)\n"
                            +"   VALUES (?, ?, ?, ?);"
            );
            pstmt.setInt(1,student.getId());
            pstmt.setString(2,student.getName());
            pstmt.setString(3,student.getFaculty());
            pstmt.setInt(4,student.getCreditPoints());

            pstmt.execute();
        } catch (SQLException e){
            int vio = Integer.valueOf(e.getSQLState());
            connectionEpilog(connection,pstmt);
            return typeOfErrorForInsert(vio);
        } finally {
            ret = connectionEpilog(connection,pstmt);
        }
        return ret;
    }

    public static Student getStudentProfile(Integer studentID) {
        Student student=Student.badStudent();
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT * FROM " + T_STUDENT
                            + " WHERE student_id = ?");
            pstmt.setInt(1,studentID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
               instanceFromStudent(rs,student);

            }
        } catch (SQLException e) {
            //student = Student.badStudent();
        } finally {
            connectionEpilog(connection, pstmt);
        }
        return student;
    }

    public static ReturnValue deleteStudent(Integer studentID) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        boolean del = false;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM " + T_STUDENT
                            + " WHERE student_id = ?");

            pstmt.setInt(1,studentID);

            del = pstmt.execute();
        } catch (SQLException e) {
            connectionEpilog(connection, pstmt);
            return ERROR;
        } finally {
            ret = connectionEpilog(connection, pstmt);
        }
        if (del) { return NOT_EXISTS; }    // del is true if the first result is a ResultSet object;
                                            // false if the first result is an update count or there is no result
        return ret;
    }

    public static ReturnValue addSupervisor(Supervisor supervisor) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO " + T_SUPERVISOR + "(\n"
                            +"   supervisor_id, name, salary)\n"
                            +"   VALUES (?, ?, ?);"
            );
            pstmt.setInt(1,supervisor.getId());
            pstmt.setString(2,supervisor.getName());
            pstmt.setInt(3,supervisor.getSalary());

            pstmt.execute();
        } catch (SQLException e){
            int vio = Integer.valueOf(e.getSQLState());
            connectionEpilog(connection,pstmt);
            return typeOfErrorForInsert(vio);
        } finally {
            ret = connectionEpilog(connection,pstmt);
        }
        return ret;
    }

    public static Supervisor getSupervisorProfile(Integer supervisorID) {
        Supervisor supervisor = Supervisor.badSupervisor();
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT * FROM " + T_SUPERVISOR
                            + " WHERE supervisor_id = ?");
            pstmt.setInt(1,supervisorID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                instanceFromSupervisor(rs,supervisor);

            }
        } catch (SQLException e) {
            //supervisor = Supervisor.badSupervisor();
        } finally {
            connectionEpilog(connection, pstmt);
        }
        return supervisor;
    }

    public static ReturnValue deleteSupervisor(Integer supervisorID) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        boolean del = false;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM " + T_SUPERVISOR
                            + " WHERE supervisor_id = ?");

            pstmt.setInt(1,supervisorID);

            del = pstmt.execute();
        } catch (SQLException e) {
            connectionEpilog(connection, pstmt);
            return ERROR;
        } finally {
            ret = connectionEpilog(connection, pstmt);
        }
        if (del) { return NOT_EXISTS; }    // del is true if the first result is a ResultSet object;
                                            // false if the first result is an update count or there is no result
        return ret;
    }

    /*****************************************************************************************************************/
    /**3.3 - BASIC API**/

    public static ReturnValue studentAttendTest(Integer studentID, Integer testID, Integer semester) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO " + T_TAKE_TEST + "(\n"
                            +"\tstudent_id, course_number, semester)\n"
                            +"\tVALUES (?, ?, ?);"
            );
            pstmt.setInt(1,studentID);
            pstmt.setInt(2,testID);
            pstmt.setInt(3,semester);

            pstmt.execute();
        } catch (SQLException e){
            int vio = Integer.valueOf(e.getSQLState());
            connectionEpilog(connection,pstmt);
            return typeOfErrorForInsert(vio);
        } finally {
            ret = connectionEpilog(connection,pstmt);
        }
        return ret;
    }

    public static ReturnValue studentWaiveTest(Integer studentID, Integer testID, Integer semester) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        boolean del = false;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM " + T_TAKE_TEST
                            + " WHERE student_id = ? AND course_number = ? AND semester = ?");

            pstmt.setInt(1,studentID);
            pstmt.setInt(1,testID);
            pstmt.setInt(1,semester);

            del = pstmt.execute();
        } catch (SQLException e) {
            connectionEpilog(connection, pstmt);
            return ERROR;
        } finally {
            ret = connectionEpilog(connection, pstmt);
        }
        if (del) { return NOT_EXISTS; }    // del is true if the first result is a ResultSet object;
                                           // false if the first result is an update count or there is no result
        return ret;
    }

    public static ReturnValue supervisorOverseeTest(Integer supervisorID, Integer testID, Integer semester) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO " + T_OVERSEE + "(\n"
                            +"\tcourse_number, semester, supervisor_id)\n"
                            +"\tVALUES (?, ?, ?);"
            );
            pstmt.setInt(1,testID);
            pstmt.setInt(2,semester);
            pstmt.setInt(3,supervisorID);

            pstmt.execute();
        } catch (SQLException e){
            int vio = Integer.valueOf(e.getSQLState());
            connectionEpilog(connection,pstmt);
            return typeOfErrorForInsert(vio);
        } finally {
            ret = connectionEpilog(connection,pstmt);
        }
        return ret;
    }

    public static ReturnValue supervisorStopsOverseeTest(Integer supervisorID, Integer testID, Integer semester) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        boolean del = false;
        ReturnValue ret = OK;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM " + T_OVERSEE
                            + " WHERE supervisor_id = ? AND course_number = ? AND semester = ?");

            pstmt.setInt(1,supervisorID);
            pstmt.setInt(1,testID);
            pstmt.setInt(1,semester);

            del = pstmt.execute();
        } catch (SQLException e) {
            connectionEpilog(connection, pstmt);
            return ERROR;
        } finally {
            ret = connectionEpilog(connection, pstmt);
        }
        if (del) { return NOT_EXISTS; }    // del is true if the first result is a ResultSet object;
                                            // false if the first result is an update count or there is no result
        return ret;
    }
    public static Float averageTestCost() {
        return 0.0f;
    }

    public static Integer getWage(Integer supervisorID) {
        return 0;
    }

    public static ArrayList<Integer> supervisorOverseeStudent() {
        return new ArrayList<Integer>();
    }

    public static ArrayList<Integer> testsThisSemester(Integer semester) {
        return new ArrayList<Integer>();
    }

    public static Boolean studentHalfWayThere(Integer studentID) {
        return true;
    }

    public static Integer studentCreditPoints(Integer studentID) {
        return 0;
    }

    public static Integer getMostPopularTest(String faculty) {
        return 0;
    }

    /*****************************************************************************************************************/
    /**3.4 - Advanced API**/

    public static ArrayList<Integer> getConflictingTests() {
        return new ArrayList<Integer>();
    }

    public static ArrayList<Integer> graduateStudents() {
        return new ArrayList<Integer>();
    }

    public static ArrayList<Integer> getCloseStudents(Integer studentID) {
        return new ArrayList<Integer>();
    }
}

