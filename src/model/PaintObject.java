package model;

import java.awt.Point;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * PaintObject
 * 
 * PaintObject is the superclass of paint objects Oval, Rectangle, Line, and
 * Picture that can be drawn using a Color, two Points, and some Canvas methods.   
 * 
 * @author Gavin Daniel
 *
 */
public abstract class PaintObject {
	private Color color;
	private Point p1;
	private Point p2;
	private Image img;
	
	
	
	public void setColor(Color c){
		this.color = c;
	}
	public void updatePoints(Point p1, Point p2){
		double w = getWidth(p1,p2);
		double h = getHeight(p1,p2);
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		double temp;
		// left of p1
 		if (p2.getX() < p1.getX()){
 			x1 = p2.getX();
 			x2 = p1.getX();
 		}
		// above p1
		if (p2.getY() < p1.getY()){
			y1 = p2.getY();
			y2 = p1.getY();
		}
		setPoint1(new Point((int) x1, (int) y1));
		setPoint2(new Point((int) x2, (int) y2));
	}
	public void setPoint1(Point p1){
		this.p1 = p1;
	}
	public void setPoint2(Point p2){
		this.p2 = p2;
	}
	public void setImage(String image_src){
		this.img = new Image("file:images/doge.jpeg");
	}
	public Color getColor(){
		return this.color;
	}
	public Point getPoint1(){
		return this.p1;
	}
	public Point getPoint2(){
		return this.p2;
	}
	public Image getImage(){
		return this.img;
	}

	public double getWidth(Point p1, Point p2){
		//return this.getPoint1().getX() - this.getPoint2().getX();
		return ( Math.abs(p1.getX() - p2.getX()) );
	}
	public double getHeight(Point p1, Point p2){
		//return this.getPoint1().getY() - this.getPoint2().getY();
		return ( Math.abs(p1.getY() - p2.getY()) );
	}
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (this instanceof Line){
			gc.setStroke(this.getColor());
			gc.strokeLine(this.getPoint1().getX(), this.getPoint1().getY(), this.getPoint2().getX(), this.getPoint2().getY());
		}
		else if (this instanceof Rectangle){
			gc.setFill(this.getColor());
			gc.fillRect(this.getPoint1().getX(), this.getPoint1().getY(), getWidth(this.getPoint1(), this.getPoint2()), getHeight(this.getPoint1(), this.getPoint2()));
		}
		else if (this instanceof Oval){
			gc.setFill(this.getColor());
			gc.fillOval(this.getPoint1().getX(), this.getPoint1().getY(), getWidth(this.getPoint1(), this.getPoint2()), getHeight(this.getPoint1(), this.getPoint2()));
		}
		else if (this instanceof Picture){
			gc.drawImage(this.getImage(), this.getPoint1().getX(), this.getPoint1().getY(), getWidth(this.getPoint1(), this.getPoint2()), getHeight(this.getPoint1(), this.getPoint2()));
		}
	}
}