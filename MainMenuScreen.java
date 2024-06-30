package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {

    final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Texture startButtonTexture;
    private float buttonY;

    private Arco arco;

    public MainMenuScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        backgroundTexture = new Texture("background.png");
        startButtonTexture = new Texture("botonstart.png");

        buttonY = camera.viewportHeight / 2 - 150;

        // Creación del Arco utilizando Builder
        Texture arcoTexture = new Texture("arcoFutbol.png");
        Sound arcoSound = game.getSound();
        arco = new Arco.Builder()
                .setBucketImage(arcoTexture)
                .setSonidoHerido(arcoSound)
                .setVidas(3)
                .setPuntos(0)
                .setVelx(250)
                .setTiempoHeridoMax(40)
                .build();
        
        arco.crear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(backgroundTexture, 0, 0, camera.viewportWidth, camera.viewportHeight);
        batch.draw(startButtonTexture, camera.viewportWidth / 2 - 100, buttonY, 200, 50);

        font.getData().setScale(2, 2);
        font.draw(batch, "Bienvenido a Recolecta Balones!!! ", 185, camera.viewportHeight / 2 + 60);
        font.draw(batch, "Toca en cualquier lugar para comenzar!", 145, camera.viewportHeight / 2 - 30);

        batch.draw(arco.getBucketImage(), 800 / 2 - 32 / 2, 20, 50, 50); // Ejemplo de dibujo de Arco

        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {
        // No se requiere implementación
    }

    @Override
    public void resize(int width, int height) {
        // No se requiere implementación
    }

    @Override
    public void pause() {
        // No se requiere implementación
    }

    @Override
    public void resume() {
        // No se requiere implementación
    }
    
    @Override
    public void hide() {
        // No se requiere implementación
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        startButtonTexture.dispose();
        arco.destruir();
    }
}
