package move;

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.Color;

public class Move{
	// ��]�ړ��̒��S���W
	private double mCenterX;
	private double mCenterY;
	
	// ��]�ړ��̔��a
	private double mRadius;
	
	// �����̃��{�b�g
	private AdvancedRobot mRobot;
	
	//�ړ����[�h(0:�������A1:�E�����A2:����)
	private int mmode;
	
	public Move(AdvancedRobot robot, double XCenter, double YCenter, double radius, int mode){
		mRobot  = robot;
		mCenterX = XCenter;
		mCenterY = YCenter;
		mRadius = radius;
		mmode = mode;
		System.out.println("Constractar was Finished");
	}
	
	public void getOnTrack() {
		System.out.println("getOnTrack() Start");
		switch(mmode) {
		case 0:
			//�F��F�ɕύX
			mRobot.setColors(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);
			//�������̋O���ɏ��
			leftOnTrack();
			break;
		case 1:
			//�F��ԐF�ɕύX
			mRobot.setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
			//�E�����̋O���ɏ��
			rightOnTrack();
			break;
		case 2:
			//�F��ΐF�ɕύX
			mRobot.setColors(Color.green, Color.green, Color.green, Color.green, Color.green);
			//�����̋O���ɏ��
			centerOnTrack();
			break;
		}
		//mRobot.execute();
	}
	
	//�������@�̂��O���ɏ��܂ł̓���
	public void leftOnTrack() {
		//���@�̏������W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double ToDegree,MyDegree,TurnDegree;
		//���S�̕����̊p�x���v�Z
		if(X<=400.0){
			if(Y<=400.0){
				ToDegree = Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI;
			}else{
				ToDegree = -1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0;
			}
		}else{
			if(Y<=400.0){
				ToDegree = -1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0;
			}else{
				ToDegree = Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI;
			}
		}
		
		//���@�̊p�x���擾
		MyDegree = mRobot.getHeading();
		
		//�ǂꂾ����]���邩���v�Z
		TurnDegree = ToDegree - MyDegree;
		
		//�p�x��-180�x�`180�x�ɕϊ�
		while((TurnDegree>180.0)||(TurnDegree<-180.0)){
			if(TurnDegree>180.0){
				TurnDegree -= 360.0;
			}else{
				TurnDegree += 360.0;
			}
		}
		
		//���@��]
		mRobot.turnRight(TurnDegree);
		
		//���@���E�����ɂ���Ƃ��ɍ������܂ňړ����鏈��
		while(mRobot.getX()>=mCenterX){
			mRobot.ahead(-10.0);
		}
		
		//���@�ƒ��S�Ƃ̋������v�Z
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//���@�ƒ��S�Ƃ̋�����10.0�����ɂȂ�܂ňړ�
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(9.0 * Diff/Math.abs(Diff));
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}
	
	//�E�����@�̂��O���ɏ��܂ł̓���
	public void rightOnTrack() {
		//���@�̏������W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double ToDegree,MyDegree,TurnDegree;
		//���S�̕����̊p�x���v�Z
		if(X<=400.0){
			if(Y<=400.0){
				ToDegree = Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0;
			}else{
				ToDegree = -1.0 * Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI;
			}
		}else{
			if(Y<=400.0){
				ToDegree = -1.0 * Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI;
			}else{
				ToDegree = Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0;
			}
		}
		
		//���@�̊p�x���擾
		MyDegree = mRobot.getHeading();
		
		//�ǂꂾ����]���邩���v�Z
		TurnDegree = ToDegree + MyDegree;
		
		//�p�x��-180�x�`180�x�ɕϊ�
		while((TurnDegree>180.0)||(TurnDegree<-180.0)){
			if(TurnDegree>180.0){
				TurnDegree -= 360.0;
			}else{
				TurnDegree += 360.0;
			}
		}
		
		//���@��]
		mRobot.turnLeft(TurnDegree);
		
		//���@���E�����ɂ���Ƃ��ɍ������܂ňړ����鏈��
		while(mRobot.getX()<=mCenterX){
			mRobot.ahead(-10.0);
		}
		
		//���@�ƒ��S�Ƃ̋������v�Z
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//���@�ƒ��S�Ƃ̋�����10.0�����ɂȂ�܂ňړ�
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(9.0 * Diff/Math.abs(Diff));
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}
	
	//�����@�̂��O���ɏ��܂ł̓���
	public void centerOnTrack() {
		//���@�̏������W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double ToDegree,MyDegree,TurnDegree;
		//���S�̕����̊p�x���v�Z
		if(X<=400.0){
			if(Y<=400.0){
				ToDegree = Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI - 180.0;
			}else{
				ToDegree = -1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI - 90.0;
			}
		}else{
			if(Y<=400.0){
				ToDegree = -1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0;
			}else{
				ToDegree = Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI;
			}
		}
		
		//���@�̊p�x���擾
		MyDegree = mRobot.getHeading();
		
		//�ǂꂾ����]���邩���v�Z
		TurnDegree = ToDegree - MyDegree;
		
		//�p�x��-180�x�`180�x�ɕϊ�
		while((TurnDegree>180.0)||(TurnDegree<-180.0)){
			if(TurnDegree>180.0){
				TurnDegree -= 360.0;
			}else{
				TurnDegree += 360.0;
			}
		}
		
		//���@��]
		mRobot.turnRight(TurnDegree);
		
		//���@�ƒ��S�Ƃ̋������v�Z
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//���@�ƒ��S�Ƃ̋�����10.0�����ɂȂ�܂ňړ�
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(-9.0 * Diff/Math.abs(Diff));
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}

	// �����l����~�O���ɂ̂�܂ł̈ړ�
	/*
	public void getOnTrack() {
		do {
			double centerAngle = calculateDegree();
			mRobot.setTurnLeft(centerAngle);
			mRobot.setAhead(2.0);
			mRobot.execute();
		}while(Math.abs(calculateDistance() - mRadius) > 10.0);
	}
	*/

	// �~�O����ł̈ړ�
	/*
	public void circulate() {
		mRobot.setTurnRightRadians(Math.cos(
				calculateDegree() - (calculateDistance() - mRadius)));
		mRobot.setAhead(2.0);
		mRobot.execute();
	}
	*/
	
	// ���ݒl�Ɖ~�̒��S�̋������v�Z
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
}
