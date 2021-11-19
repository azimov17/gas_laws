package models;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import physics.Collision;
import physics.Vector2;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import static java.lang.Integer.min;

public abstract class ParticleSystem {
    protected double volume;
    protected double temperature;
    protected double pressure;

    protected Color color;
    protected Color strokeColor;

    protected ListIterator<Particle> particleIterator;
    protected ArrayList<Particle> particles;

    private final Random random;
    private final int MAX_PARTICLES = 300;

    public ParticleSystem(){
        this.particles = new ArrayList<>();
        this.random = new Random();
        this.color = Color.RED;
        this.strokeColor = Color.BLACK;
        this.volume = 1;
        this.temperature = 1;
        this.pressure = 1;
    }
    public void init(double xBounds, double yBounds){
        for(Particle particle : this.particles) {
            particle.setWeight(10);
            particle.setColor(this.color);
            particle.setStroke(this.strokeColor);
            particle.setVelocity(new Vector2(1,1));
            DropShadow particleShadow = new DropShadow();
            particleShadow.setRadius(particle.getRadius());
            particleShadow.setOffsetX(1);
            particleShadow.setOffsetY(1);
            particle.setEffect(particleShadow);
            particle.setCache(true);

            particle.setXPos(random.nextInt((int) (xBounds - 2 * particle.getRadius())) + particle.getRadius());
            particle.setYPos(random.nextInt((int) (yBounds - 2 * particle.getRadius())) + particle.getRadius());
        }
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }


    public void updateParticlePositions(double xBounds, double yBounds){
        for(Particle particle : this.particles) {
            detectParticleCollision(particle, this.particles);

            particle.setXPos(particle.getXPos() + particle.getXVelocity());
            particle.setYPos(particle.getYPos() + particle.getYVelocity());

            detectBoundsCollision(particle, xBounds, yBounds);

        }
    }

    private void detectBoundsCollision(Particle particle, double xBounds, double yBounds){
        if(Collision.isHorizontalColliding(particle, xBounds)){
            particle.negateXVelocity();
        }
        if(Collision.isVerticalColliding(particle, yBounds)){
            particle.negateYVelocity();
        }
    }

    private void detectParticleCollision(Particle particle, ArrayList<Particle> particles){
        particleIterator = particles.listIterator();
        while(particleIterator.hasNext()){
            Particle otherParticle = particleIterator.next();
            if(Collision.isColliding(particle, otherParticle)){
                Collision.resolveCollision(particle, otherParticle);
            }
        }
    }

    public void add(int numberOfParticles){
        this.particles = new ArrayList<>(MAX_PARTICLES);
        for (int i = 0; i < min(numberOfParticles, MAX_PARTICLES); i++) {
            this.particles.add(new Particle());
        }
    }
    public void setNumberOfParticles(int numberOfParticles){
        if(numberOfParticles > MAX_PARTICLES){ return; }
        if(numberOfParticles < this.particles.size()){
            this.removeParticles(this.particles.size() - numberOfParticles);
        }
        if(numberOfParticles > this.particles.size()){
            this.add(numberOfParticles - this.particles.size());
        }
    }

    public void removeParticles(int numberOfParticlesToRemove){
        int totalRemoved = 0;
        ListIterator<Particle> particleIterator = this.particles.listIterator();
        while(particleIterator.hasNext() && totalRemoved <= numberOfParticlesToRemove){
            particleIterator.remove();
            totalRemoved++;
        }
    }

    @Override
    public String toString() {
        return "'ParticleSystem'{" +
                "\n\t'totalParticles':" + particles.size() +
                ",\n\t'volume':" + volume +
                ",\n\t'temperature':" + temperature +
                ",\n\t'pressure':" + pressure +
                ",\n\t'MAX_PARTICLES':" + MAX_PARTICLES+
                "\n}";
    }


}
