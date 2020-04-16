package pobj.pinboard.document;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Classe d'une image
 * @author walidsadat
 */
public class ClipImage extends AbstractClip {
	private static final long serialVersionUID = 3951930574002936932L;
	private transient Image image;
	private File filename;
	
	/**
	 * Constructeur d'une image
	 * @param left
	 * 		Cordonnée gauche
	 * @param top
	 * 		Cordonnée haute
	 * @param filename
	 * 		Fichier source de l'image
	 */
	public ClipImage(double left, double top, File filename) {
		super(left, top, 0, 0, Color.GREY);
		if(filename == null)
			return;
		this.filename = filename;
		image = new Image("file://"+filename.getAbsolutePath());
		setGeometry(getLeft(),getTop(),image.getWidth()+getLeft(),image.getHeight()+getTop());
	}
	
	public void rechargerImage() {
		image = new Image("file://"+filename.getAbsolutePath());
	}
	/**
	 * Affiche l'image
	 */
	@Override
	public void draw(GraphicsContext ctx) {
		ctx.drawImage(image, getLeft(), getTop());
	}

	/**
	 * Vérifie si le point de coordonnées (x,y) est à l'interieur de l'element
	 */
	@Override
	public boolean isSelected(double x, double y) {
		return (x <= (image.getWidth()+getLeft()) && x >= getLeft() && y <= (image.getHeight() + getTop()) && y >= getTop());
	}
	/**
	 * Retourne une copie de l'image
	 */
	@Override
	public Clip copy() {
		return (new ClipImage(getLeft(),getTop(), filename));
	}

}
