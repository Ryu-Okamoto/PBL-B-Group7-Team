package G7team;

import robocode.*;
import java.awt.Color;
import java.io.IOException;

public class G7SubRobot1 extends TeamRobot{
	private Gun  mGun;
	private int mode;
	private Move mMove;
	private double radius;
	private double myX;
	private double myY;
	private double buddyX;
	private double buddyY;
	private double CENTER_X = 400.0;
	private double CENTER_Y = 400.0;
	private String buddy = "G7team.G7SubRobot2";
	//private String primary = "G7LeaderRobot";
	private String HIT_MSG = "Hit Wall!";
	private String READY_MSG = "Ready!";
	private String DEATH_MSG = "I Dead!";
	private String ADJUST_MSG = "Adjust!";
	private int buddyReadyFlag;
	private int stoneFlag;
	
	public void run() {	
		mGun = new Gun(this, 2.0);
		
		//着色(黄緑色は0,255,127)
		Color teamcolor  = new Color(255,0,0);
		setColors(teamcolor, teamcolor, teamcolor, teamcolor, teamcolor);
		
		//自機の初期位置を送信
		myX = getX();
		myY = getY();
		mode = -1;
		buddyReadyFlag = 0;
		stoneFlag = 1;
		
		//自機の初期位置を送信
		try {
			sendMessage(buddy,new Point(myX,myY));
		} catch (IOException e) {
			System.out.println("Sending Message Error");
		}
		
		//もう1台の衛星機からの初期座標を待つ
		while(mode<0) {
			turnGunRight(1.0);
			turnGunLeft(1.0);
		}
		stoneFlag = 0;
		
		/*
		 TODO 回転半径(いろいろいじってみてください)
		 */
		radius = 150.0;
		
		//Move型用意
		mMove = new Move(this, CENTER_X, CENTER_Y, buddy, buddyX, buddyY, radius, mode);
		
		//軌道に乗る
		mMove.getOnTrack();
		
		//軌道に乗ったことを連絡
		try {
			sendMessage(buddy,READY_MSG);
		} catch (IOException e) {
			System.out.println("Sending Message Error");
		}
		
		//もう1台の衛星機が軌道に乗るのを待つ
		stoneFlag = 1;
		while(buddyReadyFlag==0) {
			turnGunRight(1.0);
			turnGunLeft(1.0);
		}
		stoneFlag = 0;
		
		//軌道周回
		turnGunLeft(90.0);
		mMove.centerRound();
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		if (isTeammate(e.getName())) {
			//捉えたのが味方なら何もしない
			return;
		}else {
			mGun.onScannedRobot(e);
		}
	}
	
	public void onMessageReceived(MessageEvent e) {
		//メッセージの内容で分岐
		if(mode<0) { //メッセージが「初期座標」 -> 役割(メインorアシスト、左半分or右半分)を決定
			Point p = (Point)e.getMessage();
			buddyX = p.getX();
			buddyY = p.getY();
			if((Math.abs(myY - CENTER_Y))/(Math.abs(myX - CENTER_X))<(Math.abs(buddyY - CENTER_Y))/(Math.abs(buddyX - CENTER_X))) {
				if(myX<CENTER_X) {
					mode = 11;
				}else {
					mode = 21;
				}
			}else {
				if(buddyX>CENTER_X) {
					mode = 12;
				}else {
					mode = 22;
				}
			}
		}else if(HIT_MSG.equals(e.getMessage())){ //メッセージが「他戦車に衝突」 -> 回転方向を逆転
			mMove.turnClock();
		}else if(READY_MSG.equals(e.getMessage())||(DEATH_MSG.equals(e.getMessage()))) { //メッセージが「周回準備完了」or「死亡」 -> レディフラグON
			buddyReadyFlag = 1;
		}else if(ADJUST_MSG.equals(e.getMessage())) { //メッセージが「軌道修正開始」 -> 同じく軌道修正
			mMove.centerOnTrack();
		}
	}
	
	public void onHitRobot(HitRobotEvent e){
		ahead(-10.0 * (1 - stoneFlag));
		try {
			sendMessage(buddy,HIT_MSG);
		} catch (IOException i) {
			System.out.println("Sending Message Error");
		}
		mMove.turnClock();
	}

	public void onDeath(DeathEvent e) {
		try {
			sendMessage(buddy,DEATH_MSG);
		} catch (IOException i) {
			System.out.println("Sending Message Error");
		}
	}
}