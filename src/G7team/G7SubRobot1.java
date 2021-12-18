package G7team;

import robocode.*;
import java.awt.Color;
import java.io.IOException;

public class G7SubRobot1 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	private double radius;
	private double myX;
	private String buddy = "G7team.G7SubRobot2";
	private String primary = "G7LeaderRobot";
	
	public void run() {	
		//���F(���ΐF��0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);
		
		//���@�̏����ʒu�𑗐M
		myX = getX();
		mode = -1;
		try {
			sendMessage(buddy,new Point(myX,getY()));
		} catch (IOException e) {
			System.out.println("Sending Message Error");
		}
		
		while(mode<0) {
			turnGunRight(360);
		}
		
		/*
		 TODO ��]���a(���낢�낢�����Ă݂Ă�������)
		 */
		radius = 150.0;
		
		//Move�^�p��
		mMove = new Move(this, 400.0, 400.0, radius, mode);
		
		//�O���ɏ��
		mMove.getOnTrack();
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			//�������̂������Ȃ牽�����Ȃ�
			return;
		}else {
			fire(1);
		}
	}
	
	public void onMessageReceived(MessageEvent e) {
		if(mode<0) {
			Point p = (Point)e.getMessage();
			if(myX<=p.getX()) {
				mode = 0;
			}else {
				mode = 1;
			}
		}
	}
}