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
	public void setPoints(Point p1, Point p2){
		if (p1.getX() < p2.getX()){
			if (p1.getY() < p2.getY()){
				this.p1 = new Point((int)p1.getX(), (int)p1.getY());
				this.p2 = new Point((int)p2.getX(), (int)p2.getY());
			}
			else {
				this.p1 = new Point((int)p1.getX(), (int)p2.getY());
				this.p2 = new Point((int)p2.getX(), (int)p1.getY());
			}
		}
		else {
			if (p1.getY() < p2.getY()){
				this.p1 = new Point((int)p2.getX(), (int)p1.getY());
				this.p2 = new Point((int)p1.getX(), (int)p2.getY());
			}
			else {
				this.p1 = new Point((int)p2.getX(), (int)p2.getY());
				this.p2 = new Point((int)p1.getX(), (int)p1.getY());
			}
		}
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

	public double getWidth(){
		return ( Math.abs(this.getPoint1().getX() - this.getPoint2().getX()) );
	}
	public double getHeight(){
		return ( Math.abs(this.getPoint1().getY() - this.getPoint2().getY()) );
	}
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (this instanceof Line){
			gc.setStroke(this.getColor());
			gc.strokeLine(this.getPoint1().getX(), this.getPoint1().getY(), this.getPoint2().getX(), this.getPoint2().getY());
		}
		else if (this instanceof Rectangle){
			gc.setFill(this.getColor());
			gc.fillRect(this.getPoint1().getX(), this.getPoint1().getY(), this.getWidth(), this.getHeight());
		}
		else if (this instanceof Oval){
			gc.setFill(this.getColor());
			gc.fillOval(this.getPoint1().getX(), this.getPoint1().getY(), this.getWidth(), this.getHeight());
		}
		else if (this instanceof Picture){
			gc.drawImage(this.getImage(), this.getPoint1().getX(), this.getPoint1().getY(), this.getWidth(), this.getHeight());
		}
	}
}