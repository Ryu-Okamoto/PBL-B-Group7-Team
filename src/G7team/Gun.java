package G7team;
import robocode.*;

public class Gun {
	private TeamRobot mRobot;
	private double    mBulletEnergy;
	
	public Gun(TeamRobot robot, double bulletEnergy) {
		this.mRobot        = robot; 
		this.mBulletEnergy = bulletEnergy;
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		// �v�Z�ɕK�v�ȍ��W�⑬�x���v�Z
		double absoluteBearing = mRobot.getHeadingRadians() + e.getBearingRadians();
		Point ourPosition      = new Point(mRobot.getX(), mRobot.getY());
		Point enemyPosition    = new Point(
				mRobot.getX() + e.getDistance() * Math.sin(absoluteBearing),
				mRobot.getY() + e.getDistance() * Math.cos(absoluteBearing));
		Point enemyVelocity    = new Point(
				e.getVelocity() * Math.sin(e.getHeadingRadians()),
				e.getVelocity() * Math.cos(e.getHeadingRadians()));
		double bulletVelocity = 20.0 - 3 * mBulletEnergy;
		double bulletRadius   = 0.0;
		
		// �G�@�ƕ������e�ۂ̋�����18.0�ȉ��ɂȂ�܂ŃV�~�����[�V����
		while(Math.abs(ourPosition.distance(enemyPosition) - bulletRadius) > 18.0) {
			enemyPosition = enemyPosition.add(enemyVelocity);
			bulletRadius  = bulletRadius + bulletVelocity;
		}
		double theta = convertDegree(calculateDegree(enemyPosition));
		
		mRobot.turnGunRight(theta);
		mRobot.fire(mBulletEnergy);
	}
	
	private double calculateDegree(Point enemyPosition) {
		// �v�Z�ɕK�v�Ȏ��@�̍��W���擾
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		// �v�Z�ɕK�v�ȓG�@�̍��W���擾
		double enemyX, enemyY;
		enemyX = enemyPosition.getX();
		enemyY = enemyPosition.getY();
		
		// �p�x�v�Z(�ڂ����v�Z���e��Slack�ɓ��e�����ʐ^�����Ă�������)
		if(X <= enemyX){
			if(Y <= enemyY){
				return(Math.atan((enemyX - X)/(enemyY - Y)) * 180 / Math.PI - 180.0);
			}else{
				return(-1.0 * Math.atan((enemyY - Y)/(enemyX - X)) * 180 / Math.PI - 90.0);
			}
		}else{
			if(Y <= enemyY){
				return(-1.0 * Math.atan((enemyY - Y)/(enemyX - X)) * 180 / Math.PI + 90.0);
			}else{
				return(Math.atan((enemyX - X)/(enemyY - Y)) * 180 / Math.PI);
			}
		}
	}
	
	//�p�x��-180�x�`180�x�ɕϊ����ĕԂ��֐�(-180�x�ȏ�A180�x�ȉ���Ԃ�)
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
