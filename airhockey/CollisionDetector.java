package airhockey;

/**
 * Detects collisions of the mallet and the puck.
 * @author joel
 *
 */
public class CollisionDetector {
	private Puck puck;
	private Mallet[] mallet;

	private double distance;
	private double diffX;
	private double diffY;

	private double[] collisionXY = new double[2];
	private int collisionDegree;


	/**
	 * Takes use of the puck and mallet objects to check their values.
	 * @param puck
	 * @param mallet
	 */
	public CollisionDetector(Puck puck, Mallet[] mallet) {
		this.puck = puck;
		this.mallet = mallet;
	}
	//this constructor is for test use.
	public CollisionDetector(){}

	/**
	 * If the puck and mallet collided, return true, otherwise false.
	 * They collide if the distance between their centers is less than their radiuses combined.
	 * Meaning that they touched.
	 * @return
	 */
	public boolean doesItCollide() {
		if (distanceOfCoordinates() <= 5 + (puck.getPuckRadius() + mallet[1].getMalletRadius())) {
			return true;
		}
		return false;
	}

	/**
	 * Calculate the length of a line between the puck's center coordinates and the mallets center coordinates.
	 * The formula is " d = ( (x2 - x1)^2 + (y2 - y1)^2 )^1/2 "
	 * a.k.a 
	 * d = square root( (puck's x - mallet's x)^2 + (puck's y - mallet's y)^2 )
	 * @return
	 */
	public double distanceOfCoordinates() {
		diffX = puck.getPuckCenter(0) - mallet[1].getMalletCenter(0);
		diffY = puck.getPuckCenter(1) - mallet[1].getMalletCenter(1);
		distance = Math.pow(diffX, 2) + Math.pow(diffY, 2); 
		return Math.sqrt(distance);
	}


	/**
	 * Returns a list which contains the coordinates of the collision.
	 * @return
	 */
	public double[] collisionCoordinates() {
		mallet[1].calculateMalletCircleXYList();
		mallet[0].calculateMalletCircleXYList();
		puck.calculatePuckCircleXYList();
		
		for (int i = 0; i < 360; i++) {
			double Mx = mallet[1].getMalletCircleX(i);
			double My = mallet[1].getMalletCircleY(i);
			// If the x-coordinates and y-coordinates in the puck/mallet lists are the same, we have a match.
			for (int j = 0; j < 360; j++) {
				if (puck.getPuckCircleX(i) == Mx && puck.getPuckCircleY(i) == My) {
					collisionXY[0] = puck.getPuckCircleX(i);
					collisionXY[1] = puck.getPuckCircleY(i);
					collisionDegree = i;
				}
			}
		}
		return collisionXY;
	}

	public int returnCollisionDegree(){
		return collisionDegree;
	}
}

