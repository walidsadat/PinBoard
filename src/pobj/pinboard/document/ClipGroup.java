package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

/**
 * Classe de groupe d'élements
 * @author walidsadat
 *
 */
public class ClipGroup extends AbstractClip implements Composite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5490907778033839163L;
	/** Liste des élements */
	private List<Clip> list;
	
	/** Constructeur du groupe d'élements */
	public ClipGroup() {
		super(Double.MAX_VALUE,Double.MAX_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,null);
		list = new ArrayList<>();
	}

	/**
	 * Dessine les élements du groupe
	 */
	@Override
	public void draw(GraphicsContext ctx) {
		for(Clip c:list)
			c.draw(ctx);
	}

	/** Déplace le groupe */
	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		move(left,top);
	}
	
	/** Déplace le groupe */
	@Override
	public void move(double x, double y) {
		for(Clip c:list)
			c.move(x, y);
		left += x;
		top += y;
		right += x;
		bottom += y;
	}

	/** Crée une copie du groupe */
	@Override
	public Clip copy() {
		ClipGroup copy = new ClipGroup();
		for(Clip c:list)
			copy.addClip(c.copy());
		return copy;
	}

	/** Retourne la liste des élements du groupe */
	@Override
	public List<Clip> getClips() {
		return list;
	}

	/** Ajoute un élement au groupe */
	@Override
	public void addClip(Clip toAdd) {
		list.add(toAdd);
		left = Math.min(left,toAdd.getLeft());
		right = Math.max(right,toAdd.getRight());
		top = Math.min(top,toAdd.getTop());
		bottom = Math.max(bottom,toAdd.getBottom());
	}

	/** Supprime un élement du groupe */
	@Override
	public void removeClip(Clip toRemove) {
		list.remove(toRemove);
		double l = Double.MAX_VALUE;
		double t = Double.MAX_VALUE;
		double r = Double.MIN_VALUE;
		double b = Double.MIN_VALUE;
		for(Clip c:list) {
			l = Math.min(l,c.getLeft());
			r = Math.max(r,c.getRight());
			t = Math.min(t,c.getTop());
			b = Math.max(b,c.getBottom());
		}
		left = l; right = r; top = t; bottom = b;
			
	}
}
