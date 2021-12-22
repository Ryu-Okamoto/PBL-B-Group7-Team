package G7team;

import java.awt.Color;
import java.io.IOException;

import robocode.*;

public class Move{
	// 回転移動の中心座標
	private double mCenterX;
	private double mCenterY;
	
	//もう1台の衛星機の名前
	String mBuddyName;
	
	//もう1台の衛星機の初期座標
	private double mBuddyX;
	private double mBuddyY;
	
	// 回転移動の半径
	private double mRadius;
	
	// 制御先のロボット
	private TeamRobot mRobot;
	
	//移動モード(0:中央、11:左半分メイン、12:左半分アシスト、21:右半分メイン、22:右半分アシスト)
	private int mmode;
	
	//回転方向(1:時計回り、-1:反時計回り)
	private double clkwise;
	
	private String ADJUST_MSG = "Adjust!";
	
	/*引数
		robot:自機情報
		XCenter:ステージ中央のX座標
		YCenter:ステージ中央のY座標
		XBuddy:もう1台の衛星機の初期位置X座標(アシスト機のみ必要)
		YBuddy:もう1台の衛星機の初期位置Y座標(アシスト機のみ必要)
		radius:円軌道の半径
		mode:移動モード
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
		//初期位置
		firstMove();
		
		//最初に回る方向を決める(時計回りor反時計回り)
		if((mRobot.getX()-mCenterX)*(mRobot.getY()-mCenterY)>=0) {
			//ステージ右上or左下にスポーンしたら時計回り
			clkwise = 1;
		}else {
			//ステージ左上or右下にスポーンしたら反時計回り
			clkwise = -1;
		}
		
		switch(mmode/10) {
			case 0://主星機の動き
				//中央の軌道に乗る
				centerOnTrack();
				break;
			case 1://左側衛星機の動き
				switch(mmode%10) {
					case 1://メイン機の動き
						centerOnTrack();
						break;
					case 2://アシスト機の動き
						assistFirstMove();
						assistOnLine();
						break;
				}
				break;
			case 2://右側衛星機の動き
				switch(mmode%10) {
					case 1://メイン機の動き
						centerOnTrack();
						break;
					case 2://アシスト機の動き
						assistFirstMove();
						assistOnLine();
						break;
				}
			break;
		}
		*/
		
		//自機がアシスト機ならアシスト機用の移動をする
		if(mmode%10==2) {
			assistFirstMove();
			assistOnLine();
		}
		
		//軌道に乗る
		centerOnTrack();
	}
	
	//アシスト機の初期位置の移動処理はassistFirstMoveメソッドに移しました
	/*
	//初期位置の移動の処理
	public void firstMove() {
		switch(mmode) {
			case 0:
				//左半分用の機体が右半分でスタートしていた場合、左半分まで移動する
				if(mRobot.getX()>=mCenterX) {
					mRobot.turnLeft(convertDegree(90.0 + mRobot.getHeading()));
					while(mRobot.getX()>=mCenterX) {
						mRobot.ahead(100.0);
					}
				}
				break;
			case 1:
				//右半分用の機体が左半分でスタートしていた場合、右半分まで移動する
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
	
	//着色処理(デバッグ用にどうぞ)
	public void paintColor() {
		switch(mmode%10) {
			case 0:
				//主星機の色を緑色に変更
				mRobot.setColors(Color.green, Color.green, Color.green, Color.green, Color.green);
				break;
			case 1:
				//メイン機の色を青色に変更
				mRobot.setColors(Color.blue, Color.blue, Color.blue, Color.blue, Color.blue);
				break;
			case 2:
				//アシスト機の色を赤色に変更
				mRobot.setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
				break;
		}
	}
	
	//角度計算は中央用のみで事足りるようになりました
	/*
	//中心の方向の角度を計算(左半分用)
	public double leftToCenterDegree() {
		//計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
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

	//中心の方向の角度を計算(右半分用)
	public double rightToCenterDegree() {
		//計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
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
	
	//中心の方向の角度を計算(中央用)
	public double centerToCenterDegree() {
		//計算に必要な自機の座標を取得
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
		//角度計算(詳しい計算内容はSlackに投稿した写真を見てください)
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
	
	//角度を-180度～180度に変換して返す関数(-180度以上、180度以下を返す)
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
	
	//機体が軌道に乗るまでの動きも中央用だけで済むようになりました
	/*
	//左半分機体が軌道に乗るまでの動き
	public void leftOnTrack() {
		double ToDegree,MyDegree,TurnDegree;
		//左半分へ移動
		
		
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
		
		//自機と中心との距離と半径との差が10.0未満になるまで移動
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(Diff);
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
		
		//自機と中心との距離と半径との差が10.0未満になるまで移動
		while(Math.abs(Diff)>=10.0){
			mRobot.ahead(Diff);
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
		//中心に対して垂直方向を向く
		mRobot.turnLeft(90.0);
		
	}
	*/
	
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
		
		//自機と中心との距離と半径との差が5.0未満になるまで移動
		while(Math.abs(Diff)>=5.0){
			mRobot.ahead(-Diff);
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}

	// 現在値と円の中心の距離を計算
	private double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	//角度計算も中央用のみで十分になりました
	/*
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
	*/
	
	//接線方向を向くために回転させないといけない角度を計算(中央用)
	private double calcCenterVerDegree() {
		//計算内容はまた後日、図か何かで紹介しようと思います
		return(convertDegree(centerToCenterDegree() + 90.0 - mRobot.getHeading()));
	}
	
	//軌道上の動きも中心用だけで十分です
	/*
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
			if(Math.abs(calculateDistance()-mRadius)>=20.0) {
				leftOnTrack();
			}
			
			//「銃口を1回転させる処理」を予約(この時点ではまだ回転しない)
			//mRobot.setTurnGunRight(360.0);
			
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
			if(Math.abs(calculateDistance()-mRadius)>=20.0) {
				rightOnTrack();
			}

			//「銃口を1回転させる処理」を予約(この時点ではまだ回転しない)
			//mRobot.setTurnGunRight(360.0);

			//予約していた処理(車体回転、移動、銃口回転)を同時に実行
			mRobot.execute();
		 }
	}
	*/
	
	//円軌道を描く処理(中央用)
	public void centerRound() {
		//中心に対して垂直方向を向く
		mRobot.turnRight(90.0);
		
		//以下の処理を一生ループ
		while (true) {
			//「車体が接線方向を向くように回転する処理」を予約(この時点ではまだ回転しない)
			mRobot.setTurnRight(calcCenterVerDegree());
			
			//「時計回りに前進or後退する処理」を予約(この時点ではまだ移動しない)
			mRobot.setAhead(5.0 * clkwise);

			//中心からの距離がずれ始めたら(円軌道からずれ始めたら)修正する処理
			if(Math.abs(calculateDistance()-mRadius)>=30.0) {
				sendAdjustMsg();
				centerOnTrack();
				mRobot.turnRight(90.0);
			}

			//「銃口を1回転させる処理」を予約(この時点ではまだ回転しない)
			//mRobot.setTurnGunRight(360.0);
			
			//予約していた処理(車体回転、移動、銃口回転)を同時に実行
			mRobot.execute();
		 }
	}
	
	//アシスト機が左右に分かれるメソッド
	private void assistFirstMove() {
		double myX,goalX;
		myX = mRobot.getX();
		goalX = myX;
		switch(mmode/10){
			case 1://左半分側アシスト機について
				if(myX>=mCenterX) {
					goalX = 2.0*mCenterX - myX;
				}
				break;
			case 2://右半分側アシスト機について
				if(myX<=mCenterX) {
					goalX = 2.0*mCenterX - myX;
				}
				break;
		}
		
		//アシスト機が移動すべき位置に移動
		while(Math.abs(goalX-myX)>1.0) {
			faceLateral(goalX-myX);
			mRobot.ahead(Math.abs(goalX-myX));
			myX = mRobot.getX();
		}
	}
	
	//アシスト機がライン上に乗るようにするメソッド
	private void assistOnLine() {
		double myX,myY,goalY;
		//自機の現座標
		myX = mRobot.getX();
		myY = mRobot.getY();
		
		//目標となるY座標の算出
		if(mmode/10==1) {
			goalY = mCenterY - ((mCenterX - myX)*(mBuddyY - mCenterY)/(mBuddyX - mCenterX));
		}else {
			goalY = mCenterY + ((myX - mCenterX)*(mCenterY - mBuddyY)/(mCenterX - mBuddyX));
		}
		
		//ライン上に乗るように移動
		while(Math.abs(goalY-myY)>1.0) {
			facePortrait(goalY-myY);
			mRobot.ahead(Math.abs(goalY-myY));
			myY = mRobot.getY();
		}
	}
	
	//縦方向(Y軸方向)へ車体を向けるメソッド。modeが正なら上向き(Y軸+方向)、負なら下向き(Y軸-方向)
	private void facePortrait(double mode) {
		//どの角度を向くべきか[上向き:0度、下向き:180度]
		double targetDegree;
		if(mode>0.0) {
			targetDegree = 0.0;
		}else {
			targetDegree = 180.0;
		}
		
		//その角度を向くように車体回転
		while(Math.abs(convertDegree(mRobot.getHeading()-targetDegree))>=0.1) {
			mRobot.turnRight(convertDegree(targetDegree-mRobot.getHeading()));
		}
	}
	
	//横方向(X軸方向)へ車体を向けるメソッド。modeが正なら右向き(X軸+方向)、負なら左向き(X軸-方向)
	private void faceLateral(double mode) {
		//どの角度を向くべきか[右向き:90度、左向き:270度]
		double targetDegree;
		if(mode>0.0) {
			targetDegree = 90.0;
		}else {
			targetDegree = 270.0;
		}
		
		//その角度を向くように車体回転
		while(Math.abs(convertDegree(mRobot.getHeading()-targetDegree))>=0.1) {
			mRobot.turnRight(convertDegree(targetDegree-mRobot.getHeading()));
		}
	}

	//回転方向を変えるメソッド
	public void turnClock() {
		clkwise *= -1.0;
	}
	
	//起動補正したことを相方に伝えるメソッド
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
	 TODO 今後やらないといけないこと・やっていくべきこと
	 */
}
