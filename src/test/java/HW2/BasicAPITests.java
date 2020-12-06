
package HW2;

import HW2.business.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BasicAPITests extends AbstractTest {
    @org.junit.Test
    public void studentAttendTest() {

        ReturnValue res;
        Test s = new Test();
        s.setId(1);
        s.setSemester(1);
        s.setRoom(233);
        s.setDay(1);
        s.setTime(1);
        s.setCreditPoints(3);


        res = Solution.addTest(s);
        assertEquals(ReturnValue.OK, res);

        Student a = new Student();
        a.setId(2);
        a.setName("Roei");
        a.setCreditPoints(117);
        a.setFaculty("CS");
        ReturnValue ret = Solution.addStudent(a);
        assertEquals(ReturnValue.OK, ret);

        res = Solution.studentAttendTest(2, s.getId(), s.getSemester());
        assertEquals(ReturnValue.OK, res);

        res = Solution.studentAttendTest(1,  s.getId(), s.getSemester());
        assertEquals(ReturnValue.NOT_EXISTS, res);
    }

    @org.junit.Test
    public void studentGraduateTest() {

        ReturnValue res;
        Test s = new Test();
        s.setId(1);
        s.setSemester(1);
        s.setRoom(233);
        s.setDay(1);
        s.setTime(1);
        s.setCreditPoints(3);


        res = Solution.addTest(s);
        assertEquals(ReturnValue.OK, res);

        Test s1 = new Test();
        s1.setId(1);
        s1.setSemester(2);
        s1.setRoom(233);
        s1.setDay(1);
        s1.setTime(1);
        s1.setCreditPoints(3);
        res = Solution.addTest(s1);
        assertEquals(ReturnValue.OK, res);

        Student a = new Student();
        a.setId(2);
        a.setName("Roei");
        a.setCreditPoints(117);
        a.setFaculty("CS");
        ReturnValue ret = Solution.addStudent(a);
        assertEquals(ReturnValue.OK, ret);

        res = Solution.studentAttendTest(2,  s.getId(), s.getSemester());
        assertEquals(ReturnValue.OK, res);

        res = Solution.studentAttendTest(2,  s1.getId(), s1.getSemester());
        assertEquals(ReturnValue.OK, res);

        Supervisor sup = new Supervisor();
        sup.setId(1);
        sup.setName("");
        sup.setSalary(30);

        res = Solution.addSupervisor(sup);
        assertEquals(ReturnValue.OK, res);

        res = Solution.supervisorOverseeTest(1,  s.getId(), s.getSemester());
        assertEquals(ReturnValue.OK, res);

        ArrayList<Integer> arr = Solution.supervisorOverseeStudent();
        assertEquals(0, arr.size());

        res = Solution.supervisorOverseeTest(1,  s1.getId(), s1.getSemester());
        assertEquals(ReturnValue.OK, res);

        arr = Solution.supervisorOverseeStudent();
        assertEquals(Integer.valueOf(2), arr.get(0));
        assertEquals(1, arr.size());

    }
}


