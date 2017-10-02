package model;

import java.awt.Point;

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
	private Point p_UPPER_LEFT;
	private Point p_BOTTOM_RIGHT;
	private Image img;
	
	public void setColor(Color c){
		this.color = c;
	}
	public void setPoints(Point p1, Point p2){
		if (p1.getX() < p2.getX()){
			if (p1.getY() < p2.getY()){
				this.p_UPPER_LEFT = new Point((int)p1.getX(), (int)p1.getY());
				this.p_BOTTOM_RIGHT = new Point((int)p2.getX(), (int)p2.getY());
			}
			else {
				this.p_UPPER_LEFT = new Point((int)p1.getX(), (int)p2.getY());
				this.p_BOTTOM_RIGHT = new Point((int)p2.getX(), (int)p1.getY());
			}
		}
		else {
			if (p1.getY() < p2.getY()){
				this.p_UPPER_LEFT = new Point((int)p2.getX(), (int)p1.getY());
				this.p_BOTTOM_RIGHT = new Point((int)p1.getX(), (int)p2.getY());
			}
			else {
				this.p_UPPER_LEFT = new Point((int)p2.getX(), (int)p2.getY());
				this.p_BOTTOM_RIGHT = new Point((int)p1.getX(), (int)p1.getY());
			}
		}
	}
	public void setPoint1(Point p1){
		this.p_UPPER_LEFT = p1;
	}
	public void setPoint2(Point p2){
		this.p_BOTTOM_RIGHT = p2;
	}
	public void setImage(String image_src){
		this.img = new Image("file:images/doge.jpeg");
	}
	public Color getColor(){
		return this.color;
	}
	public Point getUpperLeft(){
		return this.p_UPPER_LEFT;
	}
	public Point getBottomRight(){
		return this.p_BOTTOM_RIGHT;
	}
	public Image getImage(){
		return this.img;
	}

	public double getWidth(){
		return ( Math.abs(this.getUpperLeft().getX() - this.getBottomRight().getX()) );
	}
	public double getHeight(){
		return ( Math.abs(this.getUpperLeft().getY() - this.getBottomRight().getY()) );
	}
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (this instanceof Line){
			gc.setStroke(this.getColor());
			gc.strokeLine(this.getUpperLeft().getX(), this.getUpperLeft().getY(), this.getBottomRight().getX(), this.getBottomRight().getY());
		}
		else if (this instanceof Rectangle){
			gc.setFill(this.getColor());
			gc.fillRect(this.getUpperLeft().getX(), this.getUpperLeft().getY(), this.getWidth(), this.getHeight());
		}
		else if (this instanceof Oval){
			gc.setFill(this.getColor());
			gc.fillOval(this.getUpperLeft().getX(), this.getUpperLeft().getY(), this.getWidth(), this.getHeight());
		}
		else if (this instanceof Picture){
			gc.drawImage(this.getImage(), this.getUpperLeft().getX(), this.getUpperLeft().getY(), this.getWidth(), this.getHeight());
		}
	}
}