package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.units.Tank;

public class Bullet {
    private Tank owner;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private int damage;

    public boolean isActive() {
        return active;
    }

    public Tank getOwner() {
        return owner;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getDamage() {
        return damage;
    }

    public Bullet() {
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
        this.damage = 0;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate(Tank owner, float x, float y, float vx, float vy, int damage) {
        this.active = true;
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.damage = damage;
        this.owner = owner;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < 0 || position.x > Gdx.graphics.getWidth() || position.y < 0 || position.y > Gdx.graphics.getHeight()) {
            deactivate();
        }
    }
}
