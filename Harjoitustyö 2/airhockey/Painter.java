package airhockey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Painter handles the painting of the objects inside JPanel and repaints the panel when asked.
 * @author joel
 *
 */

public class Painter extends JPanel {
	// Don't ask. Has something to do with JFrame/JPanel.
	private static final long serialVersionUID = 1L;

	private Puck puck;
	private Mallet[] mallet;

	/**
	 * Initializes the size of the JPanel, sets its color and
	 * asks for the puck and mallet objects so that we can use their values(eg. coordinates) and methods(getters) to draw them.
	 * @param puck
	 * @param mallet
	 */
	public Painter(Puck puck, Mallet[] mallet) {	
		this.puck = puck;
		this.mallet = mallet;
		// Sets the size of the JPanel.
		setPreferredSize(new Dimension(500,700));
		// And color.
		setBackground(Color.WHITE);
	}

	/**
	 * Paints the JPanel, which includes the puck(black color), the stage lines and 2 mallets(both red).
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		// Sets the location(x, y) and the size(x-lenght, y-lenght) of the puck.
		g.fillOval(puck.getPuckX(), puck.getPuckY(), puck.getPuckRadius() * 2, puck.getPuckRadius() * 2);
		// I've added some graphical details to the arena.
		g.drawOval(100,200,300,300);
		g.drawLine(0, 350, 500, 350);

		// Paints both the mallets from the mallet[]-list.
		for (int i = 0; i < mallet.length; i++) {
			g.setColor(Color.RED);
			// Sets the location(x, y) of the mallet[i] and the size, just like the puck.
			g.fillOval(mallet[i].getMalletX(), mallet[i].getMalletY(), mallet[i].getMalletRadius() * 2, mallet[i].getMalletRadius() * 2);
		}
	}

	/**
	 * Calls JPanels own method, repaint(), nothing else
	 */
	public void paintAll() {
		repaint();
	}
}
