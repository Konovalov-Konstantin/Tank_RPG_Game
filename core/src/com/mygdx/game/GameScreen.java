package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.units.BotTank;
import com.mygdx.game.units.PlayerTank;
import com.mygdx.game.units.Tank;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Map map;
    private PlayerTank player;
    private BulletEmitter bulletEmitter;
    private BotEmitter botEmitter;
    private float gameTimer;
    private static final boolean FRIENDLY_FIRE = true;

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public PlayerTank getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }

    public GameScreen(SpriteBatch batch){
        this.batch = batch;
    }

    @Override
    public void show() {
        TextureAtlas atlas = new TextureAtlas("game.pack");
        map = new Map(atlas);
        player = new PlayerTank(this, atlas);
        bulletEmitter = new BulletEmitter(atlas);
        botEmitter = new BotEmitter(this, atlas);
        gameTimer = 6;
    }

    public void update(float dt) {
        gameTimer += dt;
        if (gameTimer > 5) {    // частота появления ботов
            gameTimer = 0;

            float coordX,coordY;
            do{
                coordX = MathUtils.random(0, Gdx.graphics.getWidth());
                coordY = MathUtils.random(0, Gdx.graphics.getHeight());
            } while (!map.isAreaClear(coordX,coordY, 25));

            botEmitter.activate(coordX,coordY);
        }
        player.update(dt);
        botEmitter.update(dt);
        bulletEmitter.update(dt);
        checkCollisions();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletEmitter.getBullets().length; i++) {
            Bullet bullet = bulletEmitter.getBullets()[i];
            if(bullet.isActive()) {
                for (int j = 0; j < botEmitter.getBots().length; j++) {
                    BotTank bot = botEmitter.getBots()[j];
                    if (bot.isActive()) {
                        if (checkBulletAndTank(bot,bullet) && bot.getCircle().contains(bullet.getPosition())) {
                            bullet.deactivate();
                            bot.takeDamage(bullet.getDamage());
                            break;
                        }
                    }
                }
                if (checkBulletAndTank(player, bullet) && player.getCircle().contains(bullet.getPosition())) {
                    bullet.deactivate();
                    player.takeDamage(bullet.getDamage());
                }
                map.checkWallANdBulletCollision(bullet);
            }
        }
    }

    public boolean checkBulletAndTank (Tank tank, Bullet bullet){
        if(!FRIENDLY_FIRE) {
            return tank.getOwnerType() != bullet.getOwner().getOwnerType();
        } else {
            return tank != bullet.getOwner();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0, 0.6f, 0, 1);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bulletEmitter.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
