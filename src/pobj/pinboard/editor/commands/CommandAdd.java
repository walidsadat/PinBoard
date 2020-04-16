package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

/**
 * Classe de la commande Add
 * @author walidsadat
 *
 */
public class CommandAdd implements Command {
	
	private List<Clip> toAdd = new ArrayList<>();
	private EditorInterface editor;
	
	public CommandAdd(EditorInterface editor, Clip toAdd) {
		this.toAdd.add(toAdd);
		this.editor = editor;
	}
	
	public CommandAdd(EditorInterface editor, List<Clip> toAdd) {
		this.toAdd = toAdd;
		this.editor = editor;
	}

	@Override
	public void execute() {
			editor.getBoard().addClip(toAdd);
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(toAdd);
	}

}
