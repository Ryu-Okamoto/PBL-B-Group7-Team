package g7team;

import robocode.*;

import gun.*;
import move.*;

public class G7LeaderRobot extends AdvancedRobot{
	private Gun  mGun;
	private Move mMove;
	
	public void run() {	
		mMove = new Move(this, 50.0);

		mMove.getOnTrack();		
		while(true) {
			// 銃口を360度回転
			turnGunRight(360.0);
			// レーダーを右に回転(不要？)
			// turnGunRightRadians(Double.POSITIVE_INFINITY);
			
			mMove.circulate();
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}
}
