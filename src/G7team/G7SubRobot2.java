package G7team;

import robocode.*;
import java.awt.Color;

public class G7SubRobot2 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	
	public void run() {	
		//’…F
		Color springgreen  = new Color(0,255,127);
		setColors(springgreen, springgreen, springgreen, springgreen, springgreen);

		//‰E’S“–
		mode = 1;
		
		//MoveŒ^—pˆÓ
		mMove = new Move(this, 400.0, 400.0, 300.0, mode);
		
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
}