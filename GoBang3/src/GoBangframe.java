//构建五子棋界面GoBangframe类

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
public class GoBangframe extends JPanel implements GoBangconfig{
	public Graphics g;//定义一支画笔
	public int[][] isAvail=new int [COLUMN][ROW];//定义一个二维数组来储存棋盘的落子情况
	public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();//保存每一步的落子情况
	public int turn=0;//等于0时无法下棋
	public int ChooseType=0;//0表示人人对战，1表示人机对战，默认人人对战
	
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();//设置不同落子情况和相应权值的数组
	static {
		
		//被堵住
		map.put("01", 25);//眠1连
		map.put("02", 22);//眠1连
		map.put("001", 17);//眠1连
		map.put("002", 12);//眠1连
		map.put("0001", 17);//眠1连
		map.put("0002", 12);//眠1连
		
		map.put("0102",25);//眠1连，15
		map.put("0201",22);//眠1连，10
		map.put("0012",15);//眠1连，15
		map.put("0021",10);//眠1连，10
		map.put("01002",25);//眠1连，15
		map.put("02001",22);//眠1连，10
		map.put("00102",17);//眠1连，15
		map.put("00201",12);//眠1连，10
		map.put("00012",15);//眠1连，15
		map.put("00021",10);//眠1连，10

		map.put("01000",25);//活1连，15
		map.put("02000",22);//活1连，10
		map.put("00100",19);//活1连，15
		map.put("00200",14);//活1连，10
		map.put("00010",17);//活1连，15
		map.put("00020",12);//活1连，10
		map.put("00001",15);//活1连，15
		map.put("00002",10);//活1连，10

		//被墙堵住
		map.put("0101",65);//眠2连，40
		map.put("0202",60);//眠2连，30
		map.put("0110",80);//眠2连，40
		map.put("0220",76);//眠2连，30
		map.put("011",80);//眠2连，40
		map.put("022",76);//眠2连，30
		map.put("0011",65);//眠2连，40
		map.put("0022",60);//眠2连，30
		
		map.put("01012",65);//眠2连，40
		map.put("02021",60);//眠2连，30
		map.put("01102",80);//眠2连，40
		map.put("02201",76);//眠2连，30
		map.put("01120",80);//眠2连，40
		map.put("02210",76);//眠2连，30
		map.put("00112",65);//眠2连，40
		map.put("00221",60);//眠2连，30

		map.put("01100",80);//活2连，40
		map.put("02200",76);//活2连，30
		map.put("01010",75);//活2连，40
		map.put("02020",70);//活2连，30
		map.put("00110",75);//活2连，40
		map.put("00220",70);//活2连，30
		map.put("00011",75);//活2连，40
		map.put("00022",70);//活2连，30
		
		//被堵住
		map.put("0111",150);//眠3连，100
		map.put("0222",140);//眠3连，80
		
		map.put("01112",150);//眠3连，100
		map.put("02221",140);//眠3连，80
	
		map.put("01110", 1100);//活3连
		map.put("02220", 1050);//活3连
		map.put("01101",1000);//活3连，130
		map.put("02202",800);//活3连，110
		map.put("01011",1000);//活3连，130
		map.put("02022",800);//活3连，110
		
		map.put("01111",3000);//4连，300
		map.put("02222",3500);//4连，280
	}
	public int[][] weightArray=new int[COLUMN][ROW];//定义一个二维数组，保存各个点的权值
	
	public void initUI() {
		//初始化一个界面,并设置标题大小等属性
		JFrame jf=new JFrame();
		jf.setTitle("五子棋");
		jf.setSize(UIWIDTH,UIHIGHTH);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);		
		jf.setLayout(new BorderLayout());//设置顶级容器JFrame为框架布局
		
		//实现左边的界面，把GoBangframe的对象添加到框架布局的中间部分
		//已经有一个GoBangframe对象了，表示当前类的对象是this
		this.setBackground(Color.WHITE);//设置下棋界面的颜色
		//这里的话直接把左边的画板添加上去，指明是在框架布局的中间版块
		//若放在其他版块会有一些小问题
		jf.add(this,BorderLayout.CENTER);//添加到框架布局的中间部分
		
		//实现右边的JPanel容器界面
		JPanel jp=new JPanel();
		jp.setPreferredSize(dim1);//设置JPanel的大小
		jp.setBackground(Color.LIGHT_GRAY);//设置右边的界面颜色为白色
		jf.add(jp,BorderLayout.EAST);//添加到框架布局的东边部分
		jp.setLayout(new FlowLayout());//设置JPanel为流式布局
		
		//编辑用户信息栏，包括头像，昵称，性别，等级
		//ImageIcon[] userPicture={USERPICTURE,USERNAME,USERSEX,USERLEVEL};
		String[] userMessage={"pic","昵称：Alexwym","性别：男","等级：新手"};
		JLabel[] user =new JLabel[4];
		//设置背景图片的大小
		USERPICTURE.setImage(USERPICTURE.getImage().getScaledInstance(dim3.width, dim3.height,Image.SCALE_DEFAULT ));
		user[0]=new JLabel(USERPICTURE);
		user[0].setPreferredSize(dim3);
		jp.add(user[0]);
		
		for(int i=1;i<4;i++){
			user[i]=new JLabel(userMessage[i]);
			user[i].setPreferredSize(dim2);
			/*
			 *利用setFont来设置TextField文本框输入信息的字体大小
			 *textx1.setFont(new Font(Font.DIALOG,Font.PLAIN,30));
			 */	
			user[i].setFont(new Font(Font.MONOSPACED,Font.ITALIC,20));
			jp.add(user[i]);
		}
		
		
		//接下来我们需要把按钮等组件依次加到那个JPanel上面
		//设置按钮数组
		//String[] butname= {"","",""};
		String[] butname= {"开始新游戏","悔棋","认输"};
		ImageIcon[] BackgroundPicture={STARTBUTTON,BACKBUTTON,LOSEBUTTON}; 
		JButton[] button=new JButton[3];
		
		//依次把三个按钮组件加上去
		for(int i=0;i<butname.length;i++) {
			BackgroundPicture[i].setImage(BackgroundPicture[i].getImage().getScaledInstance(dim4.width+20, dim4.height,Image.SCALE_DEFAULT ));
			button[i]=new JButton(butname[i],BackgroundPicture[i]);
			button[i].setPreferredSize(dim4);
			jp.add(button[i]);
		}
		
		//设置选项按钮
		
		ImageIcon[] pic={BATTLEBUTTON1,BATTLEBUTTON2}; 
		//String[] boxname= {"人人对战","人机对战"};
		JComboBox box=new JComboBox(pic);
		box.setPreferredSize(dim4);
		pic[0].setImage(pic[0].getImage().getScaledInstance(dim4.width, dim4.height,Image.SCALE_DEFAULT ));
		pic[1].setImage(pic[1].getImage().getScaledInstance(dim4.width, dim4.height,Image.SCALE_DEFAULT ));
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
	
	public void PopUp(String top,String result) {
		JOptionPane jo=new JOptionPane();
		jo.showMessageDialog(null, result, top, JOptionPane.PLAIN_MESSAGE);
	}
	
	//重写重绘方法,这里重写的是第一个大的JPanel的方法
	public void paint(Graphics g) {
		super.paint(g);//画出白框
		//添加背景图片
		g.drawImage(CHESSBOARD, 0, 0,this.getWidth(), this.getHeight(), this);
		
		//重绘出棋盘
		g.setColor(Color.black);
		for(int i=0;i<ROW;i++) {
			g.drawLine(X, Y+SIZE*i, X+SIZE*(COLUMN-1), Y+SIZE*i);
		}
		for(int j=0;j<COLUMN;j++) {
			g.drawLine(X+SIZE*j, Y, X+SIZE*j, Y+SIZE*(ROW-1));
		}
		
		//重绘出棋子
		for(int i=0;i<ROW;i++) {
			for(int j=0;j<COLUMN;j++) {
				if(isAvail[i][j]==1) {
					int countx=SIZE*j+SIZE/2;
					int county=SIZE*i+SIZE/2;
					g.drawImage(BLACKCHESS,countx-SIZE+X, county-SIZE/2, SIZE, SIZE,null);
				}
				else if(isAvail[i][j]==2) {
					int countx=SIZE*j+SIZE/2;
					int county=SIZE*i+SIZE/2;
					g.drawImage(WHITECHESS,countx-SIZE+X, county-SIZE/2, SIZE, SIZE,null);
				}
			}
		}
	}
	
}
