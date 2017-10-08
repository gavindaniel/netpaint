package model;

import java.awt.Point;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
//import javafx.scene.paint.Color;
import java.awt.Color;

/**
 * PaintObject
 * 
 * PaintObject is the superclass of paint objects Oval, Rectangle, Line, and
 * Picture that can be drawn using a Color, two Points, and some Canvas methods.   
 * 
 * @author Gavin Daniel
 *
 */
public abstract class PaintObject implements Serializable{
	private Color color;
	private Point p1;
	private Point p2;
	private Point origin;
	private String imgFilePath;
	
	
	public void updatePoints(Point p1, Point p2){
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		if (this instanceof Line){
			setPoint1(p1);
			setPoint2(p2);
			return;
		}
		else{
			// left of origin
	 		if (p2.getX() < this.getOrigin().getX()){
	 			x1 = p2.getX();
	 			x2 = this.getOrigin().getX();
	 		}
			// above origin
			if (p2.getY() < this.getOrigin().getY()){
				y1 = p2.getY();
				y2 = this.getOrigin().getY();
			}
			setPoint1(new Point((int) x1, (int) y1));
			setPoint2(new Point((int) x2, (int) y2));
		}
	}
	public void draw(GraphicsContext gc) {
		if (this instanceof Line){
			gc.setStroke(ColorTypeConverter.Awt2Fx(this.getColor()));
			gc.strokeLine(this.getPoint1().getX(), this.getPoint1().getY(), this.getPoint2().getX(), this.getPoint2().getY());
		}
		else if (this instanceof Rectangle){
			gc.setFill(ColorTypeConverter.Awt2Fx(this.getColor()));
			gc.fillRect(this.getPoint1().getX(), this.getPoint1().getY(), getWidth(this.getPoint1(), this.getPoint2()), getHeight(this.getPoint1(), this.getPoint2()));
		}
		else if (this instanceof Oval){
			gc.setFill(ColorTypeConverter.Awt2Fx(this.getColor()));
			gc.fillOval(this.getPoint1().getX(), this.getPoint1().getY(), getWidth(this.getPoint1(), this.getPoint2()), getHeight(this.getPoint1(), this.getPoint2()));
		}
		else if (this instanceof Picture){
			gc.drawImage(this.getImage(), this.getPoint1().getX(), this.getPoint1().getY(), getWidth(this.getPoint1(), this.getPoint2()), getHeight(this.getPoint1(), this.getPoint2()));
		}
	}
	
	public double getWidth(Point p1, Point p2){
		return ( Math.abs(p1.getX() - p2.getX()) );
	}
	public double getHeight(Point p1, Point p2){
		return ( Math.abs(p1.getY() - p2.getY()) );
	}
	public Point getOrigin() {
		return this.origin;
	}
	public void setOrigin(Point o){
		this.origin = o;
	}
	public Point getPoint1(){
		return this.p1;
	}
	public void setPoint1(Point p1){
		this.p1 = p1;
	}
	public Point getPoint2(){
		return this.p2;
	}
	public void setPoint2(Point p2){
		this.p2 = p2;
	}
	public Image getImage(){
		return new Image(this.imgFilePath);
	}
	public void setImageFilePath(String image_src){
		this.imgFilePath = ("file:images/doge.jpeg");
	}
	public Color getColor(){
		return this.color;
	}
	public void setColor(Color c){
		this.color = c;
	}
}