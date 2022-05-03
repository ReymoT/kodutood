import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.*;
import java.applet.Applet;
import java.awt.Font;
import java.awt.Dimension;
import java.util.*;

public class Suda extends Applet {

    public void paint(Graphics g) {

        Dimension suurus = getSize();

        int kx = suurus.width / 2;
        int ky = suurus.height / 2;

        int suurendus = 5;

        for (double t = -kx; t < kx; t += 0.5) {

            double y = 13 * Math.cos(t * Math.PI / 180) - 5 * Math.cos(2 * t * Math.PI / 180) - 2 * Math.cos(3 * t * Math.PI / 180) - Math.cos(4 * t * Math.PI / 180);
            double x = 16 * Math.pow(Math.sin(t * Math.PI / 180), 3);

            int Y = (int)(y * suurendus);
            int X = (int)(x * suurendus);

            g.drawLine(kx + X, ky - Y, kx + X, ky - Y);

        }



    }

    public static void main(String[] args) {
        Frame f = new Frame("Süda");

        f.setSize(600, 400);
        f.setLocation(200, 200);
        f.setBackground(Color.WHITE);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(new Suda());

        f.setVisible(true);
    }
}