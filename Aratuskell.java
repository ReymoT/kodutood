import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.Timer;
import java.util.Calendar;
import java.awt.image.*;
import java.util.Random;

public class Aratuskell extends Applet implements ActionListener {

    Label aratusAeg = new Label("Äratusaeg - (tunnid, minutid)");
    TextField tunnid = new TextField("6");
    TextField minutid = new TextField("0");
    Button nupp = new Button("Sea äratusaeg");
    Integer aratusTunnid = null;
    Integer aratusMinutid = null;

    Timer taimer = new Timer(1000, this);

    Aratuskell() {
        taimer.start();
        add(aratusAeg);
        add(tunnid);
        add(minutid);
        add(nupp);
        nupp.addActionListener(this);
    }

    public void actionPerformed (ActionEvent ae) {
        repaint();

        if (ae.getSource() == nupp) {
            aratusTunnid = Integer.parseInt(tunnid.getText());
            aratusMinutid = Integer.parseInt(minutid.getText());

        }
    }

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

        g.setColor(Color.BLACK);

        Dimension suurus = getSize();

        int kx = suurus.width / 2;
        int ky = suurus.height / 2;

        int raadius = 3 * Math.min(kx, ky) / 4;

        g.drawOval(kx - raadius, ky - raadius, 2 * raadius, 2 * raadius);


        int fondiSuurus = Math.min(kx, ky) / 10;
        g.setFont(new Font("Courier New", Font.PLAIN, fondiSuurus));
        //Minutite ja tundide kriipsukesed
        for (int i = 1; i <= 60; i++) {
            int kaugus = (i % 5 == 0 ? 8 : 9) * raadius / 10;
            double nurk = i * Math.PI / 30 - Math.PI / 2;
            int x  = (int)(kaugus * Math.cos(nurk));
            int y  = (int)(kaugus * Math.sin(nurk));
            int jx  = (int)(raadius * Math.cos(nurk));
            int jy  = (int)(raadius * Math.sin(nurk));
            g.drawLine(kx + jx, ky + jy, kx + x, ky + y);
            if (i % 5 == 0) {
                int nx = (int)(1.1 * raadius * Math.cos(nurk));
                int ny = (int)(1.1 * raadius * Math.sin(nurk));
                g.drawString(i/5 + "", kx + nx, ky + ny + fondiSuurus / 2);
            }
        }

        Calendar rightNow = Calendar.getInstance();
        int sekundid = rightNow.get(Calendar.SECOND);
        int minutid = rightNow.get(Calendar.MINUTE);
        int tunnid = rightNow.get(Calendar.HOUR);

        int pikkus = 3 * raadius / 4;
        double nurk = sekundid * Math.PI / 30 - Math.PI / 2;

        int x  = (int)(pikkus * Math.cos(nurk));
        int y  = (int)(pikkus * Math.sin(nurk));

        g.drawLine(kx, ky, kx + x, ky + y);

        //Minutiosuti
        pikkus = 2 * raadius / 3;
        nurk = minutid * Math.PI / 30 + sekundid * Math.PI / 1800 - Math.PI / 2;

        x  = (int)(pikkus * Math.cos(nurk));
        y  = (int)(pikkus * Math.sin(nurk));

        g.drawLine(kx, ky, kx + x, ky + y);

        //Tunniosuti
        pikkus = raadius / 2;
        nurk = tunnid * Math.PI / 6 + minutid * Math.PI / 6 / 60 + sekundid * Math.PI / 6 / 60 / 60 - Math.PI / 2;

        x  = (int)(pikkus * Math.cos(nurk));
        y  = (int)(pikkus * Math.sin(nurk));

        g.drawLine(kx, ky, kx + x, ky + y);

        //Äratusosuti
        if ((aratusTunnid != null) && (aratusMinutid != null)) {
            pikkus = raadius;
            nurk = aratusTunnid * Math.PI / 6 + aratusMinutid * Math.PI / 6 / 60 - Math.PI / 2;

            x = (int) (pikkus * Math.cos(nurk));
            y = (int) (pikkus * Math.sin(nurk));
            g.setColor(Color.RED);
            g.drawLine(kx, ky, kx + x, ky + y);

            if ((tunnid == aratusTunnid) && (minutid == aratusMinutid)) {
                //Epilepsia hoiatus
                Color[] varvid = {Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GREEN, Color.YELLOW, Color.PINK, Color.DARK_GRAY, Color.ORANGE};
                int suvalineArv = rn.nextInt(varvid.length);
                setBackground(varvid[suvalineArv]);
                repaint();
            } else {
                setBackground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args) {
        Frame f = new Frame("Aratuskell");

        f.setSize(600, 400);
        f.setLocation(200, 200);
        f.setBackground(Color.WHITE);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(new Aratuskell());

        f.setVisible(true);
    }
}