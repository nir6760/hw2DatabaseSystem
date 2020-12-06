package HW2.business;

import java.util.Objects;


public class Supervisor {


    int id = -1;
    String name = null;
    int salary = -1;

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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public static Supervisor badSupervisor()
    {
        return new Supervisor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supervisor supervisor = (Supervisor) o;
        return id == supervisor.id &&
                Objects.equals(name, supervisor.name) &&
                salary == supervisor.salary;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Supervisor{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", salary='").append(salary).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary);
    }
}
