package G7team;

import robocode.*;
import java.awt.Color;

public class G7LeaderRobot extends TeamRobot{
	private Gun  mGun;
	private Move mMove;
	private double radius;
	private double CENTER_X = 400.0;
	private double CENTER_Y = 400.0;
	//private String satelite1 = "G7team.G7SubRobot1";
	//private String satelite2 = "G7team.G7SubRobot2";
	private boolean mUnderFix = false;
	
	public void run() {	
		mGun = new Gun(this, 2.0);
		
		//着色(黄緑色は0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);

		/*
		 TODO 回転半径(いろいろいじってみてください)
		 */
		radius = 50.0;
		
		//中央用
		mMove = new Move(this, CENTER_X, CENTER_Y, "NONE", 0.0, 0.0, radius, 0);
		
		//軌道に乗る
		mMove.getOnTrack();
		
		//軌道を周回
		turnGunLeft(90.0);
		mMove.centerRound();
	}
	
	/*
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			return;
		}else {
			mGun.onScannedRobot(e);
		}
	}
	*/
	
	// 中心機体は不要?
	public void onScannedRobot(ScannedRobotEvent e) {	
		if (isTeammate(e.getName())) {
			return;
		}else if(isNecessaryToAmend()){
			if(!mUnderFix) {
				mUnderFix = true;
				setTurnGunRight(convertDegree(getHeading() - 90.0 - getGunHeading()));
			}
		}else if(!mUnderFix){
			mGun.onScannedRobot(e);
		}
		
		if(isFixedEnough(5.0)){
			mUnderFix = false;
		}
	}
	
	// 砲台の角度を修正する必要があるかを判定
	private boolean isNecessaryToAmend() {
		return (
				(CENTER_X - getX()) * Math.sin(getGunHeadingRadians())
				 + (CENTER_Y - getY()) * Math.cos(getGunHeadingRadians()) > 0
		);
	}
	// 十分に修正できたかを判定(引数には誤差)
	private boolean isFixedEnough(double degree) {
		return (
				((CENTER_X - getX()) * Math.sin(getGunHeadingRadians())
				 + (CENTER_Y - getY()) * Math.cos(getGunHeadingRadians()))
				/ Math.sqrt(
						(CENTER_X - getX())*(CENTER_X - getX()) 
						+ (CENTER_Y - getY())*(CENTER_Y - getY()))
				< (-1.0) * Math.cos(Math.toDegrees(degree))
		);
	}
	
	private double convertDegree(double Degree) {
		double D;
		D = Degree;
		while(D>180.0) {
			D -= 360.0;
		}
		while(D<-180.0) {
			D += 360.0;
		}
		return(D);
	}
}