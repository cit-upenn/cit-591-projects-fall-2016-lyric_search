package lucene_index;
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
			System.out.println("File not found. Program closing. Please use another file and run again.");
			System.exit(1);
		}
	}
	
	/** 
	 * Gets the ArrayList of the lines of the file
	 * @return the lines of the file
	 */
	public ArrayList<String> getFile() {
		return lines;
	}
}