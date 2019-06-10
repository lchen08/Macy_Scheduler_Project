package ScheduleUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainUI extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Macy's Employee Scheduler");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {

//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });

        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }


//    private Window window;
//    private FillPane filePanel;
//    private FileChooser fc;
//    private PushButton fileButton;
//    private final String START_FILE_DIR = System.getProperty("user.dir"); //gets directory of app
//
//    public void startup(Display display, Map<String, String> map) throws Exception
//    {
//        window = new Window();
//        filePanel = new FillPane();
//        fileButton = new PushButton("Open");
//        Stage stage = new Stage();
//
////        fileButton.setAction(
////                String.valueOf(new EventHandler<ActionEvent>() {
////                    @Override
////                    public void handle(final ActionEvent e) {
////                        File file = fc.showOpenDialog(stage);
////                    }
////                }));
//        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
//        dialog.setMode(FileDialog.LOAD);
//        dialog.setVisible(true);
//        String file = dialog.getFile();
//        System.out.println(file + " chosen.");
//        fc = new FileChooser();
//        fc.setTitle("Open Resource File");
//        Label label = new Label("Hello World");
//
////        fb.getSelectedFile();
////        filePanel.set
////        filePanel.add(fb);
//        filePanel.add(label);
//        filePanel.setVisible(true);
////        window.add(filePanel);
//        window.setContent(filePanel);
////        window.setContent(label);
//        window.setTitle("Hello World!");
//        window.setMaximized(true);
//
//        window.open(display);
//    }
//
//    public boolean shutdown(boolean b) throws Exception
//    {
//        if (window != null) {
//            window.close();
//        }
//        return false;
//    }
//
//    public void suspend() throws Exception {
//
//    }
//
//    public void resume() throws Exception {
//
//    }
//
//    public static void main(String[] args)
//    {
//        DesktopApplicationContext.main(MainUI.class, args);
//    }
}
