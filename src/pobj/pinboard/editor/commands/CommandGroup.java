package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command {
	
	private ClipGroup group;
	private List<Clip> toGroup = new ArrayList<>();
	private EditorInterface editor;
	
	public CommandGroup(EditorInterface editor, Clip toGroup) {
		this.toGroup.add(toGroup);
		this.group = new ClipGroup();
		this.group.addClip(toGroup);
		this.editor = editor;
	}
	
	public CommandGroup(EditorInterface editor, List<Clip> toGroup) {
		this.group = new ClipGroup();
		for(Clip c : toGroup) {
			this.group.addClip(c);
			this.toGroup.add(c);
		}
		this.editor = editor;
	}

	@Override
	public void execute() {
		editor.getBoard().removeClip(toGroup);
		editor.getBoard().addClip(group);
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(group);
		editor.getBoard().addClip(toGroup);
	}

}
