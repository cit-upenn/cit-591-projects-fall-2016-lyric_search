import java.io.IOException;
import java.util.HashMap;

import org.apache.lucene.queryparser.classic.ParseException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
/**
 * This class executes the GUI for Lyrics Search Program. 
 * It consists of two scenes, one where the user enters lyrics to search for and the second
 * displays the information of the search result.
 * @author Saurav, Sameer, Jon
 *
 */
public class GUI extends Application {
	private Stage thestage;
	private Scene scene1; 
	private Scene scene2;
	private Button btn1;
	private Button btn2;
	private TextField searchBar;
	private String lyrics;
	private HashMap<Integer, Song> container;
	private Text songTitle;
	private Text artistTitle;
	private Text albumTitle;
	private Lucene_Search search;
	private YoutubeAPICaller yt;
	private String videoEmbedLink;
	private WebView webview;
	private Text error;
	private GridPane grid;
	
	
	@Override
	/**
	 * Creates the stage (essentially the template of the GUI)
	 */
	public void start(Stage primaryStage) {
		try {
			thestage = primaryStage;
			BorderPane root = new BorderPane();
			
			//Creates a grid, which will contain the components of first scene (section) of GUI 
			grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			//Adds title of program
			Text sceneTitle = new Text("Lyric Search");
			sceneTitle.setId("title-text");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			grid.add(sceneTitle, 1, 0, 1, 1);
			
//			grid.setGridLinesVisible(true);
			
			//Adds label of search bar
			Label search = new Label("Enter Lyrics:");
			grid.add(search, 0, 2);

			//Adds search bar to enter text into
			searchBar = new TextField();
			grid.add(searchBar, 1, 2, 6, 1);
			
			//Adds text field which will be used to display error messages
			error = new Text("");
			grid.add(error, 1, 3);
			
			//Adds search button
			btn1 = new Button("Search");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn1);
			grid.add(hbBtn, 6, 3);
			
			//Adds functionality to search button
			btn1.setOnAction(e-> {
				try {
					ButtonClicked(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			//Creates event that registers keyboard input
			searchBar.setOnKeyPressed(keyEvent -> handle(keyEvent));
			
			//Adds grid to BorderPane
			root.setTop(grid);
			
			//Creates scene with BorderPane
			scene1 = new Scene(root,650,600);
			scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			BorderPane root2 = new BorderPane();
			
			//Creates a grid, which will contain the components of second scene (section) of GUI 
			GridPane grid2 = new GridPane();
			grid2.setAlignment(Pos.CENTER);
			grid2.setHgap(10);
			grid2.setVgap(10);
			grid2.setPadding(new Insets(25, 25, 25, 25));
			
			//Adds title of program
			Text sceneTitle2 = new Text("Lyric Search");
			sceneTitle2.setId("title-text");
			sceneTitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			grid2.add(sceneTitle2, 1, 0, 1, 1);
			
//			grid2.setGridLinesVisible(true);
			
			//Adds label of song text field
			Label song = new Label("Song:");
			grid2.add(song, 0, 2);
			
			//Adds text of song title search result
			songTitle = new Text("");
			grid2.add(songTitle, 1, 2);
			songTitle.setId("result-text");
//			songTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			
			//Adds label of artist text field
			Label artist = new Label("Artist:");
			grid2.add(artist, 0, 3);
			
			//Adds text of song artist search result
			artistTitle = new Text("");
			grid2.add(artistTitle, 1, 3);
			artistTitle.setId("result-text");
//			artistTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			
			//Adds label of album text field
			Label album = new Label("Album:");
			grid2.add(album, 0, 4);
			
			//Adds text of song album search result
			albumTitle = new Text("");
			grid2.add(albumTitle, 1, 4);
			albumTitle.setId("result-text");
//			albumTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			
			//Adds return button
			btn2 = new Button("Return");
			HBox hbBtn2 = new HBox(10);
			hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn2.getChildren().add(btn2);
			grid2.add(hbBtn2, 6, 5);
			
			//Adds functionality to return button
			btn2.setOnAction(e-> {
				try {
					ButtonClicked(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			//Adds grid2 to second BorderPane
			root2.setTop(grid2);
			
			//Adds webview which is used to play youtube videos
			webview = new WebView();
			webview.setPrefSize(480, 360);
			
			//Adds webview to second BorderPane
			root2.setBottom(webview);
			
			//Creates scene with second BorderPane
			scene2 = new Scene(root2,650,600);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Initiates stage with first scene
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method allows search to be initiated with enter key as well as button click
	 * @param keyEvent Event representing keyboard key press, specifically ENTER key
	 */
	public void handle(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER)  {
			btn1.fire();
			searchBar.setText("");
		}		
	}
	
	/**
	 * This method initiates search process once button is clicked. Search is conducted using 
	 * Lucene Search and results are displayed in transitioned scene.
	 * Error message printed if no search entered or no search results found.
	 * @param e Action on button 1
	 * @throws IOException
	 */
	public void ButtonClicked(ActionEvent e) throws IOException
	{
		if (e.getSource() == btn1){
			
			search = new Lucene_Search();
			yt = new YoutubeAPICaller();
			lyrics = searchBar.getText();
			
			try {
				container = search.search(lyrics);
			} catch (ParseException e2) {
				error.setText("No search entered");
				System.out.println("No search entered");
				return;
			}
			
			if(!container.isEmpty()){
				songTitle.setText(container.get(0).getTitle()) ;
				artistTitle.setText(container.get(0).getArtist());
				albumTitle.setText(container.get(0).getAlbum());
				
				try {
					videoEmbedLink = yt.getYoutubeID(container.get(0).getTitle(), container.get(0).getArtist());
				} catch (Exception e1) {
					System.out.println("No youtube link found");
				} 
				
				webview.getEngine().load( 
						videoEmbedLink
					);
				
				thestage.setScene(scene2);
			}
			else{
				error.setText("Search Not Found");
			}
		
		}
		else{
			error.setText("");
			thestage.setScene(scene1);
		}
	}
	
	/**
	 * Main that runs entire GUI
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		launch(args);
	}
}