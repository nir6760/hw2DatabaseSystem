package HW2.business;

import java.util.Objects;

public class Test {
    int id = -1, semester = -1, time = -1, room = -1, day = -1, credit_points = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) { this.time = time; }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCreditPoints() {
        return credit_points;
    }

    public void setCreditPoints(int credit_points) {
        this.credit_points = credit_points;
    }

    public static Test badTest()
    {
        return new Test();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;

        Test test = (Test) o;

        if (getId() != test.getId()) return false;
        if (getSemester() != test.getSemester()) return false;
        if (getTime() != test.getTime()) return false;
        if (getRoom() != test.getRoom()) return false;
        if (getDay() != test.getDay()) return false;
        return getCreditPoints() == test.getCreditPoints();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Test{");
        sb.append("id=").append(id);
        sb.append(", semester='").append(semester).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append(", room='").append(room).append('\'');
        sb.append(", day='").append(day).append('\'');
        sb.append(", credit pointes='").append(credit_points).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semester, time, room, day, credit_points);
    }
}
