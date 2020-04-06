package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolRect implements Tool {
	ClipRect rect;
	double x,y;
	Color color;
	public ToolRect(Color color) {
		this.color = color;
	}

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		rect = new ClipRect(x,y,x,y,Color.TRANSPARENT);
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
		rect.setColor(color);
		i.getBoard().addClip(rect);
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
