package G7team;

import robocode.*;

public class Move{
	// ��]�ړ��̒��S���W
	private double mCenterX;
	private double mCenterY;
	
	// ��]�ړ��̔��a
	private double mRadius;
	
	// �����̃��{�b�g
	private TeamRobot mRobot;
	
	//�ړ����[�h(0:�������A1:�E�����A2:����)
	private int mmode;
	
	//��]����(1:���v���A-1:�����v���)
	private double clkwise;
	
	public Move(TeamRobot robot, double XCenter, double YCenter, double radius, int mode){
		mRobot  = robot;
		mCenterX = XCenter;
		mCenterY = YCenter;
		mRadius = radius;
		mmode = mode;
	}
	
	public void getOnTrack() {
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
		//�F�͉��Όn��
		switch(mmode) {
			case 0:
				//�������̋O���ɏ��
				leftOnTrack();
				//���~�O����`��
				leftRound();
				break;
			case 1:
				//�E�����̋O���ɏ��
				rightOnTrack();
				//���~�O����`��
				rightRound();
				break;
			case 2:
				//�����̋O���ɏ��
				centerOnTrack();
				//�~�O����`��
				centerRound();
				break;
		}
	}
	
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
	
	/*
	public void paintColor() {
		switch(mmode) {
			case 0:
				//�F��F�ɕύX
				mRobot.setColors(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);
				break;
			case 1:
				//�F��ԐF�ɕύX
				mRobot.setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
				break;
			case 2:
				//�F��ΐF�ɕύX
				mRobot.setColors(Color.green, Color.green, Color.green, Color.green, Color.green);
				break;
		}
	}
	*/
	
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
	
	//�p�x��-180�x�`180�x�ɕϊ����ĕԂ��֐�
	public double convertDegree(double Degree) {
		double D;
		D = Degree;
		while(D>180.0) {
			D -= 360.0;
		}
		while(D<=-180.0) {
			D += 360.0;
		}
		return(D);
	}
	
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
		
		//���S�ɑ΂��Đ�������������
		mRobot.turnRight(90.0);
	}

	// ���ݒl�Ɖ~�̒��S�̋������v�Z
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
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
	
	//�ڐ��������������߂ɉ�]�����Ȃ��Ƃ����Ȃ��p�x���v�Z(�����p)
	private double calcCenterVerDegree() {
		//�v�Z���e�͂܂�����A�}�������ŏЉ�悤�Ǝv���܂�
		return(convertDegree(centerToCenterDegree() + 90.0 - mRobot.getHeading()));
	}
	
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
	
	//�~�O����`������(�����p)
	private void centerRound() {
		//�ȉ��̏������ꐶ���[�v
		while (true) {
			//�u�ԑ̂��ڐ������������悤�ɉ�]���鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			mRobot.setTurnRight(calcCenterVerDegree());
			
			//�u���v���ɑO�ior��ނ��鏈���v��\��(���̎��_�ł͂܂��ړ����Ȃ�)
			mRobot.setAhead(5.0 * clkwise);

			//���S����̋���������n�߂���(�~�O�����炸��n�߂���)�C�����鏈��
			if(Math.abs(calculateDistance()-mRadius)>=10.0) {
				centerOnTrack();
			}

			//�u�e����1��]�����鏈���v��\��(���̎��_�ł͂܂���]���Ȃ�)
			//mRobot.setTurnGunRight(360.0);
			
			//�\�񂵂Ă�������(�ԑ̉�]�A�ړ��A�e����])�𓯎��Ɏ��s
			mRobot.execute();
		 }
	}
	/*
	 TODO ������Ȃ��Ƃ����Ȃ����ƁE����Ă����ׂ�����
	  1.�Փˏ���
	  2.Move�N���X����e�Ɋւ��鏈��(�e����]�Ȃ�)��S�ď���
	 */
}
