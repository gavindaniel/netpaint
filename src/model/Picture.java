package model;
import java.awt.Point;
import model.PaintObject;

public class Picture extends PaintObject {
	public Picture (Point p1, Point p2, String fileName){
//		this.setImage("../images/" + fileName);
		this.setPoints(p1, p2);
	}
}
