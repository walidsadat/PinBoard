package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.Couleur;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

/**
 * Classe de l'outil ellipse
 * @author walidsadat
 */
public class ToolEllipse implements Tool {
	private ClipEllipse ellipse;
	private double x,y;
	private Couleur color;
	
	/**
	 * Constructeur de l'outil ellipse
	 * @param color
	 * 			Couleur de l'ellipse
	 */
	public ToolEllipse(Color color) {
		this.color = new Couleur(color);
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		ellipse = new ClipEllipse(x,y,x,y,color.getColor());
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
		CommandAdd cmd = new CommandAdd(i,ellipse);
		cmd.execute();
		i.getUndoStack().addCommand(cmd);
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