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
	
	//回転方向(1:時計回り、-1:反時計回り)
	private double clkwise;
	
	public Move(AdvancedRobot robot, double XCenter, double YCenter, double radius, int mode){
		mRobot  = robot;
		mCenterX = XCenter;
		mCenterY = YCenter;
		mRadius = radius;
		mmode = mode;
	}
	
	public void getOnTrack() {
		//最初に回る方向を決める(時計回りor反時計回り)
		if((mRobot.getX()-mCenterX)*(mRobot.getY()-mCenterY)>=0) {
			//ステージ右上or左下にスポーンしたら時計回り
			clkwise = 1;
		}else {
			//ステージ左上or右下にスポーンしたら反時計回り
			clkwise = -1;
		}
		
		switch(mmode) {
		case 0:
			//色を青色に変更
			mRobot.setColors(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);
			//左半分の軌道に乗る
			leftOnTrack();
			//半円軌道を描く
			leftRound();
			break;
		case 1:
			//色を赤色に変更
			mRobot.setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
			//右半分の軌道に乗る
			rightOnTrack();
			//半円軌道を描く
			rightRound();
			break;
		case 2:
			//色を緑色に変更
			mRobot.setColors(Color.green, Color.green, Color.green, Color.green, Color.green);
			//中央の軌道に乗る
			centerOnTrack();
			//円軌道を描く
			centerRound();
			break;
		}
	}
	
	public void dicideClockWise() {
		switch(mmode) {
			case 0:
				break;
			case 1:
				if((mRobot.getX()-mCenterX)*(mRobot.getY()-mCenterY)>=0) {
					clkwise = 1;
				}else {
					clkwise = -1;
				}
				break;
			default:
				clkwise = 1;
				break;
		}
	}
	
	//中心の方向の角度を計算(左半分用)
	public double leftToCenterDegree() {
		//計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
		if(X<=400.0){
			if(Y<=400.0){
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}else{
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}
		}else{
			if(Y<=400.0){
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}else{
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}
		}
	}

	//中心の方向の角度を計算(右半分用)
	public double rightToCenterDegree() {
		//計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
		if(X<=400.0){
			if(Y<=400.0){
				return(Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}else{
				return(-1.0 * Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}
		}else{
			if(Y<=400.0){
				return(-1.0 * Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}else{
				return(Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}
		}
	}
	
	//中心の方向の角度を計算(中央用)
	public double centerToCenterDegree() {
		//計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
		if(X<=400.0){
			if(Y<=400.0){
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI - 180.0);
			}else{
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI - 90.0);
			}
		}else{
			if(Y<=400.0){
				return(-1.0 * Math.atan((mCenterY-Y)/(mCenterX-X)) * 180 / Math.PI + 90.0);
			}else{
				return(Math.atan((mCenterX-X)/(mCenterY-Y)) * 180 / Math.PI);
			}
		}
	}
	
	//角度を-180度〜180度に変換して返す関数
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
	
	//左半分機体が軌道に乗るまでの動き
	public void leftOnTrack() {
		double ToDegree,MyDegree,TurnDegree;
		//中心への角度を取得
		ToDegree = leftToCenterDegree();
		
		//自機の角度を取得
		MyDegree = mRobot.getHeading();
		
		//どれだけ回転するかを計算
		TurnDegree = convertDegree(ToDegree - MyDegree);
		
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
		
		//中心に対して垂直方向を向く
		mRobot.turnLeft(90.0);
		
			
	}
	
	//右半分機体が軌道に乗るまでの動き
	public void rightOnTrack() {
		double ToDegree,MyDegree,TurnDegree;
		//中心への角度を取得
		ToDegree = rightToCenterDegree();
		
		//自機の角度を取得
		MyDegree = mRobot.getHeading();
		
		//どれだけ回転するかを計算
		TurnDegree = convertDegree(ToDegree + MyDegree);
		
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
		
		//中心に対して垂直方向を向く
		mRobot.turnLeft(90.0);
		
	}
	
	//中央機体が軌道に乗るまでの動き
	public void centerOnTrack() {
		
		double ToDegree,MyDegree,TurnDegree;
		//中心への角度を取得
		ToDegree = centerToCenterDegree();
		
		//自機の角度を取得
		MyDegree = mRobot.getHeading();
		
		//どれだけ回転するかを計算
		TurnDegree = convertDegree(ToDegree - MyDegree);
		
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
		
		//中心に対して垂直方向を向く
		mRobot.turnRight(90.0);
	}

	// 現在値と円の中心の距離を計算
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	//接線方向を向くために回転させないといけない角度を計算(左半分用)
	private double calcLeftVerDegree() {
		//計算内容はまた後日、図か何かで紹介しようと思います
		return(convertDegree(leftToCenterDegree() + 270.0 - mRobot.getHeading()));
	}
	
	//接線方向を向くために回転させないといけない角度を計算(右半分用)
	private double calcRightVerDegree() {
		//計算内容はまた後日、図か何かで紹介しようと思います
		return(convertDegree(-rightToCenterDegree() + 270.0 - mRobot.getHeading()));
	}
	
	//接線方向を向くために回転させないといけない角度を計算(中央用)
	private double calcCenterVerDegree() {
		//計算内容はまた後日、図か何かで紹介しようと思います
		return(convertDegree(centerToCenterDegree() + 90.0 - mRobot.getHeading()));
	}
	
	double sukima = 40.0; //左右の半円の境界部分の間隔(あとで変えられるように変数を使っています)
	
	//半円軌道を描く処理(左半分用)
	private void leftRound() {
		//以下の処理を一生ループ
		while (true) {
			//「車体が接線方向を向くように回転する処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnRight(calcLeftVerDegree());
			
			//「時計回りに前進or後退する処理」を予約(この時点ではまだ移動しない)
			mRobot.setAhead(10.0 * clkwise);
			
			//自分の座標が半円の境界の場合に折り返す処理
			if(mRobot.getX()>= mCenterX - sukima) {
				if(mRobot.getY()>=mCenterY) {
					clkwise = -1.0;
				}else {
					clkwise = 1.0;
				}
			}
			
			//中心からの距離がずれ始めたら(円軌道からずれ始めたら)修正する処理
			if(Math.abs(calculateDistance()-mRadius)>=10.0) {
				leftOnTrack();
			}
			
			//「銃口を1回転させる処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnGunRight(360.0);
			
			//予約していた処理(車体回転、移動、銃口回転)を同時に実行
			mRobot.execute();
		 }
	}
	
	//半円起動を描く処理(右半分用)
	private void rightRound() {
		//以下の処理を一生ループ
		while (true) {
			//「車体が接線方向を向くように回転する処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnRight(calcRightVerDegree());
			
			//「時計回りに前進or後退する処理」を予約(この時点ではまだ移動しない)
			mRobot.setAhead(10.0 * clkwise);
			
			//自分の座標が半円の境界の場合に折り返す処理
			if(mRobot.getX()<= mCenterX + sukima) {
				if(mRobot.getY()<mCenterY) {
					clkwise = -1.0;
				}else {
					clkwise = 1.0;
				}
			}

			//中心からの距離がずれ始めたら(円軌道からずれ始めたら)修正する処理
			if(Math.abs(calculateDistance()-mRadius)>=10.0) {
				rightOnTrack();
			}

			//「銃口を1回転させる処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnGunRight(360.0);

			//予約していた処理(車体回転、移動、銃口回転)を同時に実行
			mRobot.execute();
		 }
	}
	
	//円軌道を描く処理(中央用)
	private void centerRound() {
		//以下の処理を一生ループ
		while (true) {
			//「車体が接線方向を向くように回転する処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnRight(calcCenterVerDegree());
			
			//「時計回りに前進or後退する処理」を予約(この時点ではまだ移動しない)
			mRobot.setAhead(5.0 * clkwise);

			//中心からの距離がずれ始めたら(円軌道からずれ始めたら)修正する処理
			if(Math.abs(calculateDistance()-mRadius)>=10.0) {
				centerOnTrack();
			}

			//「銃口を1回転させる処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnGunRight(360.0);
			
			//予約していた処理(車体回転、移動、銃口回転)を同時に実行
			mRobot.execute();
		 }
	}
	/*
	 TODO 今後やらないといけないこと・やっていくべきこと
	  1.軌道に乗るまでにステージ中央で衝突・渋滞してそのまま死んでいく、という場合が割とあるので、それの改善
	  2.適切な半径、移動スピード、半円の境界の隙間の広さなどを決めていく
	  3.発砲処理(現状は銃口をぐるぐる回すだけなので、命中率は結構低い)
	 */
}
