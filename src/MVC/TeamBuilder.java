package MVC;/* * * * * *
 * Created by Troy Scott
 * for the Overwatch community
 * * * * * */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;



public class TeamBuilder extends Application {


    public static void main(String[] args) {

        Application.launch(TeamBuilder.class, args);
    }


    // main entry point
    @Override
    public void start(Stage primaryStage) throws Exception {

        // locate the fxml for the MVC.UI
        URL viewLocation = getClass().getResource("/MVC/UI.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(viewLocation);

        // set the display
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TeamBuilder");
        primaryStage.getIcons().add(new Image(TeamBuilder.class.getResourceAsStream("group.png")));

        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
