package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.TankOwner;

public abstract class Tank {
    GameScreen gameScreen;
    Weapon weapon;
    TextureRegion texture;
    Vector2 position;
    Vector2 tmp;
    float speed;
    float angle;
    float fireTimer;
    int widght;
    int height;
    int hp;
    int hpMax;
    Texture textureHp;
    TankOwner ownerType;
    Circle circle;


    public float getAngle() {
        return angle;
    }

    public TankOwner getOwnerType() {
        return ownerType;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getCircle() {
        return circle;
    }

    public Tank(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.tmp = new Vector2(0,0);
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            destroy();
        }
    }

    public abstract void destroy();

    public void update(float dt) {
        fireTimer += dt;
        if (position.x < 0) {
            position.x = Gdx.graphics.getWidth();
        }
        if (position.x > Gdx.graphics.getWidth()) {
            position.x = 0;
        }
        if (position.y < 0) {
            position.y = Gdx.graphics.getHeight();
        }
        if (position.y > Gdx.graphics.getHeight()) {
            position.y = 0;
        }
        circle.setPosition(position);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - widght / 2, position.y - height / 2, widght / 2, height / 2,
                widght, height, 1, 1, angle);

        batch.setColor(0, 0,0, 1);
        batch.draw(textureHp, position.x - widght / 2 + 5, position.y + height / 2 + 13, 40, 8);
        batch.setColor(0, 1, 0, 1);
        batch.draw(textureHp, position.x - widght / 2 + 5, position.y + height / 2 + 13,
                ((float) hp / hpMax) * 40, 8);
        batch.setColor(1, 1, 1, 1);

    }

    public void move(Direction direction, float dt) {
        tmp.set(position);
        tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        if(gameScreen.getMap().isAreaClear(tmp.x, tmp.y, height/2)) { // проверка на столкновения со стенами
            angle = direction.getAngle();
            position.set(tmp);
        }
    }
    public void fire(float dt) {
        if (fireTimer >= weapon.getFirePeriod()) {
            fireTimer = 0;
            float angleRad = (float) Math.toRadians(angle);
            gameScreen.getBulletEmitter().activate(this, position.x, position.y,
                    700 * (float) Math.cos(angleRad), 700 * (float) Math.sin(angleRad), weapon.getDamage());
        }
    }
}
