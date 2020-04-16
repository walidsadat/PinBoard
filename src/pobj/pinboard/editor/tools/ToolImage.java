package pobj.pinboard.editor.tools;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

/**
 * Classe de l'outil image
 * @author walidsadat
 *
 */
public class ToolImage implements Tool {
	private ClipImage image;
	
	/**
	 * Constructeur de l'outil image
	 * @param file
	 * 			Fichier source de l'image
	 */
	public ToolImage(File file) {
		image = new ClipImage(0,0,file);
	}
	
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		//Ne fait rien
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		image.setGeometry(e.getX(),e.getY(),e.getX()+image.getRight(),e.getY()+image.getBottom());
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
		image.draw(gc);
	}


	@Override
	public String getName(EditorInterface editor) {
		return "ToolImage";
	}

}