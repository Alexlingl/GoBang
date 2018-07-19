import java.awt.Image;

import javax.swing.ImageIcon;

//定义与棋盘数据相关的接口，保存棋盘的起点，格子大小，行数列数等信息
public interface GoBangconfig {
	int x=20,y=20,size=40,row=15,column=15;
	Image blackchess= new ImageIcon("black.png").getImage();	//这里不能用ImageIcon
	Image whitechess= new ImageIcon("white.png").getImage();	//这里不能用ImageIcon 
}
