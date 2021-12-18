package G7team;

import robocode.*;
import java.awt.Color;

public class G7LeaderRobot extends TeamRobot{
	//private Gun  mGun;
	private Move mMove;
	private double radius;
	private String satelite1 = "G7team.G7SubRobot1";
	private String satelite2 = "G7team.G7SubRobot2";
	
	public void run() {	
		//着色(黄緑色は0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);

		/*
		 TODO 回転半径(いろいろいじってみてください)
		 */
		radius = 50.0;
		
		//中央用
		mMove = new Move(this, 400.0, 400.0, radius, 2);
		
		//軌道に乗る
		mMove.getOnTrack();
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			return;
		}else {
			fire(1);
		}
	}
}