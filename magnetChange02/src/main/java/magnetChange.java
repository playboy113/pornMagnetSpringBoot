import java.io.*;
import java.util.Arrays;

public class magnetChange {
    public static void main(String[] args) throws IOException {
        String inputFileName = "Downloads/magnet.txt";
        String OutputFileName = "Downloads/output.txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(OutputFileName));

        String line;

        while ((line = reader.readLine()) != null) {
            //System.out.println(line);
            String[] splits = line.split("magnet");
            for (String s:splits){
                //System.out.println(s);
                if (s.equals("")){
                    continue;
                }else{
                    writer.write("magnet"+s);
                    writer.newLine();
                }

            }
        }



        reader.close();
        writer.close();

        System.out.println("文件处理完成。");

    }
}
