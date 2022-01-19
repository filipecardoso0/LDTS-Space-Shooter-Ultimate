package com.group605.spaceshooterultimate;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Enemy extends Entity {
    double health;

    Enemy(int x, int y, double health) {
        super(x,y);
        this.health = health;
    }


    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }


    public abstract void draw(TextGraphics graphics);

    public void moveEnemy(){this.position.setY(this.position.getY()+1);}

    public boolean isDead(){
        if(this.health <= 0) return true;

        return false;
    }

    public boolean checkImpact(Enemy enemy, Player player) {
        if(enemy.position.equals(player.getPosition())) {
            return true;
        }
        return false;
    }


}