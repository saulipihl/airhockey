package airhockey;

/**
 * Puck handle the coordinates and speed of the puck.
 * @author joel
 *
 */
public class Puck {
	private int puckX;
	private int puckY;

	private int puckSpeedX;
	private int puckSpeedY;

	private final int puckPositiveMaxSpeedX = 5;
	private final int puckPositiveMaxSpeedY = 5;

	private final int puckNegativeMaxSpeedX = -5;
	private final int puckNegativeMaxSpeedY = -5;

	private int puckRadius;
	private double[] puckCenter = new double[2];

	private double[] puckCircleXList;
	private double[] puckCircleYList;

	/**
	 * Initializes the speed of the puck with the given parameteres.
	 * @param xSpeed
	 * @param ySpeed
	 */
	public Puck(int xSpeed, int ySpeed, int radius) {
		this.puckSpeedX = xSpeed;
		this.puckSpeedY = ySpeed;
		this.puckRadius = radius;
	}

	/**
	 * Moves the puck to the given coordinates.
	 * @param x
	 * @param y
	 */
	public void movePuck(int x, int y) {
		this.puckX = x;
		this.puckY = y;
	}


	/**
	 * This method here reduces the speed of the puck. 
	 * Someday.
	 */
	public void reduceSpeed() {
		//int zcoord = Math.round(Math.round(Math.sqrt(puckSpeedX * puckSpeedX + puckSpeedY * puckSpeedY)));
		
		double ratio = puckSpeedX / puckSpeedY; //0,6666    0,666 x 3 = 2
		
		puckSpeedX = (int) (puckSpeedY * ratio - 1);
		puckSpeedY = (int) ((puckSpeedX / ratio) - 1);
		
		/*
		if (getPuckSpeedX() > 1 || getPuckSpeedY() > 1 ||getPuckSpeedX() < -1 || getPuckSpeedY() < -1){
			if (puckSpeedX < puckSpeedY && puckSpeedY < 0) {
				puckSpeedY = puckSpeedY + 1;
			} 
			if(puckSpeedX < puckSpeedY && puckSpeedY>0){
				puckSpeedY = puckSpeedY - 1;
			}
			if (puckSpeedX > puckSpeedY && puckSpeedX < 0) {
				puckSpeedX = puckSpeedX + 1;
			} 
			if(puckSpeedX > puckSpeedY && puckSpeedX > 0){
				puckSpeedX = puckSpeedX - 1;
			}

		} */
	} 

	/**
	 * Inverts the puck's speed along the x-axis.
	 */
	public void invertPuckSpeedX() {
		puckSpeedX = puckSpeedX* - 1;
	}

	/**
	 * Inverts the puck's speed along the y-axis.
	 */
	public void invertPuckSpeedY() {
		puckSpeedY = puckSpeedY* - 1;
	}

	/**
	 * Returns the value of the puck's x-coordinate.
	 * @return
	 */
	public int getPuckX() {
		return puckX;
	}

	/**
	 * Sets the value of the puck's x-coordinate.
	 * @param x
	 */
	public void setPuckX(int x) {
		this.puckX = x;
	}

	/**
	 * Returns the value of the puck's y-coordinate.
	 * @return
	 */
	public int getPuckY() {
		return puckY;
	}

	/**
	 * Sets the value of the pucks's y-coordinate.
	 * @param y
	 */
	public void setPuckY(int y) {
		this.puckY = y;
	}

	/**
	 * Returns the spuck's speed along the x-axis.
	 * 
	 * @return
	 */
	public int getPuckSpeedX() {
		return puckSpeedX;
	}

	/**
	 * Sets the puck's speed along the x-axis.
	 * @param dx
	 */
	public void setPuckSpeedX(int dx) {

		this.puckSpeedX = dx;

	}

	/**
	 * Makes it so, that the puck's maximum speed can't be greater than the respective puckMaxSpeed in that direction.
	 */
	public void checkPuckSpeedXY() {
		if (puckSpeedX > puckPositiveMaxSpeedX) {
			puckSpeedX = puckPositiveMaxSpeedX;
		}
		if (puckSpeedY > puckPositiveMaxSpeedY) {
			puckSpeedY = puckPositiveMaxSpeedY;
		}
		if (puckSpeedX < puckNegativeMaxSpeedX) {
			puckSpeedX = puckNegativeMaxSpeedX;
		}
		if (puckSpeedY < puckNegativeMaxSpeedY) {
			puckSpeedY = puckNegativeMaxSpeedY;
		}
	}

	/**
	 * Returns the puck's speed along the y-axis.
	 * @return
	 */
	public int getPuckSpeedY() {
		return puckSpeedY;
	}

	/**
	 * Sets the puck's speed along the y-axis.
	 * @param dy
	 */
	public void setPuckSpeedY(int dy) {


		this.puckSpeedY = dy;

	}

	/**
	 * Returns the radius of the puck.
	 * @return
	 */
	public int getPuckRadius() {
		return puckRadius;
	}

	/**
	 * Returns the value of puck's center along the x-axis with the parameter "0" 
	 * and the value of y with "1".
	 * @param x
	 * @return
	 */
	public double getPuckCenter(int x) {
		puckCenter[0] = puckX + puckRadius;
		puckCenter[1] = puckY + puckRadius;
		return puckCenter[x];
	}

	/**
	 * Calculates 360 different coordinates(full circle) along the circle of the puck.
	 * These values are stored to their respective lists.
	 */
	public void calculatePuckCircleXYList() {
		puckCircleXList = new double[360];
		puckCircleYList = new double[360];
		for (int i = 0; i < 360; i++) {
			puckCircleXList[i] = puckCenter[0] + puckRadius * Math.cos(i * Math.PI / 180);
			puckCircleYList[i] = puckCenter[1] + puckRadius * Math.sin(i * Math.PI / 180);
		}
	}

	/**
	 * Returns a specified member of the puckCircleXList.
	 * @param x
	 * @return
	 */
	public double getPuckCircleX(int x) {
		return puckCircleXList[x];
	}

	/**
	 * Returns a specified member of the puckCircleYList.
	 * @param x
	 * @return
	 */
	public double getPuckCircleY(int x) {
		return puckCircleYList[x];
	}
}