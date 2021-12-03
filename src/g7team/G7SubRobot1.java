package g7team;

import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.Color;

//import gun.Gun;
import move.Move;

public class G7SubRobot1 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	
	public void run() {	
		//着色
		Color springgreen  = new Color(0,255,127);
		setColors(springgreen, springgreen, springgreen, springgreen, springgreen);
		
		//左担当
		mode = 0;
		
		//Move型用意
		mMove = new Move(this, 400.0, 400.0, 300.0, mode);
		
		//軌道に乗る
		mMove.getOnTrack();
		
		//銃口を回転させ続ける
		while(true) {
			turnGunRight(360.0);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			//捉えたのが味方なら何もしない
			return;
		}else {
			fire(1);
		}
	}
}