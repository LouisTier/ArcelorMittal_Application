package controller;

import data_types.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.VueMenu;

public class MenuController implements EventHandler<ActionEvent> {

	private String action;
	private VueMenu vue;
	private static User user;

	/**
	 * Constructeur pour vue listener
	 */
	public MenuController(String action) {
		// TODO Auto-generated constructor stub
		this.action = action;
		vue = VueMenu.getInstance();
	}

	/**
	 * Contructeur de base
	 */
	public MenuController() {
		// TODO Auto-generated constructor stub
		vue = VueMenu.getInstance();
	}
	
	/**
	 * Affiche le menu
	 */
	public void show(User user) {
		MenuController.user = user;
		System.out.println("moveToMenu : " + user);
		vue.setVue(user.getNiveauDroit());
	}

	/**
	 * gère les boutons
	 */
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(action);
		switch (action) {
		case "moveToConfig":
			vue.moveToConfig(user);
			//MOVE TO CONFIG
			break;
		case "moveToGraph":
			vue.moveToGraph(user);
			//MOVE TO GRAPH
			break;
		case "disconnect":
			vue.moveToLogin();
			break;
		default:
			break;
		}

	}

}
