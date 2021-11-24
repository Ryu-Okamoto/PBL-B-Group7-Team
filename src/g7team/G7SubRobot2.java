package g7team;

import robocode.*;
//import gun.Gun;
import move.Move;

public class G7SubRobot2 extends AdvancedRobot{
	//private Gun  mGun;
	private Move mMove;
	
	public void run() {	
		//‰E”¼•ª—p
		mMove = new Move(this, 400.0, 400.0, 300.0, 1);
		
		//‹O“¹‚Éæ‚é
		mMove.getOnTrack();

		//eŒû‚ğ‰ñ“]‚³‚¹‘±‚¯‚é
		while(true) {
			turnGunRight(360.0);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}
}
