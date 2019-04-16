package MacySchedule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This is a class to print the text that comes from the saved html from Macy's
 * webpage for the "My Area View".
 *
 * @author Lisa Chen
 * @since 15Nov18
 * @version 1
 */
public class ScannerTestRun
{
    public static void main(String args[]) throws FileNotFoundException
    {
        String readFile = "My Area View.html";
        String saveFile = "savetest.txt";
        File read = new File(readFile);
        Scanner s = new Scanner(read);
        File save = new File(saveFile);
        PrintWriter writer = new PrintWriter(save);
        while (s.hasNext())
            writer.println(s.nextLine());
        s.close();
        writer.close();
    }
}