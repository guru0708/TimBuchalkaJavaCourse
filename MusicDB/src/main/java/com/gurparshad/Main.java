package com.gurparshad;

import com.gurparshad.model.Artist;
import com.gurparshad.model.Datasource;
import com.gurparshad.model.SongArtist;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Datasource datasource = new Datasource();

        if(!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

//        List<Artist> artists = datasource.queryArtists(Datasource.ORDER_BY_ASC);
//
//        if (artists == null) {
//            System.out.println("No artists");
//            return;
//        }
//        for(Artist artist : artists) {
//            System.out.println(artist.getName());
//        }

//        List<String> albums = datasource.queryAlbumsForArtist("%carole%", Datasource.ORDER_BY_ASC);
//
//        if (albums == null) {
//            System.out.println("No corresponding albums found");
//            return;
//        }
//
//        for(String album : albums) {
//            System.out.println("Album name: " + album);
//        }

//        List<SongArtist> songArtists = datasource.queryArtistBySong("%death%", Datasource.ORDER_BY_ASC);
//
//        if(songArtists == null) {
//            System.out.println("No corresponding song found");
//            return;
//        }
//
//        for(SongArtist songArtist : songArtists) {
//            System.out.println("Artist: " + songArtist.getArtistName() + " , Album: " + songArtist.getAlbumName() +
//                    " , Track: " + songArtist.getTrack() + " , Title: " + songArtist.getTitle());
//        }

//        datasource.queryMetadata("albums");
//
//        System.out.println("Number of songs in the database: " + datasource.getCount("songs"));

//        datasource.createViewForSongArtists();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please input your query title-: ");

        String query = scanner.nextLine();

        List<SongArtist> songArtists = datasource.querySongInfoView(query);

        if(songArtists == null) {
            System.out.println("No corresponding song found");
            return;
        }

        for(SongArtist artist : songArtists) {
            System.out.println("Artist: " + artist.getArtistName() + " , Album: " + artist.getAlbumName() +
                    " , Track: " + artist.getTrack() + " , Title: " + artist.getTitle());
        }

        datasource.close();
    }
}
