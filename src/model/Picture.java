package model;
import java.awt.Point;
import model.PaintObject;

public class Picture extends PaintObject {
	public Picture (Point p1, Point p2, String fileName){
		this.setImageFilePath(fileName);
		this.setOrigin(p1);
		this.updatePoints(p1, p2);
	}
}
