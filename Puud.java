import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.*;
import java.awt.image.*;
import java.applet.Applet;
import java.awt.Font;
import java.awt.Dimension;
import java.util.Random;

class Puu {
    private int x;
    private int y;
    private int laius;
    private int pikkus;

    Puu(int algusX, int algusY, int puuLaius, int puuPikkus) {
        x = algusX;
        y = algusY;
        laius = puuLaius;
        pikkus = (int)(puuPikkus * 0.6); //seda muutujat kasutan ovaali pikkuse määramiseks, mis on 60% puu pikkusest
    }

    public void paint(Graphics g) {
        //Ristkylik
        g.drawRect(x - (laius / 4), y - (pikkus / 3), (laius / 2), (int)(pikkus / 1.5));

        //Ovaal
        g.drawOval(x - (laius / 2) , y - (int)(pikkus / 0.75), laius, pikkus);
    }

    public void varviPuu(Graphics g) {
        g.setColor(new Color(102, 51, 0));
        g.fillRect(x - (laius / 4), y - (pikkus / 3), (laius / 2), (int)(pikkus / 1.5));
        g.setColor(new Color(0, 51, 0));
        g.fillOval(x - (laius / 2) , y - (int)(pikkus / 0.75), laius, pikkus);
    }
}

public class Puud extends Applet {

    public void update(Graphics g){
        Dimension suurus = getSize(); // Hangi joonistatava ala suurus, et BI-le õigeid mõõte ette anda!
        BufferedImage img = new BufferedImage(suurus.width, suurus.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = img.createGraphics(); // Loo BI-le graafika
        gr.setColor(getBackground()); // Määra värviks taustavärv
        gr.fillRect(0,0,suurus.width,suurus.height); // Joonista kogu pilt taustaga üle
        paint(gr); // Seesama ülemine paint meetod joonistab nüüd siia puhverdatud pildi peale!
        g.drawImage(img, 0, 0, null); // Suuna puhvris olev pilt aknasse
    }

    public void paint(Graphics g) {
        Random rn = new Random();

        Dimension suurus = getSize();

        int kx = suurus.width / 2;
        int ky = suurus.height / 2;

        // 1.
        Puu esimenePuu = new Puu(kx, ky, 100, 150);
        //esimenePuu.paint(g);

        // 2.
        for (int i = 1; i <= 3; i++) {
            int vahe = suurus.width / 4;
            Puu uusPuu = new Puu(i * vahe, ky, 100, 150);
            //uusPuu.paint(g);
        }

        // 3.
        int suvalineArv = rn.nextInt(10) + 1;
        for (int i = 1; i <= suvalineArv; i++) {
            int vahe = suurus.width / (suvalineArv + 1);
            Puu uusPuu = new Puu(i * vahe, ky, 100, 150);
            //uusPuu.paint(g);
        }

        // 4.
        int suvalineArvPuid = rn.nextInt(20) + 1;
        for (int i = 1; i <= suvalineArvPuid; i++) {
            int suvalineX = rn.nextInt(suurus.width);
            int suvalineY = rn.nextInt(suurus.height);
            Puu uusPuu = new Puu(suvalineX, suvalineY, 100, 150);
            //uusPuu.paint(g);
        }

        // 5. - mets
        int puudeArv = 5;
        for (int rida = 0; rida < 5; rida++) {
            for (int puu = 1; puu <= puudeArv; puu++) {
                int suvalineX = rn.nextInt(suurus.width);
                // pikkuse ja laiuse suhe alati 1.5
                int puuLaius = 50 + (rida * 25);
                int puuPikkus = (int)(1.5 * puuLaius);
                Puu uusPuu = new Puu(suvalineX, (suurus.height - 800) + rida * 150, puuLaius, puuPikkus);
                uusPuu.varviPuu(g);
                uusPuu.paint(g);
            }
        }
    }

    public static void main(String[] args) {
        Frame f = new Frame("Puud");

        f.setSize(1100, 1100);
        f.setLocation(200, 200);
        f.setBackground(Color.WHITE);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(new Puud());

        f.setVisible(true);
    }
}