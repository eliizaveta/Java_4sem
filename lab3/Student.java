package lab3;

public class Student {

    public int number;
    public int labsCount;
    public String subjectName;

    Student(Integer number) {

        this.number = number;
        this.labsCount = DataStore.labs[(int) (Math.random() * DataStore.subjects.length)];
        this.subjectName = DataStore.subjects[(int) (Math.random() * DataStore.subjects.length)];
    }

    public void changeLabsCount(Integer change) {

        this.labsCount = labsCount - change;
    }

    @Override
    public String toString() {

        return ("student " + number + ", who has " + labsCount + " in " + subjectName);
    }
}
