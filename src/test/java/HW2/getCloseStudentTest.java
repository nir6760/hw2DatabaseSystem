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
public class getCloseStudentTest extends AbstractTest{
    /**
     * initializes the tables before every run
     */
    @Before
    public void initTables() {
        super.clearTables();

        addTest(createTest(1, 1, 1, 1, 1, 10));
        addTest(createTest(2, 1, 1, 1, 1, 20));
        addTest(createTest(1, 2, 1, 1, 1, 30));

        addStudent(createStudent(1, 0, "a", "CS"));
        addStudent(createStudent(2, 2, "b", "EE"));
        addStudent(createStudent(3, 4, "c", "MATH"));
        addStudent(createStudent(4, 4, "d", "CS"));
        addStudent(createStudent(5, 4, "e", "CS"));


    }
    @Test
    public void testGetCloseStudents() {
        //Returns a list of the 10 "close students" to the student with id studentID.
        //Close students are defined as students who attend at least (>=) 50% of the tests the
        //student with studentID does. Note that one cannot be a close student of himself.


        // no 99 student
        assertTrue(getCloseStudents(99).isEmpty());//no valid student
        assertEquals(OK, studentAttendTest(3, 1, 1));
        assertEquals("[5, 4, 3, 2]", getCloseStudents(1).toString());//empty way (all other students)

        assertEquals(OK, studentAttendTest(1, 1, 1)); // 1 and 3 having same test
        assertEquals("[3]", getCloseStudents(1).toString());




        assertEquals(OK, studentAttendTest(2, 1, 1)); // 1, 2, 3 having same test
        assertEquals("[3, 2]", getCloseStudents(1).toString());
        assertEquals("[3, 1]", getCloseStudents(2).toString());
        assertEquals("[2, 1]", getCloseStudents(3).toString());

        assertEquals(OK, studentAttendTest(1, 2, 1)); // still close to 1
        assertEquals("[3, 2]", getCloseStudents(1).toString());
        assertEquals("[3, 1]", getCloseStudents(2).toString());
        assertEquals("[2, 1]", getCloseStudents(3).toString());

        assertEquals(OK, studentAttendTest(1, 1, 2)); //no one close to 1
        assertTrue(getCloseStudents(1).isEmpty()); //no close
        assertEquals("[3, 1]", getCloseStudents(2).toString());
        assertEquals("[2, 1]", getCloseStudents(3).toString());

        assertEquals(OK, studentAttendTest(2, 1, 2)); // only 2 close to 1
        assertEquals("[2]", getCloseStudents(1).toString());

        assertEquals(OK, studentAttendTest(3, 2, 1)); // 3 is close to 1 again
        assertEquals("[3, 2]", getCloseStudents(1).toString());
        assertEquals(OK, studentAttendTest(3, 1, 2)); // 3 is as to 1 again
        assertEquals("[3, 2]", getCloseStudents(1).toString());
        assertEquals("[2, 1]", getCloseStudents(3).toString());

        assertEquals(OK, studentWaiveTest(2, 1, 2)); //only 3 close to 1
        assertEquals("[3]", getCloseStudents(1).toString());
        assertEquals("[1]", getCloseStudents(3).toString());

        assertTrue(getCloseStudents(101).isEmpty());//no valid student

    }

}
