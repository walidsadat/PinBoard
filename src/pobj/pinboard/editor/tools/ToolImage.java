package pobj.pinboard.editor.tools;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.document.Couleur;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

/**
 * Classe de l'outil image
 * @author walidsadat
 *
 */
public class ToolImage implements Tool {
	private ClipImage image;
	private ClipRect rect;
	
	/**
	 * Constructeur de l'outil image
	 * @param file
	 * 			Fichier source de l'image
	 */
	public ToolImage(File file) {
		image = new ClipImage(0,0,file);
		rect = new ClipRect(0,0,image.getRight(),image.getBottom(),Color.TRANSPARENT);
	}
	
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		//Ne fait rien
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		rect.setGeometry(e.getX(),e.getY(),e.getX()+image.getRight(),e.getY()+image.getBottom());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		image.setGeometry(e.getX(),e.getY(),image.getRight() ,image.getBottom());
		CommandAdd cmd = new CommandAdd(i,image);
		cmd.execute();
		i.getUndoStack().addCommand(cmd);
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