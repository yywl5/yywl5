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
public class DrawPictureFrame extends JFrame implements FrameGetShape{// 继承窗体类//并且实现了新的接口
	/**
	 * 
	 */
	boolean drawShape=false;//画图形标识变量
	Shapes shape;//绘画的图形
	
	
	BufferedImage image=new BufferedImage(570,390,BufferedImage.TYPE_INT_BGR);
	Graphics gs=image.getGraphics();//获得图像的绘图对象
	Graphics2D g=(Graphics2D) gs;//将绘图对象转换为Graphics2D类型;
	DrawPictureCanvas canvas =new DrawPictureCanvas();
	Color foreColor = Color.BLACK;//定义前景色
	Color backgroundColor =Color.WHITE;//定义背景色
	int x=-1;//上一次鼠标绘制点的横坐标
	int y=-1;//上一次鼠标绘制点的纵坐标
	
	
	private PictureWindow picWindow;//简笔画展示窗体
	private JButton  showPicButton;//展开简笔画按钮
	
	
	boolean rubber = false;
	private JToolBar toolBar;//工具栏
	private JButton eraserButton;//橡皮按钮
	private JToggleButton strokeButton1;//细线按钮
	private JToggleButton strokeButton2;//粗线按钮
	private JToggleButton strokeButton3;//较粗按钮
	private JButton backgroundButton;//背景色按钮
	private JButton foregroundButton;//前景色按钮
	private JButton clearButton;//清除按钮
	private JButton saveButton;//保存按钮
	private JButton shapeButton;//形状按钮
	private JMenuItem strokeMenuItem1;//细线按钮
	private JMenuItem strokeMenuItem2;//粗线菜单
	private JMenuItem strokeMenuItem3;//较粗菜单
	private JMenuItem foregroundMenuItem;//前景色菜单
	private JMenuItem backgroundMenuItem;//背景色菜单
	private JMenuItem eraserMenuItem;//橡皮菜单
	private JMenuItem exitMenuItem;//退出菜单
	private JMenuItem SaveMenuItem;//保存菜单
	private JMenuItem clearMenuItem;//清除菜单
	private JMenuItem shuiyinMenuItem;//水印菜单
	private String shuiyin="";//水印字符内容
	public DrawPictureFrame() {
		setResizable(false);// 窗体大小不能更改
		setTitle("画图程序");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 窗口关闭则停止程序
		setTitle("画图程序(水印内容：[ "+shuiyin+"])");
		setBounds(500, 100, 574, 460);// 设置窗口位置和宽高
		init();//初始化(方法)
		addListener();//添加组件监听(方法)
		
	}// DrawPictureFrame()结束
	
	/*
	 * * 程序运行主方法
	 * 
	 * @param args - 运行时参数
	 */
	public void init() {
		g.setColor(backgroundColor);//用背景色设置绘图对象的颜色
		g.fillRect(0, 0, 570, 390);//用背景色填充整个画布
		g.setColor(foreColor);//用前景色设置绘画对象的颜色
		canvas.setImage(image);//设置画布的图像
		getContentPane().add(canvas);//将画布添加到窗体容器默认布局的中部位置
		toolBar=new JToolBar();//初始化工具栏
		getContentPane().add(toolBar,BorderLayout.NORTH);//将工具栏添加到窗体最北部位置
		
		
		
		showPicButton=new JButton("展开简笔画");//初始化按钮对象，并添加文本内容
		toolBar.add(showPicButton);//工具栏添加按钮
		picWindow=new PictureWindow(DrawPictureFrame.this);
		//创建简笔画展示面板，并将本类当作它的父窗体
		
		
		saveButton=new JButton("保存");//初始化工具栏对象,并添加文本内容
		toolBar.add(saveButton);//工具栏添加按钮
		toolBar.addSeparator();//添加分割条
		//初始化按钮对象,并添加文本内容
		shapeButton= new JButton("图形");//初始化按钮对象,并添加文本内容
		toolBar.add(shapeButton);//工具栏添加按钮
		
		strokeButton1=new JToggleButton("细线");
		strokeButton1.setSelected(true);//细线按钮处于被选中状态
		toolBar.add(strokeButton1);//工具栏添加按钮
		strokeButton2=new JToggleButton("粗线");
		toolBar.add(strokeButton2);//初始化有选中状态的按钮对象,并添加文本
		strokeButton3=new JToggleButton("较粗");//画笔粗细按钮组,保证同时只有一个按钮被选中
		ButtonGroup strokeGroup =new ButtonGroup();
		strokeGroup.add(strokeButton1);//按钮组添加按钮
		strokeGroup.add(strokeButton1);//按钮组添加按钮
		strokeGroup.add(strokeButton3);//按钮组添加按钮
		toolBar.add(strokeButton3);//工具栏添加按钮
		toolBar.addSeparator();//添加分割
		backgroundButton=new JButton("背景颜色");//初始化按钮对象，并且添加文本内容
		toolBar.add(backgroundButton);//工具栏添加按钮
		foregroundButton=new JButton("前置颜色");
		toolBar.add(foregroundButton);//工具栏添加按钮
		toolBar.addSeparator();//添加分割线
		
		clearButton=new JButton("清除");//工具栏添加按钮并且添加文本
		toolBar.add(clearButton);//工具栏添加按钮
		
		eraserButton=new JButton("橡皮");//初始化文本对象并添加文本内容
		toolBar.add(eraserButton);//工具栏添加按钮
		toolBar.setFloatable(true);
		
		JMenuBar menuBar=new JMenuBar();//创建菜单栏
		setJMenuBar(menuBar);//窗体载入菜单栏
		
		
		JMenu systemMenu =new JMenu("系统");//初始化菜单对象，并添加文本内容
		menuBar.add(systemMenu);//菜单栏添加菜单对象
		
		
		SaveMenuItem=new JMenuItem("保存");//初始化菜单对象，并添加文本内容
		systemMenu.add(SaveMenuItem);//菜单添加菜单项
		
		
		systemMenu.addSeparator();//添加分割条
		exitMenuItem=new JMenuItem("退出");//初始化菜单项对象，并添加文本内容
		systemMenu.add(exitMenuItem);//菜单添加菜单项
		JMenu strokeMenu=new JMenu("线型");//初始化菜单对象，并添加文本内容
		menuBar.add(strokeMenu);//菜单栏添加菜单项
		strokeMenuItem1=new JMenuItem("细线");//初始化菜单项对象，并添加文本内容
		strokeMenu.add(strokeMenuItem1);//菜单添加菜单项
		strokeMenuItem2=new JMenuItem("粗线");//初始化菜单项对象，并添加文本内容
		strokeMenu.add(strokeMenuItem2);//菜单添加菜单项
		strokeMenuItem3=new JMenuItem("较粗");//初始化菜单项对象，并添加文本内容
		strokeMenu.add(strokeMenuItem3);//菜单添加菜单项
		
		
		JMenu colorMenu=new JMenu("颜色");
		menuBar.add(colorMenu);
		foregroundMenuItem=new JMenuItem("前景颜色");
		colorMenu.add(foregroundMenuItem);
		backgroundMenuItem=new JMenuItem("背景颜色");
		colorMenu.add(backgroundMenuItem);
		
		
		
		JMenu editMenu=new JMenu("编辑");
		menuBar.add(editMenu);
		clearMenuItem=new JMenu("清除");
		editMenu.add(clearMenuItem);
		eraserMenuItem =new JMenuItem("橡皮");
		editMenu.add(eraserMenuItem);
		
		
		
		shuiyinMenuItem=new JMenuItem("设置水印");//初始化菜单项选项
		systemMenu.add(shuiyinMenuItem);//在菜单中添加选项
	}
	private void addListener() {
		//画板添加鼠标移动监听事件
		
		showPicButton.addActionListener(new ActionListener() {//展示简笔画按钮添加动作监听
			public void actionPerformed(ActionEvent e) {//单击时
				boolean isVisible= picWindow.isVisible();//获取简笔画展示窗体可见状态
				if(isVisible) {//如果简笔画窗体是可见的
					showPicButton.setText("展开简笔画");//修改按钮的文本
				}else {
					showPicButton.setText("隐藏简笔画");//修改按钮的文本
					//重新指定简笔画展示窗体的显示位置
					//横坐标=主窗体横坐标 - 简笔画窗体宽度  - 5
					//纵坐标 = 主窗体纵坐标
					picWindow.setLocation(getX()-picWindow.getWidth()-5, getY());//
					
					picWindow.setVisible(true);//简笔画展示窗体可见
					//
				}
			}
		});
		
		
		
		
		
		
		
		
		canvas.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(final MouseEvent e) {//当鼠标拖拽时
				if(x>0&&y>0) {//如果存在鼠标移动的记录
					if(rubber) {//如果橡皮标识为true,表示使用橡皮
						g.setColor(backgroundColor);//绘图工具使用背景色
						g.fillRect(x,y,10,10);//在鼠标滑过的位置画填充的正方形
					}else {
						g.drawLine(x,y,e.getX(),e.getY());//在鼠标滑过的位置画直线
					}
				}
				x=e.getX();//得到上一次鼠标绘制点的横坐标
				y=e.getY();//得到上一次鼠标绘制点的纵坐标
				canvas.repaint();//更新画布
			}
			public void mouseMoved(final MouseEvent arg0) {//当鼠标移动时,如果使用橡皮，
				if(rubber) {
					Toolkit kit=Toolkit.getDefaultToolkit();//获得系统默认的工具包组件
					//利用工具包获取图片
					Image img=kit.createImage("D:/JAVA_EE_Project/一起来画画/src/icon/鼠标橡皮.png");//设置鼠标的形状为图片
					Cursor c=kit.createCustomCursor(img, new Point(0, 0), "clear");//利用工具包获得一个自定义的光标对象
					setCursor(c);//参考为图片,光标热点（写成（0，0））和光标描述字符串
				}else {
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));//设置鼠标光标的形状为默认光标
				}
			}
	});
		toolBar.addMouseMotionListener(new MouseMotionAdapter() {//工具栏添加鼠标移动监听
			public void mouseMoved(final MouseEvent arg0) {//当鼠标移动时
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));//设置鼠标指针的形状为默认光标
			}
		});
		
		
		
		
		canvas.addMouseListener(new MouseAdapter() {//画板添加鼠标单击事件监听
			public void mouseReleased(final MouseEvent arg0) {//当按键抬起时
				x=-1;//将上一次鼠标绘制点的横坐标恢复为-1
				y=-1;//将上一次鼠标绘制点的纵坐标恢复为-1
			}
			public void mousePressed(MouseEvent e) {//当按键按下时,如果此按键所画是图形,判断图形的种类,如果是圆形,
				if(drawShape) {
					switch(shape.getType()) {
					case Shapes.YUAN://如果是圆形,
					//计算坐标位置,让圆处于中心点.
					int yuanx=e.getX()-shape.getWidth()/2;
					int yuany=e.getY()-shape.getHeigth()/2;
					
					Ellipse2D yuan=new Ellipse2D.Double(yuanx,yuany,shape.getWidth(),shape.getHeigth());
					//画出图形，并且指定位置和方向
					g.draw(yuan);//画图工具画此圆形
					break;
					case Shapes.FANG://如果是方形
					//计算坐标,让鼠标处于图形的中心位置
					int fangx=e.getX()-shape.getWidth()/2;
					int fangy=e.getY()-shape.getHeigth()/2;
					//创建方形图形，并且指定坐标和宽高
					Rectangle2D fang=new Rectangle2D.Double(fangx,fangy,shape.getWidth(),shape.getHeigth());
					//画图工具画出此图形
					g.draw(fang);
					break;
					//结束switch语句
					
					}
					canvas.repaint();///更新画布
					//画图形标识为false,说明现在鼠标画的是图形,而不是线条
					drawShape=false;
				}
				
			}
		});
		
		
		strokeButton1.addActionListener(new ActionListener(){//"细线"按钮添加动作监听
			public void actionPerformed(final ActionEvent arg0) {//点击时
				//声明笔画的属性,粗细为1像素，线条末端无修饰,折线处呈尖角
				BasicStroke bs=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//画笔工具使用此画笔
				g.setStroke(bs);
			}
		});
		
		strokeButton2.addActionListener(new ActionListener(){//"粗线"按钮添加动作监听
			public void actionPerformed(final ActionEvent arg0) {//点击时
				BasicStroke bs=new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//画笔工具使用此画笔
				g.setStroke(bs);
			}
		});
		
		strokeButton3.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent arg0) {
				BasicStroke bs=new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//画笔工具使用此画笔
				g.setStroke(bs);
			}
		});
		
		backgroundButton.addActionListener(new ActionListener(){//背景颜色按钮添加动作监听
			public void actionPerformed(final ActionEvent arg0) {//单击时,打开选择颜色对话框,参数依次为:父窗口，标题，默认选中颜色（青色）
			//打开选择颜色对话框,参数依次修改为:父窗体,标题,默认选中颜色,（青色）,
			Color bgColor=JColorChooser.showDialog(DrawPictureFrame.this,"选择颜色对话框",Color.CYAN);
			if(bgColor!=null) {
				backgroundColor=bgColor;//将选中的颜色赋值给背景色变量
			}
			backgroundButton.setBackground(backgroundColor);
			g.setColor(backgroundColor);//绘图工具使用背景色
			g.fillRect(0,0,570,390);//画一个背景颜色方法的方形填满整个画布
		}
		});
		foregroundButton.addActionListener(new ActionListener(){//前景色按钮添加动作监听
			public void actionPerformed(final ActionEvent arg0) {
				//单击时，打开颜色选择对话框,参数依次为:父窗口,标题,默认选中的颜色（青色）
				Color color=JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框",Color.GRAY);
				if(color!=null) {
					foreColor=color;//如果被选中的颜色不是空的，那么便将选中的颜色赋值给前景色变量
				}
				//前景色颜色的文字也同样更换为这种颜色
				foregroundButton.setForeground(foreColor);
				g.setColor(foreColor);//绘图工具也同样使用前景色
			}
		});
		clearButton.addActionListener(new ActionListener(){//清除按钮添加动作监听
			public void actionPerformed(final ActionEvent arg0) {
				//点击时
				g.setColor(backgroundColor);//画图工具使用背景色
				g.fillRect(0, 0, 570, 390);//画一个背景颜色的方块填满整个画布
				g.setColor(foreColor);//绘图工具使用前景色
				canvas.repaint();//更新画布
			}
		});
		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				//单击时
				if(eraserButton.getText().equals("橡皮")) {
					rubber=true;//设置橡皮标识为true
					eraserButton.setText("画图");//改变按钮上显示的文本为画图
				}else {
					rubber=false;
					eraserButton.setText("橡皮");//改变按钮上显示的文本为橡皮
					g.setColor(foreColor);//设置绘图对象为前景色
				}
			}
		});
		
		
	shapeButton.addActionListener(new ActionListener() {
		//图形添加动作监听
		public void actionPerformed(ActionEvent e) {//单击时
			//创建图形选择组件
			ShapeWindow shapeWindow =new ShapeWindow(DrawPictureFrame.this);
			int shapeButtonWidth=shapeButton.getWidth();
			int shapeWindowWidth=shapeWindow.getWidth();
			int shapeButtonX=shapeButton.getX();//获取图形按钮横坐标
			int shapeButtonY=shapeButton.getY();//获取图形按钮纵坐标
			int shapeWidowX = getX()+shapeButtonX-(shapeWindowWidth-shapeButtonWidth)/2;
			//计算图形组件纵坐标，让组件显示在“图形”按钮下方
			int shapeWidowY=getY()+shapeButtonY+80;
			//设置图形组件坐标
			shapeWindow.setLocation(shapeWidowX,shapeWidowY);
			shapeWindow.setVisible(true);//图形组件可见
		}
	});
	
	
	saveButton.addActionListener(new ActionListener(){//保存按钮添加动作监听
		public void actionPerformed(final ActionEvent arg0) {//单击时
			addWatermark();
			DrawImageUtil.saveImage(DrawPictureFrame.this, image);//打印图片
			addWatermark();//添加水印
		}
	});
	
	
	exitMenuItem.addActionListener(new ActionListener(){//退出菜单栏添加动作监听
		public void actionPerformed(final ActionEvent e) {//单击时
			System.exit(0);//程序关闭
		}
	});
	eraserMenuItem.addActionListener(new ActionListener(){//橡皮菜单栏添加动作监听
		public void actionPerformed(final ActionEvent e) {//单击时
			if(eraserMenuItem.getText().equals("橡皮")) {//如果菜单栏的文字内容为橡皮
				rubber=true;//设置橡皮标识为true
				eraserMenuItem.setText("画图");//改变菜单上显示的文本为图像
				eraserButton.setText("画图");//改变按钮上显示的文本为图像
			}else {
				rubber=false;//设置橡皮标识为false
				eraserMenuItem.setText("橡皮");//改变菜单上显示的文本为“橡皮”
				eraserButton.setText("橡皮");//改变按钮上显示的文本为橡皮
				g.setColor(foreColor);//设置绘图对象的前景色
			}
		}
	});
	clearMenuItem.addActionListener(new ActionListener(){//清除菜单添加动作监听
		public void actionPerformed(final ActionEvent e) {//单击
			g.setColor(backgroundColor);//绘图工具使用背景色
			g.fillRect(0, 0, 570, 390);//画一个背景颜色的方格填满整个画布
			g.setColor(foreColor);//绘图工具使用前景色
			canvas.repaint();//更新画布
		}
	});
	
	strokeMenuItem1.addActionListener(new ActionListener(){//细线菜单添加动作监听
		public void actionPerformed(final ActionEvent e) {//单击时
			BasicStroke bs=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);//声明画笔的属性：粗细为1的像素，线条末端无修饰，折线处呈尖角
			g.setStroke(bs);//画图工具使用此画笔
			strokeButton1.setSelected(true);//细线按钮设置为选中状态
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
			//打开颜色选择对话框，参数依次为；父窗口，标题，默认选中的颜色，
			Color fColor=JColorChooser.showDialog(DrawPictureFrame.this,"选择颜色对话框",Color.CYAN);
			if(fColor !=null) {//如果选中的颜色不为空
				foreColor=fColor;//将选中的颜色赋值给前景色变量
			}
			foregroundButton.setForeground(foreColor);//前景色按钮的文字也更换为这种颜色
			g.setColor(foreColor);//绘图工具使用前景色
		}
	});
	backgroundMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			Color bgColor=JColorChooser.showDialog(DrawPictureFrame.this,"选择颜色对话框",Color.CYAN);
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
			DrawImageUtil.saveImage(DrawPictureFrame.this, image);//打印图片
		}
	});
	shuiyinMenuItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			shuiyin=JOptionPane.showInputDialog(DrawPictureFrame.this,"你想添加什么水印");
			if(null==shuiyin) {
				shuiyin="";
			}else {
				setTitle("画图程序:(水印内容：["+shuiyin+"]");
			}
		}
	});
	
	
}
	
	public void addWatermark() {//添加水印
		if(!"".equals(shuiyin.trim())) {//如果水印字段不是空字符串
			g.rotate(Math.toRadians(-30));//将图片旋转30度
			Font font =new Font("楷体",Font.BOLD,72);//设置字体
			g.setFont(font);//载入字体
			g.setColor(Color.GRAY);//使用灰色
			AlphaComposite alpha=AlphaComposite.SrcOver.derive(0.4f);//设置透明效果
			g.setComposite(alpha);//使用透明效果
			g.drawString(shuiyin,150,500);//绘制文字
			canvas.repaint();//面板重绘
			g.rotate(Math.toRadians(30));//将旋转的图形再转回来
			alpha=AlphaComposite.SrcOver.derive(1f);//不透明效果
			g.setComposite(alpha);//使用不透明效果。
			g.setColor(foreColor);//画笔恢复之前颜色
		}
	}
	
	public void initShowPicButton() {
		showPicButton.setText("展开简笔画");//修改按钮的文本
	}
	
	/*
	 * FrameGetShape接口实现类，用于获得图形空间返回的被选中的图形
	 *
	 * 
	 * 
	 */
	public void getShape(Shapes shape) {
		this.shape=shape;	//将返回的对象赋予给类的全局变量
		drawShape=true;//画图变量标识为true,说明现在鼠标画的是图形，而不是线条
	}
	
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame();// new 一个窗体对象
		frame.setVisible(true);// 窗体可见
	}
}
