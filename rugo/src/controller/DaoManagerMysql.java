package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import data_types.DonneeAffichage;
import data_types.DonneeInOrowan;
import data_types.DonneeOutCapteur;
import data_types.DonneeOutOrowan;
import data_types.User;

public class DaoManagerMysql {

	private static DaoManagerMysql instance = null;
	private Connection connection = null;

	private DaoManagerMysql() {
		try {
			String url = "jdbc:mysql://localhost:3306/rugo";
			String username = "insertgetter";
			String password = "insertgetter";
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		// System.out.println("Opened database successfully");
	}

	/**
	 * return singleton
	 */
	public static DaoManagerMysql getInstance() {
		if (instance == null)
			instance = new DaoManagerMysql();
		return instance;
	}

	/**
	 * Insert une données capteur dans la base de données
	 */
	public void insertDonneeOutCapteur(DonneeOutCapteur donneeOutCapteur) {
		String champRequete = "lp,MatID,XTime,Xloc,EnThick,ExThick,EnTens,ExTens,RollForce,FSlip,daiameter,Rolled_length_for_Work_Rolls,YoungModulus,Bockup_roll_dia,Rolled_length_for_Backup_Rolls,Mu,Torque,AverageSigma,InputError,LubWFlUp,LubWFlLo,LubOilFlUp,LubOilFlLo,Work_roll_speed,nom_poste";
		
		try {
			PreparedStatement preparedStatment = connection.prepareStatement("INSERT INTO table_donnee_capteurs_out("
					+ champRequete + ") VALUES (" + donneeOutCapteur.toString() + ")");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("INSERT INTO table_donnee_capteurs_out("
					+ champRequete + ") VALUES (" + donneeOutCapteur.toString() + ")");
			System.err.println(donneeOutCapteur.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Insert une données d'entrée d'Orowan dans la base de données
	 */
	public void insertDonneeOutOrowan(DonneeOutOrowan donneeOutOrowan) {
		// System.out.println(donneeOutOrowan);
		String champRequete = "case_,Error,OffsetYield,Friction,Rolling_Torque,Sigma_Moy,Sigma_Ini,Sigma_Out,Sigma_Max,Force_Error,Slip_Error,Has_Converged,nom_poste";
		// System.out.println("INSERT INTO table_data_orowan_out(" + champRequete + ")
		// VALUES (" + donneeOutOrowan.toString() + ")");
		try {
			PreparedStatement preparedStatment = connection.prepareStatement("INSERT INTO table_data_orowan_out("
					+ champRequete + ") VALUES (" + donneeOutOrowan.toString() + ")");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Insert une données de sortie d'Orowan dans la base de données
	 */
	public void insertDonneeInOrowan(DonneeInOrowan donneeInOrowan) {
		String champRequete = "Cas,He,Hs,Te,Ts,Diam_WR,WRyoung,offset,mu_ini,Force_data,G,nom_poste";
		try {
			PreparedStatement preparedStatment = connection.prepareStatement("INSERT INTO table_data_orowan_in("
					+ champRequete + ") VALUES (" + donneeInOrowan.toString() + ")");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * recupere la derniere entrée d'orowan
	 */
	public DonneeInOrowan getLast1DonneeInOrowan(String nomPoste) {
		DonneeInOrowan returnDonneeInOrowan = null;
		try {
			Statement statement = connection.createStatement();
			String request = "SELECT * FROM table_data_orowan_in WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id DESC LIMIT 1";
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					returnDonneeInOrowan = new DonneeInOrowan(resultSet.getInt(2), resultSet.getDouble(3),
							resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6),
							resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
							resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
							resultSet.getString(13));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return returnDonneeInOrowan;
	}

	/**
	 * recupere la derniere donnée des capteurs
	 */
	public DonneeOutCapteur getLast1DonneeOutCapteur(String nomPoste) {
		DonneeOutCapteur donneeOutCapteur = null;
		try {
			Statement statement = connection.createStatement();
			String request = "SELECT * FROM table_donnee_capteurs_out WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id DESC LIMIT 1";
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					donneeOutCapteur = new DonneeOutCapteur(resultSet.getInt(2), resultSet.getInt(3),
							resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6),
							resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
							resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
							resultSet.getDouble(13), resultSet.getDouble(14), resultSet.getDouble(15),
							resultSet.getDouble(16), resultSet.getDouble(17), resultSet.getDouble(18),
							resultSet.getDouble(19), resultSet.getDouble(20), resultSet.getDouble(21),
							resultSet.getDouble(22), resultSet.getDouble(23), resultSet.getDouble(24),
							resultSet.getDouble(25), resultSet.getString(26));
					// System.out.println(donneeOutCapteur);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return donneeOutCapteur;
	}

	/**
	 * reset des tables données
	 */
	public void truncateTables() {
		PreparedStatement preparedStatment;
		try {
			preparedStatment = connection.prepareStatement("TRUNCATE TABLE table_data_orowan_out");
			preparedStatment.execute();
			preparedStatment = connection.prepareStatement("TRUNCATE TABLE table_data_orowan_in");
			preparedStatment.execute();
			preparedStatment = connection.prepareStatement("TRUNCATE TABLE table_donnee_capteurs_out");
			preparedStatment.execute();
			preparedStatment = connection.prepareStatement("TRUNCATE TABLE table_donnee_affichage");
			preparedStatment.execute();
			preparedStatment.close();
			// System.out.println("Truncated all tables");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> returnListUsers = new ArrayList<>();
		try {
			String request = "SELECT username, admin FROM table_user";
			Statement statement = connection.createStatement();
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					User user = new User(resultSet.getString(1), resultSet.getInt(2));
					returnListUsers.add(user);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return returnListUsers;
	}

	/**
	 * est-ce que l'utilisateur existe et sont role 1 admin 0 user -1 n'existe pas
	 */
	public User doesUserExistAndIsAdmin(String username, String psw) { // 1 admin 0 user -1 n'existe pas
		int result = -1;
		try {
			String sql = "SELECT * FROM table_user WHERE username = ? AND password = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // protection injection SQK
			String md5psw = md5Password(psw);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, md5psw);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getBoolean("admin"))
					result = 1;
				else
					result = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new User(username, result);
	}

	/**
	 * username exist deja ? pour création
	 */
	public boolean userExists(String username) {
		boolean exists = false;
		try {
			String sql = "SELECT * FROM table_user WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // protection injection SQK
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				exists = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	/**
	 * supprime un utilisateur
	 */
	public boolean deleteUser(String username) {
		boolean succeeded = false;
		try {
			String sql = "DELETE FROM table_user WHERE username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // protection injection SQK
			preparedStatement.setString(1, username);
			preparedStatement.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return succeeded;
	}

	/**
	 * Créer un nouvel utilisateur
	 */
	public boolean createNewUser(String username, String password, int isAdmin) {
		try {
			if (!userExists(username)) {
				String sql = "INSERT INTO table_user (username, password, admin) VALUES (? , ? , ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql); // protection injection SQK
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, md5Password(password));
				preparedStatement.setInt(3, isAdmin);
				preparedStatement.execute();
				preparedStatement.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateRight(String username, int droits) {
		String sql = "UPDATE table_user SET admin = ? WHERE username = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // protection injection SQK
			preparedStatement.setInt(1, droits);
			preparedStatement.setString(2, username);
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * Encrypte le mod de passe en md5
	 */
	private String md5Password(String psw) { // https://www.javatpoint.com/how-to-encrypt-password-in-java
		/* Plain-text password initialization. */
		String password = psw;
		String encryptedpassword = null;
		try {
			/* MessageDigest instance for MD5. */
			MessageDigest m = MessageDigest.getInstance("MD5");

			/* Add plain-text password bytes to digest using MD5 update() method. */
			m.update(password.getBytes());

			/* Convert the hash value into bytes */
			byte[] bytes = m.digest();

			/*
			 * The bytes array has bytes in decimal form. Converting it into hexadecimal
			 * format.
			 */
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			/* Complete hashed password in hexadecimal format */
			encryptedpassword = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		/* Display the unencrypted and encrypted passwords. */
		return encryptedpassword;
	}

	public boolean insertDonneeAffichage(DonneeAffichage donneeAffichage) {
		try {
			String sql = "INSERT INTO table_donnee_affichage (friction, roll_speed, sigma, nom_poste, erreur) VALUES (? , ? , ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // protection injection SQK
			preparedStatement.setDouble(1, donneeAffichage.getFriction());
			preparedStatement.setDouble(2, donneeAffichage.getRolling_speed());
			preparedStatement.setDouble(3, donneeAffichage.getSigma());
			preparedStatement.setString(4, donneeAffichage.getNomPoste());
			preparedStatement.setString(5, donneeAffichage.getErreur());
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * rend un array list avec les doonées a afficher
	 */
	public ArrayList<DonneeAffichage> getAllDonneeAffichage(String nomPoste) {
		ArrayList<DonneeAffichage> returnListDonneeAffichage = new ArrayList<>();
		try {
			String request = "SELECT * FROM table_donnee_affichage WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id ASC";
			Statement statement = connection.createStatement();
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					DonneeAffichage donneeAffichage = new DonneeAffichage(resultSet.getDouble(2),
							resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getString(5),
							resultSet.getString(6));
					returnListDonneeAffichage.add(donneeAffichage);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return returnListDonneeAffichage;
	}

	/**
	 * CREER LA DONNEE AFFICHAGE
	 */
	public DonneeAffichage getLastDonneeAffichage(String nomPoste) {
		DonneeAffichage returnDonneeAffichage = null;
		try {
			Statement statement = connection.createStatement();
			String request = "SELECT table_donnee_capteurs_out.XTime,table_donnee_capteurs_out.Work_roll_speed, "
					+ "table_data_orowan_out.Sigma_Ini, table_data_orowan_out.Friction, table_data_orowan_out.Error "
					+ "FROM table_donnee_capteurs_out " + "INNER JOIN table_data_orowan_out "
					+ "ON table_donnee_capteurs_out.id = table_data_orowan_out.id "
					+ "WHERE table_donnee_capteurs_out.nom_poste = '" + nomPoste + "' "
					+ "ORDER BY table_data_orowan_out.id DESC LIMIT 1";
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					returnDonneeAffichage = new DonneeAffichage(resultSet.getDouble(2), resultSet.getDouble(3),
							resultSet.getDouble(4), nomPoste, resultSet.getString(5));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return returnDonneeAffichage;
	}

}
