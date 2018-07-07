//构建五子棋界面GoBangframe类

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
public class GoBangframe extends JPanel implements GoBangconfig{
	public Graphics g;//定义一支画笔
	public int[][] isAvail=new int [column][row];//定义一个二维数组来储存棋盘的落子情况
	public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();//保存每一步的落子情况
	public int turn=0;
	public int ChooseType=0;//0表示人人对战，1表示人机对战，默认人人对战
	
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();//设置不同落子情况和相应权值的数组
	static {
		
		//被堵住
		map.put("01", 17);//眠1连
		map.put("02", 12);//眠1连
		map.put("001", 17);//眠1连
		map.put("002", 12);//眠1连
		map.put("0001", 17);//眠1连
		map.put("0002", 12);//眠1连
		
		map.put("0102",17);//眠1连，15
		map.put("0201",12);//眠1连，10
		map.put("0012",15);//眠1连，15
		map.put("0021",10);//眠1连，10
		map.put("01002",19);//眠1连，15
		map.put("02001",14);//眠1连，10
		map.put("00102",17);//眠1连，15
		map.put("00201",12);//眠1连，10
		map.put("00012",15);//眠1连，15
		map.put("00021",10);//眠1连，10

		map.put("01000",21);//活1连，15
		map.put("02000",16);//活1连，10
		map.put("00100",19);//活1连，15
		map.put("00200",14);//活1连，10
		map.put("00010",17);//活1连，15
		map.put("00020",12);//活1连，10
		map.put("00001",15);//活1连，15
		map.put("00002",10);//活1连，10

		//被堵住
		map.put("0101",65);//眠2连，40
		map.put("0202",60);//眠2连，30
		map.put("0110",65);//眠2连，40
		map.put("0220",60);//眠2连，30
		map.put("011",65);//眠2连，40
		map.put("022",60);//眠2连，30
		map.put("0011",65);//眠2连，40
		map.put("0022",60);//眠2连，30
		
		map.put("01012",65);//眠2连，40
		map.put("02021",60);//眠2连，30
		map.put("01102",65);//眠2连，40
		map.put("02201",60);//眠2连，30
		map.put("00112",65);//眠2连，40
		map.put("00221",60);//眠2连，30

		map.put("01010",75);//活2连，40
		map.put("02020",70);//活2连，30
		map.put("01100",75);//活2连，40
		map.put("02200",70);//活2连，30
		map.put("00110",75);//活2连，40
		map.put("00220",70);//活2连，30
		map.put("00011",75);//活2连，40
		map.put("00022",70);//活2连，30
		
		//被堵住
		map.put("0111",150);//眠3连，100
		map.put("0222",140);//眠3连，80
		
		map.put("01112",150);//眠3连，100
		map.put("02221",140);//眠3连，80
		
		map.put("01101",1000);//活3连，130
		map.put("02202",800);//活3连，110
		map.put("01011",1000);//活3连，130
		map.put("02022",800);//活3连，110
		map.put("01110", 1000);//活3连
		map.put("02220", 800);//活3连
		
		map.put("01111",3000);//4连，300
		map.put("02222",3500);//4连，280
	}
	public int[][] weightArray=new int[column][row];//定义一个二维数组，保存各个点的权值
	
	//主函数入口
	public static void main(String args[]) {
		GoBangframe gf=new GoBangframe();//初始化一个五子棋界面的对象
		gf.initUI();//调用方法进行界面的初始化
	}
	
	public void initUI() {
		//初始化一个界面,并设置标题大小等属性
		JFrame jf=new JFrame();
		jf.setTitle("五子棋");
		jf.setSize(800,650);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);
		
		jf.setLayout(new BorderLayout());//设置顶级容器JFrame为框架布局
		
		Dimension dim1=new Dimension(150,0);//设置右半部分的大小
		Dimension dim3=new Dimension(550,0);//设置左半部分的大小
		Dimension dim2=new Dimension(140,40);//设置右边按钮组件的大小
		
		//实现左边的界面，把GoBangframe的对象添加到框架布局的中间部分
		//已经有一个GoBangframe对象了，表示当前类的对象是this
		this.setPreferredSize(dim3);//设置下棋界面的大小
		this.setBackground(Color.LIGHT_GRAY);//设置下棋界面的颜色
		//这里的话直接把左边的画板添加上去，指明是在框架布局的中间版块
		//若放在其他版块会有一些小问题
		jf.add(this,BorderLayout.CENTER);//添加到框架布局的中间部分
		
		//实现右边的JPanel容器界面
		JPanel jp=new JPanel();
		jp.setPreferredSize(dim1);//设置JPanel的大小
		jp.setBackground(Color.white);//设置右边的界面颜色为白色
		jf.add(jp,BorderLayout.EAST);//添加到框架布局的东边部分
		jp.setLayout(new FlowLayout());//设置JPanel为流式布局
		
		//接下来我们需要把按钮等组件依次加到那个JPanel上面
		//设置按钮数组
		String[] butname= {"开始新游戏","悔棋","认输"};
		JButton[] button=new JButton[3];
		
		//依次把三个按钮组件加上去
		for(int i=0;i<butname.length;i++) {
			button[i]=new JButton(butname[i]);
			button[i].setPreferredSize(dim2);
			jp.add(button[i]);
		}
		
		//设置选项按钮
		String[] boxname= {"人人对战","人机对战"};
		JComboBox box=new JComboBox(boxname);
		jp.add(box);
		
		//按钮监控类
		ButtonListener butListen=new ButtonListener(this,box);
		//对每一个按钮都添加状态事件的监听处理机制
		for(int i=0;i<butname.length;i++) {
			button[i].addActionListener(butListen);//添加发生操作的监听方法
		}
		
		//对可选框添加事件监听机制
		box.addActionListener(butListen);
		
		frameListener fl=new frameListener();
		fl.setGraphics(this);//获取画笔对象
		this.addMouseListener(fl);
		
		jf.setVisible(true);
	}
	
	public void PopUp(String result) {
		JOptionPane jo=new JOptionPane();
		jo.showMessageDialog(null, result, "游戏结果", JOptionPane.PLAIN_MESSAGE);
	}
	
	//AI联合算法函数
	/*public Integer unionWeight(Integer a,Integer b ) {
		//一一
		if((a>=10)&&(a<=25)&&(b>=10)&&(b<=25)) return 60;
		//一二、二一
		else if(((a>=10)&&(a<=25)&&(b>=60)&&(b<=80))||((a>=60)&&(a<=80)&&(b>=10)&&(b<=25))) return 800;
		//一三、三一、二二
		else if(((a>=10)&&(a<=25)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=10)&&(b<=25))||((a>=60)&&(a<=80)&&(b>=60)&&(b<=80)))
			return 3000;
		//二三、三二
		else if(((a>=60)&&(a<=80)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=60)&&(b<=80))) return 3000;
		else return 0;
	}*/
	
	
	//重写重绘方法,这里重写的是第一个大的JPanel的方法
	public void paint(Graphics g) {
		super.paint(g);//画出白框
		
		//重绘出棋盘
		g.setColor(Color.black);
		for(int i=0;i<row;i++) {
			g.drawLine(x, y+size*i, x+size*(column-1), y+size*i);
		}
		for(int j=0;j<column;j++) {
			g.drawLine(x+size*j, y, x+size*j, y+size*(row-1));
		}
		
		//重绘出棋子
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				if(isAvail[i][j]==1) {
					int countx=size*j+20;
					int county=size*i+20;
					g.setColor(Color.black);
					g.fillOval(countx-size/2, county-size/2, size, size);
				}
				else if(isAvail[i][j]==2) {
					int countx=size*j+20;
					int county=size*i+20;
					g.setColor(Color.white);
					g.fillOval(countx-size/2, county-size/2, size, size);
				}
			}
		}
	}
	
}
