package HW2.business;

import java.util.Objects;


public class Student {


    int id = -1, credit_points = -1;
    String name = null;
    String faculty = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditPoints() {
        return credit_points;
    }

    public void setCreditPoints(int credit_points) {
        this.credit_points = credit_points;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public static Student badStudent() {
        return new Student();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(name, student.name) &&
                Objects.equals(faculty, student.faculty) && credit_points == student.credit_points;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", faculty='").append(faculty).append('\'');
        sb.append(", credit points='").append(credit_points).append('\'');
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, faculty, credit_points);
    }
}
