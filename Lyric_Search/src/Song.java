/** 
 * This class acts a a container for transferring song data
 * @author jon
 *
 */
public class Song {
	private String title;
	private String artist;
	private String album;
	private String lyrics;
	
	/**
	 * constructor
	 * @param title song title
	 * @param artist artist name
	 * @param album album name
	 * @param lyrics the lyrics
	 */
	public Song(String title, String artist, String album, String lyrics) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.lyrics = lyrics;
	}

	/**
	 * gets the song name
	 * @return the title of the song
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * gets the artist name
	 * @return the name of the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * gets the the title of the album
	 * @return the album name
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * gets the lyrics
	 * @return the lyrics
	 */
	public String getLyrics() {
		return lyrics;
	}
}
