package lab3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class StudentLine {

    private ArrayBlockingQueue<Student> queue;
    private Boolean flag;

    public StudentLine() {

        queue = new ArrayBlockingQueue<Student>(DataStore.queueLength);
        flag = true;
    }

    public void put(Student student) throws InterruptedException {

        queue.put(student);
    }

    public Student get() throws InterruptedException {

        return queue.poll(1, TimeUnit.SECONDS);
    }

    public Student peek() throws InterruptedException {

        return queue.peek();
    }

    public ArrayBlockingQueue<Student> getQueue() {

        return queue;
    }

    public void setStatus(Boolean newStatus) {

        flag = newStatus;
    }

    public Boolean getStatus() {

        return flag;
    }
}