package move;

import robocode.AdvancedRobot;

public class Move {
	// 回転移動の中心座標
	private double mCenterX;
	private double mCenterY;
	
	// 回転移動の半径
	private double mRadius;
	
	// 制御先のロボット
	private AdvancedRobot mRobot;
	
	public Move(AdvancedRobot robot, double radius){
		mRobot  = robot;
		mRadius = radius;
	}
	
	// 初期値から円軌道にのるまでの移動
	public void getOnTrack() {
		do {
			double centerAngle = calculateDegree();
			mRobot.setTurnLeft(centerAngle);
			mRobot.setAhead(2.0);
			mRobot.execute();
		}while(Math.abs(calculateDistance() - mRadius) > 10.0);
	}

	// 円軌道上での移動
	public void circulate() {
		mRobot.setTurnRightRadians(Math.cos(
				calculateDegree() - (calculateDistance() - mRadius)));
		mRobot.setAhead(2.0);
		mRobot.execute();
	}
	
	// 現在値と円の中心の距離を計算
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	// 現在値と円の中心との角度を計算
	private double calculateDegree() {
		// TODO 要検算(第3象限は保証済み)　arctanの値域が影響？
		return Math.atan(
			(mCenterY - mRobot.getY())/(mCenterX - mRobot.getX()))
				- mRobot.getHeading(); 
	}
}
