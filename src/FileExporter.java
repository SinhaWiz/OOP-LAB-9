import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class FileExporter {
    public static void export(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}