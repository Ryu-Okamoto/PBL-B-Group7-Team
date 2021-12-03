package g7team;

import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.awt.Color;

import move.Move;
//import gun.*;

public class G7LeaderRobot extends TeamRobot{
	//private Gun  mGun;
	private Move mMove;
	
	public void run() {	
		//着色
		Color springgreen  = new Color(0,255,127);
		setColors(springgreen, springgreen, springgreen, springgreen, springgreen);
		
		//中央用
		mMove = new Move(this, 400.0, 400.0, 50.0, 2);
		
		//軌道に乗る
		mMove.getOnTrack();
		
		//銃口を回転させ続ける
		while(true) {
			turnGunRight(360.0);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			return;
		}else {
			fire(1);
		}
	}
}