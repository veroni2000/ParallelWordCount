package poi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class FileManager {
    private final String fileName;
    public static List<String> files;
    public FileManager(String fileName) {
        this.fileName = fileName;
        files = split();
    }

    private List<String> split() {
        List<String> files = new ArrayList<>();
        int lines = countLines();
        int maxLines = lines / Demo.COUNT_OF_THREADS;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < Demo.COUNT_OF_THREADS; i++) {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("book1-"+i+".txt"));
                files.add("book1-"+i+".txt");
                for (int j = 0; j <=maxLines; j++) {
                    String line = bufferedReader.readLine();
                    if(line!=null){
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }
    private int countLines() {
        int count = 0;
        String line;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            while ((line=bufferedReader.readLine())!=null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
