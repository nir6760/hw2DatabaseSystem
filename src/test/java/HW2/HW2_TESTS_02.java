package HW2;

import HW2.AbstractTest;
import HW2.business.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static HW2.Solution.*;
import static HW2.business.ReturnValue.*;
import static org.junit.Assert.*;

// TODO: check for connection error returns a ERROR value.

/**
 * initializes the students/tests/observers tables @Before, and tests the basic API section
 */
public class HW2_TESTS_02 extends AbstractTest {

    /**
     * initializes the tables before every run
     */
    @Before
    public void initTables() {
        super.clearTables();

        addTest(TestUtils.createTest(1, 1, 1, 1, 1, 1));
        addTest(TestUtils.createTest(2, 1, 1, 1, 1, 2));
        addTest(TestUtils.createTest(1, 2, 1, 1, 1, 2));

        addStudent(TestUtils.createStudent(1, 0, "a", "CS"));
        addStudent(TestUtils.createStudent(2, 2, "b", "EE"));
        addStudent(TestUtils.createStudent(3, 4, "c", "MATH"));

        addSupervisor(TestUtils.createSupervisor(1, "a", 1));
        addSupervisor(TestUtils.createSupervisor(2, "b", 10));
        addSupervisor(TestUtils.createSupervisor(3, "c", 100));
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
        assertEquals(supervisorOverseeTest(4, 1, 1), NOT_EXISTS);
        assertEquals(supervisorOverseeTest(1, 2, 2), NOT_EXISTS);
        assertEquals(supervisorOverseeTest(2, 3, 1), NOT_EXISTS);
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
        Assert.assertEquals(averageTestCost(), 0, TestUtils.eps);
        supervisorOverseeTest(1, 1, 1); // 1
        supervisorOverseeTest(2, 1, 1); // 10
        supervisorOverseeTest(2, 2, 1); // 10
        supervisorOverseeTest(3, 1, 2); // 100
        Assert.assertEquals(averageTestCost(), 115.5 / 3.0, TestUtils.eps);
        addTest(TestUtils.createTest(1, 3));
        Assert.assertEquals(averageTestCost(), 115.5 / 4.0, TestUtils.eps);
        supervisorStopsOverseeTest(3, 1, 2);
        Assert.assertEquals(averageTestCost(), 15.5 / 4.0, TestUtils.eps);
        // return 0 in case of 0 div
        // delete all the tests and run again
        assertEquals(supervisorStopsOverseeTest(1, 1, 1), OK);
        assertEquals(supervisorStopsOverseeTest(2, 1, 1), OK);
        assertEquals(supervisorStopsOverseeTest(2, 2, 1), OK);
        assertEquals(deleteTest(1, 1), OK);
        assertEquals(deleteTest(1, 2), OK);
        assertEquals(deleteTest(1, 3), OK);
        assertEquals(deleteTest(2, 1), OK);
        Assert.assertEquals(averageTestCost(), 0, TestUtils.eps);
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
            assertEquals(OK, addStudent(TestUtils.createStudent(i)));
            assertEquals(OK, studentAttendTest(i, 1, 1));
            assertEquals(OK, studentAttendTest(i, 2, 1));
        }
        assertEquals("[10, 9, 8, 7, 6, 5, 3, 1]", supervisorOverseeStudent().toString());
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
            assertEquals(OK, addTest(TestUtils.createTest(i, 2)));
        }
        assertEquals("[20, 19, 18, 17, 16]", testsThisSemester(2).toString());
    }

    @Test
    public void testStudentHalfWayThere() {
        // if a student has >= half of the required credits
        // if there is an error return false
        // CS = 120, EE = 160, MATH = 115
        assertEquals(OK, addTest(TestUtils.createTest(55, 3, 1, 1, 1, 55)));
        assertEquals(OK, addTest(TestUtils.createTest(10, 3, 1, 1, 1, 10)));
        assertEquals(OK, addTest(TestUtils.createTest(75, 3, 1, 1, 1, 75)));
        assertEquals(OK, addTest(TestUtils.createTest(53, 3, 1, 1, 1, 53)));

        // student 1 from CS has 0 initial credit points
        assertFalse(studentHalfWayThere(1));
        // add 5 points to get 5 total, should not change anything
        assertEquals(OK, studentAttendTest(1, 1, 1)); // 0 + 1 = 1
        assertEquals(OK, studentAttendTest(1, 1, 2)); // 1 + 2 = 3
        assertEquals(OK, studentAttendTest(1, 2, 1)); // 3 + 2 = 5
        assertFalse(studentHalfWayThere(1));
        // get to exactly 60 points
        assertEquals(OK, studentAttendTest(1, 55, 3)); // 5 + 55 = 60
        assertFalse(studentHalfWayThere(1));
        // get to more then 60 points
        assertEquals(OK, studentAttendTest(1, 10, 3));
        assertFalse(studentHalfWayThere(1));

        // student 2 from EE has 2 initial credit points
        assertFalse(studentHalfWayThere(2));
        // get to 79 should return false
        assertEquals(OK, studentAttendTest(2, 2, 1)); // 2 + 2 = 4
        assertEquals(OK, studentAttendTest(2, 75, 3)); // 4 + 75 = 79
        assertFalse(studentHalfWayThere(2));
        // get to 81 should return true
        assertEquals(OK, studentAttendTest(2, 1, 2)); // 79 + 2 = 81
        assertFalse(studentHalfWayThere(2));
        // student 3 from MATH has 4 initial credit points
        assertFalse(studentHalfWayThere(3));
        // get to 57 should not be enough
        assertEquals(OK, studentAttendTest(3, 53, 3)); // 4 + 53 = 57 < 57.5
        assertFalse(studentHalfWayThere(3));

        // add a math student with initial points that are more then half
        assertEquals(OK, addStudent(TestUtils.createStudent(4, 68, "o", "MATH")));
        assertTrue(studentHalfWayThere(4));
    }

    @Test
    public void testStudentCreditPoints() {
        // return the number of credit points that a student has(adding all the tests)
        // 0 in any other case

        assertEquals(OK, addTest(TestUtils.createTest(55, 3, 1, 1, 1, 55)));
        assertEquals(OK, addTest(TestUtils.createTest(10, 3, 1, 1, 1, 10)));
        assertEquals(OK, addTest(TestUtils.createTest(75, 3, 1, 1, 1, 75)));
        assertEquals(OK, addTest(TestUtils.createTest(53, 3, 1, 1, 1, 53)));

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
        assertEquals(OK, addStudent(TestUtils.createStudent(4, 68, "o", "MATH")));
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
        assertEquals(OK, addStudent(TestUtils.createStudent(11, 1, "CS", "CS")));
        assertEquals(OK, addStudent(TestUtils.createStudent(22, 1, "EE", "EE")));

        // delete all test and see if we get 0
        assertEquals(OK, deleteTest(1, 1));
        assertEquals(OK, deleteTest(1, 2));
        assertEquals(OK, deleteTest(2, 1));
        assertEquals(0, getMostPopularTest("CS").intValue());
        assertEquals(0, getMostPopularTest("EE").intValue());

        // add some tests, make sure it returns the one with the highest id
        assertEquals(OK, addTest(TestUtils.createTest(1, 1)));
        assertEquals(OK, addTest(TestUtils.createTest(1, 2)));
        assertEquals(OK, addTest(TestUtils.createTest(2, 1)));
        assertEquals(0, getMostPopularTest("CS").intValue());

        // make 1 more popular by putting a student there
        assertEquals(OK, studentAttendTest(1, 1, 1));
        assertEquals(1, getMostPopularTest("CS").intValue());
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
}
