package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board {
	private List<Clip> list;
	
	public Board() {
		list = new ArrayList<>();
	}
	
	public List<Clip> getContents(){
		return list;
	}
	
	public void addClip(Clip clip) {
		list.add(clip);
	}
	
	public void addClip(List<Clip> clip) {
		for(Clip c:clip)
			list.add(c);
	}
	
	public void removeClip(Clip clip) {
		list.remove(clip);
	}
	
	public void removeClip(List<Clip> clip) {
		for(Clip c:clip)
			list.remove(c);
	}
	
	public void draw(GraphicsContext gc) {
		(new ClipRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight(),Color.WHITE)).draw(gc);
		for(Clip c:list) {
			c.draw(gc);
		}
		
	}
}
