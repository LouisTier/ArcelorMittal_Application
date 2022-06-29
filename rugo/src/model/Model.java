package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model implements java.beans.PropertyChangeListener{

	private ArrayList<Orowan> orowan;
	private ArrayList<TextFileHandler> textFileHandler;
	private static Model model;
	private PropertyChangeSupport pcs;

	/**
	 * Instancie le textFileHandler pour la simulation et le module orowan
	 */
	private Model() {
		pcs = new PropertyChangeSupport(this);
		textFileHandler = new ArrayList<>();
		textFileHandler.add(new TextFileHandler("1939351_F2.txt"));
		textFileHandler.add(new TextFileHandler("1939351_F3.txt"));
		orowan = new ArrayList<>();
		orowan.add(new Orowan("1939351_F2.txt"));
		orowan.add(new Orowan("1939351_F3.txt"));
		orowan.get(0).addPropertyChangeListener(this);
		orowan.get(1).addPropertyChangeListener(this);
	}

	/**
	 * model est un sigleton return l'instance
	 */
	public static Model getModel() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	/**
	 * Lance le thread de la simulation
	 */
	public void startSimulation() {
		System.out.println("starting Threads");
		textFileHandler.get(0).start(); // Reset les tables et lance le remplissage de la bdd
		textFileHandler.get(1).start(); // Reset les tables et lance le remplissage de la bdd
		orowan.get(0).start();
		orowan.get(1).start();
	}
	
	public ArrayList<String> getPostesName(){
		ArrayList<String> nomPostes = new ArrayList<>();
		for(int i = 0; i < orowan.size(); i++) {
			nomPostes.add(orowan.get(i).getNomPoste());
		}
		return nomPostes;
	}

	@Override
	/**
	 * Relance les changement dans orowan ou dans textFileHandler
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		//System.out.println(propName);
		switch (propName) {
		case "newData":
			pcs.firePropertyChange("newData", null, null);
			break;
		case "OrowanComputeTime" :
			pcs.firePropertyChange("OrowanComputeTime", evt.getOldValue(), evt.getNewValue());
			break;
		default:
			break;
		}
	}
	
	/**
	* specify who is the listener of model
	* @param listener said listener
	*/
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

	/**
	* Removes a listener of model
	* @param listener said listener
	*/
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
	 * Arrete la simulation et reprepare les threads pour qu'il soit relancable
	 */
	public void stopSim() {
		// TODO Auto-generated method stub
		textFileHandler.get(0).setIsTerminated(); // Reset les tables et lance le remplissage de la bdd
		textFileHandler.get(1).setIsTerminated(); // Reset les tables et lance le remplissage de la bdd
		orowan.get(0).setIsTerminated();
		orowan.get(1).setIsTerminated();
		
		try {
			textFileHandler.get(0).join();
			textFileHandler.get(1).join();
			orowan.get(0).join();
			orowan.get(1).join();
			recreateThreads();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Recreer les threads de 0
	 */
	private void recreateThreads() {
		// TODO Auto-generated method stub
		textFileHandler = new ArrayList<>();
		textFileHandler.add(new TextFileHandler("1939351_F2.txt"));
		textFileHandler.add(new TextFileHandler("1939351_F3.txt"));
		orowan = new ArrayList<>();
		orowan.add(new Orowan("1939351_F2.txt"));
		orowan.add(new Orowan("1939351_F3.txt"));
		orowan.get(0).addPropertyChangeListener(this);
		orowan.get(1).addPropertyChangeListener(this);
	}

}
