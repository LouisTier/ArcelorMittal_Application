package vue;


import controller.GraphController;
import controller.LoginController;
import controller.MenuController;
import data_types.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueLogin  {
	
	private static VueLogin instance;
	private TextField usrData;
	private PasswordField pswData;
	private Text messageSiProbleme;
	private Stage stage;
	private GraphController graphController ;
	private MenuController menuController ;
	
	private VueLogin() {
		// TODO Auto-generated constructor stub
		graphController = new GraphController();
		menuController = new MenuController();
	}
	
	public static VueLogin getInstance() {
		if(instance == null) {
			instance = new VueLogin();
		}
		return instance;
	}
	
	public void setVue() {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			HBox topHBox = new HBox();
			Text welcomeInfos = new Text("Bienvenue dans l'application !");
			welcomeInfos.setFont(Font.font("Arial", FontWeight.BOLD, 36));
			
			topHBox.getChildren().add(welcomeInfos);
			topHBox.setAlignment(Pos.CENTER);
			
			root.setTop(topHBox);
			
			VBox middleVBox = new VBox();
			
			Text loginInfos = new Text("Nom d'utilisateur :");
			usrData = new TextField();
			usrData.setAlignment(Pos.CENTER);
			
			Text pswInfos = new Text("Mot de passe :");
			pswData = new PasswordField();
			pswData.setAlignment(Pos.CENTER);
			
			Button connect = new Button("Connexion");
			connect.setOnAction(new LoginController("Connexion"));
			connect.setPrefSize(200, 50);
			
			messageSiProbleme = new Text();
			messageSiProbleme.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
			messageSiProbleme.setFill(Color.RED);
			
			
			middleVBox.getChildren().add(loginInfos);
			middleVBox.getChildren().add(usrData);
			
			middleVBox.getChildren().add(pswInfos);
			middleVBox.getChildren().add(pswData);

			middleVBox.getChildren().add(connect);
			
			middleVBox.getChildren().add(messageSiProbleme);
			
			
			middleVBox.setAlignment(Pos.CENTER);
			middleVBox.setSpacing(10);
			
			root.setCenter(middleVBox);
			
			stage = new Stage();
			stage.setTitle("Rugo");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveToMenu(User user) {
		stage.close();
		menuController.show(user);

	}
	
	public void moveToGraph(User user) {
		stage.close();
		graphController.show(user);
	}
	

	public String getUsrData() {
		return usrData.getText();
	}

	public String getPswData() {
		return pswData.getText();
	}

	public void setMessageSiProbleme(String messageSiProbleme) {
		this.messageSiProbleme.setText(messageSiProbleme);
	}

}
