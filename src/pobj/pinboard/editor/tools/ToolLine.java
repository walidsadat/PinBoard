package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipLine;
import pobj.pinboard.editor.EditorInterface;

/**
 * Classe de l'outil ligne
 * @author walidsadat
 */
public class ToolLine implements Tool {
	ClipLine line;
	double x,y;
	Color color;
	
	/**
	 * Constructeur de l'outil ligne
	 * @param color
	 * 			Couleur de la ligne
	 */
	public ToolLine(Color color) {
		this.color = color;
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		line = new ClipLine(x,y,x,y,color);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
			line.setGeometry(x,y,e.getX(),e.getY());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		line.setGeometry(x,y,e.getX(),e.getY());
		i.getBoard().addClip(line);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
			line.draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolLine";
	}

}
