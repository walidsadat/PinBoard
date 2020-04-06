package pobj.pinboard.document;

import javafx.scene.paint.Color;

/**
 * Classe abstraite pour factoriser les implantations des elements graphiques.
 * @author walidsadat
 */

public abstract class AbstractClip implements Clip {
	/** Coordonée de l'element */
	private double left;
	private double top;
	private double right;
	private double bottom;
	
	/** Couleur de l'element */
	private Color color;
	
	/**
	 * Constructeur de l'element
	 * @param left
	 * 				gauche
	 * @param top
	 * 				haut
	 * @param right
	 * 				droite
	 * @param bottom
	 * 				bas
	 * @param color
	 * 				couleur
	 */
	public AbstractClip(double left, double top, double right, double bottom, Color color){
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}

	/** accès à top */
	@Override
	public double getTop() {
		return top;
	}

	/** accès à left */
	@Override
	public double getLeft() {
		return left;
	}
	
	/** accès à bottom */
	@Override
	public double getBottom() {
		return bottom;
	}

	/** accès à right */
	@Override
	public double getRight() {
		return right;
	}

	/**
	 * Modifie les coordonnées de l'element
	 * @param left
	 * 				nouveau left
	 * @param top
	 * 				nouveau top
	 * @param right
	 * 				nouveau right
	 * @param bottom
	 * 				nouveau bottom
	 */
	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	/**
	 * deplace l'element
	 */
	@Override
	public void move(double x, double y) {
		this.left += x;
		this.right += x;
		this.top += y;
		this.bottom += y;
	}
	
	/**
	 * Vérifie si le point de coordonnées (x,y) est à l'interieur de l'element
	 */
	@Override
	public boolean isSelected(double x, double y) {
		return (x <= right && x >= left && y <= bottom && y >= top);
	}
	
	/**
	 * Modifie la couleur de l'element
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	
	/**
	 * Accès à la couleur de l'element
	 */
	@Override
	public Color getColor() {
		return color;
	}

}
