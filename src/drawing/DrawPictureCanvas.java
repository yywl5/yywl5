package drawing;
import java.awt.*;
//java.awt.Graphics2D ��javaƽ̨�ϳ��ֳ���ά��״���ı���ͼ��Ļ�����,���ṩ�˷ḻ�Ļ�ͼ����
public class DrawPictureCanvas extends Canvas{//����������
	private Image image=null;//����������չʾ��ͼƬ����
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
