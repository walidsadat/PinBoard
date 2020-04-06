package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.EditorInterface;

/**
 * Classe de l'outil ellipse
 * @author walidsadat
 */
public class ToolEllipse implements Tool {
	ClipEllipse ellipse;
	double x,y;
	Color color;
	
	/**
	 * Constructeur de l'outil ellipse
	 * @param color
	 * 			Couleur de l'ellipse
	 */
	public ToolEllipse(Color color) {
		this.color = color;
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		ellipse = new ClipEllipse(x,y,x,y,Color.TRANSPARENT);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		ellipse.setGeometry(x,y,e.getX(),e.getY());
		if(ellipse.getLeft() > ellipse.getRight())
			ellipse.setGeometry(ellipse.getRight(),ellipse.getTop(),ellipse.getLeft(),ellipse.getBottom());
		if(ellipse.getTop() > ellipse.getBottom())
			ellipse.setGeometry(ellipse.getLeft(),ellipse.getBottom(),ellipse.getRight(),ellipse.getTop());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		ellipse.setColor(color);
		i.getBoard().addClip(ellipse);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		ellipse.draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolEllipse";
	}

}