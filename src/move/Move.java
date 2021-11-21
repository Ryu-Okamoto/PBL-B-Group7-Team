package move;

import robocode.AdvancedRobot;

public class Move {
	// ‰ñ“]ˆÚ“®‚Ì’†SÀ•W
	private double mCenterX;
	private double mCenterY;
	
	// ‰ñ“]ˆÚ“®‚Ì”¼Œa
	private double mRadius;
	
	// §Œäæ‚Ìƒƒ{ƒbƒg
	private AdvancedRobot mRobot;
	
	public Move(AdvancedRobot robot, double radius){
		mRobot  = robot;
		mRadius = radius;
	}
	
	// ‰Šú’l‚©‚ç‰~‹O“¹‚É‚Ì‚é‚Ü‚Å‚ÌˆÚ“®
	public void getOnTrack() {
		do {
			double centerAngle = calculateDegree();
			mRobot.setTurnLeft(centerAngle);
			mRobot.setAhead(2.0);
		}while(Math.abs(calculateDistance() - mRadius) > 10.0);
	}

	// ‰~‹O“¹ã‚Å‚ÌˆÚ“®
	public void circulate() {
		mRobot.setTurnRightRadians(Math.cos(
				calculateDegree() - (calculateDistance() - mRadius)));
		mRobot.setAhead(2.0);
	}
	
	// Œ»İ’l‚Æ‰~‚Ì’†S‚Ì‹——£‚ğŒvZ
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	// Œ»İ’l‚Æ‰~‚Ì’†S‚Æ‚ÌŠp“x‚ğŒvZ
	private double calculateDegree() {
		// TODO —vŒŸZ(‘æ3ÛŒÀ‚Í•ÛØÏ‚İ)@arctan‚Ì’lˆæ‚ª‰e‹¿H
		return Math.atan(
			(mCenterY - mRobot.getY())/(mCenterX - mRobot.getX()))
				- mRobot.getHeading(); 
	}
}
