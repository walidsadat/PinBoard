package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

/**
 * Classe d'un presse-papiers
 * @author walidsadat
 *
 */
public class Clipboard {
	/** Liste des elements dans le presse-papiers */
	private List<Clip> list = new ArrayList<>();
	/** Liste des fenêtres d'éditions enregistrer*/
	private List<ClipboardListener> listeners = new ArrayList<>();
	/** le presse-papiers unique */
	private static Clipboard clipboard = new Clipboard();
	
	/** Un constructeur privé pour éviter que Java ne fournisse un constructeur publique par défaut */
	private Clipboard() {
	}
	
	/**
	 * Ajout des copies d'une liste d'élement au presse-papiers
	 */
	public void copyToClipboard(List <Clip> clips) {
		list.clear();
		for(Clip c : clips)
			list.add(c.copy());
		for(ClipboardListener l: listeners)
			l.clipboardChanged();
	}
	
	/**
	 * @return une copie des élements dans le presse-papiers
	 */
	public List<Clip> copyFromClipboard(){
		List<Clip> copy = new ArrayList<>();
		for(Clip c : list)
			copy.add(c.copy());
		for(ClipboardListener l: listeners)
			l.clipboardChanged();
		return copy;
	}
	
	/**
	 * Vide le presse-papiers
	 */
	public void clear() {
		list.clear();
		for(ClipboardListener l: listeners)
			l.clipboardChanged();
	}
	
	/**
	 * @return true si le presse-papiers est vide, false sinon
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * @return le presse-papiers
	 */
	public static Clipboard getInstance() {
		return clipboard;
	}
	
	/**
	 * Enregistre la fenêtre d'édition
	 */
	public void addListener(ClipboardListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Désenregistre la fenêtre d'édition
	 */
	public void removeListener(ClipboardListener listener) {
		listeners.remove(listener);
	}

}
