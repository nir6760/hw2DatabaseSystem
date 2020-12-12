package HW2;

import HW2.business.ReturnValue;
import HW2.business.Student;
import HW2.business.Supervisor;
import HW2.business.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HW2_TESTS_04 extends AbstractTest {

    private static final Test TEST1;
    private static final Test TEST2;
    private static final Test TEST3;
    private static final Supervisor SUPERVISOR1;
    private static final Supervisor SUPERVISOR2;
    private static final Student STUDENT1;
    private static final Student STUDENT2;
    private static final Student STUDENT3;
    private static final Student STUDENT4;

    static {
        TEST1 = new Test();
        TEST1.setId(1);
        TEST1.setSemester(1);
        TEST1.setTime(1);
        TEST1.setDay(1);
        TEST1.setRoom(233);
        TEST1.setCreditPoints(3);

        TEST2 = new Test();
        TEST2.setId(2);
        TEST2.setSemester(1);
        TEST2.setTime(1);
        TEST2.setDay(1);
        TEST2.setRoom(233);
        TEST2.setCreditPoints(3);

        TEST3 = new Test();
        TEST3.setId(3);
        TEST3.setSemester(2);
        TEST3.setTime(1);
        TEST3.setDay(1);
        TEST3.setRoom(234);
        TEST3.setCreditPoints(3);

        SUPERVISOR1 = new Supervisor();
        SUPERVISOR1.setId(1);
        SUPERVISOR1.setName("supervisor1");
        SUPERVISOR1.setSalary(100);

        SUPERVISOR2 = new Supervisor();
        SUPERVISOR2.setId(2);
        SUPERVISOR2.setName("supervisor2");
        SUPERVISOR2.setSalary(200);

        STUDENT1 = new Student();
        STUDENT1.setId(1);
        STUDENT1.setName("student1");
        STUDENT1.setFaculty("CS");
        STUDENT1.setCreditPoints(60);

        STUDENT2 = new Student();
        STUDENT2.setId(2);
        STUDENT2.setName("student2");
        STUDENT2.setFaculty("EE");
        STUDENT2.setCreditPoints(12);

        STUDENT3 = new Student();
        STUDENT3.setId(3);
        STUDENT3.setName("student3");
        STUDENT3.setFaculty("EE");
        STUDENT3.setCreditPoints(12);

        STUDENT4 = new Student();
        STUDENT4.setId(4);
        STUDENT4.setName("student4");
        STUDENT4.setFaculty("EE");
        STUDENT4.setCreditPoints(12);
    }

    @org.junit.Test
    public void studentAttendTest() {
        ReturnValue ret;
        Solution.addStudent(STUDENT1);
        Solution.addTest(TEST1);

        ret = Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.OK, ret);

        ret = Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.ALREADY_EXISTS, ret);

        ret = Solution.studentAttendTest(100, TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.studentAttendTest(STUDENT1.getId(), 100, TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), 3);
        assertEquals(ReturnValue.NOT_EXISTS, ret);
    }

    @org.junit.Test
    public void studentWaiveTest() {
        ReturnValue ret;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());

        ret = Solution.studentWaiveTest(100, TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.studentWaiveTest(STUDENT1.getId(), 100, TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.studentWaiveTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.studentWaiveTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.studentWaiveTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void supervisorOverseeTest() {
        ReturnValue ret;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addTest(TEST1);

        ret = Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.OK, ret);

        ret = Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.ALREADY_EXISTS, ret);

        ret = Solution.supervisorOverseeTest(100, TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.supervisorOverseeTest(SUPERVISOR1.getId(), 100, TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), 3);
        assertEquals(ReturnValue.NOT_EXISTS, ret);
    }

    @org.junit.Test
    public void supervisorStopsOverseeTest() {
        ReturnValue ret;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());

        ret = Solution.supervisorStopsOverseeTest(100, TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.supervisorStopsOverseeTest(SUPERVISOR1.getId(), 100, TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.supervisorStopsOverseeTest(SUPERVISOR2.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.supervisorStopsOverseeTest(SUPERVISOR1.getId(), TEST2.getId(), TEST2.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, ret);

        ret = Solution.supervisorStopsOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void averageTestCost() {
        Float res;
        float expectedAvg;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST1.getId(), TEST1.getSemester());

        res = Solution.averageTestCost();
        expectedAvg = 75;
        assertEquals(expectedAvg, res, 0.001);

        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST2.getId(), TEST2.getSemester());

        res = Solution.averageTestCost();
        expectedAvg =  (((float)(SUPERVISOR1.getSalary() + SUPERVISOR2.getSalary()) / 2) + SUPERVISOR1.getSalary()) / 2;
        assertEquals(expectedAvg, res, 0.001);
    }

    @org.junit.Test
    public void getWage() {
        Integer res;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST2.getId(), TEST2.getSemester());

        res = Solution.getWage(SUPERVISOR1.getId());
        assertEquals(200, res.intValue());

        res = Solution.getWage(SUPERVISOR2.getId());
        assertEquals(0, res.intValue());

        res = Solution.getWage(100);
        assertEquals(-1, res.intValue());
    }

    @org.junit.Test
    public void supervisorOverseeStudentReturnsOneStudent() {
        ArrayList<Integer> res;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());

        res = Solution.supervisorOverseeStudent();
        assertEquals(1, res.size());
        assertEquals(1, res.get(0).intValue());
    }

    @org.junit.Test
    public void supervisorOverseeStudentReturnsZeroStudents() {
        ArrayList<Integer> res;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());

        res = Solution.supervisorOverseeStudent();
        assertEquals(0, res.size());
    }

    @org.junit.Test
    public void supervisorOverseeStudentReturnsTwoStudentsA() {
        ArrayList<Integer> res;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST2.getId(), TEST2.getSemester());

        res = Solution.supervisorOverseeStudent();
        assertEquals(2, res.size());
        assertEquals(2, res.get(0).intValue());
        assertEquals(1, res.get(1).intValue());
    }

    @org.junit.Test
    public void supervisorOverseeStudentReturnsTwoStudentsB() {
        ArrayList<Integer> res;
        Solution.addSupervisor(SUPERVISOR1);
        Solution.addSupervisor(SUPERVISOR2);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.supervisorOverseeTest(SUPERVISOR2.getId(), TEST2.getId(), TEST2.getSemester());

        res = Solution.supervisorOverseeStudent();
        assertEquals(2, res.size());
        assertEquals(2, res.get(0).intValue());
        assertEquals(1, res.get(1).intValue());
    }

    @org.junit.Test
    public void testsThisSemester() {
        ArrayList<Integer> res;
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addTest(TEST3);

        res = Solution.testsThisSemester(1);
        assertEquals(2, res.size());
        assertEquals(2, res.get(0).intValue());
        assertEquals(1, res.get(1).intValue());

        res = Solution.testsThisSemester(2);
        assertEquals(1, res.size());
        assertEquals(3, res.get(0).intValue());

        res = Solution.testsThisSemester(3);
        assertEquals(0, res.size());

        for (int i = 1; i < 10; i++) {
            Test test = new Test();
            test.setId(i);
            test.setSemester(3);
            test.setDay(1);
            test.setCreditPoints(3);
            test.setRoom(1);
            test.setTime(1);
            Solution.addTest(test);
        }

        res = Solution.testsThisSemester(3);
        assertEquals(5, res.size());
        assertEquals(9, res.get(0).intValue());
        assertEquals(8, res.get(1).intValue());
        assertEquals(7, res.get(2).intValue());
        assertEquals(6, res.get(3).intValue());
        assertEquals(5, res.get(4).intValue());
    }

    @org.junit.Test
    public void studentHalfWayThere() {
        Boolean res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);

        res = Solution.studentHalfWayThere(STUDENT1.getId());
        assertTrue(res);

        res = Solution.studentHalfWayThere(STUDENT2.getId());
        assertFalse(res);

        res = Solution.studentHalfWayThere(100);
        assertFalse(res);
    }

    @org.junit.Test
    public void studentCreditPoints() {
        Integer res;
        Integer expectedCreditsPoints;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);

        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());

        res = Solution.studentCreditPoints(STUDENT1.getId());
        expectedCreditsPoints = STUDENT1.getCreditPoints() + TEST1.getCreditPoints() + TEST2.getCreditPoints();
        assertEquals(expectedCreditsPoints, res);

        res = Solution.studentCreditPoints(STUDENT2.getId());
        expectedCreditsPoints = STUDENT2.getCreditPoints() + TEST1.getCreditPoints();
        assertEquals(expectedCreditsPoints, res);

        res = Solution.studentCreditPoints(STUDENT3.getId());
        expectedCreditsPoints = STUDENT3.getCreditPoints();
        assertEquals(expectedCreditsPoints, res);

        res = Solution.studentCreditPoints(100);
        expectedCreditsPoints = 0;
        assertEquals(expectedCreditsPoints, res);

    }

    @org.junit.Test
    public void getMostPopularTest() {
        Integer res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addTest(TEST3);

        // CS
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST3.getId(), TEST3.getSemester());
        // EE
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), TEST3.getId(), TEST3.getSemester());

        res = Solution.getMostPopularTest("CS");
        assertEquals(TEST3.getId(), res.intValue());

        res = Solution.getMostPopularTest("EE");
        assertEquals(TEST1.getId(), res.intValue());

        res = Solution.getMostPopularTest("MATH");
        assertEquals(0, res.intValue());
    }

    @org.junit.Test
    public void getConflictingTests() {
        ArrayList<Integer> res;
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addTest(TEST3);

        res = Solution.getConflictingTests();
        assertEquals(2, res.size());
        assertEquals(TEST1.getId(), res.get(0).intValue());
        assertEquals(TEST2.getId(), res.get(1).intValue());
        
        Solution.deleteTest(TEST1.getId(), TEST1.getSemester());
        Solution.deleteTest(TEST2.getId(), TEST2.getSemester());
        Solution.deleteTest(TEST3.getId(), TEST3.getSemester());

        res = Solution.getConflictingTests();
        assertEquals(0, res.size());

        for (int i = 10; i > 0; i--) {
            Test test = new Test();
            test.setId(i);
            test.setTime(1);
            test.setDay(1);
            test.setRoom(1);
            test.setCreditPoints(1);
            test.setSemester(1);
            // just for diversity
            if (i > 7) {
                test.setSemester(2);
            }
            Solution.addTest(test);
        }
        res = Solution.getConflictingTests();
        assertEquals(10, res.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i + 1, res.get(i).intValue());
        }

    }

    @org.junit.Test
    public void graduateStudentsA() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Test test = new Test();
        test.setId(10);
        test.setSemester(1);
        test.setCreditPoints(20);
        test.setTime(1);
        test.setRoom(1);
        test.setDay(1);
        Solution.addTest(test);
        Solution.studentAttendTest(STUDENT1.getId(), test.getId(), test.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), test.getId(), test.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), test.getId(), test.getSemester());
        // add students that will graduate
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName("name");
            student.setFaculty("CS");
            student.setCreditPoints(110);
            student.setId(10 + i);
            Solution.addStudent(student);
            Solution.studentAttendTest(student.getId(), test.getId(), test.getSemester());
        }

        res = Solution.graduateStudents();
        assertEquals(5, res.size());
        assertEquals(10, res.get(0).intValue());
        assertEquals(11, res.get(1).intValue());
        assertEquals(12, res.get(2).intValue());
        assertEquals(13, res.get(3).intValue());
        assertEquals(14, res.get(4).intValue());

    }

    @org.junit.Test
    public void graduateStudentsB() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Test test = new Test();
        test.setId(10);
        test.setSemester(1);
        test.setCreditPoints(20);
        test.setTime(1);
        test.setRoom(1);
        test.setDay(1);
        Solution.addTest(test);
        Solution.studentAttendTest(STUDENT1.getId(), test.getId(), test.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), test.getId(), test.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), test.getId(), test.getSemester());
        // add students that will graduate
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setName("name");
            student.setFaculty("EE");
            student.setCreditPoints(150);
            student.setId(10 + i);
            Solution.addStudent(student);
            Solution.studentAttendTest(student.getId(), test.getId(), test.getSemester());
        }

        res = Solution.graduateStudents();
        assertEquals(3, res.size());
        assertEquals(10, res.get(0).intValue());
        assertEquals(11, res.get(1).intValue());
        assertEquals(12, res.get(2).intValue());
    }

    @org.junit.Test
    public void graduateStudentsC() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Test test = new Test();
        test.setId(10);
        test.setSemester(1);
        test.setCreditPoints(20);
        test.setTime(1);
        test.setRoom(1);
        test.setDay(1);
        Solution.addTest(test);
        Solution.studentAttendTest(STUDENT1.getId(), test.getId(), test.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), test.getId(), test.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), test.getId(), test.getSemester());

        res = Solution.graduateStudents();
        assertEquals(0, res.size());
    }

    @org.junit.Test
    public void graduateStudentsD() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Test test1 = new Test();
        test1.setId(10);
        test1.setSemester(1);
        test1.setCreditPoints(5);
        test1.setTime(1);
        test1.setRoom(1);
        test1.setDay(1);
        Solution.addTest(test1);
        Test test2 = new Test();
        test2.setId(11);
        test2.setSemester(1);
        test2.setCreditPoints(5);
        test2.setTime(1);
        test2.setRoom(1);
        test2.setDay(1);
        Solution.addTest(test2);
        Solution.studentAttendTest(STUDENT1.getId(), test1.getId(), test1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), test1.getId(), test1.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), test1.getId(), test1.getSemester());
        // add students that will graduate
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName("name");
            student.setFaculty("CS");
            student.setCreditPoints(110);
            student.setId(10 + i);
            Solution.addStudent(student);
            Solution.studentAttendTest(student.getId(), test1.getId(), test1.getSemester());
            Solution.studentAttendTest(student.getId(), test2.getId(), test2.getSemester());
        }
        Student student = new Student();
        student.setName("name");
        student.setFaculty("MATH");
        student.setCreditPoints(1000);
        student.setId(9);
        Solution.addStudent(student);

        res = Solution.graduateStudents();
        assertEquals(5, res.size());
        assertEquals(9, res.get(0).intValue());
        assertEquals(10, res.get(1).intValue());
        assertEquals(11, res.get(2).intValue());
        assertEquals(12, res.get(3).intValue());
        assertEquals(13, res.get(4).intValue());
    }

    @org.junit.Test
    public void getCloseStudentsA() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Solution.addStudent(STUDENT4);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addTest(TEST3);
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST3.getId(), TEST3.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), TEST3.getId(), TEST3.getSemester());

        res = Solution.getCloseStudents(STUDENT1.getId());
        assertEquals(1, res.size());
        assertEquals(STUDENT2.getId(), res.get(0).intValue());
    }

    @org.junit.Test
    public void getCloseStudentsB() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Solution.addStudent(STUDENT4);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addTest(TEST3);
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), TEST3.getId(), TEST3.getSemester());

        res = Solution.getCloseStudents(STUDENT1.getId());
        assertEquals(3, res.size());
        assertEquals(STUDENT4.getId(), res.get(0).intValue());
        assertEquals(STUDENT3.getId(), res.get(1).intValue());
        assertEquals(STUDENT2.getId(), res.get(2).intValue());
    }

    @org.junit.Test
    public void getCloseStudentsC() {
        ArrayList<Integer> res;
        Solution.addTest(TEST1);
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.setId(i + 1);
            student.setCreditPoints(3);
            student.setFaculty("CS");
            student.setName("name");
            Solution.addStudent(student);
            Solution.studentAttendTest(student.getId(), TEST1.getId(), TEST1.getSemester());
        }
        res = Solution.getCloseStudents(STUDENT1.getId());
        assertEquals(10, res.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(20 - i, res.get(i).intValue());
        }
    }

    @org.junit.Test
    public void getCloseStudentsD() {
        ArrayList<Integer> res;
        Solution.addStudent(STUDENT1);
        Solution.addStudent(STUDENT2);
        Solution.addStudent(STUDENT3);
        Solution.addStudent(STUDENT4);
        Solution.addTest(TEST1);
        Solution.addTest(TEST2);
        Solution.addTest(TEST3);
        Solution.studentAttendTest(STUDENT1.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT1.getId(), TEST3.getId(), TEST3.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST1.getId(), TEST1.getSemester());
        Solution.studentAttendTest(STUDENT2.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), TEST2.getId(), TEST2.getSemester());
        Solution.studentAttendTest(STUDENT3.getId(), TEST3.getId(), TEST3.getSemester());

        // student 4 doesn't attend any test and should not be in the results
        res = Solution.getCloseStudents(STUDENT1.getId());
        assertEquals(2, res.size());
        assertEquals(3, res.get(0).intValue());
        assertEquals(2, res.get(1).intValue());

        Solution.studentAttendTest(STUDENT4.getId(), TEST1.getId(), TEST1.getSemester());
        // student 4 still should not be in the results
        res = Solution.getCloseStudents(STUDENT1.getId());
        assertEquals(2, res.size());
        assertEquals(3, res.get(0).intValue());
        assertEquals(2, res.get(1).intValue());
    }

}
