package vue;

import controller.AdminConfigController;
import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private LoginController loginController ;
	private AdminConfigController adminConfigController;
	private Model model;
	
	public Main() {
		// TODO Auto-generated constructor stub
		
		loginController = new LoginController();
		adminConfigController = new AdminConfigController();
		model = Model.getModel();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {	
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Button buttonLogin = new Button("START");
			buttonLogin.setOnAction(arg0 -> buttonLoginPressed(primaryStage));
			root.setCenter(buttonLogin);
			
			primaryStage.setTitle("Rugo");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		model.stopSim();
		System.exit(-1);
	}


	
	private void buttonLoginPressed(Stage primaryStage) {
		primaryStage.close();	
		loginController.show();
	}

	public static void main(String[] args) {
		Main main = new Main();
		launch(args);
	}
}
