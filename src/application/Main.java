package application;
	

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,425,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.getStyleClass().add("root");
			init(root);
			primaryStage.setTitle("Compteur de clique");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("nuages.jpg")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private int compteur = 0;
	private final int tempsInitial = 10; // Changer cette valeur pour changer le temps restant
	private int temps = tempsInitial; 
	private Timeline timeline; // Aucune idee, mais mettre cette variable ici marche !
	private boolean notClicked = true;
	private boolean finish = false;

	
	private void init(BorderPane root) {
		Label lbl = new Label("Nombre de clique : " + compteur);
		lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lbl.getStyleClass().add("lbl");
		
		Label lbl2 = new Label("Nombre de seconde(s) restante(s) : " + temps);
		lbl2.getStyleClass().addAll("lbl2");
		BorderPane.setMargin(lbl2, new Insets(10,10,0,10));
		
		VBox bas = new VBox();
		
		Button btn1= new Button("Reset");
		btn1.getStyleClass().addAll("btn1");
		VBox.setMargin(btn1, new Insets(10,10,0,10));
		
		Button btn = new Button("Cliquez !");
		btn.getStyleClass().addAll("btn", "bttn");
		btn.setMinHeight(200);
		btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		VBox.setMargin(btn, new Insets(10,10,10,10));
		
		bas.getChildren().addAll(btn1, btn);
		
		// Action du bouton reset
		btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				MouseButton button = me.getButton();
				if (button == MouseButton.PRIMARY && notClicked == false) {
					compteur = 0;
					lbl.setText("Nombre de clique : " + compteur);
					temps = tempsInitial;
					lbl2.setText("Nombre de seconde(s) restante(s) : " + temps);
					notClicked = true;
					finish = false;
					timeline.stop();
				}
			}
		});
		
		// Action du bouton de clique
		btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle (MouseEvent event) {
				// Compteur
				MouseButton button = event.getButton();
				if (button == MouseButton.PRIMARY && finish == false) {  // Seul le clique gauche incrementera le compteur
					compteur++;                                          // Et que ce n'est pas fini
					lbl.setText("Nombre de cliques : " + compteur);
				}
				
				if (temps <= 0) {
					finish = true;
					lbl.setText("Score(s) : " + compteur);
				}
				
				// Temps
				if (button == MouseButton.PRIMARY && notClicked == true) {
					if (timeline != null)
			            timeline.stop();

			    // Mis a jour du decompte
			    timeline = new Timeline();
			    timeline.setCycleCount(Timeline.INDEFINITE);
			    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), 
			    	new EventHandler<ActionEvent>() {
			    		public void handle(ActionEvent event) {
			    			temps--;
			                lbl2.setText("Nombre de seconde(s) restante(s) : " + temps);
			                if (temps == 0 || temps == tempsInitial)
			                	timeline.stop();
			            }
			        }));
			    
			    timeline.playFromStart();
			        
			    /* POURQUOI CA MARCHE ????????????!!!!!!!!!!!
			    POURQUOI QUAND JE CLIQUE SUR LE BOUTON CA NE S'ARRETE PLUS ????????
			    ALORS QU'AVANT SI !!!!!!!*/
			    notClicked = false; 
				}
			}
		});
		
		
		root.setTop(lbl2);
		root.setCenter(lbl);
		root.setBottom(bas);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
