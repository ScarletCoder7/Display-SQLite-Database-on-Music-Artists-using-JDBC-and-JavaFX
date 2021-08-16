module rims.musicArtistList.ui {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    //requires rims.musicArtistList.common; // Since requires transitive rims.musicArtistList.common; in db module so in ui module we don't need this line anymore
    requires rims.musicArtistList.db;

    exports rims.musicArtistList.ui to javafx.graphics;
    opens rims.musicArtistList.ui to javafx.fxml;
}