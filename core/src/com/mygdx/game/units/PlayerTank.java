package com.mygdx.game.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.TankGame;
import com.mygdx.game.Weapon;
import com.mygdx.game.utils.Direction;
import com.mygdx.game.utils.TankOwner;

public class PlayerTank extends Tank {
    int lifes;

    public PlayerTank(GameScreen game, TextureAtlas atlas) {
        super(game);
        this.weapon = new Weapon();
        this.texture = atlas.findRegion("tank");
        this.position = new Vector2(100, 100);
        this.speed = 100;
        this.widght = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.textureHp = new Texture("HP.png");
        this.circle = new Circle(position.x, position.y, (height + widght) / 2);
        this.lifes = 5;
        this.ownerType = TankOwner.PLAYER;
    }

    @Override
    public void destroy() {
        lifes--;
        hp = hpMax;
    }

    public void update(float dt) {
        checkMovement(dt);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fire(dt);
        }
        super.update(dt);
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move(Direction.LEFT, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(Direction.RIGHT, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(Direction.UP, dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(Direction.DOWN, dt);
        }
    }
}
