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
		
		while(Math.abs(ourPosition.distance(enemyPosition) - bulletRadius) > 18.0) {
			enemyPosition = enemyPosition.add(enemyVelocity);
			bulletRadius  = bulletRadius + bulletVelocity;
		}
		double theta = convertDegree(calculateDegree(enemyPosition));
		
		mRobot.turnGunRight(theta);
		mRobot.fire(mBulletEnergy);
	}
	
	private double calculateDegree(Point enemyPosition) {
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double enemyX, enemyY;
		enemyX = enemyPosition.getX();
		enemyY = enemyPosition.getY();
		
		double toDeglee;
		
		if(X <= enemyX){
			if(Y <= enemyY){
				toDeglee = Math.atan((enemyX - X)/(enemyY - Y)) * 180 / Math.PI;
			}else{
				toDeglee = Math.atan((Y - enemyY)/(enemyX - X)) * 180 / Math.PI + 90.0;
			}
		}else{
			if(Y <= enemyY){
				toDeglee = -1.0 * Math.atan((X - enemyX)/(enemyY - Y)) * 180 / Math.PI;
			}else{
				toDeglee = -1.0 * Math.atan((Y - enemyY)/(X - enemyX)) * 180 / Math.PI - 90.0;
			}
		}
		
		return(toDeglee - mRobot.getGunHeading());
		
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
