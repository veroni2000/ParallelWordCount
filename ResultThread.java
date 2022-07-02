package poi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ResultThread extends Thread {
    private final List<Integer> results;

    public ResultThread(List<Integer> results) {
        this.results = results;
    }

    @Override
    public void run() {
        System.out.println("Result Consolidation Thread started!");
        Integer sum = 0;

        for (Integer result : results) {
            sum += result;
        }
        System.out.println("Number of threads: "+results.size());
        System.out.println("Total matches of word: "+sum);

        for (int i = 0; i < Demo.COUNT_OF_THREADS; i++) {
            try {
                Files.delete(Path.of(FileManager.files.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
