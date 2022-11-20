module rims.musicArtistList.ui {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    
    requires rims.musicArtistList.db;

    exports rims.musicArtistList.ui to javafx.graphics;
    opens rims.musicArtistList.ui to javafx.fxml;
}
