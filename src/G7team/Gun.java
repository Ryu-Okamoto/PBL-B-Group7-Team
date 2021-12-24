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
		// 計算に必要な座標や速度を計算
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
		
		// 敵機と放った弾丸の距離が18.0以下になるまでシミュレーション
		while(Math.abs(ourPosition.distance(enemyPosition) - bulletRadius) > 18.0) {
			enemyPosition = enemyPosition.add(enemyVelocity);
			bulletRadius  = bulletRadius + bulletVelocity;
		}
		double theta = convertDegree(calculateDegree(enemyPosition));
		
		mRobot.turnGunRight(theta);
		mRobot.fire(mBulletEnergy);
	}
	
	private double calculateDegree(Point enemyPosition) {
		// 計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		// 計算に必要な敵機の座標を取得
		double enemyX, enemyY;
		enemyX = enemyPosition.getX();
		enemyY = enemyPosition.getY();
		
		// 角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
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
	
	//角度を-180度～180度に変換して返す関数(-180度以上、180度以下を返す)
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
