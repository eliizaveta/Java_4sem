package lab3;

public class Generator implements Runnable {

    private StudentLine studentLine;
    public Generator(StudentLine studentLine) {

        this.studentLine = studentLine;
    }

    @Override
    public void run() {

        try {

            for (int i = 1; i <= DataStore.studentsCount; i++) {

                Student student = new Student(i);
                studentLine.put(student);
                System.out.println("One more person, joined " + student);
            }

            studentLine.setStatus(false);
            System.out.println("Group of students is all here");

        } catch (InterruptedException exc) {

            exc.printStackTrace();
        }
    }
}
