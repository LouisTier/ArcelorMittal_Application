package data_types;

/**
 * Encapsule les données non sensible d'un utilisateur
 */
public class User {
	private String username;
	private int niveauDroit;
	
	public User(String username, int niveauDroit) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.niveauDroit = niveauDroit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNiveauDroit() {
		return niveauDroit;
	}

	public void setNiveauDroit(int niveauDroit) {
		this.niveauDroit = niveauDroit;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username;
	}

}
