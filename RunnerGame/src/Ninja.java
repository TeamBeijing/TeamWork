
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import gpxUtilities.BufferedImageLoader;
import gpxUtilities.SpriteSheet;
import sun.applet.Main;
import javax.swing.*;

public class Ninja implements ActionListener, KeyListener {

    public BufferedImage Img;
    SpriteSheet ssRun, ssJump;
    Timer t = new Timer(100, this);
    int velx = 0, vely = 0;
    int x = 150, y = 200;
    int runSpriteOffcet = 5, jumpSpriteOffcet = 0;
    boolean isOnGround = true, jumpAnimationAllowed = true;

    public Ninja() {

        t.start();
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage spriteSheetRun = null;
        BufferedImage spriteSheetJump = null;
        try{
            spriteSheetRun = loader.loadImage("src/textures/ninjaRun.png");
            spriteSheetJump = loader.loadImage("src/textures/ninjaJump.png");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        ssRun = new SpriteSheet(spriteSheetRun);
        ssJump = new SpriteSheet(spriteSheetJump);
    }

    public void actionPerformed(ActionEvent e) {
        x += velx;
        y += vely;
        checkNinjaLocation();
    }

    public void up() {
        if(isOnGround) {
            t.setDelay(15);
            vely = -10;
            velx = 0;
            isOnGround = false;
            jump();
        }
    }
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left();
        }
    }
    public void down() {
        vely = 10;
        velx = 0;
    }

    public void left() {
        vely = 0;
        velx = -10;
    }

    public void right() {
        vely = 0;
        velx = 10;
    }

    public void jump() {
        if(jumpAnimationAllowed) {
            this.Img = ssJump.grabSprite(jumpSpriteOffcet, 0, 82, 85);
            jumpSpriteOffcet += 80;
        }
        if(jumpSpriteOffcet >= 240){
            jumpSpriteOffcet = 240;
            jumpAnimationAllowed = false;
        }
        runSpriteOffcet = 5;
    }

    public void run(){
        this.Img = ssRun.grabSprite(runSpriteOffcet, 0, 79, 85);
        runSpriteOffcet += 80 + 2;
        if(runSpriteOffcet >= 450){
            runSpriteOffcet = 5;
        }
        jumpAnimationAllowed = true;
        jumpSpriteOffcet = 0;
    }

    public void checkNinjaLocation() {
        //If the ninja is at highest point at the jump tell it to fall down.
        if(y < 20)
        {
            down();
        }
        //IF the ninja is on the ground set timer to go slower and make it run
        if(y == 200) {
            isOnGround = true;
            t.setDelay(100);
            vely = 0;
            run();
        }
        //If ninja is going for jump play the animation
        if(y > 20 && y < 200) {
            jump();
        }

    }
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}