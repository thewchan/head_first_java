import java.util.*;
import java.io.*;

class Song implements Comparable<Song>{

    String title;
    String artist;
    String rating;
    String bpm;

    Song(String t, String a, String r, String b) {
        title = t;
        artist = a;
        rating = r;
        bpm = b;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getRating() {
        return rating;
    }

    public String getBpm() {
        return bpm;
    }

    public String toString() {
        return title;
    }

    public int compareTo(Song s) {
        return title.compareTo(s.getTitle());
    }

    public boolean equals(Object aSong) {
        Song s = (Song) aSong;
        return getTitle().equals(s.getTitle());
    }

    public int hashCode() {
        return title.hashCode();
    }
}

public class Jukebox4 {

    ArrayList<Song> songList = new ArrayList<Song>();

    class ArtistCompare implements Comparator<Song> {

        public int compare(Song one, Song two) {
            return one.getArtist().compareTo(two.getArtist());
        }
    }

    void addSong(String lineToParse) {
        String[] tokens = lineToParse.split("/");
        Song nextSong = new Song(tokens[0], tokens[1], tokens[2], tokens[3]);
        songList.add(nextSong);
    }

    void getSongs() {

        try {
            File file = new File("SongListMore1.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = reader.readLine()) != null) {
                addSong(line);
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void go() {
        ArtistCompare artistCompare = new ArtistCompare();
        HashSet<Song> songSet = new HashSet<Song>();

        getSongs();
        System.out.println(songList);
        Collections.sort(songList);
        System.out.println(songList);
        Collections.sort(songList, artistCompare);
        songSet.addAll(songList);
        System.out.println(songSet);
    }

    public static void main(String[] args) {
        new Jukebox4().go();
    }
}