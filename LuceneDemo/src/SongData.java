import java.util.HashMap;

public class SongData {
	private HashMap<Integer, String> artist;
	private HashMap<Integer, String> songName;
	private HashMap<Integer, String> lyrics;

	public SongData(FileReader file) {
		artist = new HashMap<>();
		songName = new HashMap<>();
		lyrics = new HashMap<>();
		generateData(file);
	}
	
	private void generateDate(FileReader file) {
		
	}
	
	
}
