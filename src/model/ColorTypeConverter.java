package model;

import java.awt.Color;

public class ColorTypeConverter {
	  public static java.awt.Color Fx2Awt(javafx.scene.paint.Color fxColor) {
	    int r = (int) (255 * fxColor.getRed());
	    int g = (int) (255 * fxColor.getGreen());
	    int b = (int) (255 * fxColor.getBlue());
	    java.awt.Color awtColor = new java.awt.Color(r, g, b);
	    return awtColor;
	  } 

	  public static javafx.scene.paint.Color Awt2Fx(Color awtColor) {
	    int r = awtColor.getRed();
	    int g = awtColor.getGreen();
	    int b = awtColor.getBlue();
	    javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b);
	    return fxColor;
	  }
	}
