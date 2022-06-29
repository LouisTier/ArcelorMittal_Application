package vue;

import java.util.ArrayList;

import controller.GraphController;
import controller.LoginController;
import controller.MenuController;
import data_types.DonneeAffichage;
import data_types.User;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueGraph {

	private ComboBox<String> comboBox;
	private Series<Number, Number> seriesFriction;
	private Series<Number, Number> seriesSigma;
	private Series<Number, Number> seriesRollSpeed;
	private Series<Number, Number> cheating;
	private ArrayList<DonneeAffichage> data;
	private Text computeTimeData;
	private boolean flagRollSpeed;
	private boolean flagSigma;
	private boolean flagFriction;
	private static VueGraph instance;
	private MenuController menuController;
	private LoginController loginController;
	private Stage stage;
	private CheckBox checkBoxRollSpeed;
	private CheckBox checkBoxFriction;
	private CheckBox checkBoxSigma;

	private VueGraph() {
		// TODO Auto-generated constructor stub
		flagFriction = false;
		flagRollSpeed = false;
		flagSigma = false;
		cheating = new Series<>();
	}

	public static VueGraph getInstance() {
		if (instance == null)
			instance = new VueGraph();
		return instance;
	}

	public void setVue() {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			VBox buttonBox = new VBox();

			Button backButton = new Button("Retour");
			backButton.setOnMouseClicked(new GraphController("back"));
			Button disconectButton = new Button("Déconnexion");
			disconectButton.setOnMouseClicked(new GraphController("disconnect"));

			disconectButton.setStyle("-fx-background-color: #FF0000");
			buttonBox.getChildren().add(disconectButton);
			buttonBox.getChildren().add(backButton);
			buttonBox.setSpacing(50);
			root.setRight(buttonBox);

			// create a tile pane
			TilePane ligneComboBox = new TilePane();

			Label comboBoxInfos = new Label("Voir le poste : ");
			comboBox = new ComboBox<String>();
			comboBox.setOnMouseClicked(new GraphController("refresh"));

			Label computeTimeInfos = new Label("Temps de calcul d'Orowan : ");
			computeTimeData = new Text();

			ligneComboBox.getChildren().add(comboBoxInfos);
			ligneComboBox.getChildren().add(comboBox);
			ligneComboBox.getChildren().add(computeTimeInfos);
			ligneComboBox.getChildren().add(computeTimeData);

			root.setTop(ligneComboBox);

			// create a tile pane
			TilePane ligneRadio = new TilePane();
			// create a label
			Label radioButtonInfos = new Label("Que voulez-vous afficher");

			checkBoxRollSpeed = new CheckBox("Roll Speed");

			checkBoxFriction = new CheckBox("Friction");

			checkBoxSigma = new CheckBox("Sigma");

			checkBoxRollSpeed.setOnMouseClicked(new GraphController("rollSpeed"));
			checkBoxFriction.setOnMouseClicked(new GraphController("friction"));
			checkBoxSigma.setOnMouseClicked(new GraphController("sigma"));

			ligneRadio.getChildren().add(radioButtonInfos);
			ligneRadio.getChildren().add(checkBoxRollSpeed);
			ligneRadio.getChildren().add(checkBoxFriction);
			ligneRadio.getChildren().add(checkBoxSigma);
			root.setBottom(ligneRadio);

			// defining the axes
			final NumberAxis xAxis = new NumberAxis();
			final NumberAxis yAxis = new NumberAxis();
			xAxis.setLabel("Temps en seconde");
			// creating the chart
			final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
			lineChart.setAnimated(false);
			// defining a series
			seriesFriction = new Series<Number, Number>();
			seriesFriction.setName("Friction");
			seriesRollSpeed = new Series<Number, Number>();
			seriesRollSpeed.setName("Roll Speed");
			seriesSigma = new Series<Number, Number>();
			seriesSigma.setName("Sigma");
			lineChart.getData().add(seriesRollSpeed);
			lineChart.getData().add(seriesSigma);
			lineChart.getData().add(seriesFriction);
			root.setCenter(lineChart);
			stage = new Stage();
			stage.setTitle("Rugo");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addAPoste(ArrayList<String> listPoste) {
		comboBox.getItems().setAll(listPoste);
	}

	public ComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setComboBox(ComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}

	public void importPointsForData(ArrayList<DonneeAffichage> allDonneeAffichage) {
		// TODO Auto-generated method stub
		data = allDonneeAffichage;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Update UI here.

				seriesFriction.getData().clear();
				seriesSigma.getData().clear();
				seriesRollSpeed.getData().clear();
				for (int i = 0; i < data.size(); i++) {
					if (flagRollSpeed) {
						seriesRollSpeed.getData().add(new XYChart.Data<>(i, data.get(i).getRolling_speed()));
					} else {
						seriesRollSpeed.getData().add(new XYChart.Data<>(0, 0));
					}
					if (flagFriction) {
						if (data.get(i).getFriction() != 0)
							seriesFriction.getData().add(new XYChart.Data<>(i, data.get(i).getFriction()));
					} else {
						seriesFriction.getData().add(new XYChart.Data<>(0, 0));
					}
					if (flagSigma) {
						if (data.get(i).getSigma() != 0)
							seriesSigma.getData().add(new XYChart.Data<>(i, data.get(i).getSigma()));
					} else {
						seriesSigma.getData().add(new XYChart.Data<>(0, 0));
					}
				}
			}
		});
	}

	public void setComputeTimeValue(long val) {
		// TODO Auto-generated method stub
		computeTimeData.setText(val + " ms");
	}

	public void moveToMenu(User user) {
		// TODO Auto-generated method stub
		flagFriction = false;
		flagRollSpeed = false;
		flagSigma = false;
		menuController = new MenuController();
		stage.close();
		menuController.show(user);

	}

	public void moveToLogin() {
		// TODO Auto-generated method stub
		flagFriction = false;
		flagRollSpeed = false;
		flagSigma = false;
		loginController = new LoginController();
		stage.close();
		loginController.show();
	}

	public void setFlagRollSpeed(boolean flagRollSpeed) {
		this.flagRollSpeed = flagRollSpeed;
	}

	public void setFlagSigma(boolean flagSigma) {
		this.flagSigma = flagSigma;
	}

	public void setFlagFriction(boolean flagFriction) {
		this.flagFriction = flagFriction;
	}

	public boolean isCheckedFriction() {
		// TODO Auto-generated method stub
		return checkBoxFriction.isSelected();
	}

	public boolean isCheckedSigma() {
		// TODO Auto-generated method stub
		return checkBoxSigma.isSelected();
	}

	public boolean isCheckedRollSpeed() {
		// TODO Auto-generated method stub
		return checkBoxRollSpeed.isSelected();
	}

}
