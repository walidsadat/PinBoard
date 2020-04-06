package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipLine;
import pobj.pinboard.editor.EditorInterface;

public class ToolLine implements Tool {
	ClipLine line;
	double x,y;
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		line = new ClipLine(x,y,x,y,Color.BLACK);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
			i.getBoard().removeClip(line);
			line.setGeometry(x,y,e.getX(),e.getY());
			i.getBoard().addClip(line);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		i.getBoard().removeClip(line);
		line.setGeometry(x,y,e.getX(),e.getY());
		i.getBoard().addClip(line);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
			i.getBoard().draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolLine";
	}

}
