package drawing;
import java.awt.*;
//java.awt.Graphics2D 在java平台上呈现出二维形状，文本和图像的基础类,它提供了丰富的绘图方法
public class DrawPictureCanvas extends Canvas{//创建画板类
	private Image image=null;//创建画板中展示的图片对象
	/***
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image=image;
	}
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	public void update(Graphics g) {
		paint(g);
	}
}
