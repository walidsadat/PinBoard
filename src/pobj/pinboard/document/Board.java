package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe de la planche de travail
 * @author walidsadat
 *
 */
public class Board {
	/** Liste qui contient les élements de la planche */
	private List<Clip> list;
	
	/**
	 * Constructeur de la planche
	 */
	public Board() {
		list = new ArrayList<>();
	}
	
	/**
	 * Accès à la liste des élements de la planche
	 */
	public List<Clip> getContents(){
		return list;
	}
	
	/**
	 * Ajoute une element à la planche
	 */
	public void addClip(Clip clip) {
		list.add(clip);
	}
	
	/**
	 * Ajoute une liste d'élements à la planche
	 */
	public void addClip(List<Clip> clip) {
		for(Clip c:clip)
			list.add(c);
	}
	
	/**
	 * Enleve un element de la planche
	 */
	public void removeClip(Clip clip) {
		list.remove(clip);
	}
	
	/**
	 * Enleve une liste d'élements de la planche
	 */
	public void removeClip(List<Clip> clip) {
		for(Clip c:clip)
			list.remove(c);
	}
	
	/**
	 * Dessine la planche dans un contexte graphique
	 */
	public void draw(GraphicsContext gc) {
		(new ClipRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight(),Color.WHITE)).draw(gc);
		for(Clip c:list) {
			c.draw(gc);
		}
		
	}
}
