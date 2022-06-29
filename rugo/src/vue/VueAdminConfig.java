package vue;

import java.util.ArrayList;

import controller.AdminConfigController;
import controller.LoginController;
import controller.MenuController;
import data_types.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueAdminConfig {

	private static VueAdminConfig instance;
	private ObservableList<User> users;
	private Stage stage;
	private MenuController menuController;
	private LoginController loginController;
	
	private TextField usrData;
	private PasswordField pswData;
	private PasswordField pswConf;
	private TextField infoText;
	private CheckBox checkBoxAdmin;

	private VueAdminConfig() {
		// TODO Auto-generated constructor stub
	}

	public void setVue(User user, ArrayList<User> users) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			VBox buttonBox = new VBox();

			Button backButton = new Button("Retour");
			backButton.setOnAction(new AdminConfigController("back"));
			Button disconectButton = new Button("Déconnexion");
			disconectButton.setOnAction(new AdminConfigController("disconnect"));

			disconectButton.setStyle("-fx-background-color: #FF0000");
			buttonBox.getChildren().add(disconectButton);
			buttonBox.getChildren().add(backButton);
			buttonBox.setSpacing(50);
			root.setRight(buttonBox);

			VBox listUsers = new VBox();

			for (User mUser : users) {
				if (!mUser.getUsername().equals(user.getUsername())) {
					HBoxCell cell = new HBoxCell("update", mUser.getUsername(), mUser.getNiveauDroit());
					listUsers.getChildren().add(cell);
				}
			}
			root.setCenter(listUsers);
			
			usrData = new TextField();
			usrData.setPromptText("Nom utilisateur");
			pswData = new PasswordField();
			pswData.setPromptText("Mot de passe");
			pswConf = new PasswordField();
			pswConf.setPromptText("Confirmer le mot de passe");
			infoText = new TextField();
			checkBoxAdmin = new CheckBox("Admin");
			Button createButton = new Button("Créer utilisateur");
			createButton.setOnAction(new AdminConfigController("create"));

			VBox infoCreateBox = new VBox();
			HBox createBox = new HBox();
			createBox.getChildren().addAll(usrData, pswData, pswConf, checkBoxAdmin, createButton);
			infoCreateBox.getChildren().addAll(createBox, infoText);
			createBox.setAlignment(Pos.CENTER);
			createBox.setSpacing(25);
			
			root.setBottom(infoCreateBox);

			stage = new Stage();
			stage.setTitle("Rugo");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class HBoxCell extends HBox {
		Text username = new Text();
		Button updateButton = new Button();
		Button deleteButton = new Button();
		Text role = new Text();

		HBoxCell(String what, String labelText, int niveau) {
			super();

			username.setText(labelText);
			setSpacing(25);
			setAlignment(Pos.CENTER);

			updateButton.setText("Changer les droits");
			updateButton.setOnAction(new AdminConfigController(what, username.getText(), niveau));
			
			deleteButton.setText("Supprimer");
			deleteButton.setStyle("-fx-background-color: #AA0000");
			deleteButton.setOnAction(new AdminConfigController("delete", username.getText(), niveau));
			
			if (niveau == 0)
				role.setText("Utilisateur");
			else
				role.setText("Administrateur");

			updateButton.setStyle("-fx-border-color: black");
			deleteButton.setStyle("-fx-border-color: black");
			
			this.getChildren().addAll(username, role, updateButton, deleteButton);
		}
	}

	public static VueAdminConfig getInstance() {
		// TODO Auto-generated method stub
		if (instance == null)
			instance = new VueAdminConfig();
		return instance;
	}

	public void moveToMenu(User user) {
		// TODO Auto-generated method stub
		menuController = new MenuController();
		stage.close();
		menuController.show(user);

	}

	public void moveToLogin() {
		// TODO Auto-generated method stub
		loginController = new LoginController();
		stage.close();
		loginController.show();
	}

	public void close() {
		// TODO Auto-generated method stub
		stage.close();
	}

	public String getUsrData() {
		return usrData.getText();
	}

	public void setUsrData(String usrData) {
		this.usrData.setText(usrData);
	}

	public String getPswData() {
		return pswData.getText();
	}

	public void setPswData(String pswData) {
		this.pswData.setText(pswData);
	}

	public String getPswConf() {
		return pswConf.getText();
	}

	public void setPswConf(String pswConf) {
		this.pswConf.setText(pswConf);
	}

	public String getInfoText() {
		return infoText.getText();
	}

	public void setInfoText(String infoText) {
		this.infoText.setText(infoText);
	}
	
	public int getAdmin() {
		if(checkBoxAdmin.isSelected())
			return 1;
		else 
			return 0;
	}
	

}