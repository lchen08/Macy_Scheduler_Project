import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.Panel;
import org.apache.pivot.wtk.Window;

import java.awt.*;
import java.awt.Frame;
import java.io.File;

public class MainUI implements Application
{
    private Window window;
    private FillPane filePanel;
    private FileChooser fc;
    private PushButton fileButton;
    private final String START_FILE_DIR = System.getProperty("user.dir"); //gets directory of app

    public void startup(Display display, Map<String, String> map) throws Exception
    {
        window = new Window();
        filePanel = new FillPane();
        fileButton = new PushButton("Open");
        Stage stage = new Stage();

//        fileButton.setAction(
//                String.valueOf(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(final ActionEvent e) {
//                        File file = fc.showOpenDialog(stage);
//                    }
//                }));
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getFile();
        System.out.println(file + " chosen.");
        fc = new FileChooser();
        fc.setTitle("Open Resource File");
        Label label = new Label("Hello World");

//        fb.getSelectedFile();
//        filePanel.set
//        filePanel.add(fb);
        filePanel.add(label);
        filePanel.setVisible(true);
//        window.add(filePanel);
        window.setContent(filePanel);
//        window.setContent(label);
        window.setTitle("Hello World!");
        window.setMaximized(true);

        window.open(display);
    }

    public boolean shutdown(boolean b) throws Exception
    {
        if (window != null) {
            window.close();
        }
        return false;
    }

    public void suspend() throws Exception {

    }

    public void resume() throws Exception {

    }

    public static void main(String[] args)
    {
        DesktopApplicationContext.main(MainUI.class, args);
    }
}
