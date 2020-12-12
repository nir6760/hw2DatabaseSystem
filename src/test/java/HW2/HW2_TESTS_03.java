package HW2;

import java.lang.Float;

import HW2.business.ReturnValue;
import HW2.business.Student;
import HW2.business.Supervisor;
import HW2.business.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class HW2_TESTS_03 extends AbstractTest {


    static Student createStudent(int id, String name, String faculty, int points) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setFaculty(faculty);
        student.setCreditPoints(points);

        return student;
    }

    static Supervisor createSup(int id, String name, int salary) {
        Supervisor sup = new Supervisor();
        sup.setId(id);
        sup.setName(name);
        sup.setSalary(salary);
        return sup;
    }

    static Test createTest(int id, int semester, int room, int day, int time, int creditPoints) {
        Test test = new Test();
        test.setId(id);
        test.setSemester(semester);
        test.setRoom(room);
        test.setDay(day);
        test.setTime(time);
        test.setCreditPoints(creditPoints);
        return test;
    }


    // ======================== Tests ========================
    @org.junit.Test
    public void addTest_GoodPath() {
        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
    }

    @org.junit.Test
    public void addTest_BadPath1() {
        Test t1 = HW2_TESTS_03.createTest(-1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.BAD_PARAMS, Solution.addTest(t1));
    }

    @org.junit.Test
    public void addTest_BadPath2() {
        Test test = new Test();
        test.setId(1);
        assertEquals(ReturnValue.BAD_PARAMS, Solution.addTest(test));
    }

    @org.junit.Test
    public void addTest_Integration_AlreadyExist() {

        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        Test t2 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        assertEquals(ReturnValue.ALREADY_EXISTS, Solution.addTest(t2));
    }

    @org.junit.Test
    public void deleteTest_BadPath() {
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteTest(1, 2));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteTest(1, 1));
    }

    @org.junit.Test
    public void deleteTest_GoodPath() {
        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        assertEquals(ReturnValue.OK, Solution.deleteTest(1, 1));
    }

    @org.junit.Test
    public void deleteTestIntegration_NOT_EXISTS() {
        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        assertEquals(ReturnValue.OK, Solution.deleteTest(1, 1));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteTest(1, 1));
    }

    @org.junit.Test
    public void deleteSupervisor_BadPath() {
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteSupervisor(12));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteSupervisor(-1));
    }

    @org.junit.Test
    public void deleteSupervisor_GoodPath() {
        Supervisor sup1 = HW2_TESTS_03.createSup(1, "doda", 45);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));
        assertEquals(ReturnValue.OK, Solution.deleteSupervisor(1));
    }

    @org.junit.Test
    public void deleteSupervisor_BadPath2() {
        Supervisor sup1 = HW2_TESTS_03.createSup(1, "doda", 45);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));
        assertEquals(ReturnValue.OK, Solution.deleteSupervisor(1));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteSupervisor(1));
    }

    @org.junit.Test
    public void getStudentProfile_GoodPath() {
        Student a = new Student();
        a.setId(2);
        a.setName("Roei");
        a.setCreditPoints(117);
        a.setFaculty("CS");
        ReturnValue ret = Solution.addStudent(a);
        assertEquals(ReturnValue.OK, ret);
        Student b = Solution.getStudentProfile(2);
        assertEquals(a, b);

    }

    @org.junit.Test
    public void GetTestProfile_Exist() {
        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        assertEquals(t1, Solution.getTestProfile(1, 1));
    }

    @org.junit.Test
    public void GetTestProfile_NotExist() {
        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        assertEquals(Test.badTest(), Solution.getTestProfile(1, 2));
    }

    @org.junit.Test
    public void addStudentGoodPath() {
        Student s1 = HW2_TESTS_03.createStudent(3150, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));
    }

    @org.junit.Test
    public void addStusentBadPath() {
        Student s1 = HW2_TESTS_03.createStudent(0, "rami", "CS", 1);
        Student s2 = HW2_TESTS_03.createStudent(1, "rami", "CS", -1);
        assertEquals(ReturnValue.BAD_PARAMS, Solution.addStudent(s1));
        assertEquals(ReturnValue.BAD_PARAMS, Solution.addStudent(s2));
    }

    @org.junit.Test
    public void addStudentIntegration_AlreadyExist() {

        Student s1 = HW2_TESTS_03.createStudent(2, "rami", "CS", 1);
        Student s2 = HW2_TESTS_03.createStudent(2, "rami", "CS", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));
        assertEquals(ReturnValue.ALREADY_EXISTS, Solution.addStudent(s1));
    }

    @org.junit.Test
    public void deleteExistStudent() {
        Student s1 = HW2_TESTS_03.createStudent(2, "rami", "CS", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));
        assertEquals(ReturnValue.OK, Solution.deleteStudent(2));

    }

    @org.junit.Test
    public void deleteNotExistStudent() {
        Student s1 = HW2_TESTS_03.createStudent(2, "rami", "CS", 1);
        //assertEquals(ReturnValue.OK, Solution.addStudent(s1));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.deleteStudent(4));

    }

    @org.junit.Test
    public void GetSupProfile_Exist() {
        Supervisor sup1 = HW2_TESTS_03.createSup(3, "george", 100);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));
        assertEquals(sup1, Solution.getSupervisorProfile(3));
    }

    @org.junit.Test
    public void GetSupProfile_NotExist() {

        assertEquals(Supervisor.badSupervisor(), Solution.getSupervisorProfile(10));
    }

    @org.junit.Test
    public void StudentAttendTestGoodPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Student s1 = HW2_TESTS_03.createStudent(3150, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));

        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3150, 234, 1));

    }

    @org.junit.Test
    public void StudentAttendTestBadPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Student s1 = HW2_TESTS_03.createStudent(3150, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));

        assertEquals(ReturnValue.NOT_EXISTS, Solution.studentAttendTest(315, 234, 1));

        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3150, 234, 1));
        assertEquals(ReturnValue.ALREADY_EXISTS, Solution.studentAttendTest(3150, 234, 1));

    }

    @org.junit.Test
    public void StudentWaiveTestGoodPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Student s1 = HW2_TESTS_03.createStudent(3150, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3150, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentWaiveTest(3150, 234, 1));

    }

    @org.junit.Test
    public void StudentWaiveTestBadPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Student s1 = HW2_TESTS_03.createStudent(3150, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));

        assertEquals(ReturnValue.NOT_EXISTS, Solution.studentWaiveTest(315, 234, 1));

        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3150, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentWaiveTest(3150, 234, 1));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.studentWaiveTest(3150, 234, 1));

    }

    @org.junit.Test
    public void StudentWaiveTestBadPath2() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Student s1 = HW2_TESTS_03.createStudent(3150, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));

        assertEquals(ReturnValue.NOT_EXISTS, Solution.studentWaiveTest(315, 234, 1));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.studentWaiveTest(3150, 234, 2));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.studentWaiveTest(-1, 234, 2));
    }

    @org.junit.Test
    public void SuptOverseeTestGoodPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Supervisor sup1 = HW2_TESTS_03.createSup(3150, "doda", 45);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));

        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 234, 1));

    }

    @org.junit.Test
    public void SuptOverseeTestBadPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Test t2 = HW2_TESTS_03.createTest(55, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));

        Supervisor sup1 = HW2_TESTS_03.createSup(3150, "doda", 45);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));

        assertEquals(ReturnValue.NOT_EXISTS, Solution.supervisorOverseeTest(3, 234, 1));

        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 55, 1));

        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 234, 1));
        assertEquals(ReturnValue.ALREADY_EXISTS, Solution.supervisorOverseeTest(3150, 234, 1));

    }

    @org.junit.Test
    public void SuptStopOverseeTestGoodPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Supervisor sup1 = HW2_TESTS_03.createSup(3150, "doda", 45);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 234, 1));

        assertEquals(ReturnValue.OK, Solution.supervisorStopsOverseeTest(3150, 234, 1));

    }

    @org.junit.Test
    public void SuptStopOverseeTestBadPath() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Test t2 = HW2_TESTS_03.createTest(55, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));

        Supervisor sup1 = HW2_TESTS_03.createSup(3150, "doda", 45);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));

        assertEquals(ReturnValue.NOT_EXISTS, Solution.supervisorOverseeTest(3, 234, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 55, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 234, 1));


        assertEquals(ReturnValue.OK, Solution.supervisorStopsOverseeTest(3150, 234, 1));
        assertEquals(ReturnValue.NOT_EXISTS, Solution.supervisorStopsOverseeTest(1, 234, 1));

        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(3150, 234, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorStopsOverseeTest(3150, 234, 1));

    }

    @org.junit.Test
    public void getWageTest() {

        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Test t2 = HW2_TESTS_03.createTest(1, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));
        Test t3 = HW2_TESTS_03.createTest(2, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t3));
        Test t4 = HW2_TESTS_03.createTest(3, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t4));
        Test t5 = HW2_TESTS_03.createTest(3, 3, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t5));


        Supervisor sup1 = HW2_TESTS_03.createSup(9, "doda1", 30);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(9, 1, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(9, 1, 2));


        Supervisor sup2 = HW2_TESTS_03.createSup(8, "doda2", 30);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup2));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(8, 1, 1));

        Supervisor sup3 = HW2_TESTS_03.createSup(7, "doda3", 20);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup3));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(7, 1, 2));


        assertEquals(Integer.valueOf(60), Solution.getWage(9));
        assertEquals(Integer.valueOf(30), Solution.getWage(8));
        assertEquals(Integer.valueOf(20), Solution.getWage(7));
        assertEquals(Integer.valueOf(-1), Solution.getWage(1));

    }

    @org.junit.Test
    public void testThisSemesterTest() {

        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Test t2 = HW2_TESTS_03.createTest(1, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));
        Test t3 = HW2_TESTS_03.createTest(2, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t3));
        Test t4 = HW2_TESTS_03.createTest(3, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t4));
        Test t5 = HW2_TESTS_03.createTest(3, 3, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t5));


        assertEquals(Collections.singletonList(1), Solution.testsThisSemester(1));
        assertEquals(Collections.singletonList(3), Solution.testsThisSemester(3));
        assertEquals(Arrays.asList(3, 2, 1), Solution.testsThisSemester(2));

        assertEquals(Collections.emptyList(), Solution.testsThisSemester(10));


    }

    @org.junit.Test
    public void testThisSemesterTest2() {

        Test t1 = HW2_TESTS_03.createTest(1, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Test t2 = HW2_TESTS_03.createTest(2, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));
        Test t3 = HW2_TESTS_03.createTest(3, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t3));
        Test t4 = HW2_TESTS_03.createTest(4, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t4));
        Test t5 = HW2_TESTS_03.createTest(5, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t5));
        Test t6 = HW2_TESTS_03.createTest(6, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t6));

        assertEquals(Arrays.asList(6, 5, 4, 3, 2), Solution.testsThisSemester(2)); // Limit 5

    }

    @org.junit.Test
    public void averageTestCostTest() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Test t2 = HW2_TESTS_03.createTest(234, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));

        Test t3 = HW2_TESTS_03.createTest(123, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t3));

        Test t4 = HW2_TESTS_03.createTest(558, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t4));

        Test t5 = HW2_TESTS_03.createTest(5778, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t5));


        Supervisor sup1 = HW2_TESTS_03.createSup(9, "doda1", 30);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));

        Supervisor sup2 = HW2_TESTS_03.createSup(8, "doda2", 30);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup2));

        Supervisor sup3 = HW2_TESTS_03.createSup(7, "doda3", 20);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup3));


        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(9, 234, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(8, 234, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(7, 234, 2));
        assertEquals(new Float(10), Solution.averageTestCost());

    }

    @org.junit.Test
    public void supervisorOverseeStudentTest() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Test t2 = HW2_TESTS_03.createTest(234, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));

        Student student1 = HW2_TESTS_03.createStudent(1, "rami", "CS", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(student1));


        Student student2 = HW2_TESTS_03.createStudent(2, "rami", "EE", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(student2));

        Student student3 = HW2_TESTS_03.createStudent(3, "rami", "MATH", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(student3));

        Supervisor sup1 = HW2_TESTS_03.createSup(12, "doda1", 30);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup1));

        Supervisor sup2 = HW2_TESTS_03.createSup(123, "doda2", 30);
        assertEquals(ReturnValue.OK, Solution.addSupervisor(sup2));


        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3, 234, 2));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(1, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(1, 234, 2));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(2, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(2, 234, 2));


        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(12, 234, 2));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(123, 234, 1));
        assertEquals(ReturnValue.OK, Solution.supervisorOverseeTest(123, 234, 2));

        ArrayList<Integer> expectedArray = new ArrayList<Integer>();
        expectedArray.add(3);
        expectedArray.add(2);
        expectedArray.add(1);

        assertEquals(expectedArray, Solution.supervisorOverseeStudent());
    }

    @org.junit.Test
    public void studentPointsTest() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Student s1 = HW2_TESTS_03.createStudent(1, "rami", "CS", 2);
        assertEquals(ReturnValue.OK, Solution.addStudent(s1));

        assertEquals(Integer.valueOf(2), Solution.studentCreditPoints(1));

        assertEquals(ReturnValue.OK, Solution.studentAttendTest(1, 234, 1));

        assertEquals(Integer.valueOf(5), Solution.studentCreditPoints(1));
    }

    @org.junit.Test
    public void halfwayTest() {

        Student student1 = HW2_TESTS_03.createStudent(1, "rami", "CS", 60);
        assertEquals(ReturnValue.OK, Solution.addStudent(student1));

        Student student2 = HW2_TESTS_03.createStudent(2, "rami", "EE", 81);
        assertEquals(ReturnValue.OK, Solution.addStudent(student2));


        Student student3 = HW2_TESTS_03.createStudent(3, "rami", "MATH", 57);
        assertEquals(ReturnValue.OK, Solution.addStudent(student3));


        assertEquals(Boolean.TRUE, Solution.studentHalfWayThere(1));
        assertEquals(Boolean.TRUE, Solution.studentHalfWayThere(2));
        assertEquals(Boolean.FALSE, Solution.studentHalfWayThere(3));
    }

    @org.junit.Test
    public void conflictingTests() {

        Test t1 = HW2_TESTS_03.createTest(1, 1, 233, 1, 2, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));
        Test t2 = HW2_TESTS_03.createTest(2, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));
        Test t3 = HW2_TESTS_03.createTest(3, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t3));
        Test t4 = HW2_TESTS_03.createTest(4, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t4));
        Test t5 = HW2_TESTS_03.createTest(5, 3, 233, 1, 2, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t5));
        Test t6 = HW2_TESTS_03.createTest(6, 3, 233, 2, 2, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t6));

        assertEquals(Arrays.asList(3, 4), Solution.getConflictingTests());

        Test t7 = HW2_TESTS_03.createTest(7, 3, 233, 2, 2, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t7));

        assertEquals(Arrays.asList(3, 4, 6, 7), Solution.getConflictingTests());


    }


    @org.junit.Test
    public void getMostPopular() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Test t2 = HW2_TESTS_03.createTest(345, 2, 233, 1, 1, 3);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));

        Student student1 = HW2_TESTS_03.createStudent(1, "rami", "CS", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(student1));

        Student student2 = HW2_TESTS_03.createStudent(2, "rami", "CS", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(student2));

        Student student3 = HW2_TESTS_03.createStudent(3, "rami", "CS", 1);
        assertEquals(ReturnValue.OK, Solution.addStudent(student3));

        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(1, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(2, 345, 2));


        assertEquals(t1.getId(), (Solution.getMostPopularTest("CS")).intValue());


    }

    @org.junit.Test
    public void graduateStudentsTest() {

        Test t1 = HW2_TESTS_03.createTest(234, 1, 233, 1, 1, 2);
        assertEquals(ReturnValue.OK, Solution.addTest(t1));

        Test t2 = HW2_TESTS_03.createTest(234, 2, 233, 1, 1, 1);
        assertEquals(ReturnValue.OK, Solution.addTest(t2));


        Student student1 = HW2_TESTS_03.createStudent(1, "rami", "CS", 121);
        assertEquals(ReturnValue.OK, Solution.addStudent(student1));

        Student student2 = HW2_TESTS_03.createStudent(2, "rami", "CS", 117);
        assertEquals(ReturnValue.OK, Solution.addStudent(student2));

        Student student3 = HW2_TESTS_03.createStudent(3, "rami", "CS", 125);
        assertEquals(ReturnValue.OK, Solution.addStudent(student3));


        Student student4 = HW2_TESTS_03.createStudent(4, "rami", "CS", 122);
        assertEquals(ReturnValue.OK, Solution.addStudent(student4));

        Student student5 = HW2_TESTS_03.createStudent(5, "rami", "CS", 11);
        assertEquals(ReturnValue.OK, Solution.addStudent(student5));

        Student student6 = HW2_TESTS_03.createStudent(6, "rami", "CS", 122);
        assertEquals(ReturnValue.OK, Solution.addStudent(student6));

        Student student7 = HW2_TESTS_03.createStudent(7, "rami", "CS", 122);
        assertEquals(ReturnValue.OK, Solution.addStudent(student7));


        assertEquals(ReturnValue.OK, Solution.studentAttendTest(3, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(2, 234, 1));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(2, 234, 2));
        assertEquals(ReturnValue.OK, Solution.studentAttendTest(1, 234, 1));


        ArrayList<Integer> expectedArray = new ArrayList<Integer>();
        expectedArray.add(1);
        expectedArray.add(2);
        expectedArray.add(3);
        expectedArray.add(4);
        expectedArray.add(6);
        // expectedArray.add(3);

        assertEquals(expectedArray, Solution.graduateStudents());


    }

}