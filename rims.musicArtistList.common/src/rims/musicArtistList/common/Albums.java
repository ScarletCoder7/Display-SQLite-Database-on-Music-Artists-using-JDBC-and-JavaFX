// Created By ScarletCoder7

package rims.musicArtistList.common;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Albums {
	
	private SimpleIntegerProperty _id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty artist;
	
	public Albums() {
		_id = new SimpleIntegerProperty();
		name = new SimpleStringProperty();
		artist = new SimpleIntegerProperty();
	}
	
	public int get_Id() {
		return _id.get();
	}
	public void set_Id(int _id) {
		this._id.set(_id);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public int getArtist() {
		return artist.get();
	}
	public void setArtist(int artist) {
		this.artist.set(artist);
	}
	
	
}
