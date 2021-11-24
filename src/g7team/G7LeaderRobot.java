package g7team;

import robocode.*;

import move.Move;
//import gun.*;

public class G7LeaderRobot extends AdvancedRobot{
	//private Gun  mGun;
	private Move mMove;
	
	public void run() {	
		//’†‰›—p
		mMove = new Move(this, 400.0, 400.0, 50.0, 2);
		
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
