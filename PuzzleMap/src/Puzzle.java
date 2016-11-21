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
   float dimx;
   float dimy;

   BufferedImage img;
   ArrayList<Piece> pieces = new ArrayList<Piece>();
   ArrayList<ArrayList<Piece>> groups = new ArrayList<ArrayList<Piece>>();
   public Puzzle(int dx, int dy, File im) throws FileNotFoundException, IOException {
      img = ImageIO.read(new FileInputStream(im));
      dimx = dx;
      dimy = dy;
      for(float i = 0 ; i < dx ; i++) {
         for(float j = 0 ; j < dy ; j++) {
            int xm = img.getWidth();
            int ym = img.getHeight();
            BufferedImage subimg = img.getSubimage((int)(xm/dx*i), (int)(ym/dy*j), (xm/dx), (ym/dy));
            float rx = (float) (i/dx);//+(Math.random()-1)5/x);
            float ry = (float) (j/dy);//+(Math.random()-1)/y);
            float[] pos =  new float[]{rx,ry};
            Piece p = new Piece(new float[]{i/dx,j/dy}, subimg, pos, 0);
            pieces.add(p);
            ArrayList<Piece> l = new ArrayList<Piece>();
            l.add(p);
            groups.add(l);
         }
      }

      for(int i = 0 ; i < dx*dy ; i++) {
         int a = (int)(Math.random()*dx*dy);
         int b = (int)(Math.random()*dx*dy);
         float[] f = pieces.get(a).pos;
         pieces.get(a).pos = pieces.get(b).pos;
         pieces.get(b).pos = f;
      }
   }

   public Piece getPieceAt(float x, float y) {
      for(int i = pieces.size()-1 ; i >= 0 ; i--) {
         Piece p = pieces.get(i);
         float sizex = 1/(float)dimx;
         float sizey = 1/(float)dimy;
         if(x > p.pos[0] && x < p.pos[0]+sizex)
            if(y > p.pos[1] && y < p.pos[1]+sizey) {
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
         if(!p.equals(s)) {
            float[] a = s.startPos;
            float[] b = p.startPos;
            float diff = Math.abs(a[0]-b[0]) + Math.abs(a[1]-b[1]);
            System.out.println(diff + " " + 1/dimx);
            if((diff < 1/dimx*1.01 && diff > 1/dimx*.99) || (diff < 1/dimy*1.01 && diff > 1/dimy*.99)) {
               adj.add(p);
            }
         }
      }
      for(Piece p : adj) {
         float xdifa = s.pos[0] - p.pos[0];
         float xdifb = s.startPos[0] - p.startPos[0];
         float ydifa = s.pos[1] - p.pos[1];
         float ydifb = s.startPos[1] - p.startPos[1];
         System.out.println(xdifa + " "  + xdifb + " | " + ydifa + " " + ydifb);
         if(xdifa > xdifb-1/dimx*.2 && xdifa < xdifb+1/dimx*.2) { 
            if(ydifa > ydifb-1/dimy*.2 && ydifa < ydifb+1/dimy*.2) {
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

   public void drawPuzzle(Graphics g, int dx, int dy) {
      for(Piece p : pieces) {
         p.drawPiece(g, dx, dy);
      }
   }
}

