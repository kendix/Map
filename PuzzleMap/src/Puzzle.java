import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class Puzzle {
	float dim;

	BufferedImage img;
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	ArrayList<ArrayList<Piece>> groups = new ArrayList<ArrayList<Piece>>();
	public Puzzle(int d, File im) throws FileNotFoundException, IOException {
		img = ImageIO.read(new FileInputStream(im));
		dim = d;
		for(float i = 0 ; i < d ; i++) {
			for(float j = 0 ; j < d ; j++) {
				int xm = img.getWidth();
				int ym = img.getHeight();
				BufferedImage subimg = img.getSubimage((int)(xm/d*i), (int)(ym/d*j), (xm/d), (ym/d));
				float rx = (float) (i/d);//+(Math.random()-1)5/x);
				float ry = (float) (j/d);//+(Math.random()-1)/y);
				float[] pos =  new float[]{rx,ry};
				Piece p = new Piece(new float[]{i/d,j/d}, subimg, pos, 0);
				pieces.add(p);
				ArrayList<Piece> l = new ArrayList<Piece>();
				l.add(p);
				groups.add(l);
			}
		}

		for(int i = 0 ; i < d*d ; i++) {
			int a = (int)(Math.random()*d*d);
			int b = (int)(Math.random()*d*d);
			float[] f = pieces.get(a).pos;
			pieces.get(a).pos = pieces.get(b).pos;
			pieces.get(b).pos = f;
		}
	}

	public Piece getPieceAt(float x, float y) {
		for(int i = pieces.size()-1 ; i >= 0 ; i--) {
			Piece p = pieces.get(i);
			float size = 1/(float)dim;
			if(x > p.pos[0] && x < p.pos[0]+size)
				if(y > p.pos[1] && y < p.pos[1]+size) {
					for(ArrayList<Piece> gr : groups) {
						if(gr.contains(p)) {
							for(Piece pi : gr) {
								pieces.remove(pi);
								pieces.add(pi);
							}
						}
					}
					return p;
				}
		}
		return null;
	}

	public void movePieces(Piece s, float x, float y) {
		for(ArrayList<Piece> p : groups) {
			if(p.contains(s)) {
				for(Piece ps : p) {
					ps.movePiece(x, y);
				}
			}
		}
	}

	public void placePiece(Piece s) {
		ArrayList<Piece> adj = new ArrayList<Piece>();
		for(Piece p : pieces) {
			float[] a = s.startPos;
			float[] b = p.startPos;
			float diff = Math.abs(a[0]-b[0]) + Math.abs(a[1]-b[1]);
			if(diff < 1/dim*1.01 && diff > 1/dim*.99) {
				adj.add(p);
			}
		}
		for(Piece p : adj) {
			float xdifa = s.pos[0] - p.pos[0];
			float xdifb = s.startPos[0] - p.startPos[0];
			float ydifa = s.pos[1] - p.pos[1];
			float ydifb = s.startPos[1] - p.startPos[1];
			if(xdifa > xdifb-1/dim*.2 && xdifa < xdifb+1/dim*.1) { 
				if(ydifa > ydifb-1/dim*.1 && ydifa < ydifb+1/dim*.1) {
					int i,j = 0;
					for(i = 0 ; i < groups.size() ; i++) {
						if(groups.get(i).contains(p)) {
							for(j = 0 ; j < groups.size() ; j++) {
								if(groups.get(j).contains(s)) {
									movePieces(groups.get(j).get(0), -xdifa+s.startPos[0] - p.startPos[0], -ydifa+s.startPos[1] - p.startPos[1]);
									int num = groups.get(j).size();
									for(int k = 0 ; k < num ; k++) {
										groups.get(i).add(groups.get(j).remove(0));
									}


									break;
								}
							}
							break;
						}

					}
				}


			}
		}
	}

	public void drawPuzzle(Graphics g, int maxdimen) {
		for(Piece p : pieces) {
			p.drawPiece(g, maxdimen);
		}
	}
}

