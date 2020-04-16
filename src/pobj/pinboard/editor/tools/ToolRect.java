package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.document.Couleur;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

/**
 * Classe de l'outil rectangle
 * @author walidsadat
 */
public class ToolRect implements Tool {
	private ClipRect rect;
	private double x,y;
	private Couleur color;
	/**
	 * Constructeur de l'outil rectangle
	 * @param color
	 * 			Couleur du rectangle
	 */
	public ToolRect(Color color) {
		this.color = new Couleur(color);
	}

	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		rect = new ClipRect(x,y,x,y,color.getColor());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
			rect.setGeometry(x,y,e.getX(),e.getY());
			if(rect.getLeft() > rect.getRight())
				rect.setGeometry(rect.getRight(),rect.getTop(),rect.getLeft(),rect.getBottom());
			if(rect.getTop() > rect.getBottom())
				rect.setGeometry(rect.getLeft(),rect.getBottom(),rect.getRight(),rect.getTop());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		CommandAdd cmd = new CommandAdd(i,rect);
		cmd.execute();
		i.getUndoStack().addCommand(cmd);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
			rect.draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolRect";
	}

}
