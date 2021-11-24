package move;

import robocode.*;
import robocode.AdvancedRobot;
import java.awt.Color;

public class Move{
	// 回転移動の中心座標
	private double mCenterX;
	private double mCenterY;
	
	// 回転移動の半径
	private double mRadius;
	
	// 制御先のロボット
	private AdvancedRobot mRobot;
	
	//移動モード(0:左半分、1:右半分、2:中央)
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
			//色を青色に変更
			mRobot.setColors(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);
			//左半分の軌道に乗る
			leftOnTrack();
			break;
		case 1:
			//色を赤色に変更
			mRobot.setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
			//右半分の軌道に乗る
			rightOnTrack();
			break;
		case 2:
			//色を緑色に変更
			mRobot.setColors(Color.green, Color.green, Color.green, Color.green, Color.green);
			//中央の軌道に乗る
			centerOnTrack();
			break;
		}
		//mRobot.execute();
	}
	
	//左半分機体が軌道に乗るまでの動き
	public void leftOnTrack() {
		//自機の初期座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double ToDegree,MyDegree,TurnDegree;
		//中心の方向の角度を計算
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
		
		//自機の角度を取得
		MyDegree = mRobot.getHeading();
		
		//どれだけ回転するかを計算
		TurnDegree = ToDegree - MyDegree;
		
		//角度を-180度～180度に変換
		while((TurnDegree>180.0)||(TurnDegree<-180.0)){
			if(TurnDegree>180.0){
				TurnDegree -= 360.0;
			}else{
				TurnDegree += 360.0;
			}
		}
		
		//自機回転
		mRobot.turnRight(TurnDegree);
		
		//自機が右半分にいるときに左半分まで移動する処理
		while(mRobot.getX()>=mCenterX){
			mRobot.ahead(-10.0);
		}
		
		//自機と中心との距離を計算
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//自機と中心との距離が10.0未満になるまで移動
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(9.0 * Diff/Math.abs(Diff));
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}
	
	//右半分機体が軌道に乗るまでの動き
	public void rightOnTrack() {
		//自機の初期座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double ToDegree,MyDegree,TurnDegree;
		//中心の方向の角度を計算
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
		
		//自機の角度を取得
		MyDegree = mRobot.getHeading();
		
		//どれだけ回転するかを計算
		TurnDegree = ToDegree + MyDegree;
		
		//角度を-180度～180度に変換
		while((TurnDegree>180.0)||(TurnDegree<-180.0)){
			if(TurnDegree>180.0){
				TurnDegree -= 360.0;
			}else{
				TurnDegree += 360.0;
			}
		}
		
		//自機回転
		mRobot.turnLeft(TurnDegree);
		
		//自機が右半分にいるときに左半分まで移動する処理
		while(mRobot.getX()<=mCenterX){
			mRobot.ahead(-10.0);
		}
		
		//自機と中心との距離を計算
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//自機と中心との距離が10.0未満になるまで移動
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(9.0 * Diff/Math.abs(Diff));
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}
	
	//中央機体が軌道に乗るまでの動き
	public void centerOnTrack() {
		//自機の初期座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		double ToDegree,MyDegree,TurnDegree;
		//中心の方向の角度を計算
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
		
		//自機の角度を取得
		MyDegree = mRobot.getHeading();
		
		//どれだけ回転するかを計算
		TurnDegree = ToDegree - MyDegree;
		
		//角度を-180度～180度に変換
		while((TurnDegree>180.0)||(TurnDegree<-180.0)){
			if(TurnDegree>180.0){
				TurnDegree -= 360.0;
			}else{
				TurnDegree += 360.0;
			}
		}
		
		//自機回転
		mRobot.turnRight(TurnDegree);
		
		//自機と中心との距離を計算
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		//自機と中心との距離が10.0未満になるまで移動
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(-9.0 * Diff/Math.abs(Diff));
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}

	// 初期値から円軌道にのるまでの移動
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

	// 円軌道上での移動
	/*
	public void circulate() {
		mRobot.setTurnRightRadians(Math.cos(
				calculateDegree() - (calculateDistance() - mRadius)));
		mRobot.setAhead(2.0);
		mRobot.execute();
	}
	*/
	
	// 現在値と円の中心の距離を計算
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
}
