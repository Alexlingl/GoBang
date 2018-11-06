import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MyJPanel extends JPanel implements GoBangconfig{

	
	public void paint(Graphics g){
		super.paint(g);
		
		//Ìí¼Ó±³¾°Í¼Æ¬
		g.drawImage(MESSAGEPICTURE, 0, 0,this.getWidth(), this.getHeight(), this);
	}
}
