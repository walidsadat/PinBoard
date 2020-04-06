package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;

public class ToolImage implements Tool {
	ClipImage image;
	File file;
	ClipRect rect;
	double x,y;
	public ToolImage(File file) {
		this.file = file;
		image = new ClipImage(0,0,file);
		rect = new ClipRect(0,0,image.getRight(),image.getBottom(),Color.TRANSPARENT);
		x = 0;
		y = 0;
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		rect.setGeometry(e.getX(),e.getY(),e.getX()+image.getRight(),e.getY()+image.getBottom());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		i.getBoard().removeClip(image);
		image.setGeometry(e.getX(),e.getY(),image.getRight() ,image.getBottom());
		i.getBoard().addClip(image);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		rect.draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return "ToolImage";
	}

}