package lab3;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {

    public static void main(String[] args) {

        System.out.println("Robots began to accept students' works");
        try {

            StudentLine studentLine = new StudentLine();
            ExecutorService threads = Executors.newFixedThreadPool(DataStore.threadsCount);
            threads.execute(new Robot(DataStore.subjects[0], studentLine));
            threads.execute(new Robot(DataStore.subjects[1], studentLine));
            threads.execute(new Robot(DataStore.subjects[2], studentLine));
            threads.submit(new Generator(studentLine));
            threads.shutdown();

        } catch (Exception exc) {

            exc.printStackTrace();
        }
    }
}
