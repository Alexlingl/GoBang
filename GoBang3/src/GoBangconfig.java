import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

//定义与棋盘数据相关的接口，保存棋盘的起点，格子大小，行数列数等信息
public interface GoBangconfig {
	int X=170;
	int Y=20;
	int SIZE=50;
	int ROW=15;
	int COLUMN=15;
	int UIWIDTH=1265;
	int UIHIGHTH=785;
	int MESSAGEWIDTH=240;//设置右边信息栏的宽度
	Image BLACKCHESS= new ImageIcon("pic\\black.png").getImage();	//这里不能用ImageIcon
	Image WHITECHESS= new ImageIcon("pic\\white.png").getImage();	//这里不能用ImageIcon 
	Image CHESSBOARD= new ImageIcon("pic\\ChessBoard.jpg").getImage();	//这里不能用ImageIcon 
	Image MESSAGEPICTURE= new ImageIcon("pic\\MessagePictrue.jpg").getImage();	//这里不能用ImageIcon 
	Image LOGINPICTURE= new ImageIcon("pic\\LoginPicture.jpg").getImage();	//这里不能用ImageIcon 
	Image LOGINPICTURE2= new ImageIcon("pic\\LoginPicture2.jpg").getImage();	//这里不能用ImageIcon 
	ImageIcon LOGINBUTTON = new ImageIcon("pic\\LoginButton.png"); 
	ImageIcon STARTBUTTON = new ImageIcon("pic\\StartButton.png"); 
	ImageIcon BACKBUTTON = new ImageIcon("pic\\BackButton.png"); 
	ImageIcon LOSEBUTTON = new ImageIcon("pic\\LoseButton.png");
	ImageIcon BATTLEBUTTON1 = new ImageIcon("pic\\Battle1.png");
	ImageIcon BATTLEBUTTON2 = new ImageIcon("pic\\Battle2.png");
	ImageIcon USERPICTURE = new ImageIcon("pic\\UserPicture.png");
	ImageIcon USERNAME = new ImageIcon("pic\\UserName.png");
	ImageIcon USERSEX = new ImageIcon("pic\\UserSex.png");
	ImageIcon USERLEVEL = new ImageIcon("pic\\UserLevel.png");
	Dimension dim1=new Dimension(MESSAGEWIDTH,0);//设置右边信息栏的大小
	Dimension dim2=new Dimension(145,40);//设置登录按钮组件的大小
	Dimension dim3=new Dimension(120,120);//设置头像组件的大小
	Dimension dim4=new Dimension(140,45);//设置右边按钮组件的大小
}
