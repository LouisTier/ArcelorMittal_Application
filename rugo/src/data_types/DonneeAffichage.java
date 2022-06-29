package data_types;
/**
 * Classe qui encapsule les données a afficher
 */
public class DonneeAffichage {
	private double timestamp;
	private double rolling_speed;
	private double sigma;
	private double friction;
	private String nomPoste;
	private String erreur;
	
	public DonneeAffichage(double timestamp, double rolling_speed, double friction, double sigma, String nomPoste, String erreur) {
		this.timestamp = timestamp;
		this.rolling_speed = rolling_speed;
		this.sigma = sigma;
		this.friction = friction;
		this.nomPoste = nomPoste;
		this.erreur = erreur;
	}
	public DonneeAffichage(double rolling_speed, double sigma, double friction, String nomPoste, String erreur) {
		this.rolling_speed = rolling_speed;
		this.sigma = sigma;
		this.friction = friction;
		this.nomPoste = nomPoste;
		this.erreur = erreur;
	}
	public double getRolling_speed() {
		return rolling_speed;
	}
	public void setRolling_speed(double rolling_speed) {
		this.rolling_speed = rolling_speed;
	}
	public double getSigma() {
		return sigma;
	}
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}
	public double getFriction() {
		return friction;
	}
	public void setFriction(double friction) {
		this.friction = friction;
	}
	public double getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getNomPoste() {
		return nomPoste;
	}
	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}
	

	@Override
	public String toString() {
		return "DonneeAffichage [rolling_speed=" + rolling_speed + ", sigma=" + sigma
				+ ", friction=" + friction + ", nomPoste=" + nomPoste + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DonneeAffichage other = (DonneeAffichage) obj;
		return Double.doubleToLongBits(timestamp) == Double.doubleToLongBits(other.timestamp);
	}
	public String getErreur() {
		return erreur;
	}
	public void setErreur(String erreur) {
		this.erreur = erreur;
	}
	
	
	
	
}
