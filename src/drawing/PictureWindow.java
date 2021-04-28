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
 * 简笔画展示窗口
 * 
 * 
 * */
public class PictureWindow extends JWindow{
	private JButton changeButton;//更换图片按钮
	private JButton hiddenButton;//隐藏按钮
	private BackgroundPanel centerPanel;//展示图片的带背景图面板
	File list[];//图片文件数组
	int index=0;//当前选中的图片索引
	DrawPictureFrame frame;//父窗口
	public PictureWindow(DrawPictureFrame frame) {
		this.frame=frame;//构造参数的值赋值给父窗口
		setSize(400,460);//设置窗体宽高
		init();//初始化
		addListenere();//增加按钮监听
		/*
		 * 组件初始化
		 * 
		 * 
		 * */
	}
	private void addListenere() {
		// TODO Auto-generated method stub
		hiddenButton.addActionListener(new ActionListener() {//隐藏按钮添加动作监听
			public void actionPerformed(ActionEvent e){//单击时
				setVisible(false);//本窗体不可见
				frame.initShowPicButton();//父类窗体还原简笔画按钮的文本内容
			}
		});
		changeButton.addActionListener(new ActionListener() {//更换图片按钮添加动作建监听
			public void actionPerformed (ActionEvent e) {//单击时
				centerPanel.setImage(getListImage());//背景面板重新载入图片
			}
		});
	}
	private void init() {
		// TODO Auto-generated method stub
		Container c=getContentPane();//获取窗体主容器
		File dir=new File("src/Picture");//创建简笔画素材对象
		list=dir.listFiles();//获取文件夹中的所有文件
		centerPanel =new BackgroundPanel(getListImage());//初始化背景面板，使用图片文件夹中第一张简笔画
		c.add(centerPanel,BorderLayout.CENTER);//背景面板放置到主容器中部
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);//创建右对齐的流布局
		flow.setHgap(20);//水平间隔20像素
		JPanel southPanel=new JPanel();//创建南部面板
		southPanel.setLayout(flow);//南部面板使用刚刚创建好的流布局
		changeButton=new JButton("更换图片");//实例化“更换图片”按钮
		southPanel.add(changeButton);//南部面板添加按钮
		hiddenButton=new JButton("隐藏");//实例化“隐藏”按钮
		southPanel.add(hiddenButton);//南部面板添加按钮
		c.add(southPanel,BorderLayout.SOUTH);//南部面板放置到主容器的南部位置
	}
	/*
	 * 
	 * 返回文件对象
	 * 
	 * 
	 * */
	private Image getListImage() {
		// TODO Auto-generated method stub
	    String imgPath=list[index].getAbsolutePath();//获取当前索引下的图片文件路径
		ImageIcon image=new ImageIcon(imgPath);//获取此图片文件的图标对象
		index++;//索引变量递增
		if(index>=list.length) {//如果索引变量超过图片数量了，
			index=0;//索引变量归零
		}
		return image.getImage();//获得图标对象的图片对象
	}
}
