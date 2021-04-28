package drawing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.mr.util.DrawImageUtil;
import com.mr.util.FrameGetShape;
import com.mr.util.ShapeWindow;
import com.mr.util.Shapes;

@SuppressWarnings("serial")
public class DrawPictureFrame extends JFrame implements FrameGetShape{// �̳д�����//����ʵ�����µĽӿ�
	/**
	 * 
	 */
	boolean drawShape=false;//��ͼ�α�ʶ����
	Shapes shape;//�滭��ͼ��
	
	
	BufferedImage image=new BufferedImage(570,390,BufferedImage.TYPE_INT_BGR);
	Graphics gs=image.getGraphics();//���ͼ��Ļ�ͼ����
	Graphics2D g=(Graphics2D) gs;//����ͼ����ת��ΪGraphics2D����;
	DrawPictureCanvas canvas =new DrawPictureCanvas();
	Color foreColor = Color.BLACK;//����ǰ��ɫ
	Color backgroundColor =Color.WHITE;//���屳��ɫ
	int x=-1;//��һ�������Ƶ�ĺ�����
	int y=-1;//��һ�������Ƶ��������
	
	
	private PictureWindow picWindow;//��ʻ�չʾ����
	private JButton  showPicButton;//չ����ʻ���ť
	
	
	boolean rubber = false;
	private JToolBar toolBar;//������
	private JButton eraserButton;//��Ƥ��ť
	private JToggleButton strokeButton1;//ϸ�߰�ť
	private JToggleButton strokeButton2;//���߰�ť
	private JToggleButton strokeButton3;//�ϴְ�ť
	private JButton backgroundButton;//����ɫ��ť
	private JButton foregroundButton;//ǰ��ɫ��ť
	private JButton clearButton;//�����ť
	private JButton saveButton;//���水ť
	private JButton shapeButton;//��״��ť
	private JMenuItem strokeMenuItem1;//ϸ�߰�ť
	private JMenuItem strokeMenuItem2;//���߲˵�
	private JMenuItem strokeMenuItem3;//�ϴֲ˵�
	private JMenuItem foregroundMenuItem;//ǰ��ɫ�˵�
	private JMenuItem backgroundMenuItem;//����ɫ�˵�
	private JMenuItem eraserMenuItem;//��Ƥ�˵�
	private JMenuItem exitMenuItem;//�˳��˵�
	private JMenuItem SaveMenuItem;//����˵�
	private JMenuItem clearMenuItem;//����˵�
	private JMenuItem shuiyinMenuItem;//ˮӡ�˵�
	private String shuiyin="";//ˮӡ�ַ�����
	public DrawPictureFrame() {
		setResizable(false);// �����С���ܸ���
		setTitle("��ͼ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���ڹر���ֹͣ����
		setTitle("��ͼ����(ˮӡ���ݣ�[ "+shuiyin+"])");
		setBounds(500, 100, 574, 460);// ���ô���λ�úͿ��
		init();//��ʼ��(����)
		addListener();//����������(����)
		
	}// DrawPictureFrame()����
	
	/*
	 * * ��������������
	 * 
	 * @param args - ����ʱ����
	 */
	public void init() {
		g.setColor(backgroundColor);//�ñ���ɫ���û�ͼ�������ɫ
		g.fillRect(0, 0, 570, 390);//�ñ���ɫ�����������
		g.setColor(foreColor);//��ǰ��ɫ���û滭�������ɫ
		canvas.setImage(image);//���û�����ͼ��
		getContentPane().add(canvas);//��������ӵ���������Ĭ�ϲ��ֵ��в�λ��
		toolBar=new JToolBar();//��ʼ��������
		getContentPane().add(toolBar,BorderLayout.NORTH);//����������ӵ��������λ��
		
		
		
		showPicButton=new JButton("չ����ʻ�");//��ʼ����ť���󣬲�����ı�����
		toolBar.add(showPicButton);//��������Ӱ�ť
		picWindow=new PictureWindow(DrawPictureFrame.this);
		//������ʻ�չʾ��壬�������൱�����ĸ�����
		
		
		saveButton=new JButton("����");//��ʼ������������,������ı�����
		toolBar.add(saveButton);//��������Ӱ�ť
		toolBar.addSeparator();//��ӷָ���
		//��ʼ����ť����,������ı�����
		shapeButton= new JButton("ͼ��");//��ʼ����ť����,������ı�����
		toolBar.add(shapeButton);//��������Ӱ�ť
		
		strokeButton1=new JToggleButton("ϸ��");
		strokeButton1.setSelected(true);//ϸ�߰�ť���ڱ�ѡ��״̬
		toolBar.add(strokeButton1);//��������Ӱ�ť
		strokeButton2=new JToggleButton("����");
		toolBar.add(strokeButton2);//��ʼ����ѡ��״̬�İ�ť����,������ı�
		strokeButton3=new JToggleButton("�ϴ�");//���ʴ�ϸ��ť��,��֤ͬʱֻ��һ����ť��ѡ��
		ButtonGroup strokeGroup =new ButtonGroup();
		strokeGroup.add(strokeButton1);//��ť����Ӱ�ť
		strokeGroup.add(strokeButton1);//��ť����Ӱ�ť
		strokeGroup.add(strokeButton3);//��ť����Ӱ�ť
		toolBar.add(strokeButton3);//��������Ӱ�ť
		toolBar.addSeparator();//��ӷָ�
		backgroundButton=new JButton("������ɫ");//��ʼ����ť���󣬲�������ı�����
		toolBar.add(backgroundButton);//��������Ӱ�ť
		foregroundButton=new JButton("ǰ����ɫ");
		toolBar.add(foregroundButton);//��������Ӱ�ť
		toolBar.addSeparator();//��ӷָ���
		
		clearButton=new JButton("���");//��������Ӱ�ť��������ı�
		toolBar.add(clearButton);//��������Ӱ�ť
		
		eraserButton=new JButton("��Ƥ");//��ʼ���ı���������ı�����
		toolBar.add(eraserButton);//��������Ӱ�ť
		toolBar.setFloatable(true);
		
		JMenuBar menuBar=new JMenuBar();//�����˵���
		setJMenuBar(menuBar);//��������˵���
		
		
		JMenu systemMenu =new JMenu("ϵͳ");//��ʼ���˵����󣬲�����ı�����
		menuBar.add(systemMenu);//�˵�����Ӳ˵�����
		
		
		SaveMenuItem=new JMenuItem("����");//��ʼ���˵����󣬲�����ı�����
		systemMenu.add(SaveMenuItem);//�˵���Ӳ˵���
		
		
		systemMenu.addSeparator();//��ӷָ���
		exitMenuItem=new JMenuItem("�˳�");//��ʼ���˵�����󣬲�����ı�����
		systemMenu.add(exitMenuItem);//�˵���Ӳ˵���
		JMenu strokeMenu=new JMenu("����");//��ʼ���˵����󣬲�����ı�����
		menuBar.add(strokeMenu);//�˵�����Ӳ˵���
		strokeMenuItem1=new JMenuItem("ϸ��");//��ʼ���˵�����󣬲�����ı�����
		strokeMenu.add(strokeMenuItem1);//�˵���Ӳ˵���
		strokeMenuItem2=new JMenuItem("����");//��ʼ���˵�����󣬲�����ı�����
		strokeMenu.add(strokeMenuItem2);//�˵���Ӳ˵���
		strokeMenuItem3=new JMenuItem("�ϴ�");//��ʼ���˵�����󣬲�����ı�����
		strokeMenu.add(strokeMenuItem3);//�˵���Ӳ˵���
		
		
		JMenu colorMenu=new JMenu("��ɫ");
		menuBar.add(colorMenu);
		foregroundMenuItem=new JMenuItem("ǰ����ɫ");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem=new JMenuItem("������ɫ");
		colorMenu.add(backgroundMenuItem);
		
		
		
		JMenu editMenu=new JMenu("�༭");
		menuBar.add(editMenu);
		clearMenuItem=new JMenu("���");
		editMenu.add(clearMenuItem);
		eraserMenuItem =new JMenuItem("��Ƥ");
		editMenu.add(eraserMenuItem);
		
		
		
		shuiyinMenuItem=new JMenuItem("����ˮӡ");//��ʼ���˵���ѡ��
		systemMenu.add(shuiyinMenuItem);//�ڲ˵������ѡ��
	}
	private void addListener() {
		//�����������ƶ������¼�
		
		showPicButton.addActionListener(new ActionListener() {//չʾ��ʻ���ť��Ӷ�������
			public void actionPerformed(ActionEvent e) {//����ʱ
				boolean isVisible= picWindow.isVisible();//��ȡ��ʻ�չʾ����ɼ�״̬
				if(isVisible) {//�����ʻ������ǿɼ���
					showPicButton.setText("չ����ʻ�");//�޸İ�ť���ı�
				}else {
					showPicButton.setText("���ؼ�ʻ�");//�޸İ�ť���ı�
					//����ָ����ʻ�չʾ�������ʾλ��
					//������=����������� - ��ʻ�������  - 5
					//������ = ������������
					picWindow.setLocation(getX()-picWindow.getWidth()-5, getY());//
					
					picWindow.setVisible(true);//��ʻ�չʾ����ɼ�
					//
				}
			}
		});
		
		
		
		
		
		
		
		
		canvas.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(final MouseEvent e) {//�������קʱ
				if(x>0&&y>0) {//�����������ƶ��ļ�¼
					if(rubber) {//�����Ƥ��ʶΪtrue,��ʾʹ����Ƥ
						g.setColor(backgroundColor);//��ͼ����ʹ�ñ���ɫ
						g.fillRect(x,y,10,10);//����껬����λ�û�����������
					}else {
						g.drawLine(x,y,e.getX(),e.getY());//����껬����λ�û�ֱ��
					}
				}
				x=e.getX();//�õ���һ�������Ƶ�ĺ�����
				y=e.getY();//�õ���һ�������Ƶ��������
				canvas.repaint();//���»���
			}
			public void mouseMoved(final MouseEvent arg0) {//������ƶ�ʱ,���ʹ����Ƥ��
				if(rubber) {
					Toolkit kit=Toolkit.getDefaultToolkit();//���ϵͳĬ�ϵĹ��߰����
					//���ù��߰���ȡͼƬ
					Image img=kit.createImage("D:/JAVA_EE_Project/һ��������/src/icon/�����Ƥ.png");//����������״ΪͼƬ
					Cursor c=kit.createCustomCursor(img, new Point(0, 0), "clear");//���ù��߰����һ���Զ���Ĺ�����
					setCursor(c);//�ο�ΪͼƬ,����ȵ㣨д�ɣ�0��0�����͹�������ַ���
				}else {
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));//������������״ΪĬ�Ϲ��
				}
			}
	});
		toolBar.addMouseMotionListener(new MouseMotionAdapter() {//�������������ƶ�����
			public void mouseMoved(final MouseEvent arg0) {//������ƶ�ʱ
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));//�������ָ�����״ΪĬ�Ϲ��
			}
		});
		
		
		
		
		canvas.addMouseListener(new MouseAdapter() {//���������굥���¼�����
			public void mouseReleased(final MouseEvent arg0) {//������̧��ʱ
				x=-1;//����һ�������Ƶ�ĺ�����ָ�Ϊ-1
				y=-1;//����һ�������Ƶ��������ָ�Ϊ-1
			}
			public void mousePressed(MouseEvent e) {//����������ʱ,����˰���������ͼ��,�ж�ͼ�ε�����,�����Բ��,
				if(drawShape) {
					switch(shape.getType()) {
					case Shapes.YUAN://�����Բ��,
					//��������λ��,��Բ�������ĵ�.
					int yuanx=e.getX()-shape.getWidth()/2;
					int yuany=e.getY()-shape.getHeigth()/2;
					
					Ellipse2D yuan=new Ellipse2D.Double(yuanx,yuany,shape.getWidth(),shape.getHeigth());
					//����ͼ�Σ�����ָ��λ�úͷ���
					g.draw(yuan);//��ͼ���߻���Բ��
					break;
					case Shapes.FANG://����Ƿ���
					//��������,����괦��ͼ�ε�����λ��
					int fangx=e.getX()-shape.getWidth()/2;
					int fangy=e.getY()-shape.getHeigth()/2;
					//��������ͼ�Σ�����ָ������Ϳ��
					Rectangle2D fang=new Rectangle2D.Double(fangx,fangy,shape.getWidth(),shape.getHeigth());
					//��ͼ���߻�����ͼ��
					g.draw(fang);
					break;
					//����switch���
					
					}
					canvas.repaint();///���»���
					//��ͼ�α�ʶΪfalse,˵��������껭����ͼ��,����������
					drawShape=false;
				}
				
			}
		});
		
		
		strokeButton1.addActionListener(new ActionListener(){//"ϸ��"��ť��Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {//���ʱ
				//�����ʻ�������,��ϸΪ1���أ�����ĩ��������,���ߴ��ʼ��
				BasicStroke bs=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//���ʹ���ʹ�ô˻���
				g.setStroke(bs);
			}
		});
		
		strokeButton2.addActionListener(new ActionListener(){//"����"��ť��Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {//���ʱ
				BasicStroke bs=new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//���ʹ���ʹ�ô˻���
				g.setStroke(bs);
			}
		});
		
		strokeButton3.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs=new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//���ʹ���ʹ�ô˻���
				g.setStroke(bs);
			}
		});
		
		backgroundButton.addActionListener(new ActionListener(){//������ɫ��ť��Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {//����ʱ,��ѡ����ɫ�Ի���,��������Ϊ:�����ڣ����⣬Ĭ��ѡ����ɫ����ɫ��
			//��ѡ����ɫ�Ի���,���������޸�Ϊ:������,����,Ĭ��ѡ����ɫ,����ɫ��,
			Color bgColor=JColorChooser.showDialog(DrawPictureFrame.this,"ѡ����ɫ�Ի���",Color.CYAN);
			if(bgColor!=null) {
				backgroundColor=bgColor;//��ѡ�е���ɫ��ֵ������ɫ����
			}
			backgroundButton.setBackground(backgroundColor);
			g.setColor(backgroundColor);//��ͼ����ʹ�ñ���ɫ
			g.fillRect(0,0,570,390);//��һ��������ɫ�����ķ���������������
		}
		});
		foregroundButton.addActionListener(new ActionListener(){//ǰ��ɫ��ť��Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {
				//����ʱ������ɫѡ��Ի���,��������Ϊ:������,����,Ĭ��ѡ�е���ɫ����ɫ��
				Color color=JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���",Color.GRAY);
				if(color!=null) {
					foreColor=color;//�����ѡ�е���ɫ���ǿյģ���ô�㽫ѡ�е���ɫ��ֵ��ǰ��ɫ����
				}
				//ǰ��ɫ��ɫ������Ҳͬ������Ϊ������ɫ
				foregroundButton.setForeground(foreColor);
				g.setColor(foreColor);//��ͼ����Ҳͬ��ʹ��ǰ��ɫ
			}
		});
		clearButton.addActionListener(new ActionListener(){//�����ť��Ӷ�������
			public void actionPerformed(final ActionEvent arg0) {
				//���ʱ
				g.setColor(backgroundColor);//��ͼ����ʹ�ñ���ɫ
				g.fillRect(0, 0, 570, 390);//��һ��������ɫ�ķ���������������
				g.setColor(foreColor);//��ͼ����ʹ��ǰ��ɫ
				canvas.repaint();//���»���
			}
		});
		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				//����ʱ
				if(eraserButton.getText().equals("��Ƥ")) {
					rubber=true;//������Ƥ��ʶΪtrue
					eraserButton.setText("��ͼ");//�ı䰴ť����ʾ���ı�Ϊ��ͼ
				}else {
					rubber=false;
					eraserButton.setText("��Ƥ");//�ı䰴ť����ʾ���ı�Ϊ��Ƥ
					g.setColor(foreColor);//���û�ͼ����Ϊǰ��ɫ
				}
			}
		});
		
		
	shapeButton.addActionListener(new ActionListener() {
		//ͼ����Ӷ�������
		public void actionPerformed(ActionEvent e) {//����ʱ
			//����ͼ��ѡ�����
			ShapeWindow shapeWindow =new ShapeWindow(DrawPictureFrame.this);
			int shapeButtonWidth=shapeButton.getWidth();
			int shapeWindowWidth=shapeWindow.getWidth();
			int shapeButtonX=shapeButton.getX();//��ȡͼ�ΰ�ť������
			int shapeButtonY=shapeButton.getY();//��ȡͼ�ΰ�ť������
			int shapeWidowX = getX()+shapeButtonX-(shapeWindowWidth-shapeButtonWidth)/2;
			//����ͼ����������꣬�������ʾ�ڡ�ͼ�Ρ���ť�·�
			int shapeWidowY=getY()+shapeButtonY+80;
			//����ͼ���������
			shapeWindow.setLocation(shapeWidowX,shapeWidowY);
			shapeWindow.setVisible(true);//ͼ������ɼ�
		}
	});
	
	
	saveButton.addActionListener(new ActionListener(){//���水ť��Ӷ�������
		public void actionPerformed(final ActionEvent arg0) {//����ʱ
			addWatermark();
			DrawImageUtil.saveImage(DrawPictureFrame.this, image);//��ӡͼƬ
			addWatermark();//���ˮӡ
		}
	});
	
	
	exitMenuItem.addActionListener(new ActionListener(){//�˳��˵�����Ӷ�������
		public void actionPerformed(final ActionEvent e) {//����ʱ
			System.exit(0);//����ر�
		}
	});
	eraserMenuItem.addActionListener(new ActionListener(){//��Ƥ�˵�����Ӷ�������
		public void actionPerformed(final ActionEvent e) {//����ʱ
			if(eraserMenuItem.getText().equals("��Ƥ")) {//����˵�������������Ϊ��Ƥ
				rubber=true;//������Ƥ��ʶΪtrue
				eraserMenuItem.setText("��ͼ");//�ı�˵�����ʾ���ı�Ϊͼ��
				eraserButton.setText("��ͼ");//�ı䰴ť����ʾ���ı�Ϊͼ��
			}else {
				rubber=false;//������Ƥ��ʶΪfalse
				eraserMenuItem.setText("��Ƥ");//�ı�˵�����ʾ���ı�Ϊ����Ƥ��
				eraserButton.setText("��Ƥ");//�ı䰴ť����ʾ���ı�Ϊ��Ƥ
				g.setColor(foreColor);//���û�ͼ�����ǰ��ɫ
			}
		}
	});
	clearMenuItem.addActionListener(new ActionListener(){//����˵���Ӷ�������
		public void actionPerformed(final ActionEvent e) {//����
			g.setColor(backgroundColor);//��ͼ����ʹ�ñ���ɫ
			g.fillRect(0, 0, 570, 390);//��һ��������ɫ�ķ���������������
			g.setColor(foreColor);//��ͼ����ʹ��ǰ��ɫ
			canvas.repaint();//���»���
		}
	});
	
	strokeMenuItem1.addActionListener(new ActionListener(){//ϸ�߲˵���Ӷ�������
		public void actionPerformed(final ActionEvent e) {//����ʱ
			BasicStroke bs=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//�������ʵ����ԣ���ϸΪ1�����أ�����ĩ�������Σ����ߴ��ʼ��
			g.setStroke(bs);//��ͼ����ʹ�ô˻���
			strokeButton1.setSelected(true);//ϸ�߰�ť����Ϊѡ��״̬
		}
	});
	strokeMenuItem2.addActionListener(new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			BasicStroke bs=new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
			g.setStroke(bs);
			strokeButton2.setSelected(true);
		}
	});
	strokeMenuItem3.addActionListener(new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			BasicStroke bs=new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
			g.setStroke(bs);
			strokeButton3.setSelected(true);
		}
	});
	foregroundMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			//����ɫѡ��Ի��򣬲�������Ϊ�������ڣ����⣬Ĭ��ѡ�е���ɫ��
			Color fColor=JColorChooser.showDialog(DrawPictureFrame.this,"ѡ����ɫ�Ի���",Color.CYAN);
			if(fColor !=null) {//���ѡ�е���ɫ��Ϊ��
				foreColor=fColor;//��ѡ�е���ɫ��ֵ��ǰ��ɫ����
			}
			foregroundButton.setForeground(foreColor);//ǰ��ɫ��ť������Ҳ����Ϊ������ɫ
			g.setColor(foreColor);//��ͼ����ʹ��ǰ��ɫ
		}
	});
	backgroundMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			Color bgColor=JColorChooser.showDialog(DrawPictureFrame.this,"ѡ����ɫ�Ի���",Color.CYAN);
			if(bgColor!=null) {
				backgroundColor=bgColor;
			}
			backgroundButton.setBackground(backgroundColor);
			g.setColor(backgroundColor);
			g.fillRect(0, 0, 570, 390);
			g.setColor(foreColor);
			canvas.repaint();
		}
	});
	SaveMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DrawImageUtil.saveImage(DrawPictureFrame.this, image);//��ӡͼƬ
		}
	});
	shuiyinMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			shuiyin=JOptionPane.showInputDialog(DrawPictureFrame.this,"�������ʲôˮӡ");
			if(null==shuiyin) {
				shuiyin="";
			}else {
				setTitle("��ͼ����:(ˮӡ���ݣ�["+shuiyin+"]");
			}
		}
	});
	
	
}
	
	public void addWatermark() {//���ˮӡ
		if(!"".equals(shuiyin.trim())) {//���ˮӡ�ֶβ��ǿ��ַ���
			g.rotate(Math.toRadians(-30));//��ͼƬ��ת30��
			Font font =new Font("����",Font.BOLD,72);//��������
			g.setFont(font);//��������
			g.setColor(Color.GRAY);//ʹ�û�ɫ
			AlphaComposite alpha=AlphaComposite.SrcOver.derive(0.4f);//����͸��Ч��
			g.setComposite(alpha);//ʹ��͸��Ч��
			g.drawString(shuiyin,150,500);//��������
			canvas.repaint();//����ػ�
			g.rotate(Math.toRadians(30));//����ת��ͼ����ת����
			alpha=AlphaComposite.SrcOver.derive(1f);//��͸��Ч��
			g.setComposite(alpha);//ʹ�ò�͸��Ч����
			g.setColor(foreColor);//���ʻָ�֮ǰ��ɫ
		}
	}
	
	public void initShowPicButton() {
		showPicButton.setText("չ����ʻ�");//�޸İ�ť���ı�
	}
	
	/*
	 * FrameGetShape�ӿ�ʵ���࣬���ڻ��ͼ�οռ䷵�صı�ѡ�е�ͼ��
	 *
	 * 
	 * 
	 */
	public void getShape(Shapes shape) {
		this.shape=shape;	//�����صĶ���������ȫ�ֱ���
		drawShape=true;//��ͼ������ʶΪtrue,˵��������껭����ͼ�Σ�����������
	}
	
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame();// new һ���������
		frame.setVisible(true);// ����ɼ�
	}
}
