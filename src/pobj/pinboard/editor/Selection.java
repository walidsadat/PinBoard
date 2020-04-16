package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
/**
 * Classe de selection
 * @author walidsadat
 */
public class Selection {
	/** Liste des élements sélectionées */
	private List<Clip> list = new ArrayList<>();
	
	/**
	 * Méthode de selection d'un seul élement
	 */
	public void select(Board board, double x, double y) {
		if(list.size() == 1)
			if(list.get(0).isSelected(x, y)) {
				list.remove(0);
				return;
			}	
		clear();
		for(Clip c:board.getContents())
			if (list.isEmpty())
				if(c.isSelected(x, y)) {
					list.add(c);
					break;
				}
	}
	
	/**
	 * Méthode de selection de plusieur élements
	 */
	public void toogleSelect(Board board, double x, double y) {
		for(Clip c:board.getContents()) {
			if(c.isSelected(x, y)) {
				if(list.contains(c))
					list.remove(c);
				else
					list.add(c);
			}
		}
	}
	
	/**
	 * Vider la liste des élements
	 */
	public void clear(){
		list.clear();
	}
	
	/**
	 * Accès à la liste des élements
	 */
	public List<Clip> getContents(){
		return list;
	}
	
	/**
	 * Affiche les élements sélectionnés
	 */
	public void drawFeedback(GraphicsContext gc) {
		gc.setStroke(Color.BLUE);
		for(Clip c: list) {
				gc.strokeRect(c.getLeft(),c.getTop(),c.getRight()-c.getLeft(),c.getBottom()-c.getTop());
		}
	}
}
