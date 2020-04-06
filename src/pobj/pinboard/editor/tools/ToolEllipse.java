package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.EditorInterface;

public class ToolEllipse implements Tool {
	ClipEllipse ellipse;
	double x,y;
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		ellipse = new ClipEllipse(x,y,x,y,Color.TRANSPARENT);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		i.getBoard().removeClip(ellipse);
		ellipse.setGeometry(x,y,e.getX(),e.getY());
		if(ellipse.getLeft() > ellipse.getRight())
			ellipse.setGeometry(ellipse.getRight(),ellipse.getTop(),ellipse.getLeft(),ellipse.getBottom());
		if(ellipse.getTop() > ellipse.getBottom())
			ellipse.setGeometry(ellipse.getLeft(),ellipse.getBottom(),ellipse.getRight(),ellipse.getTop());
		i.getBoard().addClip(ellipse);
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		i.getBoard().removeClip(ellipse);
		ellipse.setGeometry(x,y,e.getX(),e.getY());
		if(ellipse.getLeft() > ellipse.getRight())
			ellipse.setGeometry(ellipse.getRight(),ellipse.getTop(),ellipse.getLeft(),ellipse.getBottom());
		if(ellipse.getTop() > ellipse.getBottom())
			ellipse.setGeometry(ellipse.getLeft(),ellipse.getBottom(),ellipse.getRight(),ellipse.getTop());
		ellipse.setColor(Color.BLACK);
		i.getBoard().addClip(ellipse);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolEllipse";
	}

}