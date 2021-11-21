package move;

import robocode.AdvancedRobot;

public class Move {
	// ��]�ړ��̒��S���W
	private double mCenterX;
	private double mCenterY;
	
	// ��]�ړ��̔��a
	private double mRadius;
	
	// �����̃��{�b�g
	private AdvancedRobot mRobot;
	
	public Move(AdvancedRobot robot, double radius){
		mRobot  = robot;
		mRadius = radius;
	}
	
	// �����l����~�O���ɂ̂�܂ł̈ړ�
	public void getOnTrack() {
		do {
			double centerAngle = calculateDegree();
			mRobot.setTurnLeft(centerAngle);
			mRobot.setAhead(2.0);
		}while(Math.abs(calculateDistance() - mRadius) > 10.0);
	}

	// �~�O����ł̈ړ�
	public void circulate() {
		mRobot.setTurnRightRadians(Math.cos(
				calculateDegree() - (calculateDistance() - mRadius)));
		mRobot.setAhead(2.0);
	}
	
	// ���ݒl�Ɖ~�̒��S�̋������v�Z
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	// ���ݒl�Ɖ~�̒��S�Ƃ̊p�x���v�Z
	private double calculateDegree() {
		// TODO �v���Z(��3�ی��͕ۏ؍ς�)�@arctan�̒l�悪�e���H
		return Math.atan(
			(mCenterY - mRobot.getY())/(mCenterX - mRobot.getX()))
				- mRobot.getHeading(); 
	}
}
