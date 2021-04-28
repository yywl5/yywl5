package drawing;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.mr.util.BackgroundPanel;
/*
 * 
 * ��ʻ�չʾ����
 * 
 * 
 * */
public class PictureWindow extends JWindow{
	private JButton changeButton;//����ͼƬ��ť
	private JButton hiddenButton;//���ذ�ť
	private BackgroundPanel centerPanel;//չʾͼƬ�Ĵ�����ͼ���
	File list[];//ͼƬ�ļ�����
	int index=0;//��ǰѡ�е�ͼƬ����
	DrawPictureFrame frame;//������
	public PictureWindow(DrawPictureFrame frame) {
		this.frame=frame;//���������ֵ��ֵ��������
		setSize(400,460);//���ô�����
		init();//��ʼ��
		addListenere();//���Ӱ�ť����
		/*
		 * �����ʼ��
		 * 
		 * 
		 * */
	}
	private void addListenere() {
		// TODO Auto-generated method stub
		hiddenButton.addActionListener(new ActionListener() {//���ذ�ť��Ӷ�������
			public void actionPerformed(ActionEvent e){//����ʱ
				setVisible(false);//�����岻�ɼ�
				frame.initShowPicButton();//���ര�廹ԭ��ʻ���ť���ı�����
			}
		});
		changeButton.addActionListener(new ActionListener() {//����ͼƬ��ť��Ӷ���������
			public void actionPerformed (ActionEvent e) {//����ʱ
				centerPanel.setImage(getListImage());//���������������ͼƬ
			}
		});
	}
	private void init() {
		// TODO Auto-generated method stub
		Container c=getContentPane();//��ȡ����������
		File dir=new File("src/Picture");//������ʻ��زĶ���
		list=dir.listFiles();//��ȡ�ļ����е������ļ�
		centerPanel =new BackgroundPanel(getListImage());//��ʼ��������壬ʹ��ͼƬ�ļ����е�һ�ż�ʻ�
		c.add(centerPanel,BorderLayout.CENTER);//���������õ��������в�
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);//�����Ҷ����������
		flow.setHgap(20);//ˮƽ���20����
		JPanel southPanel=new JPanel();//�����ϲ����
		southPanel.setLayout(flow);//�ϲ����ʹ�øոմ����õ�������
		changeButton=new JButton("����ͼƬ");//ʵ����������ͼƬ����ť
		southPanel.add(changeButton);//�ϲ������Ӱ�ť
		hiddenButton=new JButton("����");//ʵ���������ء���ť
		southPanel.add(hiddenButton);//�ϲ������Ӱ�ť
		c.add(southPanel,BorderLayout.SOUTH);//�ϲ������õ����������ϲ�λ��
	}
	/*
	 * 
	 * �����ļ�����
	 * 
	 * 
	 * */
	private Image getListImage() {
		// TODO Auto-generated method stub
	    String imgPath=list[index].getAbsolutePath();//��ȡ��ǰ�����µ�ͼƬ�ļ�·��
		ImageIcon image=new ImageIcon(imgPath);//��ȡ��ͼƬ�ļ���ͼ�����
		index++;//������������
		if(index>=list.length) {//���������������ͼƬ�����ˣ�
			index=0;//������������
		}
		return image.getImage();//���ͼ������ͼƬ����
	}
}
