package vue;

import controller.AdminConfigController;
import controller.GraphController;
import controller.LoginController;
import controller.MenuController;
import data_types.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VueMenu {

	private Stage stage;
	private static VueMenu instance;
	private GraphController graphController;
	private AdminConfigController adminConfigController;
	private LoginController loginController;

	private VueMenu() {
		// TODO Auto-generated constructor stub
	}

	public static VueMenu getInstance() {
		if (instance == null)
			instance = new VueMenu();
		return instance;
	}

	public void setVue(int droit) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Button disconectButton = new Button("Déconnexion");
			disconectButton.setOnAction(new MenuController("disconnect"));
			disconectButton.setStyle("-fx-background-color: #FF0000");
			root.setRight(disconectButton);

			VBox vBox = new VBox();
			vBox.setSpacing(20);
			vBox.setAlignment(Pos.CENTER);

			if (droit > 0) {
				Button versAdmin = new Button("Configuration");
				versAdmin.setOnAction(new MenuController("moveToConfig"));
				versAdmin.setPadding(new Insets(10, 50, 10, 50));
				vBox.getChildren().add(versAdmin);
			}

			Button versGraph = new Button("Vers Graph");
			versGraph.setOnAction(new MenuController("moveToGraph"));
			versGraph.setPadding(new Insets(10, 50, 10, 50));
			vBox.getChildren().add(versGraph);

			root.setCenter(vBox);

			stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveToGraph(User user) {
		graphController = new GraphController();
		stage.close();
		graphController.show(user);
		// TODO Auto-generated method stub
	}

	public void moveToConfig(User user) {
		adminConfigController = new AdminConfigController();
		stage.close();
		adminConfigController.show(user);
		// TODO Auto-generated method stub

	}

	public void moveToLogin() {
		// TODO Auto-generated method stub
		loginController = new LoginController();
		stage.close();
		loginController.show();
	}

}
