package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe d'une ligne
 * @author walidsadat
 *
 */
public class ClipLine extends AbstractClip {
	
	/**
	 * Constructeur d'une ligne
	 * @param left
	 * 		coordonnée gauche
	 * @param top
	 * 		coordonnée haute
	 * @param right
	 * 		coordonnée droite
	 * @param bottom
	 * 		coordonnée basse
	 * @param color
	 * 		Couleur de la ligne
	 */
	public ClipLine(double left, double top, double right, double bottom, Color color){
		super(left, top, right, bottom, color);
	}

	/**
	 * Dessine la ligne
	 */
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setStroke(getColor());
		ctx.strokeLine(getLeft(),getTop(),getRight(),getBottom());
	}

	/**
	 * Vérifie si le point de coordonnées (x,y) est traversé par la ligne
	 */
	@Override
	public boolean isSelected(double x, double y) {
		boolean on = ((y - getTop()) == (((getBottom() - getTop()) / (getRight() - getLeft())) * (x - getLeft())));
		boolean between = ((Math.min(getLeft(), getRight()) <= x && x <= Math.max(getLeft(),getRight())) && 
							  (Math.min(getTop(),getBottom()) <= y && y <= (Math.max(getTop(),getBottom()))));
		return (on && between);
		
	}

	/**
	 * Retourne une copie de la ligne
	 */
	@Override
	public Clip copy() {
		return new ClipLine(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

}
