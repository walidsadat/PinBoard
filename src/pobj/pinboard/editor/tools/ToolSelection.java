package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandMove;

/**
 * Classe de l'outil selection
 * @author walidsadat
 */
public class ToolSelection implements Tool {
	private double x;
	private double y;
	private double _x,_y;

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		_x = x;
		_y = y;
		if(e.isShiftDown())
			i.getSelection().toogleSelect(i.getBoard(),e.getX(),e.getY());
		else
			i.getSelection().select(i.getBoard(),e.getX(),e.getY());
		i.getBoard().removeClip(i.getSelection().getContents());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		for(Clip c:i.getSelection().getContents()) 
			c.move(e.getX() - x, e.getY() - y);
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		i.getUndoStack().addCommand((new CommandMove(i,i.getSelection().getContents(),e.getX() - _x, e.getY() - _y)));
		i.getBoard().addClip(i.getSelection().getContents());
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return null;
	}

}
