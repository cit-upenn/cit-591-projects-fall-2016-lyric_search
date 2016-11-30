import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	private String fileName;
	private ArrayList<String> lines;
	
	/**
	 * Constructor.
	 * Initialize the instance variables and run importFile method.
	 * @param fileName the location of the file to be imported.
	 */
	public FileReader(String fileName) {
		this.fileName = fileName;
		lines = new ArrayList<String>();
		importFile();
		/* trying to read the lines of text for tom-sawyer from here generates an error where the stoplist is read in along with a portion of tom-sawyer.txt
//		for (int i = 0; i < lines.size(); i++) {
//			System.out.println(lines.get(i));
		} 
		*/
	}
	
	/**
	 * Takes each line of the file and add each line to the ArrayList of lines.
	 */
	private void importFile() {
		try {
			File inputFile = new File(fileName);
			Scanner in = new Scanner(inputFile);
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				lines.add(line);
			}
			in.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found. Please use another file and run again.");
		}
	}
	
	/** 
	 * Gets the ArrayList of the lines of the file
	 * @return the lines of the file
	 */
	public ArrayList<String> getFile() {
		return lines;
	}
	
	public String getFileName() {
		return fileName;
	}
}