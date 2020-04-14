package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	private List<Command> undo = new ArrayList<>();
	private List<Command> redo = new ArrayList<>();
	
	public void addCommand(Command cmd) {
		undo.add(cmd);
		redo.clear();
	}
	
	public void undo() {
		if(!isUndoEmpty()) {
			Command cmd = undo.remove(undo.size() - 1);
			cmd.undo();
			redo.add(cmd);
		}
	}
	
	public void redo() {
		if(!isRedoEmpty()) {
			Command cmd = redo.remove(redo.size() - 1);
			cmd.execute();
			undo.add(cmd);
		}
	}
	
	public boolean isUndoEmpty() {
		return undo.isEmpty();
	}
	public boolean isRedoEmpty() {
		return redo.isEmpty();
	}
}
