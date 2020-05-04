package lab3;

public class Generator implements Runnable {

    private StudentLine studentLine;
    public Generator(StudentLine studentLine) {

        this.studentLine = studentLine;
    }

    @Override
    public void run() {

        try {

            int i = 1;
            while (i <= DataStore.studentsCount) {

                if (studentLine.getQueue().size() != DataStore.queueLength) {

                    Student student = new Student(i);
                    synchronized(studentLine) {

                        studentLine.put(student);
                        System.out.println("One more person, joined " + student);
                        studentLine.notifyAll();
                    }
                    i=i+1;
                }
            }
            studentLine.setStatus(false);
            System.out.println("Group of students is all here");

        } catch (InterruptedException exc) {

            exc.printStackTrace();
        }
    }
}
