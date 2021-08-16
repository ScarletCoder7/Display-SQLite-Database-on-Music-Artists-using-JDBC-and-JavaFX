// Created By ScarletCoder7

package rims.musicArtistList.common;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Artists {  // Created By ScarletCoder7
	
	private SimpleIntegerProperty _id;
	private SimpleStringProperty name;
	
	public Artists() {
		_id = new SimpleIntegerProperty();
		name = new SimpleStringProperty();
	}
	
	public void set_Id(int _id) {
		this._id.set(_id);
	}
	
	public int get_Id() {
		return _id.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getName() {
		return this.name.get();
	}
}
