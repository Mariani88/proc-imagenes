

 
import java.io.InputStream;
 
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
 
public class ImageViewDemo extends Application {
 
    @Override
    public void start(Stage primaryStage) throws Exception {
 
        
 
        File_Opener a=new File_Opener();
		a.run("0",primaryStage);
 
        Image image = a.getImage();
 
        ImageView imageView = new ImageView(image);
 
       
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20));
 
        root.getChildren().addAll(imageView );
 
        Scene scene = new Scene(root, 400, 200);
 
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }
 
}