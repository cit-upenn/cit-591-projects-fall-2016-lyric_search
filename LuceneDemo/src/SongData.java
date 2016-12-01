import java.util.HashMap;

public class SongData {
	private HashMap<Integer, String> artist;
	private HashMap<Integer, String> album;
	private HashMap<Integer, String> title;
	private HashMap<Integer, String> lyrics;
	private int dataSize;
	public SongData(FileReader file) {
		artist = new HashMap<>();
		album = new HashMap<>();
		title = new HashMap<>();
		lyrics = new HashMap<>();
		generateData(file);
		dataSize = lyrics.size();
//		System.out.println("artists: " + artist);
//		System.out.println("album: " + album);
//		System.out.println("title: " + title);
//		System.out.println("lyrics: " + lyrics);
	}

	private void generateData(FileReader file) {
		// think about how to deal with this if the line is an empty line?
		// what about if the file is not formatted properly? 
		for (int i = 0; i < file.getFile().size(); i++) {
			String[] temp = file.getFile().get(i).split("\\|");
			// if it's an empty line skip this line
			if (temp.length == 1) {
				continue;
			}
			artist.put(i, temp[0]);
			album.put(i, temp[1]);
			title.put(i, temp[2]);
			lyrics.put(i, temp[3]);
		}
	}
	
	public String getArtist(int index) {
		return artist.get(index);
	}

	public String getAlbum(int index) {
		return album.get(index);
	}

	public String getTitle(int index) {
		return title.get(index);
	}

	public String getLyrics(int index) {
		return lyrics.get(index);
	}
	public int getDataSize() {
		return dataSize;
	}
}
