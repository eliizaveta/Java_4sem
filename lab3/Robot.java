package lab3;

public class Robot implements Runnable {

    private String subject;
    private StudentLine studentLine;

    public Robot(String subject, StudentLine broker) {

        this.subject = subject;
        this.studentLine = broker;
    }

    @Override
    public void run() {

        try {

            while (true) {

                Student student = null;
                synchronized (studentLine) {

                    if (!studentLine.getStatus() && studentLine.getQueue().size() == 0)

                        break;

                    while(studentLine.getQueue().size() == 0) {

                        studentLine.wait();
                    }

                    if (studentLine.peek() != null) {

                        if (studentLine.peek().subjectName.equals(subject)) {

                            student = studentLine.get();
                            studentLine.notifyAll();
                        } else {

                            studentLine.wait();
                        }
                    }
                }

                if (student != null) {

                    while (student.labsCount != 0) {

                        System.out.println(subject + " robot is working with " + student);
                        student.changeLabsCount(DataStore.labsAcceptance);
                        Thread.sleep(DataStore.sleepTime);
                    }
                    System.out.println(subject + " robot finished work with student number " + student.number);
                }
            }
        } catch (InterruptedException exc) {

            exc.printStackTrace();
        }
    }
}
