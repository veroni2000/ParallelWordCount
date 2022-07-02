package poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTask extends Thread {
    private final String fileName;
    private final String word;
    private int count;
    private final CyclicBarrier barrier;
    private final List<Integer> results;
    private final List<String> list = Arrays.asList("?", ",", ".", "=", ":", "“","!");

    public CyclicBarrierTask(String filename, String word, CyclicBarrier barrier, List<Integer> results) {
        this.fileName = filename;
        this.word = word;
        this.barrier = barrier;
        this.results = results;
        System.out.println("Create Task to get content from " + filename);
    }
    public int getCount() {
        return count;
    }
    public String getFileName() {
        return fileName;
    }

    @Override
    public void run() {
        long start = new Date().getTime();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] words = scanner.nextLine().split(" ");
                for (String w : words) {
                    if (w.length() >= word.length() && w.length()<word.length()+4) {
                        String s = Character.toString( w.charAt(w.length() - 1));
                        if (list.contains(s)) {
                            if(w.endsWith("“.")||w.endsWith("“,")){
                                w = w.substring(0, w.length() - 2);
                            }else {  w = w.substring(0, w.length() - 1);}
                            if (w.startsWith("„")){
                                w =w.substring(1);
                            }
                        }
                    }
                    if (word.equalsIgnoreCase(w)) {
                        count++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        results.add(count);

        long end = new Date().getTime();
        System.out.println("Execution time of "+fileName+" thread: "+(end-start));
        System.out.println("Count of word in "+fileName+": "+count);

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
