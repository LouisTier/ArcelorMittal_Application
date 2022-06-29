package controller;

import java.beans.PropertyChangeEvent;

import data_types.User;
import javafx.event.Event;
import javafx.event.EventHandler;
import model.Model;
import vue.VueGraph;

public class GraphController implements java.beans.PropertyChangeListener, EventHandler<Event> {

	private Model model;
	private String name;
	private DaoManagerMysql daoManagerMysql;
	private VueGraph vue;
public static User user;

/**
 * Constructeur classis
 */
	public GraphController() {
		// TODO Auto-generated constructor stub
		daoManagerMysql = DaoManagerMysql.getInstance();
		model = Model.getModel();
		model.addPropertyChangeListener(this);
		vue = VueGraph.getInstance();
	}

	
	/**
	 * Affiche la page graph
	 */
	public void show(User user) {
		System.out.println("GraphCont : " + user);
		GraphController.user = user;
		vue.setVue();
		Model.getModel().startSimulation();
	}

	/**
	 * Constructeur pour listener vue
	 */
	public GraphController(String name) { // We create one instance of controler per button
		System.out.println("in second controler constructor");
		this.name = name;
		daoManagerMysql = DaoManagerMysql.getInstance();
		model = Model.getModel();
		vue = VueGraph.getInstance();
	}
	
	/**
	 * Changement dans model
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String propName = evt.getPropertyName();
		switch (propName) {
		case "newData": //nouvelle données a afficher
			String value;
			try {
				value = vue.getComboBox().getValue();
			}catch (Exception e) {
				// TODO: handle exception
				value = "";
			}
			vue.importPointsForData(daoManagerMysql.getAllDonneeAffichage(value));
			//System.out.println("new data");
			break;
		case "OrowanComputeTime" : //compute time orowan
			String nomPoste = (String) evt.getOldValue();
			long computeTime = (long) evt.getNewValue();
			
			try {
				value = vue.getComboBox().getValue();
			}catch (Exception e) {
				// TODO: handle exception
				value = "";
			}
			
			if(nomPoste.equals(value)) {
				vue.setComputeTimeValue(computeTime);
			}
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * Arrete les thread de la simulation
	 */
	public void stopSim() {
		// TODO Auto-generated method stub
		model.stopSim();
	}

	
	/**
	 * Gère les boutons de la vue
	 */
	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		System.out.println(name);
		switch (name) {
		case "refresh":
			vue.addAPoste(model.getPostesName());
			break;
		case "rollSpeed" :
			vue.setFlagRollSpeed(vue.isCheckedRollSpeed());
			break;
		case "friction" :
			vue.setFlagFriction(vue.isCheckedFriction());
			break;
		case "sigma" :
			vue.setFlagSigma(vue.isCheckedSigma());
			break;
		case "back" :
			stopSim();
			vue.moveToMenu(user);
			break;
		case "disconnect" :
			stopSim();
			vue.moveToLogin();
			break;
		}
	}
}
