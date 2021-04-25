package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\data\\training\\oss\\TimBuchalkaJavaCourse\\MusicUI\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final String COLUMN_SONG_ID = "_id";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUERY_ALBUMS_BY_ARTIST_START =
            "SELECT " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " FROM " + TABLE_ALBUMS +
                " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_ARTIST +
                " = " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_ID +
                " WHERE " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME + " LIKE " + '"';

    public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
            " ORDER BY " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTIST_BY_SONG_START =
            "SELECT " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + '.' + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + '.' + COLUMN_SONG_TITLE + " FROM " +
                    TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + '.' + COLUMN_SONG_ALBUM + " = " +
                    TABLE_ALBUMS + '.' + COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' +
                    COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_ID + " WHERE " + TABLE_SONGS + '.' +
                    COLUMN_SONG_TITLE + " LIKE " + '"';

    public static final String QUERY_ARTIST_BY_SONG_SORT =
            " ORDER BY " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME + " COLLATE NOCASE" +
            ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
    public static final String CREATE_ARTIST_FOR_SONG_VIEW =
            "CREATE VIEW IF NOT EXISTS " +
                    TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONG_ALBUM + ", " +
                    TABLE_SONGS + '.' + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + '.' + COLUMN_SONG_TITLE + " FROM " +
                    TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + '.' + COLUMN_SONG_ALBUM + " = " +
                    TABLE_ALBUMS + '.' + COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " +
                    TABLE_ALBUMS + '.' + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_ID +
                    " ORDER BY " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + '.' + COLUMN_SONG_TRACK;

    public static final String QUERY_VIEW_SONG_INFO =
            "SELECT  " + COLUMN_ARTIST_NAME + ", " + COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + " FROM " +
                    TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONG_TITLE + " LIKE \"";

    public static final String QUERY_VIEW_SONG_INFO_PREP =
            "SELECT  " + COLUMN_ARTIST_NAME + ", " + COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + " FROM " +
                    TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONG_TITLE + " = ?";

    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS + '(' + COLUMN_ARTIST_NAME + ") VALUES(?)";
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS + '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST +
                                                    ") VALUES(?, ?)";
    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS + '(' + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE +
                                                ", " + COLUMN_SONG_ALBUM + ") VALUES(?, ?, ?)";

    public static final String QUERY_ARTISTS = "SELECT " + COLUMN_ARTIST_ID + " FROM " + TABLE_ARTISTS + " WHERE " +
                                                    COLUMN_ARTIST_NAME + " = ?";
    public static final String QUERY_ALBUMS = "SELECT " + COLUMN_ALBUM_ID + " FROM " + TABLE_ALBUMS + " WHERE " +
                                                    COLUMN_ALBUM_NAME + " = ?";
    public static final String QUERY_SONGS = "SELECT " + COLUMN_SONG_ID + " FROM " + TABLE_SONGS + " WHERE " +
            COLUMN_SONG_TITLE + " = ?" + " AND " + COLUMN_SONG_ALBUM + " = ?";


    private Connection conn;
    private PreparedStatement querySongInfoView;
    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;
    private PreparedStatement queryArtists;
    private PreparedStatement queryAlbums;
    private PreparedStatement querySongs;

    private static Datasource instance = new Datasource();

    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            querySongInfoView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
            insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = conn.prepareStatement(INSERT_SONGS);
            queryArtists = conn.prepareStatement(QUERY_ARTISTS);
            queryAlbums = conn.prepareStatement(QUERY_ALBUMS);
            querySongs = conn.prepareStatement(QUERY_SONGS);

            return true;
        } catch(SQLException e) {
            System.out.println("Couldn't connect to the database " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if(querySongInfoView != null) {
                querySongInfoView.close();
            }
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
            if(querySongs != null) {
                querySongs.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("There was a problem in closing the connection to database " + e.getMessage());
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_ASC) {
                sb.append("ASC");
            } else if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            }
        }

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sb.toString());

            List<Artist> artists = new ArrayList<>();
            while (result.next()) {
                Artist artist = new Artist();
                artist.setId(result.getInt(INDEX_ARTIST_ID));
                artist.setName(result.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                //What can you do
                System.out.println("failed to close the result set " + e.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                //What can you do
                System.out.println("failed to close the statement " + e.getMessage());
            }
        }
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {
        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
        sb.append(artistName + '"');
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
            if (sortOrder == ORDER_BY_ASC) {
                sb.append("ASC");
            } else if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            }
        }
        System.out.println(sb.toString());
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sb.toString());

            List<String> albums = new ArrayList<>();
            while (result.next()) {
                String album = result.getString(1);
                albums.add(album);
            }

            return albums;

        } catch (SQLException e) {
            System.out.println("Query failed " + e.getMessage());
            return null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                //What can you do
                System.out.println("failed to close the result set " + e.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                //What can you do
                System.out.println("failed to close the statement " + e.getMessage());
            }
        }
    }

//    public List<SongArtist> queryArtistBySong(String songTitle, int sortOrder) {
//        StringBuilder sb = new StringBuilder(QUERY_ARTIST_BY_SONG_START);
//        sb.append(songTitle);
//        sb.append('"');
//        if (sortOrder != ORDER_BY_NONE) {
//            sb.append(QUERY_ARTIST_BY_SONG_SORT);
//            if (sortOrder == ORDER_BY_ASC) {
//                sb.append("ASC");
//            } else if (sortOrder == ORDER_BY_DESC) {
//                sb.append("DESC");
//            }
//        }
//        System.out.println(sb.toString());
//        Statement statement = null;
//        ResultSet result = null;
//        try {
//            statement = conn.createStatement();
//            result = statement.executeQuery(sb.toString());
//
//            List<SongArtist> songArtists = new ArrayList<>();
//            while (result.next()) {
//                SongArtist songArtist = new SongArtist();
//                songArtist.setArtistName(result.getString(1));
//                songArtist.setAlbumName(result.getString(2));
//                songArtist.setTrack(result.getInt(3));
//                songArtist.setTitle(result.getString(4));
//                songArtists.add(songArtist);
//            }
//
//            return songArtists;
//
//        } catch (SQLException e) {
//            System.out.println("Query failed " + e.getMessage());
//            return null;
//        } finally {
//            try {
//                if (result != null) {
//                    result.close();
//                }
//            } catch (SQLException e) {
//                //What can you do
//                System.out.println("failed to close the result set " + e.getMessage());
//            }
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                //What can you do
//                System.out.println("failed to close the statement " + e.getMessage());
//            }
//        }
//    }

    public void queryMetadata(String table) {
        String sql = "SELECT * FROM " + table;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();

            for (int i = 1; i <= numColumns; i++) {
                System.out.format("Column %d in the %s table is named %s\n", i, table, meta.getColumnName(i));
            }

        } catch (SQLException e) {
            System.out.println("Query failed for songs metadata: " + e.getMessage());
        }
    }

    public int getCount(String table) {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
             return results.getInt(1);
        } catch (SQLException e) {
            System.out.println("Query failed for getting number of rows in " + table + ": " + e.getMessage());
            return -1;
        }
    }

    public boolean createViewForSongArtists() {

        try(Statement statement = conn.createStatement()) {
            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
            return true;

        } catch (SQLException e) {
            System.out.println("View creation failed: " + e.getMessage());
            return false;
        }
    }

//    public List<SongArtist> querySongInfoView(String title) {
//
//        try {
//            querySongInfoView.setString(1, title);
//            ResultSet results = querySongInfoView.executeQuery();
//            List<SongArtist> songArtists = new ArrayList<>();
//
//            while (results.next()) {
//                SongArtist songArtist = new SongArtist();
//                songArtist.setArtistName(results.getString(1));
//                songArtist.setAlbumName(results.getString(2));
//                songArtist.setTrack(results.getInt(3));
//                songArtist.setTitle(results.getString(4));
//
//                songArtists.add(songArtist);
//            }
//
//            return songArtists;
//
//        } catch (SQLException e) {
//            System.out.println("query failed: " + e.getMessage());
//            return null;
//        }
//    }

    private int insertArtist(String name) throws SQLException {
        queryArtists.setString(1, name);
        ResultSet results = queryArtists.executeQuery();
        if(results.next()) {
            return results.getInt(1);
        } else {
            insertIntoArtists.setString(1, name);
            int affectedRows = insertIntoArtists.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert artist!");
            }

            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for artist");
            }
        }
    }

    private int insertAlbums(String name, int artistId) throws SQLException {
        queryAlbums.setString(1, name);
        ResultSet results = queryAlbums.executeQuery();
        if(results.next()) {
            return results.getInt(1);
        } else {
            insertIntoAlbums.setString(1, name);
            insertIntoAlbums.setInt(2, artistId);
            int affectedRows = insertIntoAlbums.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert album!");
            }

            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
            if(generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for album");
            }
        }
    }

    public void insertSong(String title, String artist, String album, int track) {
        try {
            conn.setAutoCommit(false);
            int artistId = insertArtist(artist);
            int albumId = insertAlbums(album, artistId);
            querySongs.setString(1, title);
            querySongs.setInt(2, albumId);
            ResultSet results = querySongs.executeQuery();

            if (!results.next()) {
                insertIntoSongs.setInt(1, track);
                insertIntoSongs.setString(2, title);
                insertIntoSongs.setInt(3, albumId);

                int affectedRows = insertIntoSongs.executeUpdate();
                if (affectedRows == 1) {
                    System.out.println("we were successfully in inserting the song");
                    conn.commit();
                } else {
                    throw new SQLException("Song insert failed.");
                }
            } else {
                throw new SQLException("Song entry already exists.");
            }

        } catch (Exception e) {
            System.out.println("Insert song exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("WTF!!!! What is your problem , you stupid language. (Probably my mistake anyway): " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Unable to reset default commit behavior-: " + e.getMessage());
            }
        }
    }
}
