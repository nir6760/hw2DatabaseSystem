package HW2;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
public class AbstractTest {


    @BeforeClass
    public static void createTables()
    {
        Solution.createTables();
    }

    @AfterClass
    public static void dropTables()
    {
        Solution.dropTables();
    }

    @Before
    public void clearTables()
    {
        Solution.clearTables();
    }

    @Rule
    public
    TestName name = new TestName();

}
