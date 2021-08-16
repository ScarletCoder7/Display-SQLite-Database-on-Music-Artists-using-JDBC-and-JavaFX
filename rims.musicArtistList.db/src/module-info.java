module rims.musicArtistList.db {
    requires java.sql;
    requires sqlite.jdbc;
    requires transitive rims.musicArtistList.common;  // So the ui module can have access to all export packages and ui module will not be required the common module
    exports rims.musicArtistList.db;
}