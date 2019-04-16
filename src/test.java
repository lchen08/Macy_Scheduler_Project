import java.awt.*;

public class test
{
    public static void main(String[] args)
    {
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        System.out.println(file + " chosen.");
        Object blah = new Object();
        blah.
    }
}
