package G7team;

import robocode.*;
import java.awt.Color;
import java.io.IOException;

public class G7SubRobot1 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	private double radius;
	private double myX;
	private String buddy = "G7team.G7SubRobot2";
	private String primary = "G7LeaderRobot";
	
	public void run() {	
		//’…F(‰©—ÎF‚Í0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);
		
		//©‹@‚Ì‰ŠúˆÊ’u‚ğ‘—M
		myX = getX();
		try {
			sendMessage(buddy,new Point(myX,getY()));
		} catch (IOException e) {
			System.out.println("Sending Message Error");
		}
		
		//‚à‚¤1‹@‚Ì‰q¯–ğ‚©‚çÀ•W‚ª‘—‚ç‚ê‚Ä‚­‚é‚Ü‚Å‘Ò‹@
		mode = -1;
		while(mode<0) {
			turnGunRight(360.0);
		}
		
		/*
		 TODO ‰ñ“]”¼Œa(‚¢‚ë‚¢‚ë‚¢‚¶‚Á‚Ä‚İ‚Ä‚­‚¾‚³‚¢)
		 */
		radius = 150.0;
		
		//MoveŒ^—pˆÓ
		mMove = new Move(this, 400.0, 400.0, radius, mode);
		
		//‹O“¹‚Éæ‚é
		mMove.getOnTrack();
		
		//eŒû‚ğ‰ñ“]‚³‚¹‘±‚¯‚é
		while(true) {
			turnGunRight(360.0);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			//‘¨‚¦‚½‚Ì‚ª–¡•û‚È‚ç‰½‚à‚µ‚È‚¢
			return;
		}else {
			fire(1);
		}
	}
	
	public void onMessageReceived(MessageEvent e) {
		if(mode<0) {
			Point p = (Point)e.getMessage();
			if(myX<=p.getX()) {
				mode = 0;
			}else {
				mode = 1;
			}
		}
	}
}