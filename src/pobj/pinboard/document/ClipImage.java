package pobj.pinboard.document;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage extends AbstractClip {
	Image image;
	
	public ClipImage(double left, double top, File filename) {
		super(left, top, 0, 0, Color.TRANSPARENT);
		if(filename == null)
			return;
		image = new Image("file://"+filename.getAbsolutePath());
		setGeometry(getLeft(),getTop(),image.getWidth()+getLeft(),image.getHeight()+getTop());
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(image, getLeft(), getTop());
	}

	@Override
	public boolean isSelected(double x, double y) {
		return (x <= (image.getWidth()+getLeft()) && x >= getLeft() && y <= (image.getHeight() + getTop()) && y >= getTop());
	}

	@Override
	public Clip copy() {
		return (new ClipImage(getLeft(),getTop(),new File(image.getUrl())));
	}

}
