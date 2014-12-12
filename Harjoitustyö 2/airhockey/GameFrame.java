package airhockey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * Handles the JFrame, calls for the Painter to fill it with JPanel, handles the inner game logic and
 * calls to the game objects(puck, mallet) and the running of the ActionListener.
 * @author joel
 *
 */
public class GameFrame extends JFrame implements ActionListener {
	// Don't ask, has something to do with JFrame/JPanel.
	private static final long serialVersionUID = 1L;

	private Puck puck;
	private Painter painter;
	private Mallet[] mallet = new Mallet[2];
	private CollisionDetector collisionDetector;

	private boolean isColliding = false;
	private int immunityTicks = 0;
	
	private int malletMaxPositive = 3;
	private int malletMaxNegative = -3;

	/**
	 * Sets the style of the JFrame window borders and calls for the initialize()-method.
	 */
	public GameFrame() {

		try {
			// Sets the window borders according to the style of the OS.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initializes the game objects and calls for the Painter to create a JPanel.
	 */
	public void initialize() {

		// Initializes the puck object with the speed of 3 pixels per tick in both x- and y-axis
		puck = new Puck(0, 0, 30);

		// Creates 2 new Mallet objects into the list with the predetermined coordinates (top and bottom) and radius.
		// The second mallet(mallet[1]) is the one we will be controlling.
		mallet[0] = new Mallet(210, 0, 40);
		mallet[1] = new Mallet(210, 620, 40);

		// collisionDetector is used to check if a collision happened between the mallet and the puck.
		collisionDetector = new CollisionDetector(puck, mallet);

		// Gives the painter the puck and mallet objects and runs the painters constructor which creates the JPanel.
		painter = new Painter(puck, mallet);


		// We use the keyboard to control our mallet. Is this another "nameless class"?
		// Looks ugly D:, can't the keyPressed and keyReleased be put into the bottom of the GameFrame or
		// could we give this keylistener it's own class altogether? Like: public class keyBoard extends KeyListener?
		KeyListener keyListener = new KeyListener() {

			/**
			 * Moves the mallet according to the presses.
			 */
			public void keyPressed(KeyEvent e) {
				// Reads the pressed key.
				int keyCode = e.getKeyCode();

				if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
					mallet[1].setMalletSpeedX(malletMaxNegative);

				}
				if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
					mallet[1].setMalletSpeedX(malletMaxPositive);
				}
				if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
					mallet[1].setMalletSpeedY(malletMaxNegative);
				}
				if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
					mallet[1].setMalletSpeedY(malletMaxPositive);
				}
			} // keyPressed


			/**
			 * Stops the mallet when the button is released.
			 */
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
					mallet[1].setMalletSpeedX(0);
				}
				if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
					mallet[1].setMalletSpeedX(0);
				}
				if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
					mallet[1].setMalletSpeedY(0);
				}
				if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
					mallet[1].setMalletSpeedY(0);
				}
			} // keyReleased


			/**
			 * I have no idea.
			 */
			public void keyTyped(KeyEvent e) {}
		}; // KeyListener d

		// Puts the puck in the middle of the action right from the start!
		puck.setPuckX(215);
		puck.setPuckY(320);

		// The Painter gets added to the JFrame so that it can start drawing the JPanel.
		add(painter);
		addKeyListener(keyListener); // adding the Key listener to the JPanel;
		// Sets the size of the window according to the JPanel dimensions.
		pack();

		setVisible(true);
		setFocusable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/**
		 * We create a new ActionListener to do stuff inside the JPanel.
		 */
		ActionListener aListener = new ActionListener() {
			
			int hasBounced = 0;

			/**
			 * actionPerfomed handles the game logic and calls for the Painter to repaint everything once it's done.
			 */
			public void actionPerformed(ActionEvent e) {
				
				// puckMovement handles the movement of the puck, just like in the old days but only in a method.
				puckMovement();
				
				isColliding = collisionDetector.doesItCollide();  //FIXING, DONT KNOW IF WORK
				if (isColliding == true && hasBounced > immunityTicks) {
					bounce();
					hasBounced = 0;
					isColliding = false;
				}

				// I implemented the KeyListener methods. Now the mallet movement happens in a separate key listener.

				mallet[1].moveMallet(mallet[1].getMalletX() + mallet[1].getMalletSpeedX(), mallet[1].getMalletY() + mallet[1].getMalletSpeedY());
				
				
				// Prints the speed of our mallet(keypresses) to the title of the window.
				setTitle(mallet[1].getMalletSpeedX() + " " + mallet[1].getMalletSpeedY()+" " + puck.getPuckSpeedY() + " " + puck.getPuckSpeedX() );


				// If the mallet and puck collided, change "isColliding" to true.
				isColliding = collisionDetector.doesItCollide();

				
				// Call for the method bounce(), sets the pucks maxspeed and then sets isColliding to false to prevent this loop from running forever.
				if (isColliding && hasBounced > immunityTicks) {

					/* It bounces twice or more from the mallet, something needs to be done. Notice that I've disabled the puckMaxSpeed's to test this.
					 * 
					 * Right at the start of the game if you just drive straight into the puck, the puck will get a speed of -10.
					 * It should be -5, as the puck has no speed and gets the mallets speed(which is -5).
					 * If you hit it really carefully with the tip of the mallet, it gets a speed of -5, which is correct.
					 * Same thing with bounces, if you bounce straight against the puck with the mallet 
					 * the puck's speed should get reduced by the mallets speed, but in the game it just increases, unless you are very lucky
					 * and careful, then you might get it to reduce it's speed when hitting it.
					 * So the puck hits the mallet multiple times when bouncing, not just once as is intended.
					 * Very possible related to the fact, that the mallet and puck always slightly overlap.
					 * One fix could be that we check the collision immediately after any movement from the puck or mallet happens.
					 * Like, we we have a while(isColliding == false)(DON'T TRY THIS, CRASHES COMPUTER) and when it breaks, we bounce. 
					 * Meaning that we are always aware/checking the collision, because now the collisionchecking happens *after* we've moved both the puck and mallet.
					 * Meaning that the puck could move into our mallet, we move the mallet and *then* we check the collision when they are already overlapping.
					 * 
					 * I tried adding the same isCollidining check and bounce after both puck- and malletMovement, but didn't fix. 
					 * Maybe fixing the malletspeed == 0 case will fix this, dunno.
					 * The overlapp needs to be fixed somehow.
					 * Maybe giving the puck/mallet a giveImmunity() method or something which makes them "immune" or "transparent" for 1-2 ticks after the bounce?
					 * This could help with the problem.
					 */
				
					bounce();
					hasBounced = 0;
					//puck.checkPuckSpeedXY();
					isColliding = false;
				} 

				// The variable slow determines the rate at which the method "reduceSpeed()" is called.
				// Someday.

				// This should probably be done after everything else in the ActionListener is done. 
				// Calls the Painter to paint everything, meaning that it calls repaint() once.
				painter.paintAll();
				hasBounced++;
				
			} // actionPerformed
		}; // aListener

		// Timer defines in milliseconds how often the ActionListener is run.
		Timer t = new Timer(2, aListener);
		// I guess the Timer is a Thread?
		// Yes it is. It's also a daemon thread.
		t.start();
	} // initialize()

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Handles the movement of the puck. 
	 * If the puck comes into a contact with a wall/floor/ceiling, it will bounce off from it.
	 */
	public void puckMovement() {
		// Puck hits the  left wall.
		if (puck.getPuckX() < 0) {
			puck.invertPuckSpeedX();
		}
		// Puck hits the right wall..
		if (puck.getPuckX() > 460) {
			puck.invertPuckSpeedX();
		}
		// Puck hits the ceiling.
		if (puck.getPuckY() < 0) {
			puck.invertPuckSpeedY();
		}
		// Puck hits the floor.
		if (puck.getPuckY() > 660) {
			puck.invertPuckSpeedY();
		}
		// Puck is moved from the original coordinates to the new ones (old coordinates + the speed).
		puck.movePuck(puck.getPuckX() + puck.getPuckSpeedX(), puck.getPuckY() + puck.getPuckSpeedY());
	} // puckMovement()

	/**
	 * Puck's speed is changed to match the mallet's speed(not realistic, needs more work).
	 * Doesn't take into account the angle. eg. you can hit a stationary puck with the side of the mallet and
	 * still make it go straight up as long as you were moving straight up.
	 */
	public void bounce() {
		// double[] d = collisionDetector.collisionCoordinates();
		// If the mallet is moving, do the stuff
		if (mallet[1].getMalletSpeedX() != 0 || mallet[1].getMalletSpeedY() != 0 ) {

			puck.setPuckSpeedX(puck.getPuckSpeedX() + mallet[1].getMalletSpeedX());
			puck.setPuckSpeedY(puck.getPuckSpeedY() + mallet[1].getMalletSpeedY());
		}
		// this needs to be implemented.
		if (mallet[1].getMalletSpeedX() == 0 && mallet[1].getMalletSpeedY() == 0 ) {

		}
	} // bounce()
} // GameFrame