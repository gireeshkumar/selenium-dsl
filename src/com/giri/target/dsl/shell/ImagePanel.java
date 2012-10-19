package com.giri.target.dsl.shell;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
       try {                
    	   InputStream stream = this.getClass().getClassLoader().getResourceAsStream("TARGET.jpg");
          try {
			image = ImageIO.read(stream);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
          setPreferredSize(size);
          setMinimumSize(size);
          setMaximumSize(size);
          setSize(size);
          setLayout(null);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

    }

}

