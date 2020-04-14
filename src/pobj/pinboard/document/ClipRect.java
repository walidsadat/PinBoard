package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe d'un rectangle
 * @author walidsadat
 *
 */
public class ClipRect extends AbstractClip{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8038823332020674577L;

	/**
	 * Constructeur d'un rectangle
	 * @param left
	 * 		coordonnée gauche
	 * @param top
	 * 		coordonnée haute
	 * @param right
	 * 		coordonnée droite
	 * @param bottom
	 * 		coordonnée basse
	 * @param color
	 * 		Couleur du rectangle
	 */
	public ClipRect(double left, double top, double right, double bottom, Color color){
		super(left, top, right, bottom, color);
	}

	/**
	 * Dessine le rectangle
	 */
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.setStroke(Color.BLACK);
		ctx.strokeRect(getLeft(),getTop(),getRight()-getLeft(),getBottom()-getTop());
		ctx.fillRect(getLeft(),getTop(),getRight()-getLeft(),getBottom()-getTop());
	}

	/**
	 * Retourne une copie du rectangle
	 */
	@Override
	public Clip copy() {
		return new ClipRect(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

}
