package model;

import java.awt.Point;
import model.PaintObject;

public class Picture extends PaintObject {
	public Picture (Point p1, Point p2, String image_src){
		this.setImage(image_src);
		this.setPoints(p1, p2);
	}
}
