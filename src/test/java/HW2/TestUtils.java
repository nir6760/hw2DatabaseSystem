package HW2;

import HW2.business.*;


public class TestUtils {
    final public static double eps = 1e-5;
    public static Test createTest(int id, int semester, int time, int room, int day, int credit_points) {
        Test t = new Test();
        t.setId(id);
        t.setSemester(semester);
        t.setTime(time);
        t.setRoom(room);
        t.setDay(day);
        t.setCreditPoints(credit_points);
        return t;
    }
    public static Test createTest(int id, int semester) {
        return createTest(id, semester, 1, 1, 1, 1);
    }

    public static Student createStudent(int id, int credit_points, String name, String faculty) {
        Student s = new Student();
        s.setId(id);
        s.setCreditPoints(credit_points);
        s.setName(name);
        s.setFaculty(faculty);
        return s;
    }
    public static Student createStudent(int id) {
        return createStudent(id, 1, "s", "CS");
    }

    public static Supervisor createSupervisor(int id, String name, int salary) {
        Supervisor s = new Supervisor();
        s.setId(id);
        s.setName(name);
        s.setSalary(salary);
        return s;
    }
    public static Supervisor createSupervisor(int id) {
        return createSupervisor(id, "s", 1);
    }
}
