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
//Les séparations entre chaque données sont des ";"

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
	 * Lance le thread poru qu'il envoie les donn�es dans la bdd
	 */
	public void run() {
		flagInterupt = false;
		// Le fichier qu'on vient lire en entr�e qui contient les donn�es capteurs
		File file = new File(pathFichierCapteur);

		// Le scanner utilis� sur notre fichier txt pour la lecture
		Scanner scan;
		try {
			scan = new Scanner(file);

			// Cha�ne de caract�res � laquelle on ajoute le contenu du fichier lu
			String fileContent = "";

			// Cha�ne de caract�re o� l'on ajoute le contenu du fichier lu delimiter par
			// delimiter
			String cutting = "";

			// On parcourt le fichier ligne par ligne et on ajoute ce qu'on lit dans un
			// notre string fileContent
			while (scan.hasNextLine()) {
				fileContent = fileContent.concat(scan.nextLine() + "\n");
			}

			/*
			 * string tokenizer permet de decouper une chaine de caract en tableau de chaine
			 * de caract � chaque delimiter
			 */
			StringTokenizer stk = new StringTokenizer(fileContent, ";");

			// On cr�e notre tableau afin de stocker les param�tres de notre capteurs
			double[] parameter = new double[25];

			// Compteur
			int i = 0;
			long start = System.currentTimeMillis();// PRENDRE TEMPS
			long lastTime = 0;

			// System.out.println("test avant while");
			while (stk.hasMoreTokens() && !flagInterupt) {

				cutting = stk.nextToken(); // On decoupe l'ensemble de notre cha�ne
				if (i == 0) {
					cutting = "100"; // On re indexe le fichier pour tout le temps avoir la m�me strucutre :
										// "100","Donn�es1",...,"Donn�es23"
				} else if (i == 24) {
					cutting = "100"; // Pour �viter une erreur : texte g�n�r� pour permettre le parsing � la derni�re
										// valeur
					i = 0; // On r�-initialise pour la lecture de la ligne suivante
				} else {
					// System.out.println("Test avant substring : " + cutting);
					cutting = cutting.substring(1); // Pour renvoyer une sous cha�ne
				}
				// System.out.println(i + " : " + cutting); // Pour v�rifier le contenu du
				// d�coupage � chaque it�ration

				fileContent = fileContent.concat(cutting); // on ajoute le decoupage � notre chaine de caract�res2

				NumberFormat format = NumberFormat.getInstance(Locale.FRANCE); // Pour lire correctement les valeurs
																				// avec
																				// des "," au lieu de "."
				Number number = format.parse(cutting); // On convertir le string en valeur num�rique
				parameter[i++] = number.doubleValue(); // On convertit la valeur num�rique en double

				if (i == 24) {
					long waitFor = (long) (parameter[2] * 1000 - lastTime);// GET TEMPS DANS DONNEE
					lastTime = (long) (parameter[2] * 1000);
					long lancementRequete = start + waitFor;
					// On cr�e notre �l�ment DonneCapteurIn qu'on va vouloir ajouter � la BDD d�s
					// qu'on a les 23 "param�tres" qui le caract�risent
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
		// FileWriter("C:/Users/Louis/Desktop/DonneesCapteurs.txt"); // On cr�e un
		// nouveau fichier
		// txt
		// writer.write(fileContent); // On permet l'�criture dans notre nouveau fichier
		// writer.close(); // On ferme le fichier apr�s avoir �crit
	}

	/**
	 * termine thread maison
	 */
	public void setIsTerminated() {
		// TODO Auto-generated method stub
		flagInterupt = true;
	}
}