package poi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Demo {
    public static int COUNT_OF_THREADS = 3;
    private static CyclicBarrier barrier;
    private static final List<Integer> results = Collections.synchronizedList(new ArrayList<>(COUNT_OF_THREADS));

    public static void main(String[] args) {
            barrier = new CyclicBarrier(COUNT_OF_THREADS, new ResultThread(results));

            long start = new Date().getTime();
            FileManager fileManager = new FileManager("book1.txt");
            processFilesMultiThreading(fileManager);

            long end = new Date().getTime();

            System.out.println("Reading took: "+(end - start));
            System.out.println("Memory used: "+Runtime.getRuntime().totalMemory());
    }
    private static void processFilesMultiThreading(FileManager fileManager) {
        for (int i = 0; i < Demo.COUNT_OF_THREADS; i++) {
            CyclicBarrierTask thread = new CyclicBarrierTask(FileManager.files.get(i), "word", barrier, results);
            thread.start();
        }
    }
}
