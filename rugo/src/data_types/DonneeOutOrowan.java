package data_types;

/**
 * Encapsule les données en sortie d'orowan
 */
public class DonneeOutOrowan {
	private String nomPoste;
	private int cas;
	private String Errors;
	private double OffsetYield;
	private double Friction;
	private double Rolling_Torque;
	private double Sigma_Moy;
	private double Sigma_Ini;
	private double Sigma_Out;
	private double Sigma_Max;
	private double Force_Error;
	private double Slip_Error;
	private String Has_Converged;

	public DonneeOutOrowan(int cas, String errors, double offsetYield, double friction, double rolling_Torque,
			double sigma_Moy, double sigma_Ini, double sigma_Out, double sigma_Max, double force_Error, double slip_Error,
			String has_Converged, String nomPoste) {
		this.cas = cas;
		Errors = errors;
		OffsetYield = offsetYield;
		Friction = friction;
		Rolling_Torque = rolling_Torque;
		Sigma_Moy = sigma_Moy;
		Sigma_Ini = sigma_Ini;
		Sigma_Out = sigma_Out;
		Sigma_Max = sigma_Max;
		Force_Error = force_Error;
		Slip_Error = slip_Error;
		Has_Converged = has_Converged;
		this.nomPoste = nomPoste;
	}

	public int getCas() {
		return cas;
	}

	public void setCas(int cas) {
		this.cas = cas;
	}

	public String getErrors() {
		return Errors;
	}

	public void setErrors(String errors) {
		Errors = errors;
	}

	public double getOffsetYield() {
		return OffsetYield;
	}

	public void setOffsetYield(double offsetYield) {
		OffsetYield = offsetYield;
	}

	public double getFriction() {
		return Friction;
	}

	public void setFriction(double friction) {
		Friction = friction;
	}

	public double getRolling_Torque() {
		return Rolling_Torque;
	}

	public void setRolling_Torque(double rolling_Torque) {
		Rolling_Torque = rolling_Torque;
	}

	public double getSigma_Moy() {
		return Sigma_Moy;
	}

	public void setSigma_Moy(double sigma_Moy) {
		Sigma_Moy = sigma_Moy;
	}

	public double getSigma_Ini() {
		return Sigma_Ini;
	}

	public void setSigma_Ini(double sigma_Ini) {
		Sigma_Ini = sigma_Ini;
	}

	public double getSigma_Out() {
		return Sigma_Out;
	}

	public void setSigma_Out(double sigma_Out) {
		Sigma_Out = sigma_Out;
	}

	public double getSigma_Max() {
		return Sigma_Max;
	}

	public void setSigma_Max(double sigma_Max) {
		Sigma_Max = sigma_Max;
	}

	public double getForce_Error() {
		return Force_Error;
	}

	public void setForce_Error(double force_Error) {
		Force_Error = force_Error;
	}

	public double getSlip_Error() {
		return Slip_Error;
	}

	public void setSlip_Error(double slip_Error) {
		Slip_Error = slip_Error;
	}

	public String getHas_Converged() {
		return Has_Converged;
	}

	public void setHas_Converged(String has_Converged) {
		Has_Converged = has_Converged;
	}

	public String getNomPoste() {
		return nomPoste;
	}

	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}

	@Override
	public String toString() {
		return cas + ", '" + Errors + "', " + OffsetYield + ", "
				+ Friction + ", " + Rolling_Torque + ", " + Sigma_Moy + ", "
				+ Sigma_Ini + ", " + Sigma_Out + ", " + Sigma_Max + ", " + Force_Error
				+ ", " + Slip_Error + ", '" + Has_Converged +"'" + ", '" + nomPoste + "'";
	}
	
	

}
