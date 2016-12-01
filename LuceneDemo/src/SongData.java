import java.util.HashMap;
import java.util.Set;

public class SongData {
	private HashMap<Integer, String> artist;
	private HashMap<Integer, String> album;
	private HashMap<Integer, String> title;
	private HashMap<Integer, String> lyrics;

	public SongData(FileReader file) {
		artist = new HashMap<>();
		album = new HashMap<>();
		title = new HashMap<>();
		lyrics = new HashMap<>();
		generateData(file);
		// System.out.println("artists: " + artist);
		// System.out.println("album: " + album);
		// System.out.println("title: " + title);
		// System.out.println("lyrics: " + lyrics);
	}

	private void generateData(FileReader file) {
		// think about how to deal with this if the line is an empty line?
		// what about if the file is not formatted properly?
		int marker = 0;

		for (int i = 0; i < file.getFile().size(); i++) {
			if (file.getFile().get(i).contains("|")) {
				String[] temp = file.getFile().get(i).split("\\|");
				/*
				 * code for old way of formatting the lyrics
				 * if it's an empty line skip this line if (temp.length == 1) {
				 * continue; }
				 * 
				 */
				artist.put(i, temp[0].trim());
				album.put(i, temp[1].trim());
				title.put(i, temp[2].trim());
				lyrics.put(i, temp[3].trim());
				marker = i;
			} else if (!file.getFile().get(i).contains("**")) {
				if (file.getFile().get(i).equals("")) {
					lyrics.put(marker, lyrics.get(marker) + "\n");
				} else {
					lyrics.put(marker, lyrics.get(marker) + (file.getFile().get(i)) + "\n");
				}
			}
		}
	}

	public String getArtist(int key) {
		return artist.get(key);
	}

	public String getAlbum(int key) {
		return album.get(key);
	}

	public String getTitle(int key) {
		return title.get(key);
	}

	public String getLyrics(int key) {
		return lyrics.get(key);
	}

	public Set<Integer> getKeySet() {
		return title.keySet();
	}
}
