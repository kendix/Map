import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends JFrame implements MouseMotionListener, MouseListener{
	int dim;
	Puzzle p;
	Piece s; // currently selected piece
	int mouse[] = new int[2];
	int state = 0; // 0 - nothing 1 - holding piece
	BufferedImage bi;
	Graphics bg;
	public Game(int d, String i, int x, int y) {
		super("Puzzle Game");
		dim = d;
		bi = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB);
		try {
			p = new Puzzle(x, y, new File(i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(dim, dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void paint(Graphics g) {
		bg = bi.getGraphics();
		bg.setColor(Color.WHITE);
		bg.fillRect(0, 0, dim, dim);
		p.drawPuzzle(bg, dim);
		g.drawImage(bi, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mx = e.getX() - mouse[0];
		int my = e.getY() - mouse[1];
		if(s != null ) {
			float x = (float)(mx)/dim;
			float y = (float)(my)/dim;
			p.movePieces(s, x, y);
			repaint();
		}
		mouse[0] = e.getX();
		mouse[1] = e.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse[0] = e.getX();
		mouse[1] = e.getY();
		float x = (float)(e.getX())/dim;
		float y = (float)(e.getY())/dim;
		s = p.getPieceAt(x, y);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(s!=null)
			p.placePiece(s);
		s = null;
		repaint();
	}


	public static void main(String[] args) {
		new Game(500, "test.png", 10, 2);
	}

}
