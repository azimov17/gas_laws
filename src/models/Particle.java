package models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import physics.Vector2;

public class Particle extends Circle {
    private Vector2 velocity;
    public Particle(){
        super(50,50,5);
    }

    public double getXPos(){
        return this.getCenterX();
    }
    public double getYPos(){
        return this.getCenterY();
    }
    public Vector2 getPosVector(){return new Vector2(this.getXPos(), this.getYPos());}
    public double getXVelocity(){
        return this.velocity.getX();
    }
    public double getYVelocity(){
        return this.velocity.getY();
    }
    public void setXPos(double xPos){
        this.setCenterX(xPos);
    }
    public void setYPos(double yPos){
        this.setCenterY(yPos);
    }
    public void setXVelocity(double velocity){
        this.velocity.setX(velocity);
    }
    public void setYVelocity(double velocity){
        this.velocity.setY(velocity);
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setWeight(double weight) {
        this.setRadius(weight);
    }
    public void setColor(Color color){
        this.setFill(color);
    }

    public void negateXVelocity(){this.velocity.setX(this.getXVelocity() *-1);}
    public void negateYVelocity(){this.velocity.setY(this.getYVelocity() *-1);}

    @Override
    public String toString() {
        return "'Particle'{" +
                "'weight':" + this.getRadius() +
                ", 'xPos':" + getYPos() +
                ", 'yPos':" + getXPos() +
                '}';
    }
}
