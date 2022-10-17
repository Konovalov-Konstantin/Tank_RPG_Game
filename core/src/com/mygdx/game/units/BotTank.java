package com.mygdx.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.TankOwner;

public class BotTank extends Tank {

    Direction preferredDirection;
    private float aiTimer;
    private float aiTimerTo;
    boolean active;
    float pursuitRadius;
    Vector3 lastPosition;

    public boolean isActive() {
        return active;
    }

    public BotTank(GameScreen game, TextureAtlas atlas) {
        super(game);
        this.weapon = new Weapon();
        this.texture = atlas.findRegion("BotTank");
        this.position = new Vector2(500, 500);
        this.speed = 100;
        this.widght = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 4;
        this.hp = this.hpMax;
        this.aiTimerTo = 3;
        this.preferredDirection = Direction.UP;
        this.textureHp = new Texture("HP.png");
        this.circle = new Circle(position.x, position.y, (height + widght) / 2);
        this.pursuitRadius = 300;
        this.ownerType = TankOwner.AI;
        this.lastPosition = new Vector3(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void destroy() {
        active = false;
    }

    public void activate(float x, float y) {
        hpMax = 4;
        hp = hpMax;
        preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
        angle = preferredDirection.getAngle();
        position.set(x, y);
        aiTimer = 0;
        active = true;
    }

    public void update(float dt) {
        aiTimer += dt;
        if (aiTimer >= aiTimerTo) {
            aiTimer = 0;
            aiTimerTo = MathUtils.random(2, 6);
            preferredDirection = Direction.values()[MathUtils.random(0, Direction.values().length - 1)];
            angle = preferredDirection.getAngle();
        }
        move(preferredDirection, dt);
        float dst = this.position.dst(gameScreen.getPlayer().getPosition());
        if (dst < pursuitRadius) {
            fire(dt);
        }

        if (Math.abs(position.x - lastPosition.x) < 0.5 && Math.abs(position.y - lastPosition.y) < 0.5) {
            lastPosition.z += dt;
            if (lastPosition.z > 0.25) {
                aiTimer += 10;
            }
        } else {
            lastPosition.x = position.x;
            lastPosition.y = position.y;
            lastPosition.z = 0;
        }
        super.update(dt);
    }
}
