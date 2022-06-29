package data_types;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Encapsule les données d'entrée a orowan
 */
public class DonneeInOrowan {
	private String nomPoste;
	private int cas;
	private double he;
	private double hs;
	private double te;
	private double ts;
	private double diamWR;
	private double wRYoung;
	private double offset;
	private double mu_ini;
	private double force;
	private double g;

	public DonneeInOrowan(
			int cas, double he, double hs, 
			double te, double ts, double diamWR,
			double wRYoung, double offset,
			double mu_ini, double force, double g,
			String nomPoste) {
		
		this.cas = cas;
		this.he = he;
		this.hs = hs;
		this.te = te;
		this.ts = ts;
		this.diamWR = diamWR;
		this.wRYoung = wRYoung;
		this.offset = offset;
		this.mu_ini = mu_ini;
		this.force = force;
		this.g = g;
		this.nomPoste = nomPoste;
	}
	
	
	public int getCas() {
		return cas;
	}
	
	public void setCas(int cas) {
		this.cas = cas;
	}
	
	public double getHe() {
		return he;
	}
	
	public void setHe(double he) {
		this.he = he;
	}
	
	public double getHs() {
		return hs;
	}
	
	public void setHs(double hs) {
		this.hs = hs;
	}
	
	public double getTe() {
		return te;
	}
	
	public void setTe(double te) {
		this.te = te;
	}
	
	public double getTs() {
		return ts;
	}
	
	public void setTs(double ts) {
		this.ts = ts;
	}
	
	public double getDiamWR() {
		return diamWR;
	}
	
	public void setDiamWR(double diamWR) {
		this.diamWR = diamWR;
	}
	
	public double getwRYoung() {
		return wRYoung;
	}
	
	public void setwRYoung(double wRYoung) {
		this.wRYoung = wRYoung;
	}
	
	public double getOffset() {
		return offset;
	}
	
	public void setOffset(double offset) {
		this.offset = offset;
	}
	
	public double getMu_ini() {
		return mu_ini;
	}
	
	public void setMu_ini(double mu_ini) {
		this.mu_ini = mu_ini;
	}
	
	public double getForce() {
		return force;
	}
	
	public void setForce(double force) {
		this.force = force;
	}
	
	public double getG() {
		return g;
	}
	
	public void setG(double g) {
		this.g = g;
	}
	
	public String getNomPoste() {
		return nomPoste;
	}


	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}


	@Override
	public String toString() {
		DecimalFormatSymbols mySymbol = new DecimalFormatSymbols();
		mySymbol.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.000", mySymbol);
		return cas + "," + df.format(he) + "," + df.format(hs) + "," + df.format(te) + "," + df.format(ts) + ","
				+ df.format(diamWR) + "," + df.format(wRYoung) + "," + df.format(offset) + "," + df.format(mu_ini)
				+ "," + df.format(force) + "," + df.format(g) + ", '" + nomPoste + "'";
	}
	
	public String toStringnoNomPoste() {
		DecimalFormat df = new DecimalFormat("0.000");
		return cas + "," + df.format(he) + "," + df.format(hs) + "," + df.format(te) + "," + df.format(ts) + ","
				+ df.format(diamWR) + "," + df.format(wRYoung) + "," + df.format(offset) + "," + df.format(mu_ini)
				+ "," + df.format(force) + "," + df.format(g);
	}
	
}
