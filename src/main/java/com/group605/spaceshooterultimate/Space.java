package com.group605.spaceshooterultimate;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Space {

    private int width, height;
    private int ammotype; //1- Single, 2-Double, 3-Burst
    private Player player;
    private List<Border> borders;
    private List<SingleShot> singleShots;
    private List<DoubleShot> doubleShots;
    private List<BurstShot> burstShots;


    //TEXT OFFSET VALUES
    private int LIFESREMAINING_TEXT_DISPLAY_X_OFFSET_VALUE;
    private int LIFESREMAINING_TEXT_DISPLAY_Y_OFFSET_VALUE;
    private int CYCLEAMMO_TEXT_DISPLAY_X_OFFSET_VALUE;
    private int CYCLEAMMO_TEXT_DISPLAY_Y_OFFSET_VALUE;
    private int SESSIONINFO_TEXT_DISPLAY_X_OFFSET_VALUE;
    private int SESSIONINFO_TEXT_DISPLAY_Y_OFFSET_VALUE;

    Space(int width, int height){
        this.width = width;
        this.height = height;
        this.player = new Player(10,10);
        this.borders = createBorders();
        this.singleShots = new ArrayList<>();
        this.doubleShots = new ArrayList<>();
        this.burstShots = new ArrayList<>();
        this.ammotype = 1; //Sets Single as Default Ammo Type

        LIFESREMAINING_TEXT_DISPLAY_X_OFFSET_VALUE = width+10;
        LIFESREMAINING_TEXT_DISPLAY_Y_OFFSET_VALUE = height-10;
        CYCLEAMMO_TEXT_DISPLAY_X_OFFSET_VALUE = width+10;
        CYCLEAMMO_TEXT_DISPLAY_Y_OFFSET_VALUE = height-13;
        SESSIONINFO_TEXT_DISPLAY_X_OFFSET_VALUE = width+10;
        SESSIONINFO_TEXT_DISPLAY_Y_OFFSET_VALUE = height-20;
    }

    public void draw(TextGraphics graphics) throws IOException {
        //Draws Game Background
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        //Draw Map Borders
        for(Border border : borders){
            border.draw(graphics);
        }

        //Draw Single Shot Bullets
        for(SingleShot singleShot : singleShots){
            singleShot.draw(graphics);
            singleShot.move();
        }

        //Draw Double Shot Bullets
        for(DoubleShot doubleShot : doubleShots){
            doubleShot.draw(graphics);
            doubleShot.move();
        }

        //Draw Burst Shot Bullets
        for(BurstShot burstShot : burstShots){
            burstShot.draw(graphics);
            burstShot.move();
        }

        //Draws Character
        player.draw(graphics);

        //Session Info Type Text
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(SESSIONINFO_TEXT_DISPLAY_X_OFFSET_VALUE, SESSIONINFO_TEXT_DISPLAY_Y_OFFSET_VALUE), "SESSION INFO: ");

        //Lives Remaining Text
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(LIFESREMAINING_TEXT_DISPLAY_X_OFFSET_VALUE, LIFESREMAINING_TEXT_DISPLAY_Y_OFFSET_VALUE), player.displayLifes());

        //Cycle Ammo Type Text
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(CYCLEAMMO_TEXT_DISPLAY_X_OFFSET_VALUE, CYCLEAMMO_TEXT_DISPLAY_Y_OFFSET_VALUE), "ESC- FIRE; F1- SINGLE; F2- DOUBLE; F3- BURST");
    }

    public void processKey(KeyStroke key){
        switch (key.getKeyType()){
            case ArrowUp:
                movePlayer(player.moveUp());
                break;
            case ArrowDown:
                movePlayer(player.moveDown());
                break;
            case ArrowRight:
                movePlayer(player.moveRight());
                break;
            case ArrowLeft:
                movePlayer(player.moveLeft());
                break;
            case Escape:
                FireWeapon();
                break;
            case F1:
                ammotype = 1;
                break;
            case F2:
                ammotype = 2;
                break;
            case F3:
                ammotype = 3;
                break;
        }
    }

    private void FireWeapon(){
        if(ammotype == 1){
            singleShotFire();
        } else if (ammotype == 2){
            doubleShotFire();
        } else if (ammotype == 3) {
            burstShotFire();
        }
    }

    private List<SingleShot> singleShotFire(){
        singleShots.add(new SingleShot(player.position.getX(), player.position.getY()-1));
            return singleShots;
    }

    private List<DoubleShot> doubleShotFire(){
        doubleShots.add(new DoubleShot(player.position.getX()+1, player.position.getY()-1));
        doubleShots.add(new DoubleShot(player.position.getX()-1, player.position.getY()-1));
        return doubleShots;
    }
    private List<BurstShot> burstShotFire(){
        burstShots.add(new BurstShot(player.position.getX(), player.position.getY()-1));
        burstShots.add(new BurstShot(player.position.getX()-1, player.position.getY()-1));
        burstShots.add(new BurstShot(player.position.getX()+1, player.position.getY()-1));
        return burstShots;
    }



    public void movePlayer(Position position){
        if(canEntityMove(position) == true){
            player.setPosition(position);
        }
    }

    private List<Border> createBorders() {
        List<Border> borders = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            borders.add(new Border(i, 0));
            borders.add(new Border(i, height - 1));
        }
        for (int j = 1; j < height - 1; j++) {
            borders.add(new Border(0, j));
            borders.add(new Border(width - 1, j));
        }
        return borders;
    }

    public boolean canEntityMove(Position position){
        for(Border border : borders){
            if(position.equals(border.getPosition())){
                return false;
            }
        }
        return true;
    }

    //Added for test implementation
    public Player getPlayer() {
        return player;
    }
}
