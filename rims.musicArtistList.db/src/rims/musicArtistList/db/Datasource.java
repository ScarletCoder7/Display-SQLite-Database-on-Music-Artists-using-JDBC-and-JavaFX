// Created By ScarletCoder7

package rims.musicArtistList.db;
import rims.musicArtistList.common.Albums;
import rims.musicArtistList.common.Artists;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Datasource {
	public static final String DB_NAME = "musicFX.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:"+ DB_NAME;
	
	public static final String TABLE_ALBUMS = "albums";
	public static final String FIELD_ALBUMS_ID = "_id";
	public static final String FIELD_ALBUMS_NAME = "name";
	public static final String FIELD_ALBUMS_ARTIST = "artist";
	public static final int INDEX_ALBUMS_ID = 1;
	public static final int INDEX_ALBUMS_NAME = 2;
	public static final int INDEX_ALBUMS_ARTIST = 3;
	
	public static final String TABLE_ARTISTS = "artists";
	public static final String FIELD_ARTISTS_ID = "_id";
	public static final String FIELD_ARTISTS_NAME = "name";
	public static final int INDEX_ARTISTS_ID=1;
	public static final int INDEX_ARTISTS_NAME=2;
	
	public static final String TABLE_SONGS = "songs";
	public static final String FIELD_SONGS_ID = "_id";
	public static final String FIELD_SONGS_TRACK = "track";
	public static final String FIELD_SONGS_TITLE = "title";
	public static final String FIELD_SONGS_ALBUM = "album";
	public static final int INDEX_SONGS_ID= 1;
	public static final int INDEX_SONGS_TRACK= 2;
	public static final int INDEX_SONGS_TITLE= 3;
	public static final int INDEX_SONGS_ALBUM= 4;
	
	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASC = 2;
	public static final int ORDER_BY_DESC = 3;
	
	// INSERT INTO artists (name) VALUES(?)
	public static final String INSERT_ARTIST = "INSERT INTO "+TABLE_ARTISTS+" ("+FIELD_ARTISTS_NAME+") VALUES(?)";
	
	// INSERT INTO albums (name, artist) VALUES(?, ?)
	public static final String INSERT_ALBUMS = "INSERT INTO "+TABLE_ALBUMS+" ("+FIELD_ALBUMS_NAME+", "+FIELD_ALBUMS_ARTIST+") VALUES(?, ?)";
	
	// INSERT INTO songs (track, title, album) VALUES(?, ?, ?)
	public static final String INSERT_SONGS = "INSERT INTO "+TABLE_SONGS+" ("+FIELD_SONGS_TRACK+", "+FIELD_SONGS_TITLE+", "+FIELD_SONGS_ALBUM+") VALUES(?, ?, ?)";
	
	// SELECT _id FROM artists WHERE name = ?
	public static final String QUERY_ARTISTS = "SELECT "+FIELD_ARTISTS_ID+" FROM "+TABLE_ARTISTS+" WHERE "+FIELD_ARTISTS_NAME+" = ?";
	
	//SELECT _id FROM albums Where name = ?
	public static final String QUERY_ALBUMS = "SELECT "+FIELD_ALBUMS_ID+" FROM "+TABLE_ALBUMS+" WHERE "+FIELD_ALBUMS_NAME+" = ?";
	
	//UPDATE artists SET name = ? WHERE _id = ?
	public static final String UPDATE_ARTISTS_NAME = "UPDATE "+TABLE_ARTISTS+" SET "+FIELD_ARTISTS_NAME+" = ? WHERE "+FIELD_ARTISTS_ID+" = ?";
	
	//SELECT * FROM albums WHERE artist = ? ORDER BY name COLLATE NOCASE
	
	public static final String QUERY_ALBUMS_BY_ARTISTID = "SELECT * FROM "+TABLE_ALBUMS+" WHERE "+FIELD_ALBUMS_ARTIST+" = ? ORDER BY "+FIELD_ALBUMS_NAME+" COLLATE NOCASE";
	
	private Connection conn;
	
	private PreparedStatement insertIntoArtists;
	private PreparedStatement insertIntoAlbums;
	private PreparedStatement insertIntoSongs;
	
	private PreparedStatement queryArtists;
	private PreparedStatement queryAlbums;
	
	private PreparedStatement updateArtistsName;
	
	private PreparedStatement queryAlbumsByArtist;
	
	private static Datasource instance;
	
	private Datasource() {  //Singleton
		
	}
	
	public static Datasource getInstance() {
		if(instance == null) {
			instance = new Datasource();
		}
		return instance;
	}
	
	public boolean open(){
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			
			insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS); //  Statement.RETURN_GENERATED_KEYS will return keys of integer data type
			insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
			insertIntoSongs = conn.prepareStatement(INSERT_SONGS);
			
			queryArtists = conn.prepareStatement(QUERY_ARTISTS);
			queryAlbums = conn.prepareStatement(QUERY_ALBUMS);
			
			updateArtistsName = conn.prepareStatement(UPDATE_ARTISTS_NAME);
			
			queryAlbumsByArtist = conn.prepareStatement(QUERY_ALBUMS_BY_ARTISTID);
			
			return true;
			
		}catch(SQLException e) {
			System.out.println("Couldn't connect to the database "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public void close() {
		try {
			if(insertIntoArtists != null) {
				insertIntoArtists.close();
			}
			if(insertIntoAlbums != null) {
				insertIntoAlbums.close();
			}
			if(insertIntoSongs != null) {
				insertIntoSongs.close();
			}
			
			if(queryArtists != null) {
				queryArtists.close();
			}
			if(queryAlbums != null) {
				queryAlbums.close();
			}
			
			if(updateArtistsName!=null) {
				updateArtistsName.close();
			}
			
			if(queryAlbumsByArtist!=null) {
				queryAlbumsByArtist.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		}catch(SQLException e) {
			System.out.println("Couldn't close connection "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<Artists> queryArtists(int sortOrder){
		
		StringBuilder sb = new StringBuilder(" SELECT * FROM ");
		sb.append(TABLE_ARTISTS);
		if(sortOrder != ORDER_BY_NONE) {
			sb.append(" ORDER BY ");
			sb.append(FIELD_ARTISTS_NAME);
			sb.append( " COLLATE NOCASE ");
			if( sortOrder == ORDER_BY_DESC) {
				sb.append(" DESC ");
			}else {
				sb.append(" ASC ");
			}
		}
		
		try (Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery(sb.toString())){
			List<Artists> artistsList= new ArrayList<>();
			while (results.next()) {
				try {
					Thread.sleep(20);
				}catch(InterruptedException e) {
					System.out.println("Error ! Unable to Load : "+e.getMessage());
					e.printStackTrace();
				}
				Artists artists = new Artists();
				//artists.set_Id(results.getInt(FIELD_ARTISTS_ID));
				artists.set_Id(results.getInt(INDEX_ARTISTS_ID));
				//artists.setName(results.getString(FIELD_ARTISTS_NAME));
				artists.setName(results.getString(INDEX_ARTISTS_NAME));
				artistsList.add(artists);
			}
			return artistsList;
		}catch(SQLException e) {
			System.out.println("Error Occured "+e.getMessage());
			e.printStackTrace();
			return null;
		}
		/*finally {
			try {
				if(results !=null) {
					results.close();
				}
			}catch(SQLException e) {
				System.out.println("Error closing result set !! "+e.getMessage());
				e.printStackTrace();
			}
			try {
				if(statement != null) {
					statement.close();
				}
			}catch(SQLException e) {
				System.out.println("Error closing statement !! "+e.getMessage());
				e.printStackTrace();
			}
		}*/
	}
	
	public List<String> queryAlbumsForArtist(String artistName, int sortOrder){
		// SELECT albums.name FROM albums INNER JOIN artists ON albums.artist = artists._id WHERE artists.name = "Carole King" ORDER BY albums.name COLLATE NOCASE ASC
		StringBuilder sb = new StringBuilder("SELECT ");
		sb.append(TABLE_ALBUMS);
		sb.append(".");
		sb.append(FIELD_ALBUMS_NAME);
		sb.append(" FROM ");
		sb.append(TABLE_ALBUMS);
		sb.append(" INNER JOIN ");
		sb.append(TABLE_ARTISTS);
		sb.append(" ON ");
		sb.append(TABLE_ALBUMS);
		sb.append(".");
		sb.append(FIELD_ALBUMS_ARTIST);
		sb.append(" = ");
		sb.append(TABLE_ARTISTS);
		sb.append(".");
		sb.append(FIELD_ARTISTS_ID);
		sb.append(" WHERE ");
		sb.append(TABLE_ARTISTS);
		sb.append(".");
		sb.append(FIELD_ARTISTS_NAME);
		sb.append(" =  \"");
		sb.append(artistName);
		sb.append("\"");
		if(sortOrder != ORDER_BY_NONE) {
			sb.append(" ORDER BY ");
			sb.append(TABLE_ALBUMS);
			sb.append(".");
			sb.append(FIELD_ALBUMS_NAME);
			sb.append(" COLLATE NOCASE ");
			if(sortOrder == ORDER_BY_DESC) {
				sb.append(" DESC ");
			}else {
				sb.append(" ASC ");
			}
		}
		System.out.println(" SQL Statement : "+sb.toString());
		
		try(Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery(sb.toString())){
				List<String> albumList2 = new ArrayList<String>();
				while(results.next()) {
					Albums albums = new Albums();
					//albums.setName(results.getString(FIELD_ALBUMS_NAME));
					//albumList2.add(albums.getName()); 
					albumList2.add(results.getString(FIELD_ALBUMS_NAME));
				}
				return albumList2;
		}catch(SQLException e) {
			System.out.println("Error ! Unable to view data "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public void querySongsMetadata() {  //Not required for this project
		String query1 = "SELECT * FROM "+TABLE_SONGS;
		try(Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery(query1)){
			ResultSetMetaData meta =results.getMetaData();
			int numColumn = meta.getColumnCount();
			for(int i = 1; i<=numColumn; i++) {
				System.out.format("Column %d in the song table is %s\n",i,meta.getColumnName(i));
			}
		}catch(SQLException e) {
			System.out.println("Unable to perform : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int getCount(String tableName) {  //Not required for this project
		String query = "SELECT COUNT(*) AS count, MIN(_id) AS min_id FROM "+tableName;
		
		try(Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery(query)){
				int count = results.getInt("count");
				int min = results.getInt("min_id");
				System.out.format("Count : %d, Minimum value : %d\n", count,min);
				return count;
		}catch(SQLException e) {
			System.out.println("Oops something isn't right ! "+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<Albums> queryAlbumsByArtistsID(int artistID){

		try {
			queryAlbumsByArtist.setInt(1, artistID);
			ResultSet results = queryAlbumsByArtist.executeQuery();
			List<Albums> allAlbums = new ArrayList<Albums>();
			while(results.next()) {  //results.next returns boolean value
				Albums albums = new Albums();
				albums.setName(results.getString(2));
				albums.set_Id(results.getInt(1));
				albums.setArtist(results.getInt(3));
				allAlbums.add(albums);
			}
			return allAlbums;
		}catch(SQLException e) {
			System.out.println("Unable to load albums ! "+e.getMessage()+"\n");
			e.printStackTrace();
			return null;
		}
	}

	private int insertArtist(String artistName) throws SQLException {
		queryArtists.setString(1, artistName);
		ResultSet results = queryArtists.executeQuery();
		if(results.next()) {
			return results.getInt(1);
		}
		else {
			// So we need to Insert the artistName
			insertIntoArtists.setString(1, artistName);
			ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
			int affectedRows = insertIntoArtists.executeUpdate(); // preparedStatement.executeUpdate() returns keys which is of integer format
			if(affectedRows != 1) {
				throw new SQLException("Couldn't insert the artist name !");
			}
			if(generatedKeys.next()) {
				return generatedKeys.getInt(1);
			}else {
				throw new SQLException("Couldn't get _id for the artist !");
			}
		}
	}
	
	private int insertAlbum(String albumName, int artistID) throws SQLException {
		queryAlbums.setString(1, albumName);
		ResultSet results = queryAlbums.executeQuery();
		if(results.next()) {
			return results.getInt(1);
		}
		else {
			//Insert Into albums
			 insertIntoAlbums.setString(1, albumName);
			 insertIntoAlbums.setInt(2, artistID);
			 ResultSet generatedKeys =  insertIntoAlbums.getGeneratedKeys();
			 int affectedRows =  insertIntoAlbums.executeUpdate();
			 if(affectedRows != 1) {
				 throw new SQLException("Couldn't insert album !");
			 }
			 if(generatedKeys.next()) {
				 return generatedKeys.getInt(1);
			 }else {
				 throw new SQLException("Couldn't get_id for albums !");
			 }
		}
	}
	
	public boolean updateArtistName(String name, int _id) {
		try {
			updateArtistsName.setString(1, name);
			updateArtistsName.setInt(2, _id);
			int affectedRows = updateArtistsName.executeUpdate();
			return affectedRows == 1;
		}catch(SQLException e) {
			System.out.println("Error ! unable to update Artist name "+e.getMessage());
			return false;
		}
	}
	
	public void insertSong(String title, int track, String artistName, String albumName) {  //Not required for this project
		try {  								// Performing Transaction !!
			conn.setAutoCommit(false); // Turning autoCommit false means it doesn't save the changes in the database that have been done !  // Starting a new transaction 
			int artistID = insertArtist(artistName);
			int albumID = insertAlbum(albumName, artistID);
			insertIntoSongs.setInt(1,track);
			insertIntoSongs.setString(2,title);
			insertIntoSongs.setInt(3, albumID);
			int affectedRows = insertIntoSongs.executeUpdate();
			if(affectedRows == 1) {
				conn.commit();  //commit changes
			}else {
				throw new SQLException("Couldn't insert song !");
			}
			
		}catch(Exception e) {  // Here we'll put regular Exception instead of SQLException as we want to catch all Exceptions and not just SQLException , putting SQLException might cause the program to bypass the catch block and go to finally block !!
			System.out.println("Insert song exception : "+e.getMessage());
			try {
				System.out.println("Performing rollback !");
				conn.rollback();
			}catch (SQLException e2) {
				System.out.println("Something went wrong "+e2.getMessage());
			}
		}finally {
			try {
				System.out.println("Resetting default commit behavior !");
				conn.setAutoCommit(true);
			}catch(SQLException e) {
				System.out.println("Couldn't reset Auto-commit to true !");
				e.printStackTrace();
			}
		}
	}
}
