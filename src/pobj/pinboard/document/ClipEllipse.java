package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe d'une ellipse
 * @author walidsadat
 */
public class ClipEllipse extends AbstractClip {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3070917392353007894L;

	/**
	 * Constructeur d'une ellipse
	 * @param left
	 * 		coordonnée gauche
	 * @param top
	 * 		coordonnée haute
	 * @param right
	 * 		coordonnée droite
	 * @param bottom
	 * 		coordonnée basse
	 * @param color
	 * 		Couleur de l'ellipse
	 */
	public ClipEllipse(double left, double top, double right, double bottom, Color color){
		super(left, top, right, bottom, color);
	}

	/**
	 * Dessine l'ellipse
	 */
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.setStroke(Color.BLACK);
		ctx.strokeOval(getLeft(),getTop(),getRight()-getLeft(),getBottom()-getTop());
		ctx.fillOval(getLeft(),getTop(),getRight()-getLeft(),getBottom()-getTop());
	}

	/**
	 * Vérifie si le point de coordonnées (x,y) est à l'interieur de l'element
	 */
	@Override
	public boolean isSelected(double x, double y) {
		double cx = (getLeft() + getRight())/2;
		double cy = (getTop() + getBottom())/2;
		double rx = (getRight() - getLeft())/2;
		double ry = (getBottom() - getTop())/2;
		return (Math.pow((x-cx)/rx, 2)+Math.pow((y-cy)/ry, 2)) <= 1;
		
	}

	/**
	 * Retourne une copie de l'ellipse
	 */
	@Override
	public Clip copy() {
		return new ClipEllipse(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

}
