package model;

/**
 *
 * @author Louis
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import controller.DaoManagerMysql;
import data_types.DonneeOutCapteur;

//Il faut lire le fichier 1939351_F2 dans Krakov (fichier)
//Les sÃ©parations entre chaque donnÃ©es sont des ";"

public class TextFileHandler extends Thread {

	private DaoManagerMysql daoManager;
	private String pathFichierCapteur;
	private String nomFichier;
	private boolean flagInterupt;

	public TextFileHandler(String nomFichier) {
		daoManager = DaoManagerMysql.getInstance();
		daoManager.truncateTables();
		pathFichierCapteur = "./Rugo/Ressources/Krakov/" + nomFichier;
		this.nomFichier = nomFichier;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lance le thread poru qu'il envoie les données dans la bdd
	 */
	public void run() {
		flagInterupt = false;
		// Le fichier qu'on vient lire en entrée qui contient les données capteurs
		File file = new File(pathFichierCapteur);

		// Le scanner utilisé sur notre fichier txt pour la lecture
		Scanner scan;
		try {
			scan = new Scanner(file);

			// Chaîne de caractères à laquelle on ajoute le contenu du fichier lu
			String fileContent = "";

			// Chaîne de caractère où l'on ajoute le contenu du fichier lu delimiter par
			// delimiter
			String cutting = "";

			// On parcourt le fichier ligne par ligne et on ajoute ce qu'on lit dans un
			// notre string fileContent
			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			/*
			 * string tokenizer permet de decouper une chaine de caract en tableau de chaine
			 * de caract à chaque delimiter
			 */
			StringTokenizer stk = new StringTokenizer(fileContent, ";");

			// On crée notre tableau afin de stocker les paramètres de notre capteurs
			double[] parameter = new double[25];

			// Compteur
			int i = 0;
			long start = System.currentTimeMillis();// PRENDRE TEMPS
			long lastTime = 0;

			// System.out.println("test avant while");
			while (stk.hasMoreTokens() && !flagInterupt) {

				cutting = stk.nextToken(); // On decoupe l'ensemble de notre chaîne
				if (i == 0) {
					cutting = "100"; // On re indexe le fichier pour tout le temps avoir la même strucutre :
										// "100","Données1",...,"Données23"
				} else if (i == 24) {
					cutting = "100"; // Pour éviter une erreur : texte généré pour permettre le parsing à la dernière
										// valeur
					i = 0; // On ré-initialise pour la lecture de la ligne suivante
				} else {
					// System.out.println("Test avant substring : " + cutting);
					cutting = cutting.substring(1); // Pour renvoyer une sous chaîne
				}
				// System.out.println(i + " : " + cutting); // Pour vérifier le contenu du
				// découpage à chaque itération

				fileContent = fileContent.concat(cutting); // on ajoute le decoupage à notre chaine de caractères2

				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE); // Pour lire correctement les valeurs
																				// avec
																				// des "," au lieu de "."
				Number number = format.parse(cutting); // On convertir le string en valeur numérique
				parameter[i++] = number.doubleValue(); // On convertit la valeur numérique en double

				if (i == 24) {
					long waitFor = (long) (parameter[2] * 1000 - lastTime);// GET TEMPS DANS DONNEE
					lastTime = (long) (parameter[2] * 1000);
					long lancementRequete = start + waitFor;
					// On crée notre élément DonneCapteurIn qu'on va vouloir ajouter à la BDD dès
					// qu'on a les 23 "paramètres" qui le caractérisent
					DonneeOutCapteur donneesCapteur = new DonneeOutCapteur((int) parameter[0], (int) parameter[1],
							parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7],
							parameter[8], parameter[9], parameter[10], parameter[11], parameter[12], parameter[13],
							parameter[14], parameter[15], parameter[16], parameter[17], parameter[18], parameter[19],
							parameter[20], parameter[21], parameter[22], parameter[23], nomFichier);
					while (System.currentTimeMillis() < lancementRequete) {
						sleep(1);
					}
					daoManager.insertDonneeOutCapteur(donneesCapteur); // Commande pour INSERT IN TABLE(BDD)
					start = System.currentTimeMillis();
					if (isInterrupted())
						break;
				}
			}

			System.out.println("Text File Handler thread just ended");

		} catch (FileNotFoundException | InterruptedException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Fichier : Ligne par ligne ET ; par ; (notre delimiter)
		// FileWriter writer = new
		// FileWriter("C:/Users/Louis/Desktop/DonneesCapteurs.txt"); // On crée un
		// nouveau fichier
		// txt
		// writer.write(fileContent); // On permet l'écriture dans notre nouveau fichier
		// writer.close(); // On ferme le fichier après avoir écrit
	}

	/**
	 * termine thread maison
	 */
	public void setIsTerminated() {
		// TODO Auto-generated method stub
		flagInterupt = true;
	}
}