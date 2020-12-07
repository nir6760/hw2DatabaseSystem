package HW2;

import HW2.business.ReturnValue;

import HW2.business.Student;
import HW2.business.Supervisor;
import HW2.business.Test;
import static org.junit.Assert.assertEquals;


public class SimpleTest extends AbstractTest{
    /*****************************************************************************************************************/
    /**Test**/
    @org.junit.Test
    public void createTest()
    {
        Test test = new Test();
        test.setId(1);
        test.setSemester(1);
        test.setTime(1);
        test.setDay(1);
        test.setRoom(233);
        test.setCreditPoints(3);

        ReturnValue ret = Solution.addTest(test);
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void deleteTest(){
        Test test = new Test();
        test.setId(1);
        test.setSemester(1);
        test.setTime(1);
        test.setDay(1);
        test.setRoom(233);
        test.setCreditPoints(3);

        ReturnValue ret = Solution.addTest(test);
        assertEquals(ReturnValue.OK, ret);

        ret = Solution.deleteTest(1,1);
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void profileTest(){
        Test test = new Test();
        test.setId(1);
        test.setSemester(1);
        test.setTime(1);
        test.setDay(1);
        test.setRoom(233);
        test.setCreditPoints(3);

        ReturnValue ret = Solution.addTest(test);
        assertEquals(ReturnValue.OK, ret);

        Test test1=Solution.getTestProfile(1,1);
        assertEquals(1,test1.getId());
        assertEquals(1,test1.getSemester());
        assertEquals(1,test1.getTime());
        assertEquals(1,test1.getDay());
        assertEquals(233,test1.getRoom());
        assertEquals(3,test1.getCreditPoints());
    }
    /*****************************************************************************************************************/
    /**Student**/
    @org.junit.Test
    public void createStudent()
    {
        Student student = new Student();
        student.setId(20546);
        student.setName("Nir");
        student.setFaculty("CS");
        student.setCreditPoints(18);


        ReturnValue ret = Solution.addStudent(student);
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void deleteStudent(){
        Student student = new Student();
        student.setId(20546);
        student.setName("Nir");
        student.setFaculty("CS");
        student.setCreditPoints(18);

        ReturnValue ret = Solution.addStudent(student);
        assertEquals(ReturnValue.OK, ret);

        ret = Solution.deleteStudent(20546);
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void profileStudent(){
        Student student = new Student();
        student.setId(20546);
        student.setName("Nir");
        student.setFaculty("CS");
        student.setCreditPoints(18);

        ReturnValue ret = Solution.addStudent(student);
        assertEquals(ReturnValue.OK, ret);

        Student student1=Solution.getStudentProfile(20546);
        assertEquals(20546,student1.getId());
        assertEquals("Nir",student1.getName());
        assertEquals("CS",student1.getFaculty());
        assertEquals(18,student1.getCreditPoints());
    }
    /*****************************************************************************************************************/
    /**Supervisor**/
    @org.junit.Test
    public void createSupervisor()
    {
        Supervisor supervisor = new Supervisor();
        supervisor.setId(123);
        supervisor.setName("NirSuper");
        supervisor.setSalary(99);


        ReturnValue ret1 = Solution.addSupervisor(supervisor);
        assertEquals(ReturnValue.OK, ret1);

        ReturnValue ret2 = Solution.addSupervisor(supervisor);
        assertEquals(ReturnValue.ALREADY_EXISTS, ret2);
    }

    @org.junit.Test
    public void deleteSupervisor(){
        Supervisor supervisor = new Supervisor();
        supervisor.setId(123);
        supervisor.setName("NirSuper");
        supervisor.setSalary(99);


        ReturnValue ret = Solution.addSupervisor(supervisor);
        assertEquals(ReturnValue.OK, ret);

        ret = Solution.deleteSupervisor(123);
        assertEquals(ReturnValue.OK, ret);
    }

    @org.junit.Test
    public void profileSupervisor(){
        Supervisor supervisor = new Supervisor();
        supervisor.setId(123);
        supervisor.setName("NirSuper");
        supervisor.setSalary(99);


        ReturnValue ret = Solution.addSupervisor(supervisor);
        assertEquals(ReturnValue.OK, ret);

        Supervisor supervisor1=Solution.getSupervisorProfile(123);
        assertEquals(123,supervisor1.getId());
        assertEquals("NirSuper",supervisor1.getName());
        assertEquals(99,supervisor1.getSalary());
    }
    /*****************************************************************************************************************/
    /**OverSee**/
    

    /*****************************************************************************************************************/
    /**TakeTest**/
}