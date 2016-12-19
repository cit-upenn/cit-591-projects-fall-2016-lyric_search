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
	public void start(Stage primaryStage) {
		try {
			thestage = primaryStage;
			BorderPane root = new BorderPane();
			
			grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			Text sceneTitle = new Text("Lyric Search");
			sceneTitle.setId("title-text");
			sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			grid.add(sceneTitle, 1, 0, 1, 1);
			
//			grid.setGridLinesVisible(true);
			
			Label search = new Label("Enter Lyrics:");
			grid.add(search, 0, 2);

			searchBar = new TextField();
			grid.add(searchBar, 1, 2, 6, 1);
			
			error = new Text("");
			grid.add(error, 1, 3);
			
			btn1 = new Button("Search");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn1);
			grid.add(hbBtn, 6, 3);
			
			btn1.setOnAction(e-> {
				try {
					ButtonClicked(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			searchBar.setOnKeyPressed(keyEvent -> handle(keyEvent));
			
			root.setTop(grid);
			
			scene1 = new Scene(root,650,600);
			scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			BorderPane root2 = new BorderPane();
			GridPane grid2 = new GridPane();
			grid2.setAlignment(Pos.CENTER);
			grid2.setHgap(10);
			grid2.setVgap(10);
			grid2.setPadding(new Insets(25, 25, 25, 25));
			
			Text sceneTitle2 = new Text("Lyric Search");
			sceneTitle2.setId("title-text");
			sceneTitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			grid2.add(sceneTitle2, 1, 0, 1, 1);
			
//			grid2.setGridLinesVisible(true);
			
			Label song = new Label("Song:");
			grid2.add(song, 0, 2);
			
			songTitle = new Text("");
			grid2.add(songTitle, 1, 2);
			songTitle.setId("result-text");
//			songTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			
			Label artist = new Label("Artist:");
			grid2.add(artist, 0, 3);
			
			artistTitle = new Text("");
			grid2.add(artistTitle, 1, 3);
			artistTitle.setId("result-text");
//			artistTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			
			Label album = new Label("Album:");
			grid2.add(album, 0, 4);
			
			albumTitle = new Text("");
			grid2.add(albumTitle, 1, 4);
			albumTitle.setId("result-text");
//			albumTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			
			btn2 = new Button("Return");
			HBox hbBtn2 = new HBox(10);
			hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn2.getChildren().add(btn2);
			grid2.add(hbBtn2, 6, 5);
			
			btn2.setOnAction(e-> {
				try {
					ButtonClicked(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			
			root2.setTop(grid2);
			
			webview = new WebView();

			webview.setPrefSize(480, 360);
			
			
			root2.setBottom(webview);
			
			scene2 = new Scene(root2,650,600);
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handle(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER)  {
			btn1.fire();
			searchBar.setText("");
		}		
	}
	
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
	
	
	public static void main(String[] args) throws IOException, ParseException {
		launch(args);
	}
}