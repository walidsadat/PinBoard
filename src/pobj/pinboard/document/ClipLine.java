package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipLine extends AbstractClip {
	

	public ClipLine(double left, double top, double right, double bottom, Color color){
		super(left, top, right, bottom, color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setStroke(getColor());
		ctx.strokeLine(getLeft(),getTop(),getRight(),getBottom());
	}

	@Override
	public boolean isSelected(double x, double y) {
		boolean on = ((y - getTop()) == (((getBottom() - getTop()) / (getRight() - getLeft())) * (x - getLeft())));
		boolean between = ((Math.min(getLeft(), getRight()) <= x && x <= Math.max(getLeft(),getRight())) && 
							  (Math.min(getTop(),getBottom()) <= y && y <= (Math.max(getTop(),getBottom()))));
		return (on && between);
		
	}

	@Override
	public Clip copy() {
		return new ClipLine(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

}
