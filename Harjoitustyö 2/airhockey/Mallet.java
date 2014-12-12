package airhockey;

/**
 * Mallet handles the coordinates and speed of a single mallet and the change of these values.
 * @author joel
 *
 */


// 					LET IT GOOOOOOOOO


public class Mallet {
	private int malletX;
	private int malletY;

	private int malletSpeedX;
	private int malletSpeedY;

	private int malletRadius;
	private double[] malletCenter = new double[2];
	private double[] malletCircleXList;
	private double[] malletCircleYList;

	/**
	 * Initialize the position of the mallet according to the given parameters.
	 */
	public Mallet(int x, int y, int r) {
		malletX = x; // Default for the first mallet is (240, 0) and for the second it's (240, 620);
		malletY = y;
		malletRadius = r;
	}

	/**
	 * Sets the mallets coordinates.  
	 * @param x
	 * @param y
	 */
	// Calculating the speed of the mallet is now irrelevant as the speed in the are of key listener is constant. 
	public void moveMallet(int x, int y) {
		/*
		int oldX = malletX;
		int oldY = malletY;
		 */
		malletX = x;
		malletY = y;
		/*
		malletSpeedX = malletX - oldX;
		malletSpeedY = malletY - oldY;
		 */
	}

	/**
	 * Returns the mallet's coordinate along the x-axis.
	 * @return
	 */
	public int getMalletX() {
		return malletX;
	}

	/**
	 * Sets the mallets x-coordinate.
	 * @param x
	 */
	public void setMalletX(int x) {
		malletX = x;
	}

	/**
	 * Returns the mallet's coordinate along the y-axis.
	 * @return
	 */
	public int getMalletY() {
		return malletY;
	}

	/**
	 * Sets the mallets y-coordinate.
	 * @param y
	 */
	public void setMalletY(int y) {
		malletY = y;
	}

	/**
	 * Returns the mallets speed along the x-axis.
	 * @return
	 */
	public int getMalletSpeedX() {
		return malletSpeedX;
	}

	/**
	 * Sets the mallets speed along the x-axis.
	 * Might be deprecated, as the moveMallet() already calculates the speed.
	 * @param malletSpeedX
	 */
	public void setMalletSpeedX(int malletSpeedX) {
		this.malletSpeedX = malletSpeedX;
	}

	/**
	 * Returns the mallets speed along the y-axis.
	 * @return
	 */
	public int getMalletSpeedY() {
		return malletSpeedY;
	}

	/**
	 * Sets the mallets speed along the y-axis.
	 * Might be deprecated as the moveMallet() already calculates the speed.
	 * @param malletSpeedY
	 */
	public void setMalletSpeedY(int malletSpeedY) {
		this.malletSpeedY = malletSpeedY;
	}

	/**
	 * Returns the radius of the mallet.
	 * @return
	 */
	public int getMalletRadius() {
		return malletRadius;
	}

	/**
	 * Returns the value of mallet's center along the x-axis with the parameter "0" 
	 * and the value of y with "1".
	 * @param x
	 * @return
	 */
	public double getMalletCenter(int x) {
		malletCenter[0] = malletX + malletRadius;
		malletCenter[1] = malletY + malletRadius;
		return malletCenter[x];
	}

	/**
	 * Calculates 360 different coordinates(full circle) along the circle of the puck.
	 * These values are stored to their respective lists.
	 */
	public void calculateMalletCircleXYList() {
		malletCircleXList = new double[360];
		malletCircleYList = new double[360];
		for (int i = 0; i < 360; i++) {
			malletCircleXList[i] = (double) Math.round(malletCenter[0] + malletRadius * Math.cos(i * Math.PI / 180));
			malletCircleYList[i] = (double) Math.round(malletCenter[1] + malletRadius * Math.sin(i * Math.PI / 180));
		}
	}

	/**
	 * Returns a specified member of the malletCircleXList.
	 * @param x
	 * @return
	 */
	public double getMalletCircleX(int x) {
		return malletCircleXList[x];
	}

	/**
	 * Returns a specified member of the malletCircleYList.
	 * @param x
	 * @return
	 */
	public double getMalletCircleY(int x) {
		return malletCircleYList[x];
	}

}