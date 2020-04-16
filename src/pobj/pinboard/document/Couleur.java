package pobj.pinboard.document;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 * Classe de couleur
 * @author walidsadat
 *
 */
public class Couleur implements Serializable {

	private static final long serialVersionUID = 8339907931135669085L;
	private double red;
	private double green;
	private double blue;
	
	public Couleur(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public Couleur(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
	}
	
	//Pour convertir Couleur en Color de JavaFX
	public Color getColor() {
		return Color.color(red,green,blue,1);
	}
	
	public void setRed(double red) {
		this.red = red;
	}
	public void setGreen(double green) {
		this.green = green;
	}
	public void setBlue(double blue) {
		this.blue = blue;
	}
	public void setColor(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
	}
}
