import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

    //Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

    //*******************************************************************************
// Class Definition Section
// step 1: implent keylisitener
// step 1" implent Mouslisinter
    public class InvadersOfSpace implements Runnable, KeyListener, MouseListener {

        //Variable Definition Section
        //Declare the variables used in the program
        //You can set their initial values too

        //Sets the width and height of the program window
        final int WIDTH = 1000;
        final int HEIGHT = 700;

        //Declare the variables needed for the graphics
        public JFrame frame;
        public Canvas canvas;
        public JPanel panel;

        public BufferStrategy bufferStrategy;
        public Image Spacedude;
        public Image Ealien;
        public Image AbdulBlast;
        public Ealien[] CSbros;
        public AbdulBlast[] Blast;
        public int Counter;
        public int Points;
        public Image CSbro1;
        public Image CSbro2;
        public Image CSbro3;
        public Image CSbro4;
        public Image Salud;
        public Image Win;
        public Image Lose;
        public Image BackgroundPic;



        //Declare the objects used in the program
        //These are things that are made up of more than one variable type
        private Spacedude Hship;
        private Salud ChugChug;


        // Main method definition
        // This is the code that runs first and automatically
        public static void main(String[] args) {
            InvadersOfSpace ex = new InvadersOfSpace();   //creates a new instance of the game
            new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
        }


        // Constructor Method
        // This has the same name as the class
        // This section is the setup portion of the program
        // Initialize your variables and construct your program objects here.
        public InvadersOfSpace() {

            setUpGraphics();

            int randx = (int) (Math.random() * 10) + 1;

            randx = (int) (Math.random() * 999) + 1;

            int randy = (int) (Math.random() * 699) + 1;
            int randpx;
            int randpy;
            int randtx;
            int randty;
            randpx = (int) (Math.random() * 999) + 1;
            randpy = (int) (Math.random() * 699) + 1;
            randtx = (int) (Math.random() * 999) + 1;
            randty = (int) (Math.random() * 699) + 1;



            //variable and objects
            //create (construct) the objects needed for the game and load up
            Spacedude = Toolkit.getDefaultToolkit().getImage("SpaceBus.jpeg"); //load the picture
            AbdulBlast = Toolkit.getDefaultToolkit().getImage("AbdulBlast!!!.JPG");
            Ealien =  Toolkit.getDefaultToolkit().getImage("Moooon.jpeg");
            CSbro1 = Toolkit.getDefaultToolkit().getImage("NattyNiam.png");
            CSbro2 = Toolkit.getDefaultToolkit().getImage("RobustRen.png");
            CSbro3 = Toolkit.getDefaultToolkit().getImage("TryannicalToby.png");
            CSbro4 = Toolkit.getDefaultToolkit().getImage("GDAWG.png");
            Salud =  Toolkit.getDefaultToolkit().getImage("ChugJug.png");
            Win =  Toolkit.getDefaultToolkit().getImage("Wining.gif");
            Lose = Toolkit.getDefaultToolkit().getImage("Losing.gif");
            BackgroundPic = Toolkit.getDefaultToolkit().getImage("THEMOON.jpg");
            CSbros = new Ealien[10];
            Blast = new AbdulBlast[10];
            Hship = new Spacedude(randx, randy);
            ChugChug = new Salud(500,400);

            Scanner S = new Scanner(System.in);
            System.out.println("What is your name?");
            String NameH = S.nextLine();
            System.out.println(NameH);
            Hship.name = NameH;

            //This is where the specif place where the blast are going to spawn: on top of the Hship
            for(int b = 0; b <Blast.length; b = b +1) {
                Blast[b] = new AbdulBlast(55350,3421) ;
            }
            //This is where the CSbros(the asteroids) will spawn and also a random number between 1 throut 5 will be assoinged to each object, giving it a different image
            for(int n=0; n<CSbros.length; n=n+1) {
                CSbros[n] = new Ealien((int)(Math.random()*1000),(int)(Math.random()*50));
                CSbros[n].image = (int)(Math.random()*5)+1;
            }





            }// InvadersOfSpace()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

        // main thread
        // this is the code that plays the game after you set things up
        public void run() {

            //for the moment we will loop things forever.
            while (true) {

                moveThings();  //move all the game objects
                render();  // paint the graphics
                pause(20);
                // sleep for 10 ms
            }
        }


        public void moveThings() {
            //calls the move( ) code in the objects
           //This makes my Hship move
            Hship.move();
            //This makes my CSbros move
            for(int m=0; m<CSbros.length; m=m+1){
                CSbros[m].move();
            }
            //This makes my CSbros move
            for(int o = 0; o <Blast.length; o = o +1) {
                Blast[o].move();
            }
            //This makes my ChugChug move
            ChugChug.move();
            crashing();
        }

        public void crashing() {
            //checks to see if the bullet and CSbros intercects and if they do the CSbro loses 50 HP
            for(int l=0; l<Blast.length; l++ )
            for (int c=0; c<CSbros.length; c=c+1){
                if (Blast[l].hitBox.intersects(CSbros[c].hitBox)){
                    System.out.println("Blast! crash");
                    CSbros[c].HP = CSbros[c].HP - 50;


                }

            }
            //this checks if the CSbro is alive using hp and if its no then it gets telported far away
            // You also gain 10 points
            for (int e=0; e<CSbros.length; e=e+1){
                if (CSbros[e].HP <= 0 && CSbros[e].isAlive == true){
                    CSbros[e].isAlive = false;
                    CSbros[e].xpos = 100000;
                    Points = Points + 10;
                }
            }
            // this checks if CS bros and the Hship intercet if they do then both of them lose health and Hship bounces off
            for (int y = 0; y <CSbros.length; y = y +1){
                if (CSbros[y].hitBox.intersects(Hship.hitBox)&& Hship.iscrasinhg == false){
                    Hship.HP = Hship.HP - 100;
                    CSbros[y].HP = CSbros[y].HP - 50;
                    Hship.dx = -Hship.dx+10;
                    Hship.dy = -Hship.dy+10;

                }
                // This checks to see if the CSbros are below a certian leavl and if they are the Hsip loses health
                for(int h=0; h<CSbros.length; h=h+1){
                    if (CSbros[h].ypos == 600){
                        Hship.HP = Hship.HP - 2;
                    }
                }
            }


//  start screen
        }

        //Pauses or sleeps the computer for the amount specified in milliseconds
        public void pause(int time) {
            //sleep
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {

            }
        }

        //Graphics setup method
        private void setUpGraphics() {
            frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

            panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
            panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
            panel.setLayout(null);   //set the layout

            // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
            // and trap input events (Mouse and Keyboard events)
            canvas = new Canvas();
            canvas.setBounds(0, 0, WIDTH, HEIGHT);
            canvas.setIgnoreRepaint(true);

            //step 2: as canvas as the keylisten
            canvas.addKeyListener(this);
            //step: 2 set canvas
            canvas.addMouseListener(this);
            panel.add(canvas);  // adds the canvas to the panel.

            // frame operations
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
            frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
            frame.setResizable(false);   //makes it so the frame cannot be resized
            frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

            // sets up things so the screen displays images nicely.
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            canvas.requestFocus();
            System.out.println("DONE graphic setup");

        }


        //paints things on the screen using bufferStrategy
        private void render() {
            Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.clearRect(0, 0, WIDTH, HEIGHT);
            // Start adding things here
            g.drawImage(BackgroundPic, 0, 0, WIDTH, HEIGHT, null);
            //an if stament to check if HShip is alive and if it isn't then lose screen comes up
            if(Hship.HP <= 0 ) {
                Hship.isAlive = false;
                Hship.xpos = 102443;
                Hship.ypos = 13155;
                g.drawString("Game Over:( ", 290, 250);
                for (int q = 0; q < CSbros.length; q = q + 1) {
                    CSbros[q].isAlive = false;
                }
                ChugChug.isAlive = false;
                ChugChug.xpos = 102443;
                ChugChug.ypos = 13155;
                g.setColor(Color.BLUE);
                g.setFont(new Font("TimesNewRoman", Font.BOLD, 100));
                g.drawImage(Lose,0,0,WIDTH,HEIGHT,null);
            }
            //If points reach 100 then a win screen comes up
                if(Points == 100){
                    ChugChug.isAlive = false;
                    ChugChug.xpos = 102443;
                    ChugChug.ypos = 13155;
                    g.drawImage(Win,0,0,WIDTH,HEIGHT,null);
                    g.setColor(Color.CYAN);
                    g.setFont(new Font("TimesNewRoman",Font.BOLD,100));
                    g.drawString("You Won!!!:) ",290,250);
                }

            // This draws Hship and also it's health
            if (Hship.isAlive == true) {
                g.setColor(Color.RED);
                g.setFont(new Font("TimesNewRoman",Font.BOLD,15));
                g.drawString("Health: " + Hship.HP,Hship.xpos,Hship.ypos+80);
                g.drawImage(Spacedude, Hship.xpos, Hship.ypos, Hship.width, Hship.height, null);
                g.drawRect(Hship.hitBox.x, Hship.hitBox.y, Hship.hitBox.width+1, Hship.hitBox.height+1);
                g.setColor(Color.PINK);
                g.setFont(new Font("TimesNewRoman",Font.BOLD,15));
                g.drawString(Hship.name,Hship.xpos,Hship.ypos-1);
            }
           // checks if Hship's HP is lower than 600 if so then ChugChug spawns
            if(Hship.HP < 600){
                g.drawImage(Salud,ChugChug.xpos, ChugChug.ypos, ChugChug.width, ChugChug.height,null);
                g.drawRect(ChugChug.xpos,ChugChug.ypos,ChugChug.width, ChugChug.height);
                g.setColor(Color.YELLOW);
                g.setFont(new Font("TimesNewRoman",Font.BOLD,10));
                g.drawString("CLICK ME!!!: " ,ChugChug.xpos,ChugChug.ypos+80);
            }
            //this draws the blasters
            for(int a = 0; a <Blast.length; a = a +1) {
                //if(Blast[a].isAlive == true) {
                    g.drawImage(AbdulBlast, Blast[a].xpos + 5, Blast[a].ypos + 20, Blast[a].width, Blast[a].height, null);
              //  }
                }
            //This draws the CSbros and depending on what image number they got they spawin with a different image
            for(int r=0; r<CSbros.length; r=r+1) {
                if(CSbros[r].image == 1 && CSbros[r].isAlive == true) {
                    g.drawImage(Ealien, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 2 && CSbros[r].isAlive == true) {
                    g.drawImage(CSbro1, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 3 && CSbros[r].isAlive == true) {
                    g.drawImage(CSbro2, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 4 && CSbros[r].isAlive == true) {
                    g.drawImage(CSbro3, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 5 && CSbros[r].isAlive == true) {
                    g.drawImage(CSbro4, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);
                }

                g.setColor(Color.GREEN);
                g.setFont(new Font("TimesNewRoman",Font.BOLD,40));
                g.drawString("Points: " + Points,20,50);
            }


            // use this to draw hitbox

// end of adding things
            g.dispose();

            bufferStrategy.show();
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            // all of these are the key binds to control Hship

            if (e.getKeyCode()== 38){
                Hship.dy = 10;
                Hship.dy = -Math.abs(Hship.dy);
            }
            if (e.getKeyCode()== 40){
                Hship.dy = 10;
                Hship.dy = Math.abs(Hship.dy);
            }
            if (e.getKeyCode()== 39){
                Hship.dx = 10;
                Hship.dx = Math.abs(Hship.dx);

            }
            if (e.getKeyCode()== 37){
                Hship.dx = 10;
                Hship.dx = -Math.abs(Hship.dx);
            }
        }

        //Step3: added
        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == 38) {
                Hship.dy = 0;
            }
            if (e.getKeyCode() == 40) {
                Hship.dy = 0;
            }
            if (e.getKeyCode() == 39){
                Hship.dx = 0;
            }
            if (e.getKeyCode() == 37) {
                Hship.dx = 0;
            }
            // When space bar is pressed one blast lunches
            //This counter vairble keeps track of the blast and when they should spawn
            if (e.getKeyCode() == 32) {
                System.out.println("space bar");
                if(Counter >= 10){
                    Counter = 0;
                }
                System.out.println(Counter);
                Blast[Counter].xpos = Hship.xpos+10;
                Blast[Counter].ypos = Hship.ypos;
                Blast[Counter].dy = -50;
                Blast[Counter].isAlive = true;
                Counter = Counter + 1;

            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //This checks to see if you click on the ChugChug and if so then Hship gains HP
        int mousexpos = e.getX();
        int mouseypos = e.getY();

        if(ChugChug.hitBox.contains(mousexpos,mouseypos)){
            Hship.HP = Hship.HP + 200;
            ChugChug.isAlive = false;
            ChugChug.xpos = 1540000;
            ChugChug.ypos = 1430000;
        }


        }

        @Override//p
        public void mousePressed(MouseEvent e) {

           /*/ for(int u=0; u<10; u=u + 1){
             for(int v = 0; v <Blast.length; v = v +1 ) {

                Blast[v].isAlive = true;
                Blast[v].dy = -50;

            }
            }/*/

        }

        @Override
        public void mouseReleased(MouseEvent e) {


        }

        @Override
        public void mouseEntered(MouseEvent e) {


        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }




