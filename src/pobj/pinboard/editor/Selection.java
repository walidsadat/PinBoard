package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.document.ClipRect;

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
		clear();
		for(Clip c:board.getContents())
			if (list.isEmpty())
				if(c.isSelected(x, y))
					list.add(c);
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
		gc.setStroke(Color.LIGHTBLUE);	
		for(Clip c: list) {
			if(c instanceof ClipRect || c instanceof ClipImage)
				gc.strokeRect(c.getLeft(),c.getTop(),c.getRight()-c.getLeft(),c.getBottom()-c.getTop());
			else if(c instanceof ClipEllipse)
				gc.strokeOval(c.getLeft(),c.getTop(),c.getRight()-c.getLeft(),c.getBottom()-c.getTop());
			else
				gc.strokeLine(c.getLeft(),c.getTop(),c.getRight(),c.getBottom());
		}
	}
}
