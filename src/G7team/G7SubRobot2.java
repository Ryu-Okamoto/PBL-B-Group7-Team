package G7team;

import robocode.*;
import java.awt.Color;
import java.io.IOException;

public class G7SubRobot2 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	private double radius;
	private double myX;
	private String buddy = "G7team.G7SubRobot1";
	private String primary = "G7LeaderRobot";
	
	public void run() {	
		//着色(黄緑色は0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);
		
		//自機の初期位置を送信
		myX = getX();
		mode = -1;
		try {
			sendMessage(buddy,new Point(myX,getY()));
		} catch (IOException e) {
			System.out.println("Sending Message Error");
		}
		
		while(mode<0) {
			turnGunRight(360.0);
		}
		
		/*
		 TODO 回転半径(いろいろいじってみてください)
		 */
		radius = 150.0;
		
		//Move型用意
		mMove = new Move(this, 400.0, 400.0, radius, mode);
		
		//軌道に乗る
		mMove.getOnTrack();
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			//捉えたのが味方なら何もしない
			return;
		}else {
			fire(1);
		}
	}
	
	public void onMessageReceived(MessageEvent e) {
		if(mode<0) {
			Point p = (Point)e.getMessage();
			if(myX<p.getX()) {
				mode = 0;
			}else {
				mode = 1;
			}
		}
	}
	
}