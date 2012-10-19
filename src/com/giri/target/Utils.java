/*	
*	License
*	
*	This file is part of The TARGet framework
* 
*   	/__  ___/ // | |     //   ) )  //   ) )
*   	  / /    //__| |    //___/ /  //         ___    __  ___
*   	 / /    / ___  |   / ___ (   //  ____  //___) )  / /
*   	/ /    //    | |  //   | |  //    / / //        / /
*      / /    //     | | //    | | ((____/ / ((____    / /
*   	 
*	    ______     __,             _ ___              ,____                                                   
*      (  /       /  |            ( /   )              /   )                                            
*	     /       /-.-|             /-.-<              /  __                                  
*  Web _/est   _/    |_utomation f/     \_amework by (___/iri
*  -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.
*	

*  TARGet is free software: you can redistribute it and/or
*  modify it  under  the  terms  of  the  GNU  General Public License as 
*  published  by  the  Free  Software Foundation,  either  version  3 of 
*  the License, or any later version.
*
*  TARGet is distributed in the hope that it will be useful,
*  but  WITHOUT  ANY  WARRANTY;  without  even the  implied  warranty  of
*  MERCHANTABILITY   or   FITNESS   FOR  A  PARTICULAR  PURPOSE.  See the
*  GNU General Public License for more details.
*
*  You should have received a copy of the GNU General Public License
*  along with The SeleniumFlex-API.
*  If not, see http://www.gnu.org/licenses/
*  
* 
*  @Author	Gireesh Kumar G - gireeshkumar.g@gmail.com
*  @Date 	July 2010
*
*/
package com.giri.target;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableWorkbook;

/**
 * @author Gireesh Kumar G
 * @Created Aug 6, 2009
 */
public class Utils {

	public static ImageIcon toImageIcon(URL resource,int width, int height){
		return new ImageIcon(getScaledImage(new ImageIcon(resource).getImage(),width, height));  //  @jve:decl-index=0:
	}
	
	/**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    private static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

}
