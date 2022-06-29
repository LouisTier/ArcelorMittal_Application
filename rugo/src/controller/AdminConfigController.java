package controller;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data_types.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.VueAdminConfig;
import vue.VueAdminConfig.HBoxCell;

public class AdminConfigController implements EventHandler<ActionEvent> {

	private String action;
	private String username;
	private int levelRole;
	private static User user;
	private DaoManagerMysql daoManagerMysql;
	private VueAdminConfig vue;

	// digit + lowercase char + uppercase char + punctuation + symbol
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_.-]{3,20}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	/**
	 * Constructeur simple
	 */
	public AdminConfigController() {
		this.vue = VueAdminConfig.getInstance();
		daoManagerMysql = DaoManagerMysql.getInstance();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Contructeur pour vue
	 */
	public AdminConfigController(String action, String username, int niveau) {
		// TODO Auto-generated constructor stub
		this.vue = VueAdminConfig.getInstance();
		this.username = username;
		this.levelRole = niveau;
		daoManagerMysql = DaoManagerMysql.getInstance();
		this.action = action; // update
	}

	/**
	 * Constructeur pour vue
	 */
	public AdminConfigController(String action) {
		// TODO Auto-generated constructor stub
		this.vue = VueAdminConfig.getInstance();
		daoManagerMysql = DaoManagerMysql.getInstance();
		this.action = action; // back déco
	}

	/**
	 * Affichage vue
	 */
	public void show(User user) {
		// TODO Auto-generated method stub
		this.user = user;
		ArrayList<User> listUsers = daoManagerMysql.getAllUsers();
		vue.setVue(user, listUsers);
	}

	/**
	 * test si mot de passe respect le parttern
	 */
	private static boolean isValid(final String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	
	/**
	 * Gestion des boutons
	 */
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(action);
		switch (action) {
		case "back":
			vue.moveToMenu(user);
			break;
		case "disconnect":
			vue.moveToLogin();
			break;
		case "create":
			if (!(isValid(vue.getPswData()) && isValid(vue.getUsrData()))) {
				vue.setInfoText("Charactère interdit semble reconnu dans le login ou le mot de passe\nLe login ou le mot de passe est peut être trop court !");
			} else {
				if (vue.getPswData().equals(vue.getPswConf())) {
					if (!daoManagerMysql.userExists(vue.getUsrData())) {
						daoManagerMysql.createNewUser(vue.getUsrData(), vue.getPswData(), vue.getAdmin());
						vue.close();
						show(user);
					} else
						vue.setInfoText("Nom d'utilisateur déjà utilisé");
				} else
					vue.setInfoText("Les mots de passe ne sont pas identique");
			}
			break;
		case "update":
			if (daoManagerMysql.userExists(username)) {
				int levelRoleFinal;
				if (levelRole == 0)
					levelRoleFinal = 1;
				else
					levelRoleFinal = 0;
				daoManagerMysql.updateRight(username, levelRoleFinal);
				vue.close();
				show(user);
			}
			break;
		case "delete":
			if (daoManagerMysql.userExists(username)) {
				daoManagerMysql.deleteUser(username);
				vue.close();
				show(user);
			}
			break;
		default:
			break;
		}
	}

}
