//设置按钮监听方法ButttonLitener类
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JComboBox;
//实现对JPanel的监听接口处理
public class ButtonListener implements GoBangconfig,ActionListener{
	public GoBangframe gf;
	public JComboBox box;
	
	public ButtonListener(GoBangframe gf,JComboBox box) {
		this.gf=gf;//获取左半部分的画板
		this.box=box;//获取可选框对象
	}
	//当界面发生操作时进行处理
	public void actionPerformed(ActionEvent e) {
		
		//必须得用else if，因为如果没有else if你每次在右边的界面点击时它都会获取人人对战或者人机对战的信息，每次都会重置棋盘数组
		//获取当前被点击按钮的内容，判断是不是"开始新游戏"这个按钮
		if(e.getActionCommand().equals("开始新游戏")) {
			//重绘棋盘
		    for(int i=0;i<gf.isAvail.length;i++)
			   for(int j=0;j<gf.isAvail[i].length;j++)
			    	 gf.isAvail[i][j]=0;
		    gf.repaint();
			//如果是开始新游戏的按钮，再为左半部分设置监听方法
			gf.turn=1;
		}
		//判断当前点击的按钮是不是悔棋
		else if(e.getActionCommand().equals("悔棋")) {
			if(gf.ChessPositonList.size()>1) {
				//把棋子数组相应的位置置为0；
				ChessPosition l=new ChessPosition();
				//获取最后一个棋子的对象信息
				l=gf.ChessPositonList.remove(gf.ChessPositonList.size()-1);
				//把相应的数组位置置为0
				gf.isAvail[l.Listi][l.Listj]=0;
				//把玩家还原为上一步的玩家
				if(gf.turn==1) gf.turn++;
				else gf.turn--;
				
				//直接调用gf的重绘方法，重绘方法的画笔应该是在棋盘页面还没生成的时候就要获取
				//调用repaint会自动调用paint方法，而且不用给参数
				gf.repaint();
				//gf.paint(gf.getGraphics());

			}
			else {
				System.out.println("不能悔棋!");
			}
		}
		else if(e.getActionCommand().equals("认输")) {
			if(gf.turn==1) System.out.println("白方赢");
			else System.out.println("黑方赢");
		    //重新把棋盘设置为不可操作
		    gf.turn=0;
		}
		else if(box.getSelectedItem().equals("人机对战")) {
			 gf.ChooseType=1;
			 gf.turn=0;
		}
		else if(box.getSelectedItem().equals("人人对战")){
			 gf.ChooseType=0;
			 gf.turn=0;
		}
	}
	
}
