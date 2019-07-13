import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class Engine {
	String imgLocation = "Images/mona_lisa.jpg";
	String newImgLocation = "Images/stego_mona_lisa.jpg";
	BufferedImage img = getImage();
	BufferedImage newImg;
	String[] messageArray;
	String message;
	
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			File f = new File(imgLocation);
			image = ImageIO.read(f);
			
			int imgWidth = image.getWidth();
			int imgHeight = image.getHeight();
			
			image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
			
			image = ImageIO.read(f);
			System.out.println("Image Read");
		}
		catch(IOException e) {
			System.out.println("Error "+e);
		}
		
		return image;
	}
	
	public int readRGBValue(int x, int y, BufferedImage img) {
		return img.getRGB(x, y);
	}

	
	public void setMessage(int number) {
		message = Integer.toBinaryString(number);
		this.messageArray = to22DigitBinary(splitInput(message));
	}
	
	public String[] splitInput(String input) {
		messageArray = input.split("");
		return messageArray;
	}
	
	public void encodeImage() {
		int implementedCode = 0;
		Color pixelColour = null;
		Color newPixelColour = null;
		String[] redBinary = new String[8];
		int x = 50;
		int y = 10;
		
		newImg = img;
		
		while(implementedCode < messageArray.length) {
			
			pixelColour = new Color(img.getRGB(x, y));
			
			redBinary = to8DigitBinary(Integer.toBinaryString(pixelColour.getRed()).split(""));
			redBinary[redBinary.length - 1] = messageArray[implementedCode];
			
			newPixelColour = new Color(Integer.parseInt(arrayToString(redBinary), 2), pixelColour.getGreen(), pixelColour.getBlue());
			
			newImg.setRGB(x, y, newPixelColour.getRGB());
			
			
//			System.out.println("Original: " + pixelColour.getRGB());
//			System.out.println("Altereed: " + newPixelColour.getRGB());
			
			System.out.println("Original: " + Integer.toBinaryString(pixelColour.getRed()));
			System.out.println("Altereed: " + Integer.toBinaryString(newPixelColour.getRed()) + "\n");
			
//			System.out.println("Original: " + pixelColour.getRed() + " " + pixelColour.getGreen() + " " + pixelColour.getBlue());
//			System.out.println("Altereed: " + Integer.parseInt(arrayToString(redBinary), 2) + " " + newPixelColour.getGreen() + " " + newPixelColour.getBlue() + "\n");
			
			implementedCode++;
			x+=50;
			y+=10;
		}
	}
	
	public String[] to8DigitBinary(String[] number) {
		String[] binary = new String[8];
		int implementedString = 0;
		
		while (implementedString < binary.length) {
			if(implementedString < number.length) {
				binary[binary.length - implementedString - 1] = number[number.length - implementedString - 1];
			}
			else {
				binary[binary.length - implementedString - 1] = "0";
			}
			implementedString++;
		}
		
		return binary;
	}
	
	public String arrayToString(String[] number) {
		int implementedString = 0;
		String binary = "";
		
		while(implementedString < number.length) {
			binary+= number[implementedString];
			implementedString++;
		}
		
		return binary;
	}
	
	public void checkChanges() {
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		
		try {
			File f = new File(imgLocation);
			img1 = ImageIO.read(f);
			int imgWidth = img1.getWidth();
			int imgHeight = img1.getHeight();
			
			img1 = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
			
			img1 = ImageIO.read(f);
			System.out.println("Image 1 Read");
			
			File f2 = new File(newImgLocation);
			img2 = ImageIO.read(f2);
			int imgWidth2 = img2.getWidth();
			int imgHeight2 = img2.getHeight();
			
			img2 = new BufferedImage(imgWidth2, imgHeight2, BufferedImage.TYPE_INT_ARGB);
			
			img2 = ImageIO.read(f2);
			System.out.println("Image 2 Read");
		}
		catch(IOException e) {
			System.out.println("Error "+e);
		}
		
		int checkedPixels = 0;
		int x = 50;
		int y = 10;
		String[] valueReceived = new String[22];
		while (checkedPixels < 22) {
			Color pixelColour1 = new Color(img1.getRGB(x, y));
			Color pixelColour2 = new Color(img2.getRGB(x, y));
			
			String pixelRed1 = arrayToString(to8DigitBinary(splitInput(Integer.toBinaryString(pixelColour1.getRed()))));
			String pixelRed2 = arrayToString(to8DigitBinary(splitInput(Integer.toBinaryString(pixelColour2.getRed()))));
			System.out.println("Original: " + pixelRed1);
			System.out.println("Altereed: " + pixelRed2 + "\n");
			
			valueReceived[checkedPixels] = to8DigitBinary(splitInput(Integer.toBinaryString(pixelColour2.getRed())))[7];
			
			checkedPixels++;
			x+=50;
			y+=10;
		}
		
		String binaryString = arrayToString(to22DigitBinary(valueReceived));
		System.out.println("Scanned Binary from Stego image: " + binaryString);
		System.out.println("Converted to integer: " + Integer.parseInt(binaryString ,2));
	}
	
	public String[] to22DigitBinary(String[] number) {
		String[] binary = new String[22];
		int implementedString = 0;
		
		while (implementedString < binary.length) {
			if(implementedString < number.length) {
				binary[binary.length - implementedString - 1] = number[number.length - implementedString - 1];
			}
			else {
				binary[binary.length - implementedString - 1] = "0";
			}
			implementedString++;
		}
		
		return binary;
	}
	
	public void createImage() {
		File f = new File(newImgLocation);
		try {
			ImageIO.write(newImg, "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
