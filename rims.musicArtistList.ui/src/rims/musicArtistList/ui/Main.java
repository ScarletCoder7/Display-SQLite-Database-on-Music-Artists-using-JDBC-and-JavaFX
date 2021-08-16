// Created By ScarletCoder7

package rims.musicArtistList.ui;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import rims.musicArtistList.db.Datasource;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root = loader.load();
			Controller controller = loader.getController();
			controller.listArtists();
			
			Scene scene = new Scene(root,800,600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Music Database");
			primaryStage.show();
	}
	
	@Override
	public void init() throws Exception{
		super.init();
		if(!Datasource.getInstance().open()) {
			System.out.println("Unable to open database !");
			Platform.exit();
		}
	}
	@Override
	public void stop() throws Exception{
		super.stop();
		Datasource.getInstance().close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
