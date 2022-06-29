package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data_types.User;
import javafx.event.EventHandler;
import vue.VueLogin;

public class LoginController implements EventHandler<javafx.event.ActionEvent> {

	private DaoManagerMysql daoManagerMysql;
	private String action;
	private VueLogin vue;

	// digit + lowercase char + uppercase char + punctuation + symbol
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_.-]{3,20}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	/**
	 * Constructeur de base
	 */
	public LoginController() {
		// TODO Auto-generated constructor stub
		daoManagerMysql = DaoManagerMysql.getInstance();
		vue = VueLogin.getInstance();
	}
	
	/**
	 * Affiche le login
	 */
	public void show() {
		vue.setVue();
	}

	/**
	 * Constructeur pour vue comme boutton listener
	 */
	public LoginController(String action) {
		System.out.println("listener created");
		this.action = action;
		vue = VueLogin.getInstance();
		daoManagerMysql = DaoManagerMysql.getInstance();
	}

	/**
	 * Test si le string respect le pattern
	 */
	private static boolean isValid(final String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * Gère les click sur la vue
	 */
	@Override
	public void handle(javafx.event.ActionEvent evt) {
		// TODO Auto-generated method stub
		String usr = vue.getUsrData();
		String psw = vue.getPswData();
		switch (action) {
		case "Connexion":
			if (isValid(psw) && isValid(usr)) {
				System.out.println("valid");
				User user = daoManagerMysql.doesUserExistAndIsAdmin(usr, psw);
				int niveauDuLogin = user.getNiveauDroit();
				if (niveauDuLogin != -1) {
					System.out.println("existe");
					switch (niveauDuLogin) {
					case 0:
					case 1:
						vue.moveToMenu(user);
						break;
					default:
						break;
					}
					// connexion OK
				} else {
					System.out.println("existe pas");
					vue.setMessageSiProbleme("Login ou mot de passe non reconnu");
				}
			} else {
				System.out.println("notValid");
				vue.setMessageSiProbleme("Charactère interdit semble reconnu dans le login ou le mot de passe\nLe login ou le mot de passe est peut être trop court !");
			}
			break;
		default:
			break;
		}
	}

}
