package G7team;

import robocode.*;
import java.awt.Color;

public class G7SubRobot2 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	
	public void run() {	
		//���F
		Color springgreen  = new Color(0,255,127);
		setColors(springgreen, springgreen, springgreen, springgreen, springgreen);

		//�E�S��
		mode = 1;
		
		//Move�^�p��
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
			//�������̂������Ȃ牽�����Ȃ�
			return;
		}else {
			fire(1);
		}
	}
}