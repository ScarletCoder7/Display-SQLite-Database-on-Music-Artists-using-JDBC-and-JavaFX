// Created By ScarletCoder7

package rims.musicArtistList.ui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import rims.musicArtistList.common.Albums;
import rims.musicArtistList.common.Artists;
import rims.musicArtistList.db.Datasource;

public class Controller {

	@FXML
	private TableView artistTable;
	@FXML
	private ProgressBar progressBar;
	@FXML
	public void listArtists() {
		Task<ObservableList<Artists>> task = new getAllArtists();
		artistTable.itemsProperty().bind(task.valueProperty());
		progressBar.progressProperty().bind(task.progressProperty());
		progressBar.setVisible(true);
		task.setOnSucceeded(e ->  progressBar.setVisible(false));
		task.setOnFailed(e ->  progressBar.setVisible(false));
		
		new Thread(task).start();
	}
	
	public void listAlbumsByArtists() {
		Artists artist = (Artists) artistTable.getSelectionModel().getSelectedItem();
		if(artist == null) {
			System.out.println("No artist selected !");
		}
		Task<ObservableList<Albums>> task = new Task<ObservableList<Albums>>() {
			public final ObservableList<Albums> call() throws Exception{
				return FXCollections.observableArrayList(Datasource.getInstance().queryAlbumsByArtistsID(artist.get_Id()));
			}
		};
		artistTable.itemsProperty().bind(task.valueProperty());
		new Thread(task).start();
	}
	
	public void updateArtist() {
		Artists artist = (Artists) artistTable.getSelectionModel().getSelectedItem();
		Task<Boolean> task = new Task<Boolean>() {
			public final Boolean call() throws Exception{
				return Datasource.getInstance().updateArtistName("AC/DC", artist.get_Id());
			}
		};
		task.setOnSucceeded(e -> {
			if(task.valueProperty().get()) {
				artist.setName("AC/DC");
				artistTable.refresh();
			}
		});
		new Thread(task).start();
	}
}

class getAllArtists extends Task{
	@FXML
	public final  ObservableList<Artists> call() throws Exception{
		return FXCollections.observableArrayList(Datasource.getInstance().queryArtists(Datasource.ORDER_BY_ASC));
	}
}
