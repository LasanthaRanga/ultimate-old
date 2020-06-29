/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.ultimate;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ranga
 */
public class CATULTIMATE extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            primaryStage = stage;
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("/grafics/info.png"));
            stage.setScene(scene);
            stage.show();
            modle.StaticViews.setPrimaryStage(primaryStage);
        }catch (Exception e){
            e.printStackTrace();
        }

        //  Session session = conn.NewHibernateUtil.getSessionFactory().openSession();
        //  session.close();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
