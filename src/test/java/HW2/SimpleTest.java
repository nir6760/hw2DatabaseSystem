package HW2;

import HW2.business.ReturnValue;

import HW2.business.Test;
import static org.junit.Assert.assertEquals;


public class SimpleTest extends AbstractTest{

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
}