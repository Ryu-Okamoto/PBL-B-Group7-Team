package G7team;

import robocode.*;
import java.awt.Color;

public class G7LeaderRobot extends TeamRobot{
	private Gun  mGun;
	private Move mMove;
	private double radius;
	private double CENTER_X = 400.0;
	private double CENTER_Y = 400.0;
	private boolean mUnderFix = false;
	
	public void run() {	
		mGun = new Gun(this, 2.0);
		
		Color teamcolor  = new Color(0,255,127);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);

		radius = 50.0;
		
		mMove = new Move(this, CENTER_X, CENTER_Y, "NONE", 0.0, 0.0, radius, 0);
		
		mMove.getOnTrack();
		
		turnGunLeft(90.0);
		mMove.centerRound();
	}
	
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
	
	private boolean isNecessaryToAmend() {
		return (
				(CENTER_X - getX()) * Math.sin(getGunHeadingRadians())
				 + (CENTER_Y - getY()) * Math.cos(getGunHeadingRadians()) > 0
		);
	}
	
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
	
	public void onHitRobot(HitRobotEvent e) {
		ahead(-10.0);
		mMove.turnClock();
	}
}