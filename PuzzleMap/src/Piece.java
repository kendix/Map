import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Piece {
	float[] startPos;				// id represents correct location in the puzzle
	BufferedImage img;
	float[] pos;
	int orientation;	// 0-3 for rotation
	
	public Piece(float[] start, BufferedImage img_, float[] pos_, int orientation_) {
		startPos = start;
		img = img_;
		pos = pos_;
		orientation = orientation_;
	}
	
	public void movePiece(float x, float y) {
		pos[0] += x;
		pos[1] += y;
	}
	
	public void drawPiece(Graphics g, int dx, int dy) {
		int x = (int) (pos[0] *dx);
		int y = (int) (pos[1] *dy);
		g.drawImage(img, x, y, null);
	}

}
