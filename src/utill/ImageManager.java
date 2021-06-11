package utill;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageManager {
	public ImageIcon getScaledIcon(String filename, int width	, int height) {
		//System.out.println(url);
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(filename));
		//이미지의 크기를 줄이는 메서드는 image추상클래스에서 지원한다..
		//현재 아이콘을 image로 변경해보자
		icon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		
		return icon;
	}
}
