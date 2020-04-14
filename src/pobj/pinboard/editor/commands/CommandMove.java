package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command {
	
	private List<Clip> toMove = new ArrayList<>();
	private EditorInterface editor;
	private double x,y;
	
	public CommandMove(EditorInterface editor, Clip toMove, double x, double y) {
		this.toMove.add(toMove);
		this.editor = editor;
		this.x = x;
		this.y = y;
	}
	
	public CommandMove(EditorInterface editor, List<Clip> toMove, double x, double y) {
		this.toMove = toMove;
		this.editor = editor;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
			editor.getBoard().removeClip(toMove);
			for(Clip c : toMove) {
				c.move(x, y);
			}
			editor.getBoard().addClip(toMove);
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(toMove);
		for(Clip c : toMove) {
			c.move(-x, -y);
		}
		editor.getBoard().addClip(toMove);
	}

}
