package g7team;

import robocode.*;
import robocode.util.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.Color;

//import gun.Gun;
import move.Move;

public class G7SubRobot2 extends TeamRobot{
	//private Gun  mGun;
	private int mode;
	private Move mMove;
	private double myX;
	private double buddyX;
	//private String buddyName = "g7team.G7SubRobot1";
	
	public void run() {	
		//着色
		//Color springgreen  = new Color(0,255,127);
		//setColors(springgreen, springgreen, springgreen, springgreen, springgreen);
		setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
		
		//自分の初期位置を取得
		myX = getX();
		buddyX = -1.0;
		
		//座標送信
		try {
			broadcastMessage(new Point(myX,getY()));
		}catch(IOException ex) {}
		
		//メッセージを受け取るまでその場で回転
		while(buddyX<0.0) {
			turnGunRight(360.0);
		}
		
		//初期位置によって左右を決定
		if(myX<buddyX) {
			mode = 0;
		}else {
			mode = 1;
		}
		
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
			//線形予測(robowikiより引用)
			double bulletPower = Math.min(3.0,getEnergy());
			double myX = getX();
			double myY = getY();
			double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
			double enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
			double enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
			double enemyHeading = e.getHeadingRadians();
			double enemyVelocity = e.getVelocity();


			double deltaTime = 0;
			double battleFieldHeight = getBattleFieldHeight(), 
			       battleFieldWidth = getBattleFieldWidth();
			double predictedX = enemyX, predictedY = enemyY;
			while((++deltaTime) * (20.0 - 3.0 * bulletPower) < 
			      Point2D.Double.distance(myX, myY, predictedX, predictedY)){		
				predictedX += Math.sin(enemyHeading) * enemyVelocity;	
				predictedY += Math.cos(enemyHeading) * enemyVelocity;
				if(	predictedX < 18.0 
					|| predictedY < 18.0
					|| predictedX > battleFieldWidth - 18.0
					|| predictedY > battleFieldHeight - 18.0){
					predictedX = Math.min(Math.max(18.0, predictedX), 
			                    battleFieldWidth - 18.0);	
					predictedY = Math.min(Math.max(18.0, predictedY), 
			                    battleFieldHeight - 18.0);
					break;
				}
			}
			double theta = Utils.normalAbsoluteAngle(Math.atan2(
			    predictedX - getX(), predictedY - getY()));

			setTurnRadarRightRadians(
			    Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
			setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
			fire(bulletPower);
		}
	}
	
	public void onMessageReceived(MessageEvent e) {
		if(buddyX<0) {
			//もう1つの衛星機の初期位置を受け取る
			Point p = (Point) e.getMessage();
			buddyX = p.getX();
		}
	}
}