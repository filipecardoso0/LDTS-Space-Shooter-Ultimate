package com.group605.spaceshooterultimate;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;


public class Player extends Entity{
    int lives = 3;
    String lifestr = "3";

    Player(int x, int y){
        super(x,y);
    }

    public int getLifes() {
        return lives;
    }

    public void setLifes(int lifes) {
        this.lives = lifes;
    }

    public Position moveUp(){
        return new Position(position.getX(), position.getY()-1);
    }

    public Position moveDown(){
        return new Position(position.getX(), position.getY()+1);
    }

    public Position moveRight(){
        return new Position(position.getX()+1, position.getY());
    }

    public Position moveLeft(){
        return new Position(position.getX()-1, position.getY());
    }

    public void draw(TextGraphics graphics){
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "X");
    }

    //Quick Easy Way to Display Player Amount of Lives Remaining in an Arcade Way
    public String displayLifes(){

        if(this.lives == 3){
            return lifestr = "LIVES REMAINING: 3";
        }
        else if(this.lives == 2){
            return lifestr = "LIVES REMAINING: 2";
        }
        else if(this.lives == 1){
            return lifestr = "LIVES REMAINING: 1";
        }
        else {
            return lifestr = "LIVES REMAINING: 0";
        }
    }
}
