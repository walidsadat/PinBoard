package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	private List<Clip> list = new ArrayList<>();
	private List<ClipboardListener> listeners = new ArrayList<>();
	private static Clipboard clipboard = new Clipboard();
	
	private Clipboard() {
	}
	
	public void copyToClipboard(List <Clip> clips) {
		list.clear();
		for(Clip c : clips)
			list.add(c.copy());
		for(ClipboardListener l: listeners)
			l.clipboardChanged();
	}
	
	public List<Clip> copyFromClipboard(){
		List<Clip> copy = new ArrayList<>();
		for(Clip c : list)
			copy.add(c.copy());
		for(ClipboardListener l: listeners)
			l.clipboardChanged();
		return copy;
	}
	
	public void clear() {
		list.clear();
		for(ClipboardListener l: listeners)
			l.clipboardChanged();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public static Clipboard getInstance() {
		return clipboard;
	}
	
	public void addListener(ClipboardListener listener) {
		listeners.add(listener);
	}
	public void removeListener(ClipboardListener listener) {
		listeners.remove(listener);
	}

}
