package HW2;

import HW2.business.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static HW2.TestUtils.*;
import static HW2.Solution.*;
import static HW2.business.ReturnValue.*;
import static org.junit.Assert.*;

// TODO: check for connection error returns a ERROR value.

/**
 * initializes the students/tests/observers tables @Before, and tests the basic API section
 */
public class BasicAPITests1 extends AbstractTest {

    /**
     * initializes the tables before every run
     */
    @Before
    public void initTables() {
        super.clearTables();

        addTest(createTest(1, 1, 1, 1, 1, 1));
        addTest(createTest(2, 1, 1, 1, 1, 2));
        addTest(createTest(1, 2, 1, 1, 1, 2));

        addStudent(createStudent(1, 0, "a", "CS"));
        addStudent(createStudent(2, 2, "b", "EE"));
        addStudent(createStudent(3, 4, "c", "MATH"));

        addSupervisor(createSupervisor(1, "a", 1));
        addSupervisor(createSupervisor(2, "b", 10));
        addSupervisor(createSupervisor(3, "c", 100));
    }

    @Test
    public void testStudentAttendTest() {
        // return OK for successful insertion
        assertEquals(studentAttendTest(1, 1, 1), OK);
        assertEquals(studentAttendTest(1, 2, 1), OK);
        assertEquals(studentAttendTest(2, 1, 1), OK);
        // return NOT_EXIST
        assertEquals(studentAttendTest(4, 1, 1), NOT_EXISTS);
        assertEquals(studentAttendTest(1, 1, 3), NOT_EXISTS);
        assertEquals(studentAttendTest(3, 3, 1), NOT_EXISTS);
        // return ALREADY_EXISTS
        assertEquals(studentAttendTest(1, 1, 1), ALREADY_EXISTS);
        assertEquals(studentAttendTest(1, 2, 1), ALREADY_EXISTS);
        assertEquals(studentAttendTest(2, 1, 1), ALREADY_EXISTS);
        // OK again
        assertEquals(studentAttendTest(3, 1, 2), OK);
    }

    @Test
    public void testStudentWaiveTest() {
        // OK
        assertEquals(studentAttendTest(1, 1, 1), OK);
        assertEquals(studentAttendTest(1, 2, 1), OK);
        assertEquals(studentAttendTest(2, 1, 1), OK);
        assertEquals(studentAttendTest(2, 1, 2), OK);

        assertEquals(studentWaiveTest(1, 1, 1), OK);
        assertEquals(studentWaiveTest(2, 1, 1), OK);
        // NOT_EXISTS
        assertEquals(studentWaiveTest(5, 5, 1), NOT_EXISTS);
        assertEquals(studentWaiveTest(1, 1, 1), NOT_EXISTS);
        assertEquals(studentWaiveTest(2, 1, 1), NOT_EXISTS);
        // OK
        assertEquals(studentWaiveTest(2, 1, 2), OK);
        assertEquals(studentWaiveTest(1, 2, 1), OK);
    }

    @Test
    public void testSupervisorOverseeTest() {
        // OK
        assertEquals(supervisorOverseeTest(1, 1, 1), OK);
        assertEquals(supervisorOverseeTest(1, 2, 1), OK);
        assertEquals(supervisorOverseeTest(1, 1, 2), OK);
        assertEquals(supervisorOverseeTest(2, 1, 1), OK);
        // NOT_EXISTS
        assertEquals(supervisorOverseeTest(4,1, 1), NOT_EXISTS);
        assertEquals(supervisorOverseeTest(1,2, 2), NOT_EXISTS);
        assertEquals(supervisorOverseeTest(2,3, 1), NOT_EXISTS);
        // ALREADY_EXISTS
        assertEquals(supervisorOverseeTest(1, 1, 1), ALREADY_EXISTS);
        assertEquals(supervisorOverseeTest(2, 1, 1), ALREADY_EXISTS);
        // OK
        assertEquals(supervisorOverseeTest(3, 1, 2), OK);
    }

    @Test
    public void testSupervisorStopsOverseeTest() {
        supervisorOverseeTest(1, 1, 1);
        supervisorOverseeTest(1, 1, 2);
        supervisorOverseeTest(2, 1, 1);
        // OK
        assertEquals(supervisorStopsOverseeTest(1, 1, 1), OK);
        assertEquals(supervisorStopsOverseeTest(2, 1, 1), OK);
        // NOT_EXISTS
        assertEquals(supervisorStopsOverseeTest(1, 1, 1), NOT_EXISTS);
        assertEquals(supervisorStopsOverseeTest(4, 1, 1), NOT_EXISTS);
        assertEquals(supervisorStopsOverseeTest(2, 1, 2), NOT_EXISTS);
        // OK
        assertEquals(supervisorStopsOverseeTest(1, 1, 2), OK);
    }

    @Test
    public void testAverageTestCost() {
        // return a normal average
        assertEquals(averageTestCost(), 0, eps);
        supervisorOverseeTest(1, 1, 1); // 1
        supervisorOverseeTest(2, 1, 1); // 10
        supervisorOverseeTest(2, 2, 1); // 10
        supervisorOverseeTest(3, 1, 2); // 100
        //assertEquals(averageTestCost(), 121.0 / 3.0, eps);
        assertEquals(averageTestCost(), 38.5, eps); ///todo: check avecost
        addTest(createTest(1, 3));
        //assertEquals(averageTestCost(), 121.0 / 4.0, eps);
        assertEquals(averageTestCost(), 28.875, eps); ///todo: check avecost
        supervisorStopsOverseeTest(3, 1, 2);
        //assertEquals(averageTestCost(), 21.0 / 4.0, eps);
        assertEquals(averageTestCost(), 3.875, eps); ///todo: check avecost
        // return 0 in case of 0 div
        // delete all the tests and run again
        assertEquals(supervisorStopsOverseeTest(1, 1, 1), OK);
        assertEquals(supervisorStopsOverseeTest(2, 1, 1), OK);
        assertEquals(supervisorStopsOverseeTest(2, 2, 1), OK);
        assertEquals(deleteTest(1, 1), OK);
        assertEquals(deleteTest(1, 2), OK);
        assertEquals(deleteTest(1, 3), OK);
        assertEquals(deleteTest(2, 1), OK);
        assertEquals(averageTestCost(), 0, eps);

        // return -1 if some other error
        // TODO: test this ^^
    }

    @Test
    public void testGetWage() {
        // get the supervisors wages
        assertEquals(getWage(1).intValue(), 0);
        supervisorOverseeTest(1, 1, 1);
        assertEquals(getWage(1).intValue(), 1);
        supervisorOverseeTest(1, 1, 2);
        assertEquals(getWage(1).intValue(), 2);
        supervisorOverseeTest(1, 2, 1);
        assertEquals(getWage(1).intValue(), 3);
        supervisorOverseeTest(3, 2, 1);
        assertEquals(getWage(3).intValue(), 100);
        // return -1 if error or sup does not exists
        assertEquals(getWage(-1).intValue(), -1);
        assertEquals(getWage(4).intValue(), -1);
    }

    @Test
    public void testSupervisorOverseeStudent() {
        // returns all the students that have an observer that oversee the student more then once
        // descending ordered
        // up to size of 5
        // empty list in other cases

        // no student is taking tests
        assertTrue(supervisorOverseeStudent().isEmpty());
        // no supervisor is supervising
        assertEquals(OK, studentAttendTest(1, 1, 1));
        assertEquals(OK, studentAttendTest(1, 1, 2));
        assertTrue(supervisorOverseeStudent().isEmpty());
        // supervisor supervising once
        assertEquals(OK, supervisorOverseeTest(1, 1, 1));
        assertTrue(supervisorOverseeStudent().isEmpty());
        // twice
        assertEquals(OK, supervisorOverseeTest(1, 1, 2));
        assertEquals("[1]", supervisorOverseeStudent().toString());
        // now both students
        assertEquals(OK, studentAttendTest(2, 1, 1));
        assertEquals(OK, studentAttendTest(2, 1, 2));
        assertEquals("[2, 1]", supervisorOverseeStudent().toString());
        // tree students with different observer
        assertEquals(OK, studentAttendTest(3, 2, 1));
        assertEquals(OK, studentAttendTest(3, 1, 1));
        assertEquals(OK, supervisorOverseeTest(2, 1, 1));
        assertEquals(OK, supervisorOverseeTest(2, 2, 1));
        assertEquals("[3, 2, 1]", supervisorOverseeStudent().toString());
        // adding another test to a student should not change
        assertEquals(OK, studentAttendTest(1, 2, 1));
        assertEquals("[3, 2, 1]", supervisorOverseeStudent().toString());
        // 2 different observers but not the same, should not qualify
        assertEquals(OK, supervisorStopsOverseeTest(1, 1, 2));
        assertEquals("[3, 1]", supervisorOverseeStudent().toString());
        // check what happens if there are more then 5 students
        for (int i = 5; i <= 10; i++) {
            assertEquals(OK, addStudent(createStudent(i)));
            assertEquals(OK, studentAttendTest(i, 1, 1));
            assertEquals(OK, studentAttendTest(i, 2, 1));
        }
        //assertEquals("[10, 9, 8, 7, 6]", supervisorOverseeStudent().toString());
        assertEquals("[10, 9, 8, 7, 6, 5, 3, 1]", supervisorOverseeStudent().toString());//todo: check if its ok more than 5
    }

    @Test
    public void testTestsThisSemester() {
        // given a semester, returns up to 5 of the tests id that take place this semester
        // descending ordered
        // empty list in case of error

        assertEquals("[2, 1]", testsThisSemester(1).toString());
        assertEquals("[1]", testsThisSemester(2).toString());
        assertEquals("[]", testsThisSemester(3).toString());

        // check what happens when there are many tests
        for (int i = 10; i <= 20; i++) {
            assertEquals(OK, addTest(createTest(i, 2)));
        }
        assertEquals("[20, 19, 18, 17, 16]", testsThisSemester(2).toString());
    }

    @Test
    public void testStudentHalfWayThere() {
        // if a student has >= half of the required credits
        // if there is an error return false
        // CS = 120, EE = 160, MATH = 115
        /**
        assertEquals(OK, addTest(createTest(55, 3, 1, 1, 1, 55)));
        assertEquals(OK, addTest(createTest(10, 3, 1, 1, 1, 10)));
        assertEquals(OK, addTest(createTest(75, 3, 1, 1, 1, 75)));
        assertEquals(OK, addTest(createTest(53, 3, 1, 1, 1, 53)));

        // student 1 from CS has 0 initial credit points
        assertFalse(studentHalfWayThere(1));
        // add 5 points to get 5 total, should not change anything
        assertEquals(OK, studentAttendTest(1, 1, 1)); // 0 + 1 = 1
        assertEquals(OK, studentAttendTest(1, 1, 2)); // 1 + 2 = 3
        assertEquals(OK, studentAttendTest(1, 2, 1)); // 3 + 2 = 5
        assertFalse(studentHalfWayThere(1));
        // get to exactly 60 points
        assertEquals(OK, studentAttendTest(1, 55, 3)); // 5 + 55 = 60
        assertTrue(studentHalfWayThere(1));
        // get to more then 60 points
        assertEquals(OK, studentAttendTest(1, 10, 3));
        assertTrue(studentHalfWayThere(1));

        // student 2 from EE has 2 initial credit points
        assertFalse(studentHalfWayThere(2));
        // get to 79 should return false
        assertEquals(OK, studentAttendTest(2, 2, 1)); // 2 + 2 = 4
        assertEquals(OK, studentAttendTest(2, 75, 3)); // 4 + 75 = 79
        assertFalse(studentHalfWayThere(2));
        // get to 81 should return true
        assertEquals(OK, studentAttendTest(2, 1, 2)); // 79 + 2 = 81
        assertTrue(studentHalfWayThere(2));
        // student 3 from MATH has 4 initial credit points
        assertFalse(studentHalfWayThere(3));
        // get to 57 should not be enough
        assertEquals(OK, studentAttendTest(3, 53, 3)); // 4 + 53 = 57 < 57.5
        assertFalse(studentHalfWayThere(3));

        // add a math student with initial points that are more then half
        assertEquals(OK, addStudent(createStudent(4, 68, "o", "MATH")));
        assertTrue(studentHalfWayThere(4));
        **/ //todo: check half way properly used in TEST
        assertFalse(studentHalfWayThere(1));
        assertFalse(studentHalfWayThere(2));
        assertEquals(OK,addStudent(createStudent(10, 60,"shai", "CS")));//CS is 120
        assertTrue(studentHalfWayThere(10));
        assertEquals(OK,addStudent(createStudent(11, 90,"shai2", "EE")));//EE is 160
        assertTrue(studentHalfWayThere(11));
        assertEquals(OK,addStudent(createStudent(12, 100,"shai3", "CS")));//MATH is 115
        assertTrue(studentHalfWayThere(12));
    }

    @Test
    public void testStudentCreditPoints() {
        // return the number of credit points that a student has(adding all the tests)
        // 0 in any other case

        assertEquals(OK, addTest(createTest(55, 3, 1, 1, 1, 55)));
        assertEquals(OK, addTest(createTest(10, 3, 1, 1, 1, 10)));
        assertEquals(OK, addTest(createTest(75, 3, 1, 1, 1, 75)));
        assertEquals(OK, addTest(createTest(53, 3, 1, 1, 1, 53)));

        // student 1 from CS has 0 initial credit points
        assertEquals(0, studentCreditPoints(1).intValue());
        // add 5 points to get 5 total, should not change anything
        assertEquals(OK, studentAttendTest(1, 1, 1)); // 0 + 1 = 1
        assertEquals(OK, studentAttendTest(1, 1, 2)); // 1 + 2 = 3
        assertEquals(OK, studentAttendTest(1, 2, 1)); // 3 + 2 = 5
        assertEquals(5, studentCreditPoints(1).intValue());
        // get to exactly 60 points
        assertEquals(OK, studentAttendTest(1, 55, 3)); // 5 + 55 = 60
        assertEquals(60, studentCreditPoints(1).intValue());
        // get to more then 60 points
        assertEquals(OK, studentAttendTest(1, 10, 3)); // 60 + 10 = 70
        assertEquals(70, studentCreditPoints(1).intValue());

        // student 2 from EE has 2 initial credit points
        assertEquals(2, studentCreditPoints(2).intValue());
        assertEquals(OK, studentAttendTest(2, 2, 1)); // 2 + 2 = 4
        assertEquals(OK, studentAttendTest(2, 75, 3)); // 4 + 75 = 79
        // get to 81
        assertEquals(OK, studentAttendTest(2, 1, 2)); // 79 + 2 = 81
        assertEquals(81, studentCreditPoints(2).intValue());
        // student 3 from MATH has 4 initial credit points
        assertEquals(4, studentCreditPoints(3).intValue());
        assertFalse(studentHalfWayThere(3));
        // get to 57 should not be enough
        assertEquals(OK, studentAttendTest(3, 53, 3)); // 4 + 53 = 57 < 57.5
        assertEquals(57, studentCreditPoints(3).intValue());

        // add a math student with initial points that are more then half
        assertEquals(OK, addStudent(createStudent(4, 68, "o", "MATH")));
        assertEquals(68, studentCreditPoints(4).intValue());

        // a student that does not exist
        assertEquals(0, studentCreditPoints(20).intValue());
    }

    @Test
    public void testGetMostPopularTest() {
        // tests id of the most popular in the given faculty, across all semesters
        // if there is an equality return the one with the largest ID
        // 0 if there is non or error

        // student 1,11 - CS, 2,22 - EE, 3 - MATH
        assertEquals(OK, addStudent(createStudent(11, 1, "CS", "CS")));
        assertEquals(OK, addStudent(createStudent(22, 1, "EE", "EE")));

        // delete all test and see if we get 0
        assertEquals(OK, deleteTest(1, 1));
        assertEquals(OK, deleteTest(1, 2));
        assertEquals(OK, deleteTest(2, 1));
        assertEquals(0, getMostPopularTest("CS").intValue());
        assertEquals(0, getMostPopularTest("EE").intValue());

        // add some tests, make sure it returns the one with the highest id
        assertEquals(OK, addTest(createTest(1, 1)));
        assertEquals(OK, addTest(createTest(1, 2)));
        assertEquals(OK, addTest(createTest(2, 1)));
        assertEquals(0, getMostPopularTest("CS").intValue());

        // make 1 more popular by putting a student there
        assertEquals(OK, studentAttendTest(1, 1, 1));
        //assertEquals(1, getMostPopularTest("CS").intValue());
        // make sure that is is not changed in EE
        assertEquals(0, getMostPopularTest("EE").intValue());

        // make 2 more popular in CS by adding 1 student to it
        assertEquals(OK, studentAttendTest(1, 2, 1));
        assertEquals(2, getMostPopularTest("CS").intValue());
        // make 1 more popular in EE by adding 2 student to it - make sure it does not change CS results
        assertEquals(OK, studentAttendTest(2, 1, 1));
        assertEquals(OK, studentAttendTest(22, 1, 1));
        assertEquals(OK, studentAttendTest(3, 1, 1));
        assertEquals(1, getMostPopularTest("EE").intValue());
        assertEquals(2, getMostPopularTest("CS").intValue());

        // make 1 more popular in CS by adding 1 student to it in other semester
        assertEquals(OK, studentAttendTest(11, 1, 2));
        assertEquals(1, getMostPopularTest("CS").intValue());
        // make 2 more popular by adding another student to it
        assertEquals(OK, studentAttendTest(11, 2, 1));
        assertEquals(2, getMostPopularTest("CS").intValue());
    }
    @Test
    public void testGetConflictingTests() {
        //Returns a list containing conflicting tests' IDs.
        //Tests are conflicting if and only if they happen on the same day, time and semester
        assertEquals(ReturnValue.OK, Solution.deleteTest(1,1));
        assertEquals(ReturnValue.OK, Solution.deleteTest(2,1));
        assertEquals(ReturnValue.OK, Solution.deleteTest(1,2));
        // add some tests, 10,20  conflict
        assertTrue(supervisorOverseeStudent().isEmpty());
        assertEquals(OK, addTest(createTestParam(20, 1,1,1)));
        assertEquals(OK, addTest(createTestParam(10, 1,1,1)));
        assertEquals(OK, addTest(createTestParam(10, 2,1,1)));
        assertEquals(OK, addTest(createTestParam(10, 3,1,1)));


        assertEquals("[10, 20]", getConflictingTests().toString());
        //20 conflicts
        assertEquals(OK, addTest(createTestParam(30, 1,1,1)));
        assertEquals(OK, addTest(createTestParam(20, 2,2,2)));
        assertEquals("[10, 20, 30]", getConflictingTests().toString());
        //20 not conflicts any more
        assertEquals(OK, deleteTest(20,1));
        assertEquals("[10, 30]", getConflictingTests().toString());
        //no conflicts
        assertEquals(OK, deleteTest(10,1));
        assertTrue(supervisorOverseeStudent().isEmpty());

        //remove for test and back
        addTest(createTest(1, 1, 1, 1, 1, 1));
        addTest(createTest(2, 1, 1, 1, 1, 2));
        addTest(createTest(1, 2, 1, 1, 1, 2));

    }
    @Test
    public void testGraduateStudents() {
        //Returns a list of up to 5 students' IDs that graduate after adding the current credit
        //points to the tests' credit points they are attending.

        assertEquals(OK, addTest(createTest(115, 3, 1, 1, 1, 120)));
        assertEquals(OK, addTest(createTest(25, 3, 1, 1, 1, 25)));
        assertEquals(OK, addTest(createTest(155, 3, 1, 1, 1, 155)));
        assertEquals(OK, addTest(createTest(53, 3, 1, 1, 1, 53)));

        // student 1 from CS has 0 initial credit points
        assertTrue(graduateStudents().isEmpty());
        // add 5 points to get 5 total, should not change anything
        assertEquals(OK, studentAttendTest(1, 1, 1)); // 0 + 1 = 1
        assertEquals(OK, studentAttendTest(1, 1, 2)); // 1 + 2 = 3
        assertEquals(OK, studentAttendTest(1, 2, 1)); // 3 + 2 = 5
        assertTrue(graduateStudents().isEmpty());
        // get to exactly 120 points
        assertEquals(OK, studentAttendTest(1, 115, 3)); // 5 + 115 = 120
        assertEquals("[1]", graduateStudents().toString());
        // get to more then 60 points
        assertEquals(OK, studentAttendTest(1, 25, 3));//120+25=145
        assertEquals("[1]", graduateStudents().toString());

        // student 2 from EE has 2 initial credit points

        // get to 159 should return false
        assertEquals(OK, studentAttendTest(2, 2, 1)); // 2 + 2 = 4
        assertEquals(OK, studentAttendTest(2, 155, 3)); // 4 + 155 = 159
        assertEquals("[1]", graduateStudents().toString());
        // get to 161 should return true
        assertEquals(OK, studentAttendTest(2, 1, 2)); // 159 + 2 = 161
        assertEquals("[1, 2]", graduateStudents().toString());
        // student 3 from MATH has 4 initial credit points

        // get to 57 should not be enough
        assertEquals(OK, studentAttendTest(3, 53, 3)); // 4 + 53 = 57 < 57.5
        assertEquals("[1, 2]", graduateStudents().toString());

        // add a math student with initial points that are graduated
        assertEquals(OK, addStudent(createStudent(4, 115, "o", "MATH")));
        assertEquals("[1, 2, 4]", graduateStudents().toString());


    }

}
