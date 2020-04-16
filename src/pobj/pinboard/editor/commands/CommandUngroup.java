package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

/**
 * Classe de la commande Ungroup
 * @author walidsadat
 *
 */
public class CommandUngroup implements Command {
	
	private List<ClipGroup> toUngroup = new ArrayList<>();
	private EditorInterface editor;
	
	public CommandUngroup(EditorInterface editor, ClipGroup toUngroup) {
		this.toUngroup.add(toUngroup);
		this.editor = editor;
	}
	
	public CommandUngroup(EditorInterface editor, List<ClipGroup> toUngroup) {
		this.toUngroup = toUngroup;
		this.editor = editor;
	}

	@Override
	public void execute() {
		for(ClipGroup g:toUngroup) {
			editor.getBoard().removeClip(g);
			editor.getBoard().addClip(g.getClips());
		}
	}

	@Override
	public void undo() {
		for(ClipGroup g:toUngroup) {
			editor.getBoard().addClip(g);
			editor.getBoard().removeClip(g.getClips());
		}
	}

}
