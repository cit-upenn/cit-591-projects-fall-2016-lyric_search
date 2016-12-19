package lucene_index;
import java.util.HashMap;
import java.util.Set;
/**
 * This class will store the song data
 * @author jon
 *
 */
public class SongData {
	private HashMap<Integer, String> artist;
	private HashMap<Integer, String> album;
	private HashMap<Integer, String> title;
	private HashMap<Integer, String> lyrics;

	/** constructor
	 * 
	 * @param file The file read from the FileReader
	 */
	public SongData(FileReader file) {
		artist = new HashMap<>();
		album = new HashMap<>();
		title = new HashMap<>();
		lyrics = new HashMap<>();
		System.out.println("lines in file: " + file.getFile().size());
		generateData(file);
		System.out.println("lines in file: " + file.getFile().size());
		System.out.println("artist hashmap size: " + artist.size());
	}

	/**
	 * Creates HashMaps for the songdata, storing artist, album, title, lyrics
	 * 
	 * @param file the song file from the FileReader
	 */
	private void generateData(FileReader file) {
		// think about how to deal with this if the line is an empty line?
		// what about if the file is not formatted properly?
//		int marker = 0;
		for (int i = 0; i < file.getFile().size(); i++) {
			if (file.getFile().get(i).contains("|")) {
				String[] temp = file.getFile().get(i).split("\\|");
				artist.put(i, temp[0].trim());
				album.put(i, temp[1].trim());
				title.put(i, temp[2].trim());
				lyrics.put(i, temp[3].trim());
//				marker = i;
//			} else if (!file.getFile().get(i).contains("**")) {
//				if (file.getFile().get(i).equals("")) {
//					lyrics.put(marker, lyrics.get(marker) + "\n");
//				} else {
//					lyrics.put(marker, lyrics.get(marker) + (file.getFile().get(i)) + "\n");
//				}
			}
		}
	}
	/**
	 * gets the artist
	 * @param key the key mapping to the artist
	 * @return the artist
	 */
	public String getArtist(int key) {
		return artist.get(key);
	}

	/**
	 * gets the album 
	 * @param key the key mapping to the album
	 * @return the album
	 */
	public String getAlbum(int key) {
		return album.get(key);
	}

	/**
	 * gets the title 
	 * @param key the key mapping to the title
	 * @return the title
	 */
	public String getTitle(int key) {
		return title.get(key);
	}

	/** 
	 * gets the lyrics
	 * @param key the key mapping to the lyrics
	 * @return the lyrics
	 */
	public String getLyrics(int key) {
		return lyrics.get(key);
	}

	
	/**
	 * Gets the keyset to titles.
	 * Helps to determine how many iterations when indexing the files with lucene
	 * @return the keyset
	 */
	public Set<Integer> getKeySet() {
		return title.keySet();
	}

	public HashMap<Integer, String> getArtistMap() {
		return artist;
	}

	public HashMap<Integer, String> getAlbumMap() {
		return album;
	}

	public HashMap<Integer, String> getTitleMap() {
		return title;
	}

	public HashMap<Integer, String> getLyricsMap() {
		return lyrics;
	}
}
