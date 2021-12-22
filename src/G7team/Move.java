package G7team;

import java.awt.Color;
import java.io.IOException;

import robocode.*;

public class Move{
	// ��]�ړ��̒��S���W
	private double mCenterX;
	private double mCenterY;
	
	//����1��̉q���@�̖��O
	String mBuddyName;
	
	//����1��̉q���@�̏������W
	private double mBuddyX;
	private double mBuddyY;
	
	// ��]�ړ��̔��a
	private double mRadius;
	
	// �����̃��{�b�g
	private TeamRobot mRobot;
	
	//�ړ����[�h(0:�����A11:���������C���A12:�������A�V�X�g�A21:�E�������C���A22:�E�����A�V�X�g)
	private int mmode;
	
	//��]����(1:���v���A-1:�����v���)
	private double clkwise;
	
	private String ADJUST_MSG = "Adjust!";
	
	/*����
		robot:���@���
		XCenter:�X�e�[�W������X���W
		YCenter:�X�e�[�W������Y���W
		XBuddy:����1��̉q���@�̏����ʒuX���W(�A�V�X�g�@�̂ݕK�v)
		YBuddy:����1��̉q���@�̏����ʒuY���W(�A�V�X�g�@�̂ݕK�v)
		radius:�~�O���̔��a
		mode:�ړ����[�h
	*/
	public Move(TeamRobot robot, double XCenter, double YCenter, String BuddyName, double XBuddy, double YBuddy, double radius, int mode){
		mRobot  = robot;
		mCenterX = XCenter;
		mCenterY = YCenter;
		mBuddyName = BuddyName;
		mBuddyX = XBuddy;
		mBuddyY = YBuddy;
		mRadius = radius;
		mmode = mode;
		clkwise = 1;
	}
	
	public void getOnTrack() {
		/*
		//�����ʒu
		firstMove();
		
		//�ŏ��ɉ����������߂�(���v���or�����v���)
		if((mRobot.getX()-mCenterX)*(mRobot.getY()-mCenterY)>=0) {
			//�X�e�[�W�E��or�����ɃX�|�[�������玞�v���
			clkwise = 1;
		}else {
			//�X�e�[�W����or�E���ɃX�|�[�������甽���v���
			clkwise = -1;
		}
		
		switch(mmode/10) {
			case 0://�启�@�̓���
				//�����̋O���ɏ��
				centerOnTrack();
				break;
			case 1://�����q���@�̓���
				switch(mmode%10) {
					case 1://���C���@�̓���
						centerOnTrack();
						break;
					case 2://�A�V�X�g�@�̓���
						assistFirstMove();
						assistOnLine();
						break;
				}
				break;
			case 2://�E���q���@�̓���
				switch(mmode%10) {
					case 1://���C���@�̓���
						centerOnTrack();
						break;
					case 2://�A�V�X�g�@�̓���
						assistFirstMove();
						assistOnLine();
						break;
				}
			break;
		}
		*/
		
		//���@���A�V�X�g�@�Ȃ�A�V�X�g�@�p�̈ړ�������
		if(mmode%10==2) {
			assistFirstMove();
			assistOnLine();
		}
		
		//�O���ɏ��
		centerOnTrack();
	}
	
	//�A�V�X�g�@�̏����ʒu�̈ړ�������assistFirstMove���\�b�h�Ɉڂ��܂���
	/*
	//�����ʒu�̈ړ��̏���
	public void firstMove() {
		switch(mmode) {
			case 0:
				//�������p�̋@�̂��E�����ŃX�^�[�g���Ă����ꍇ�A�������܂ňړ�����
				if(mRobot.getX()>=mCenterX) {
					mRobot.turnLeft(convertDegree(90.0 + mRobot.getHeading()));
					while(mRobot.getX()>=mCenterX) {
						mRobot.ahead(100.0);
					}
				}
				break;
			case 1:
				//�E�����p�̋@�̂��������ŃX�^�[�g���Ă����ꍇ�A�E�����܂ňړ�����
				if(mRobot.getX()<=mCenterX) {
					mRobot.turnRight(convertDegree(90.0 - mRobot.getHeading()));
					while(mRobot.getX()<=mCenterX) {
						mRobot.ahead(100.0);
					}
				}
				break;
			default:
				break;
			}
	}
	*/
	
	//���F����(�f�o�b�O�p�ɂǂ���)
	public void paintColor() {
		switch(mmode%10) {
			case 0:
				//�启�@�̐F��ΐF�ɕύX
				mRobot.setColors(Color.green, Color.green, Color.green, Color.green, Color.green);
				break;
			case 1:
				//���C���@�̐F��F�ɕύX
				mRobot.setColors(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);
				break;
			case 2:
				//�A�V�X�g�@�̐F��ԐF�ɕύX
				mRobot.setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
				break;
		}
	}
	
	//�p�x�v�Z�͒����p�݂̂Ŏ������悤�ɂȂ�܂���
	/*
	//���S�̕����̊p�x���v�Z(�������p)
	public double leftToCenterDegree() {
		//�v�Z�ɕK�v�Ȏ��@�̍��W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//�p�x�v�Z(�ڂ����v�Z���e��Slack�ɓ��e�����ʐ^�����Ă�������)
		if(X<=mCenterX){
			if(Y<=mCenterY){
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}else{
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}
		}else{
			if(Y<=mCenterY){
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}else{
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}
		}
	}

	//���S�̕����̊p�x���v�Z(�E�����p)
	public double rightToCenterDegree() {
		//�v�Z�ɕK�v�Ȏ��@�̍��W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//�p�x�v�Z(�ڂ����v�Z���e��Slack�ɓ��e�����ʐ^�����Ă�������)
		if(X<=mCenterX){
			if(Y<=mCenterY){
				return(Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}else{
				return(-1.0 * Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}
		}else{
			if(Y<=mCenterY){
				return(-1.0 * Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}else{
				return(Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}
		}
	}
	*/
	
	//���S�̕����̊p�x���v�Z(�����p)
	public double centerToCenterDegree() {
		//�v�Z�ɕK�v�Ȏ��@�̍��W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//�p�x�v�Z(�ڂ����v�Z���e��Slack�ɓ��e�����ʐ^�����Ă�������)
		if(X<=mCenterX){
			if(Y<=mCenterY){
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI - 180.0);
			}else{
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI - 90.0);
			}
		}else{
			if(Y<=mCenterY){
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}else{
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}
		}
	}
	
	//�p�x��-180�x�`180�x�ɕϊ����ĕԂ��֐�(-180�x�ȏ�A180�x�ȉ���Ԃ�)
	public double convertDegree(double Degree) {
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
	
	//�@�̂��O���ɏ��܂ł̓����������p�����ōςނ悤�ɂȂ�܂���
	/*
	//�������@�̂��O���ɏ��܂ł̓���
	public void leftOnTrack() {
		double ToDegree,MyDegree,TurnDegree;
		//�������ֈړ�
		
		
		//���S�ւ̊p�x���擾
		ToDegree = leftToCenterDegree();
		
		//���@�̊p�x���擾
		MyDegree = mRobot.getHeading();
		
		//�ǂꂾ����]���邩���v�Z
		TurnDegree = convertDegree(ToDegree - MyDegree);
		
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
		
		//���@�ƒ��S�Ƃ̋����Ɣ��a�Ƃ̍���10.0�����ɂȂ�܂ňړ�
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(Diff);
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
		//���S�ɑ΂��Đ�������������
		mRobot.turnLeft(90.0);
		
			
	}
	
	//�E�����@�̂��O���ɏ��܂ł̓���
	public void rightOnTrack() {
		double ToDegree,MyDegree,TurnDegree;
		//���S�ւ̊p�x���擾
		ToDegree = rightToCenterDegree();
		
		//���@�̊p�x���擾
		MyDegree = mRobot.getHeading();
		
		//�ǂꂾ����]���邩���v�Z
		TurnDegree = convertDegree(ToDegree + MyDegree);
		
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
		
		//���@�ƒ��S�Ƃ̋����Ɣ��a�Ƃ̍���10.0�����ɂȂ�܂ňړ�
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(Diff);
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
		//���S�ɑ΂��Đ�������������
		mRobot.turnLeft(90.0);
		
	}
	*/
	
	//�����@�̂��O���ɏ��܂ł̓���
	public void centerOnTrack() {
		
		double ToDegree,MyDegree,TurnDegree;
		//���S�ւ̊p�x���擾
		ToDegree = centerToCenterDegree();
		
		//���@�̊p�x���擾
		MyDegree = mRobot.getHeading();
		
		//�ǂꂾ����]���邩���v�Z
		TurnDegree = convertDegree(ToDegree - MyDegree);
		
		//���@��]
		mRobot.turnRight(TurnDegree);
		
		//���@�ƒ��S�Ƃ̋������v�Z
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//���@�ƒ��S�Ƃ̋����Ɣ��a�Ƃ̍���5.0�����ɂȂ�܂ňړ�
		while(Math.abs(Diff)>=5.0){
			mRobot.ahead(-Diff);
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}

	// ���ݒl�Ɖ~�̒��S�̋������v�Z
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	//�p�x�v�Z�������p�݂̂ŏ\���ɂȂ�܂���
	/*
	//�ڐ��������������߂ɉ�]�����Ȃ��Ƃ����Ȃ��p�x���v�Z(�������p)
	private double calcLeftVerDegree() {
		//�v�Z���e�͂܂�����A�}�������ŏЉ�悤�Ǝv���܂�
		return(convertDegree(leftToCenterDegree() + 270.0 - mRobot.getHeading()));
	}
	
	//�ڐ��������������߂ɉ�]�����Ȃ��Ƃ����Ȃ��p�x���v�Z(�E�����p)
	private double calcRightVerDegree() {
		//�v�Z���e�͂܂�����A�}�������ŏЉ�悤�Ǝv���܂�
		return(convertDegree(-rightToCenterDegree() + 270.0 - mRobot.getHeading()));
	}
	*/
	
	//�ڐ��������������߂ɉ�]�����Ȃ��Ƃ����Ȃ��p�x���v�Z(�����p)
	private double calcCenterVerDegree() {
		//�v�Z���e�͂܂�����A�}�������ŏЉ�悤�Ǝv���܂�
		return(convertDegree(centerToCenterDegree() + 90.0 - mRobot.getHeading()));
	}
	
	//�O����̓��������S�p�����ŏ\���ł�
	/*
	double sukima = 40.0; //���E�̔��~�̋��E�����̊Ԋu(���Ƃŕς�����悤�ɕϐ����g���Ă��܂�)
	
	//���~�O����`������(�������p)
	private void leftRound() {
		//�ȉ��̏������ꐶ���[�v
		while (true) {
			//�u�ԑ̂��ڐ������������悤�ɉ�]���鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			mRobot.setTurnRight(calcLeftVerDegree());
			
			//�u���v���ɑO�ior��ނ��鏈���v��\��(���̎��_�ł͂܂��ړ����Ȃ�)
			mRobot.setAhead(10.0 * clkwise);
			
			//�����̍��W�����~�̋��E�̏ꍇ�ɐ܂�Ԃ�����
			if(mRobot.getX()>= mCenterX - sukima) {
				if(mRobot.getY()>=mCenterY) {
					clkwise = -1.0;
				}else {
					clkwise = 1.0;
				}
			}
			
			//���S����̋���������n�߂���(�~�O�����炸��n�߂���)�C�����鏈��
			if(Math.abs(calculateDistance()-mRadius)>=20.0) {
				leftOnTrack();
			}
			
			//�u�e����1��]�����鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			//mRobot.setTurnGunRight(360.0);
			
			//�\�񂵂Ă�������(�ԑ̉�]�A�ړ��A�e����])�𓯎��Ɏ��s
			mRobot.execute();
		 }
	}
	
	//���~�N����`������(�E�����p)
	private void rightRound() {
		//�ȉ��̏������ꐶ���[�v
		while (true) {
			//�u�ԑ̂��ڐ������������悤�ɉ�]���鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			mRobot.setTurnRight(calcRightVerDegree());
			
			//�u���v���ɑO�ior��ނ��鏈���v��\��(���̎��_�ł͂܂��ړ����Ȃ�)
			mRobot.setAhead(10.0 * clkwise);
			
			//�����̍��W�����~�̋��E�̏ꍇ�ɐ܂�Ԃ�����
			if(mRobot.getX()<= mCenterX + sukima) {
				if(mRobot.getY()<mCenterY) {
					clkwise = -1.0;
				}else {
					clkwise = 1.0;
				}
			}

			//���S����̋���������n�߂���(�~�O�����炸��n�߂���)�C�����鏈��
			if(Math.abs(calculateDistance()-mRadius)>=20.0) {
				rightOnTrack();
			}

			//�u�e����1��]�����鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			//mRobot.setTurnGunRight(360.0);

			//�\�񂵂Ă�������(�ԑ̉�]�A�ړ��A�e����])�𓯎��Ɏ��s
			mRobot.execute();
		 }
	}
	*/
	
	//�~�O����`������(�����p)
	public void centerRound() {
		//���S�ɑ΂��Đ�������������
		mRobot.turnRight(90.0);
		
		//�ȉ��̏������ꐶ���[�v
		while (true) {
			//�u�ԑ̂��ڐ������������悤�ɉ�]���鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			mRobot.setTurnRight(calcCenterVerDegree());
			
			//�u���v���ɑO�ior��ނ��鏈���v��\��(���̎��_�ł͂܂��ړ����Ȃ�)
			mRobot.setAhead(5.0 * clkwise);

			//���S����̋���������n�߂���(�~�O�����炸��n�߂���)�C�����鏈��
			if(Math.abs(calculateDistance()-mRadius)>=30.0) {
				sendAdjustMsg();
				centerOnTrack();
				mRobot.turnRight(90.0);
			}

			//�u�e����1��]�����鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			//mRobot.setTurnGunRight(360.0);
			
			//�\�񂵂Ă�������(�ԑ̉�]�A�ړ��A�e����])�𓯎��Ɏ��s
			mRobot.execute();
		 }
	}
	
	//�A�V�X�g�@�����E�ɕ�����郁�\�b�h
	private void assistFirstMove() {
		double myX,goalX;
		myX = mRobot.getX();
		goalX = myX;
		switch(mmode/10){
			case 1://���������A�V�X�g�@�ɂ���
				if(myX>=mCenterX) {
					goalX = 2.0*mCenterX - myX;
				}
				break;
			case 2://�E�������A�V�X�g�@�ɂ���
				if(myX<=mCenterX) {
					goalX = 2.0*mCenterX - myX;
				}
				break;
		}
		
		//�A�V�X�g�@���ړ����ׂ��ʒu�Ɉړ�
		while(Math.abs(goalX-myX)>1.0) {
			faceLateral(goalX-myX);
			mRobot.ahead(Math.abs(goalX-myX));
			myX = mRobot.getX();
		}
	}
	
	//�A�V�X�g�@�����C����ɏ��悤�ɂ��郁�\�b�h
	private void assistOnLine() {
		double myX,myY,goalY;
		//���@�̌����W
		myX = mRobot.getX();
		myY = mRobot.getY();
		
		//�ڕW�ƂȂ�Y���W�̎Z�o
		if(mmode/10==1) {
			goalY = mCenterY - ((mCenterX - myX)*(mBuddyY - mCenterY)/(mBuddyX - mCenterX));
		}else {
			goalY = mCenterY + ((myX - mCenterX)*(mCenterY - mBuddyY)/(mCenterX - mBuddyX));
		}
		
		//���C����ɏ��悤�Ɉړ�
		while(Math.abs(goalY-myY)>1.0) {
			facePortrait(goalY-myY);
			mRobot.ahead(Math.abs(goalY-myY));
			myY = mRobot.getY();
		}
	}
	
	//�c����(Y������)�֎ԑ̂������郁�\�b�h�Bmode�����Ȃ�����(Y��+����)�A���Ȃ牺����(Y��-����)
	private void facePortrait(double mode) {
		//�ǂ̊p�x�������ׂ���[�����:0�x�A������:180�x]
		double targetDegree;
		if(mode>0.0) {
			targetDegree = 0.0;
		}else {
			targetDegree = 180.0;
		}
		
		//���̊p�x�������悤�Ɏԑ̉�]
		while(Math.abs(convertDegree(mRobot.getHeading()-targetDegree))>=0.1) {
			mRobot.turnRight(convertDegree(targetDegree-mRobot.getHeading()));
		}
	}
	
	//������(X������)�֎ԑ̂������郁�\�b�h�Bmode�����Ȃ�E����(X��+����)�A���Ȃ獶����(X��-����)
	private void faceLateral(double mode) {
		//�ǂ̊p�x�������ׂ���[�E����:90�x�A������:270�x]
		double targetDegree;
		if(mode>0.0) {
			targetDegree = 90.0;
		}else {
			targetDegree = 270.0;
		}
		
		//���̊p�x�������悤�Ɏԑ̉�]
		while(Math.abs(convertDegree(mRobot.getHeading()-targetDegree))>=0.1) {
			mRobot.turnRight(convertDegree(targetDegree-mRobot.getHeading()));
		}
	}

	//��]������ς��郁�\�b�h
	public void turnClock() {
		clkwise *= -1.0;
	}
	
	//�N���␳�������Ƃ𑊕��ɓ`���郁�\�b�h
	public void sendAdjustMsg() {
		if(mmode>0) {
			try {
				mRobot.sendMessage(mBuddyName,ADJUST_MSG);
			} catch (IOException e) {
				System.out.println("Sending Message Error");
			}
		}
	}
	
	/*
	 TODO ������Ȃ��Ƃ����Ȃ����ƁE����Ă����ׂ�����
	 */
}
