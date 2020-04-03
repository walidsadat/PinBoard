package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool {
	ClipRect rect;
	double x,y;
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		rect = new ClipRect(x,y,x,y,Color.GREEN);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
			i.getBoard().removeClip(rect);
			rect.setGeometry(x,y,e.getX(),e.getY());
			i.getBoard().addClip(rect);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		i.getBoard().removeClip(rect);
		rect.setGeometry(x,y,e.getX(),e.getY());
		if(rect.getLeft() > rect.getRight())
			rect.setGeometry(rect.getRight(),rect.getTop(),rect.getLeft(),rect.getBottom());
		if(rect.getTop() > rect.getBottom())
			rect.setGeometry(rect.getLeft(),rect.getBottom(),rect.getRight(),rect.getTop());
		rect.setColor(Color.RED);
		i.getBoard().addClip(rect);
	}

	public ClipRect getRect() {
			return rect;
	}
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolRect";
	}

}
