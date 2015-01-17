package framework;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageObject extends GeometricObject {
	protected Image img;
	String imgFolder = "images/";
	String completeUrl;

	public ImageObject(String imageFileName, Vertex corner, Vertex movement) {
		super(corner, 0, 0, movement);

		completeUrl = imgFolder+imageFileName;
		
		URL resource = this.getClass().getClassLoader().getResource(completeUrl);
		ImageIcon icon = new ImageIcon(resource);
		img = icon.getImage();

		width = img.getWidth(null);
		height = img.getHeight(null);
	}

	
	
	@Override
	public void paintTo(Graphics g) {
		g.drawImage(img, (int) corner.x, (int) corner.y, null);
	}
}
