package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.editor.EditorInterface;

public interface Tool {
	/** Méthode d'appuie sur la souris */
	public void press(EditorInterface i, MouseEvent e);
	/** Méthode de glissement de la souris */
	public void drag(EditorInterface i, MouseEvent e);
	/** Méthode de relache de la souris */
	public void release(EditorInterface i, MouseEvent e);
	/** Méthode d'affichage de l'outil */
	public void drawFeedback(EditorInterface i, GraphicsContext gc);
	/** Retourne le nom de l'outil */
	public String getName(EditorInterface editor);
}
