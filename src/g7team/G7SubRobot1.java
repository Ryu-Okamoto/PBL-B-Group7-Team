package g7team;

import robocode.*;

import java.awt.Color;

//import gun.Gun;
import move.Move;

public class G7SubRobot1 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	
	public void run() {	
		//���F
		Color springgreen  = new Color(0,255,127);
		setColors(springgreen, springgreen, springgreen, springgreen, springgreen);
		
		//�������p
		mode = 0;
		mMove = new Move(this, 400.0, 400.0, 300.0, mode);
		
		//�O���ɏ��
		mMove.getOnTrack();
		
		//�e������]����������
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