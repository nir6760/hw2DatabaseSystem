package HW2;

import HW2.business.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static HW2.TestUtils.*;
import static HW2.Solution.*;
import static HW2.business.ReturnValue.*;
import static org.junit.Assert.*;

// TODO: check for connection error returns a ERROR value.

public class CRUDTests extends AbstractTest {

    @org.junit.Test
    public void testAddTest() {
        // should return OK on good insert
        Test t1 = createTest(1, 1, 1, 1 ,1 ,1);
        Test t2 = createTest(2, 2, 2, 2, 2, 2);
        Test t7 = createTest(2, 1, 2, 2, 2, 2);
        ReturnValue rv = addTest(t1);
        assertEquals(rv, ReturnValue.OK);
        rv = addTest(t2);
        assertEquals(rv, ReturnValue.OK);
        rv = addTest(t7);
        assertEquals(rv, ReturnValue.OK);

        // should return BAD_PARAMS for bad test params
        Test t3 = createTest(5, 4, 1, 1, 1, 1);
        rv = addTest(t3);
        assertEquals(rv, ReturnValue.BAD_PARAMS);
        Test t4 = createTest(1, 1, 5, 1, 1, 1);
        rv = addTest(t4);
        assertEquals(rv, ReturnValue.BAD_PARAMS);

        // should return ALREADY_EXISTS
        Test t5 = createTest(1, 1, 1, 34, 2, 1);
        rv = addTest(t5);
        assertEquals(rv, ReturnValue.ALREADY_EXISTS);
        Test t6 = createTest(2, 1, 1, 34, 2, 1);
        rv = addTest(t5);
        assertEquals(rv, ReturnValue.ALREADY_EXISTS);

        // should return OK
        Test t8 = createTest(11, 3, 3, 666, 30, 11);
        assertEquals(addTest(t8), ReturnValue.OK);
    }

    @org.junit.Test
    public void testGetTestProfile() {
        Test t = createTest(1, 1 ,1 ,1 ,1, 1);
        Test bt = new Test();
        // should return a correct test object
        ReturnValue rv = addTest(t);
        assertEquals(rv, ReturnValue.OK);
        Test t1 = getTestProfile(t.getId(), t.getSemester());
        assertEquals(t1, t);
        // should return bad test object
        t1 = getTestProfile(t.getId() + 1, t.getSemester());
        assertEquals(t1, bt);
        // check with several items in the DB
        Test t2 = createTest(2, 2, 2, 2, 2 ,2);
        Test t3 = createTest(3, 3, 3 ,3 ,3 ,3);
        assertEquals(addTest(t2), ReturnValue.OK);
        assertEquals(addTest(t3), ReturnValue.OK);
        t1 = getTestProfile(t2.getId(), t2.getSemester());
        assertEquals(t1, t2);
        t1 = getTestProfile(t.getId() + 1, t.getSemester());
        assertEquals(t1, bt);
    }

    @org.junit.Test
    public void testDeleteTest() {
        // should return OK
        addTest(createTest(1, 1, 1, 1, 1, 1));
        ReturnValue rv = deleteTest(1, 1);
        assertEquals(rv, OK);
        // should return NOT_EXISTS
        rv = deleteTest(1, 1);
        assertEquals(rv, NOT_EXISTS);
        rv = addTest(createTest(1, 1, 1, 1, 1, 1));
        assertEquals(rv, OK);
        rv = deleteTest(1, 2);
        assertEquals(rv, NOT_EXISTS);
        rv = deleteTest(2, 1);
        assertEquals(rv, NOT_EXISTS);
        // check that it deletes it from the DB
        rv = addTest(createTest(1, 1, 1 ,1 ,1, 1));
        assertEquals(rv, ALREADY_EXISTS);
    }

    @org.junit.Test
    public void testAddStudent() {
        // should return OK
        ReturnValue rv = addStudent(createStudent(1, 1,"shai", "CS"));
        assertEquals(rv, OK);
        rv = addStudent(createStudent(2, 0, "shai", "CS"));
        assertEquals(rv, OK);
        // should return BAD_PARAMS - not good
        rv = addStudent(createStudent(10, 11, "bad", "TONTO"));
       // assertEquals(rv, BAD_PARAMS); //should not be tested by piazza question @75
        assertEquals(addStudent(createStudent(10, -1, "bonzo", "EE")), BAD_PARAMS);
        assertEquals(addStudent(createStudent(1, -1, "bonzo", "EE")), BAD_PARAMS);
        assertEquals(addStudent(createStudent(20, 1, null, "EE")), BAD_PARAMS);
        // should return ALREADY_EXISTS
        assertEquals(addStudent(createStudent(1, 1, "mo", "EE")), ALREADY_EXISTS);
        assertEquals(addStudent(createStudent(2, 1, "mo", "EE")), ALREADY_EXISTS);
    }

    @org.junit.Test
    public void testGetStudentProfile() {
        Student s1 = createStudent(1, 1, "s1", "CS");
        Student s2 = createStudent(2, 1, "s2", "EE");
        Student s3 = createStudent(3, 1, "s2", "MATH");
        Student bs = new Student();
        assertEquals(addStudent(s1), OK);
        assertEquals(addStudent(s2), OK);
        assertEquals(addStudent(s3), OK);
        assertEquals(addStudent(bs), BAD_PARAMS);
        // check that it returns the correct student object
        assertEquals(getStudentProfile(s1.getId()), s1);
        assertEquals(getStudentProfile(s2.getId()), s2);
        assertEquals(getStudentProfile(s3.getId()), s3);
        // check that it returns a bad student value of a student that does not exists
        assertEquals(getStudentProfile(-1), bs);
        assertEquals(getStudentProfile(5), bs);
        // check with several students in the DB
        Student s4 = createStudent(5, 1, "EE", "EE");
        assertEquals(addStudent(s4), OK);
        assertEquals(getStudentProfile(5), s4);
    }

    @org.junit.Test
    public void testDeleteStudent() {
        Student s1 = createStudent(1, 1, "s1", "CS");
        Student s2 = createStudent(2, 1, "s2", "EE");
        Student s3 = createStudent(3, 1, "s2", "MATH");
        assertEquals(addStudent(s1), OK);
        assertEquals(addStudent(s2), OK);
        assertEquals(addStudent(s3), OK);
        // should return OK
        assertEquals(deleteStudent(s1.getId()), OK);
        assertEquals(deleteStudent(s2.getId()), OK);
        // should return NOT_EXISTS
        assertEquals(deleteStudent(s1.getId()), NOT_EXISTS);
        assertEquals(deleteStudent(s2.getId()), NOT_EXISTS);
        assertEquals(deleteStudent(-1), NOT_EXISTS);
        // check that it deletes it from the DB
        assertEquals(addStudent(s1), OK);
        assertEquals(getStudentProfile(s3.getId()), s3);
    }

    @org.junit.Test
    public void testAddSupervisor() {
        // should return OK
        assertEquals(addSupervisor(createSupervisor(1, "a", 4)), OK);
        assertEquals(addSupervisor(createSupervisor(2, "a", 4)), OK);
        assertEquals(addSupervisor(createSupervisor(3, "b", 4)), OK);
        // should return BAD_PARAMS - not good
        assertEquals(addSupervisor(createSupervisor(-1, "b", 4)), BAD_PARAMS);
        assertEquals(addSupervisor(createSupervisor(5, null, 4)), BAD_PARAMS);
        assertEquals(addSupervisor(createSupervisor(5, "c", -1)), BAD_PARAMS);
        assertEquals(addSupervisor(createSupervisor(1, "c", -1)), BAD_PARAMS);
        // should return ALREADY_EXISTS
        assertEquals(addSupervisor(createSupervisor(1, "t", 22)), ALREADY_EXISTS);

    }

    @org.junit.Test
    public void testGetSupervisorProfile() {
        Supervisor s1 = createSupervisor(1, "1", 10);
        Supervisor s2 = createSupervisor(2, "2", 20);
        Supervisor s3 = createSupervisor(10, "3", 10);
        addSupervisor(s1);
        addSupervisor(s2);
        addSupervisor(s3);
        Supervisor bs = new Supervisor();
        // check that it returns the correct supervisor object
        assertEquals(getSupervisorProfile(s1.getId()), s1);
        assertEquals(getSupervisorProfile(s2.getId()), s2);
        assertEquals(getSupervisorProfile(s3.getId()), s3);
        // check that it returns a bad supervisor value of a supervisor that does not exists
        assertEquals(getSupervisorProfile(5), bs);
        assertEquals(getSupervisorProfile(-1), bs);
    }

    @org.junit.Test
    public void testDeleteSupervisor() {
        Supervisor s1 = createSupervisor(1, "1", 10);
        Supervisor s2 = createSupervisor(2, "2", 20);
        Supervisor s3 = createSupervisor(10, "3", 10);
        addSupervisor(s1);
        addSupervisor(s2);
        addSupervisor(s3);
        Supervisor bs = new Supervisor();
        // should return OK
        assertEquals(deleteSupervisor(s1.getId()), OK);
        assertEquals(deleteSupervisor(s2.getId()), OK);
        // should return NOT_EXIST
        assertEquals(deleteSupervisor(s1.getId()), NOT_EXISTS);
        assertEquals(deleteSupervisor(5), NOT_EXISTS);
        assertEquals(deleteSupervisor(-1), NOT_EXISTS);
        // check that it changes the DB
        assertEquals(getSupervisorProfile(s1.getId()), bs);
        assertEquals(getSupervisorProfile(s3.getId()), s3);
    }
}
