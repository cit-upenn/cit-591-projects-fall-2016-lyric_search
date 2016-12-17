import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	Stage thestage;
	Scene scene1; 
	Scene scene2;
	Button btn1;
	Button btn2;
	TextField searchBar;
	String lyrics;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			thestage = primaryStage;
			BorderPane root = new BorderPane();
			
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			Text scenetitle = new Text("Lyric Search");
			scenetitle.setId("title-text");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			grid.add(scenetitle, 1, 0, 1, 1);
			
//			grid.setGridLinesVisible(true);
			
			Label search = new Label("Enter Lyrics:");
			grid.add(search, 0, 2);

			searchBar = new TextField();
			grid.add(searchBar, 1, 2, 6, 1);
			
			btn1 = new Button("Search");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn1);
			grid.add(hbBtn, 6, 3);
			
			btn1.setOnAction(e-> ButtonClicked(e));
			
			root.setTop(grid);
			
			scene1 = new Scene(root,650,600);
			scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			BorderPane root2 = new BorderPane();
			GridPane grid2 = new GridPane();
			grid2.setAlignment(Pos.CENTER);
			grid2.setHgap(10);
			grid2.setVgap(10);
			grid2.setPadding(new Insets(25, 25, 25, 25));
			
			Text scenetitle2 = new Text("Lyric Search");
			scenetitle2.setId("title-text");
			scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
			grid2.add(scenetitle2, 1, 0, 1, 1);
			
//			grid2.setGridLinesVisible(true);
			
			Label song = new Label("Song:");
			grid2.add(song, 0, 2);
			Label artist = new Label("Artist:");
			grid2.add(artist, 0, 3);
			Label album = new Label("Album:");
			grid2.add(album, 0, 4);
			
			btn2 = new Button("Return");
			HBox hbBtn2 = new HBox(10);
			hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn2.getChildren().add(btn2);
			grid2.add(hbBtn2, 6, 5);
			
			btn2.setOnAction(e-> ButtonClicked(e));
			
			root2.setTop(grid2);
			
			WebView webview = new WebView();
			webview.getEngine().load(
				"https://www.youtube.com/embed/7yDmGnA8Hw0"
			);
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
	
	public void ButtonClicked(ActionEvent e)
	{
		if (e.getSource() == btn1){
			lyrics = searchBar.getText();
			System.out.println(lyrics);
			thestage.setScene(scene2);
		}
		else
			thestage.setScene(scene1);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}