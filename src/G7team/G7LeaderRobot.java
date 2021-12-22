package G7team;

import robocode.*;
import java.awt.Color;

public class G7LeaderRobot extends TeamRobot{
	//private Gun  mGun;
	private Move mMove;
	private double radius;
	private double CENTER_X = 400.0;
	private double CENTER_Y = 400.0;
	//private String satelite1 = "G7team.G7SubRobot1";
	//private String satelite2 = "G7team.G7SubRobot2";
	
	public void run() {	
		//���F(���ΐF��0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);

		/*
		 TODO ��]���a(���낢�낢�����Ă݂Ă�������)
		 */
		radius = 50.0;
		
		//�����p
		mMove = new Move(this, CENTER_X, CENTER_Y, "NONE", 0.0, 0.0, radius, 0);
		
		//�O���ɏ��
		mMove.getOnTrack();
		
		//�O��������
		turnGunLeft(90.0);
		mMove.centerRound();
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			return;
		}else {
			fire(1);
		}
	}
}