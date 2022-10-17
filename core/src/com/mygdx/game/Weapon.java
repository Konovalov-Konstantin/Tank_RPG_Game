package com.mygdx.game;

public class Weapon {
    private float firePeriod;
    private int damage;

    public float getFirePeriod() {
        return firePeriod;
    }

    public int getDamage() {
        return damage;
    }

    public Weapon() {
        this.damage = 1;
        this.firePeriod = 0.5f;   // промежуток времени между выстрелами
    }
}
