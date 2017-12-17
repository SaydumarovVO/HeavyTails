import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class FileWorker<T> {
    public void write(String fileName, Collection<T> data) {
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                for (T k : data) {
                    out.println(k);
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Double> read(String fileName) throws FileNotFoundException {
        ArrayList<Double> list = new ArrayList<Double>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNext()) {
            String string = scanner.nextLine();
            list.add(Double.valueOf(string));
        }
        scanner.close();
        return list;
    }
}
