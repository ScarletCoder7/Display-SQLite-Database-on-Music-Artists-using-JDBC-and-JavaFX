module rims.musicArtistList.db {
    requires java.sql;
    requires sqlite.jdbc;
    requires transitive rims.musicArtistList.common;  
    exports rims.musicArtistList.db;
}
