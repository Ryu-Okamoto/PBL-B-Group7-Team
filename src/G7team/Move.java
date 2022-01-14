package G7team;

import java.io.IOException;

import robocode.*;

public class Move{
	private double mCenterX;
	private double mCenterY;
	
	String mBuddyName;
	
	private double mBuddyX;
	private double mBuddyY;
	
	private double mRadius;
	
	private TeamRobot mRobot;
	
	private int mmode;
	
	private double clkwise;
	
	private String ADJUST_MSG = "Adjust!";
	
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
		if(mmode%10==2) {
			assistFirstMove();
			assistOnLine();
		}
		
		centerOnTrack();
	}
	
	public double centerToCenterDegree() {
		double X,Y;
		X = mRobot.getX();
		Y = mRobot.getY();
		
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
	
	public void centerOnTrack() {
		
		double ToDegree,MyDegree,TurnDegree;
		
		ToDegree = centerToCenterDegree();
		MyDegree = mRobot.getHeading();
		TurnDegree = convertDegree(ToDegree - MyDegree);
		
		mRobot.turnRight(TurnDegree);
		
		double Dist,Diff;
		Dist = calculateDistance();
		Diff = Dist - mRadius;
		
		while(Math.abs(Diff)>=5.0){
			mRobot.ahead(-Diff);
			Dist = calculateDistance();
			Diff = Dist - mRadius;
		}
		
	}

	public double calculateDistance() {
		double delta_x = mRobot.getX() - mCenterX;
		double delta_y = mRobot.getY() - mCenterY;
		return Math.sqrt(delta_x*delta_x + delta_y*delta_y);
	}
	
	public double calcCenterVerDegree() {
		return(convertDegree(centerToCenterDegree() + 90.0 - mRobot.getHeading()));
	}
	
	public void centerRound() {
		mRobot.turnRight(90.0);
		
		while (true) {
			mRobot.setTurnRight(calcCenterVerDegree());
			
			mRobot.setAhead(5.0 * clkwise);

			if(Math.abs(calculateDistance()-mRadius)>=30.0) {
				sendAdjustMsg();
				centerOnTrack();
				mRobot.turnRight(90.0);
			}
			
			mRobot.execute();
		 }
	}
	
	private void assistFirstMove() {
		double myX,goalX;
		myX = mRobot.getX();
		goalX = myX;
		switch(mmode/10){
			case 1:
				if(myX>=mCenterX) {
					goalX = 2.0*mCenterX - myX;
				}
				break;
			case 2:
				if(myX<=mCenterX) {
					goalX = 2.0*mCenterX - myX;
				}
				break;
		}
		
		while(Math.abs(goalX-myX)>1.0) {
			faceLateral(goalX-myX);
			mRobot.ahead(Math.abs(goalX-myX));
			myX = mRobot.getX();
		}
	}
	
	private void assistOnLine() {
		double myX,myY,goalY;
		
		myX = mRobot.getX();
		myY = mRobot.getY();
		
		if(mmode/10==1) {
			goalY = mCenterY - ((mCenterX - myX)*(mBuddyY - mCenterY)/(mBuddyX - mCenterX));
		}else {
			goalY = mCenterY + ((myX - mCenterX)*(mCenterY - mBuddyY)/(mCenterX - mBuddyX));
		}
		
		while(Math.abs(goalY-myY)>1.0) {
			facePortrait(goalY-myY);
			mRobot.ahead(Math.abs(goalY-myY));
			myY = mRobot.getY();
		}
	}
	
	private void facePortrait(double mode) {
		double targetDegree;
		if(mode>0.0) {
			targetDegree = 0.0;
		}else {
			targetDegree = 180.0;
		}
		
		while(Math.abs(convertDegree(mRobot.getHeading()-targetDegree))>=0.1) {
			mRobot.turnRight(convertDegree(targetDegree-mRobot.getHeading()));
		}
	}
	
	private void faceLateral(double mode) {
		double targetDegree;
		
		if(mode>0.0) {
			targetDegree = 90.0;
		}else {
			targetDegree = 270.0;
		}
		
		while(Math.abs(convertDegree(mRobot.getHeading()-targetDegree))>=0.1) {
			mRobot.turnRight(convertDegree(targetDegree-mRobot.getHeading()));
		}
	}

	public void turnClock() {
		clkwise *= -1.0;
	}
	
	public void sendAdjustMsg() {
		if(mmode>0) {
			try {
				mRobot.sendMessage(mBuddyName,ADJUST_MSG);
			} catch (IOException e) {
				System.out.println("Sending Message Error");
			}
		}
	}
}
