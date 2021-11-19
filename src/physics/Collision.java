package physics;

import models.Particle;
public abstract class Collision {

    public static boolean isColliding(Particle particle1, Particle particle2){
        return Vector2.distance(particle1.getPosVector(), particle2.getPosVector()) <= particle1.getRadius() + particle2.getRadius();
    }
    public static void resolveCollision(Particle particle1, Particle particle2){
        Vector2 tangentVector = new Vector2();
        tangentVector.setY(-(particle2.getXPos() - particle1.getXPos()));
        tangentVector.setX(particle2.getYPos() - particle1.getYPos());
        tangentVector.normalize();
        Vector2 relativeVelocity = new Vector2(particle1.getXVelocity() - particle2.getXVelocity(), particle1.getYVelocity() - particle2.getYVelocity());
        double length = Vector2.dotProduct(relativeVelocity, tangentVector);
        if(length > 0){
            return;
        }
        Vector2 velocityComponentOnTangent = Vector2.multiply(tangentVector, length);
        Vector2 velocityComponentPerpendicularToTangent = Vector2.subtract(relativeVelocity, velocityComponentOnTangent);

        particle1.setXVelocity(particle1.getXVelocity() - velocityComponentPerpendicularToTangent.getX());
        particle1.setYVelocity(particle1.getYVelocity() - velocityComponentPerpendicularToTangent.getY());

        particle2.setXVelocity(particle2.getXVelocity() + velocityComponentPerpendicularToTangent.getX());
        particle2.setYVelocity(particle2.getYVelocity() + velocityComponentPerpendicularToTangent.getY());
    }

    public static boolean isVerticalColliding(Particle particle, double boundary){
        return particle.getYPos() + particle.getYVelocity() > boundary - particle.getRadius() || particle.getYPos() + particle.getYVelocity() < particle.getRadius();
    }

    public static boolean isHorizontalColliding(Particle particle, double boundary){
        return particle.getXPos() + particle.getXVelocity() > boundary - particle.getRadius() || particle.getXPos() + particle.getXVelocity() < particle.getRadius();
    }
}
