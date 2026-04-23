import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.util.ArrayList;
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
        public Image CSbro1;
        public Image CSbro2;
        public Image CSbro3;
        public Image CSbro4;
        public Image BackgroundPic;



        //public Niastroid[] roids;
        //

        //Declare the objects used in the program
        //These are things that are made up of more than one variable type
        private Spacedude Hship;


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
            //random strucutre
            //(int) (Math. Random() * range) + start(where the range starts)
            //range is 1-10
            int randx = (int) (Math.random() * 10) + 1;
            //range 1-999
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


            //todo make a varible rand y that generates a random number between 1-699

            //variable and objects
            //create (construct) the objects needed for the game and load up
            Spacedude = Toolkit.getDefaultToolkit().getImage("SpaceBus.jpeg"); //load the picture
            AbdulBlast = Toolkit.getDefaultToolkit().getImage("AbdulBlast!!!.JPG");
            Ealien =  Toolkit.getDefaultToolkit().getImage("Moooon.jpeg");
            CSbro1 = Toolkit.getDefaultToolkit().getImage("NattyNiam.png");
            CSbro2 = Toolkit.getDefaultToolkit().getImage("RobustRen.png");
            CSbro3 = Toolkit.getDefaultToolkit().getImage("TryannicalToby.png");
            CSbro4 = Toolkit.getDefaultToolkit().getImage("GDAWG.png");
            BackgroundPic = Toolkit.getDefaultToolkit().getImage("THEMOON.jpg");
            CSbros = new Ealien[10];
            Blast = new AbdulBlast[10];
            Hship = new Spacedude(randx, randy);
            for(int b = 0; b <Blast.length; b = b +1) {
                Blast[b] = new AbdulBlast(Hship.xpos+5,Hship.ypos+21) ;
                Blast[b].isAlive = false ;
            }

            for(int n=0; n<CSbros.length; n=n+1) {
                CSbros[n] = new Ealien((int)(Math.random()*1000),(int)(Math.random()*100));
                CSbros[n].image = (int)(Math.random()*5)+1;
            }

            if(Counter > 10){
                Counter = 0;
            }

           /*/ Blast.dx = Hship.dx;
            Blast.dy = Hship.dy;/*/

            //Niamthemenece.dx = -Niamthemenece.dx; - use this to change the dx or dy of two objects that come from the same class



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
            Hship.move();
            for(int m=0; m<CSbros.length; m=m+1){
                CSbros[m].move();
            }
            for(int o = 0; o <Blast.length; o = o +1) {
                Blast[o].move();
            }
            crashing();
        }

        public void crashing() {
            // check to see if my astros crash into eachother
            for (int c=0; c<CSbros.length; c=c+1){
                if (CSbros[c].hitBox.intersects(Hship.hitBox)&& Hship.iscrasinhg == false){
                    System.out.println("astroid crash");
                }
            }
            for (int c=0; c<Blast.length; c=c+1){
                if (Blast[c].hitBox.intersects(CSbros[c].hitBox)){
                    System.out.println("Blast! crash");
                    Blast[c].isAlive = false;
                }
            }



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

            // When the niamastroid intercects with gdawg, GDAWG disapears
            if (Hship.isAlive == true) {
                g.drawImage(Spacedude, Hship.xpos, Hship.ypos, Hship.width, Hship.height, null);
                g.drawRect(Hship.hitBox.x, Hship.hitBox.y, Hship.hitBox.width+1, Hship.hitBox.height+1);
            }
            for(int a = 0; a <Blast.length; a = a +1) {
                //if(Blast[a].isAlive == true) {
                System.out.println(Blast[a].xpos+5);
                    g.drawImage(AbdulBlast, Blast[a].xpos + 5, Blast[a].ypos + 20, Blast[a].width, Blast[a].height, null);
              //  }
                }

            for(int r=0; r<CSbros.length; r=r+1) {
                if(CSbros[r].image == 1) {
                    g.drawImage(Ealien, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 2) {
                    g.drawImage(CSbro1, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 3) {
                    g.drawImage(CSbro2, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 4) {
                    g.drawImage(CSbro3, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }
                if(CSbros[r].image == 5) {
                    g.drawImage(CSbro4, CSbros[r].xpos, CSbros[r].ypos, CSbros[r].height, CSbros[r].width, null);

                }

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
            System.out.println("key typed " + e.getKeyCode());
            // up arrow is 38
            if (e.getKeyCode()== 38){
                System.out.println("pressed up arrow");
                Hship.dy = 10;
                Hship.dy = -Math.abs(Hship.dy);
            }

            if (e.getKeyCode()== 40){
                System.out.println("pressed down arrow");
                Hship.dy = 10;
                Hship.dy = Math.abs(Hship.dy);
            }
            if (e.getKeyCode()== 39){
                System.out.println("pressed right arrow");
                Hship.dx = 10;
                Hship.dx = Math.abs(Hship.dx);

            }
            if (e.getKeyCode()== 37){
                System.out.println("pressed left arrow");
                Hship.dx = 10;
                Hship.dx = -Math.abs(Hship.dx);
            }
        }

        //Step3: added
        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == 38) {
                System.out.println("not pressed up arrow");
                Hship.dy = 0;
            }
            if (e.getKeyCode() == 40) {
                System.out.println("not pressed up arrow");
                Hship.dy = 0;
            }
            if (e.getKeyCode() == 39);{
                System.out.println("not pressed up arrow");
                Hship.dx = 0;
            }
            if (e.getKeyCode() == 37) {
                System.out.println("not pressed up arrow");
                Hship.dx = 0;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            Blast[Counter].xpos = Hship.xpos;
            Blast[Counter].ypos = Hship.ypos;
            Blast[Counter].dy = -50;
            Blast[Counter].isAlive = true;
            Counter = Counter + 1;
           /*/ for(int u=0; u<10; u=u + 1){
             for(int v = 0; v <Blast.length; v = v +1 ) {

                Blast[v].isAlive = true;
                Blast[v].dy = -50;

            }
            }/*/

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println("blast");
            //Blast[Counter].isAlive = true;
            //Counter = Counter + 1;

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("enterred!!");

        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        //step 3 add keylisitener method
    }




