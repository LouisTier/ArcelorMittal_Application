package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import controller.DaoManagerMysql;
import data_types.DonneeAffichage;
import data_types.DonneeInOrowan;
import data_types.DonneeOutCapteur;
import data_types.DonneeOutOrowan;

public class Orowan extends Thread {

	private LinkedList<DonneeAffichage> linkedListAffichage;
	private DaoManagerMysql daoManagerMysql;
	private String nomPoste;
	private PropertyChangeSupport pcs;
	private boolean flagInterupt;

	public Orowan(String nomPoste) {
		linkedListAffichage = new LinkedList<>();
		this.nomPoste = nomPoste;
		daoManagerMysql = DaoManagerMysql.getInstance();
		pcs = new PropertyChangeSupport(this);
		//System.out.println(nomPoste);
	}

	/**
	 * Calcul la moyenne des X derniere données recus
	 */
	public DonneeAffichage returnMeanOfDataOut() {
		ArrayList<DonneeAffichage> uniqueList = new ArrayList<>();
		while (linkedListAffichage.size() != 0) {
			DonneeAffichage tester = linkedListAffichage.pop();
			if (!linkedListAffichage.contains(tester) && !uniqueList.contains(tester)) {
				uniqueList.add(tester);
			}
		}
		// System.out.println(uniqueList.size());
		double sigma = 0;
		double rollSpeed = 0;
		double friction = 0;
		int nbrUnique = 0;
		String error = "VOID";
		DonneeAffichage donneeAffichage;
		// System.out.println(uniqueList.toString());
		for (DonneeAffichage mAffichage : uniqueList) {
			if (mAffichage.getErreur().equals("VOID")) {
				sigma += mAffichage.getSigma();
				friction += mAffichage.getFriction();
				nbrUnique++;
			} else
				error = mAffichage.getErreur();
			rollSpeed += mAffichage.getRolling_speed();
		}
		if (nbrUnique == 0)
			nbrUnique = 1;
		sigma /= nbrUnique;
		friction /= nbrUnique;
		donneeAffichage = new DonneeAffichage(rollSpeed / uniqueList.size(), sigma, friction,
				uniqueList.get(0).getNomPoste(), error);
		// System.out.println(donneeAffichage.toString());
		pcs.firePropertyChange("newData", null, null);
		return (donneeAffichage);
	}

	/**
	 * lance orowan.exe
	 */
	public void runCmd() throws Exception {
		long start = System.currentTimeMillis();
		String cmd = "./Rugo/Ressources/orowan/Orowan_x64.exe"; // la commande à exec

		ProcessBuilder processBuilder = new ProcessBuilder(cmd); // Prépare le lancement de CMD avec la ma commande

		Process process = processBuilder.start(); // lance le process

		DataOutputStream writer = new DataOutputStream(process.getOutputStream()); // on setup l'ecriture et la lecture
																					// de l'invite de commande
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		// Démarche a suivre d'après le PDF
		try {
			// System.out.println("i\n");
			writer.writeBytes("i\n");
			writer.flush();

			// System.out.println("c\n");
			writer.writeBytes("c\n");
			writer.flush();

			// System.out.println("./Rugo/Ressources/orowan/inv_cst.txt\n");
			writer.writeBytes("./Rugo/Ressources/orowan/" + nomPoste + "\n");
			writer.flush();

			// System.out.println("./Rugo/Ressources/orowan/output.txt\n");
			writer.writeBytes("./Rugo/Ressources/orowan/output_" + nomPoste + "\n");
			writer.flush();

			process.waitFor();
			pcs.firePropertyChange("OrowanComputeTime", nomPoste, System.currentTimeMillis() - start);

			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * on ajoute virtuellement une donnée, quand on arrive a 5, il faut lancer la
	 * moyenne et reset l'arrayList
	 */
	public boolean insertData(DonneeAffichage donneeAffichage) { // J'ai attendu avant Si ça return true il faut r la
																	// moyenne et reset la list (Faudra check pour les
																	// doublons)
		if (donneeAffichage != null) {
			linkedListAffichage.addFirst(donneeAffichage);
			if (linkedListAffichage.size() >= 5) {
				// System.out.println(LocalTime.now());
				return true;
			}
		}
		return false;
	}

	public DonneeInOrowan convertDonneeCapteurs(DonneeOutCapteur donneeOutCapteur) {
		DonneeInOrowan donneeInOrowan = new DonneeInOrowan(1, donneeOutCapteur.getEnThick(),
				donneeOutCapteur.getExThick(), donneeOutCapteur.getEnTens(), donneeOutCapteur.getExTens(),
				donneeOutCapteur.getDaiameter(), donneeOutCapteur.getYoungModulus(), donneeOutCapteur.getAverageSigma(),
				donneeOutCapteur.getMu(), donneeOutCapteur.getRollForce(), donneeOutCapteur.getfSlip(),
				donneeOutCapteur.getNomPoste());
		return donneeInOrowan;
	}

	/**
	 * Lire la sortie sur le fichier
	 */
	public DonneeOutOrowan readOneDataOutFromFile() {

		BufferedReader br = null;
		DonneeOutOrowan donneeOutOrowan = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("./Rugo/Ressources/orowan/output_" + nomPoste));// file name with
																									// path
			int flagFirstLine = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				if (flagFirstLine == 1) {
					flagFirstLine = 0;
				} else {
					String[] lineOfData = sCurrentLine.split("\t");

					NumberFormat format = NumberFormat.getInstance();

					try {
						donneeOutOrowan = new DonneeOutOrowan(format.parse(lineOfData[0]).intValue(), lineOfData[1],
								format.parse(lineOfData[2]).doubleValue(), format.parse(lineOfData[3]).doubleValue(),
								format.parse(lineOfData[4]).doubleValue(), format.parse(lineOfData[5]).doubleValue(),
								format.parse(lineOfData[6]).doubleValue(), format.parse(lineOfData[7]).doubleValue(),
								format.parse(lineOfData[8]).doubleValue(), format.parse(lineOfData[9]).doubleValue(),
								format.parse(lineOfData[10]).doubleValue(), lineOfData[11], nomPoste);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// System.out.println(donneeOutOrowan.toString());
		return donneeOutOrowan;
	}

	/**
	 * on ecrit une donnée d'entrée sur le fichier
	 */
	public boolean writeOneDataInToFile(DonneeInOrowan data) {
		try {
			FileWriter myWriter = new FileWriter("./Rugo/Ressources/orowan/" + nomPoste);
			myWriter.write("Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G\n");
			myWriter.write(data.toStringnoNomPoste().replace(',', '\t'));
			myWriter.close();
		} catch (Exception e) {
			return true;
		}
		return false; // success
	}

	public void run() {
		try {
			flagInterupt = false;
			sleep(1000); // Simulation On a deja reçu des données
			long sleepTime = 1;
			while (!flagInterupt) {
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					System.out.println("ON EST EN RETARD : " + sleepTime);
				long startTime = System.currentTimeMillis();
				DonneeOutCapteur donneeOutCapteur = daoManagerMysql.getLast1DonneeOutCapteur(nomPoste);// recup
				DonneeInOrowan donneeInOrowan = convertDonneeCapteurs(donneeOutCapteur);// convrt
				daoManagerMysql.insertDonneeInOrowan(donneeInOrowan);// Insert
				writeOneDataInToFile(donneeInOrowan);// ecrit dans file isrt_cst.txt
				runCmd(); // run orowan
				DonneeOutOrowan donneeOutOrowan = readOneDataOutFromFile();
				daoManagerMysql.insertDonneeOutOrowan(donneeOutOrowan);
				if (insertData(daoManagerMysql.getLastDonneeAffichage(nomPoste))) { // on ajoute virtuellement une
																					// donnée, quand on arrive a 5, il
																					// faut lancer la moyenne et reset
																					// l'arrayList
					DonneeAffichage donneeAffichage = returnMeanOfDataOut();
					daoManagerMysql.insertDonneeAffichage(
							new DonneeAffichage(donneeAffichage.getRolling_speed(), donneeAffichage.getSigma(),
									donneeAffichage.getFriction(), nomPoste, donneeAffichage.getErreur()));
				}
				long endTime = System.currentTimeMillis();
				sleepTime = 200 - (endTime - startTime);
			}
			System.out.println("orowan thread just ended");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNomPoste() {
		return nomPoste;
	}

	/**
	 * specify who is the listener of orowan
	 * 
	 * @param listener said listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Removes a listener of orowan
	 * 
	 * @param listener said listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	/**
	 * interupt thread maison
	 */
	public void setIsTerminated() {
		// TODO Auto-generated method stub
		flagInterupt = true;
	}
}
