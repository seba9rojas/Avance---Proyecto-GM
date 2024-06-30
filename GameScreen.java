package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Arco arco;
    private Balones balones;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        // Cargar texturas y sonidos
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        Texture bucketTexture = new Texture(Gdx.files.internal("arcoFutbol.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Texture balonNormal = new Texture(Gdx.files.internal("balonNormal.png"));
        Texture balonRojo = new Texture(Gdx.files.internal("balonRojo.png"));
        Texture balonDorado = new Texture(Gdx.files.internal("balonDorado.png"));
        Texture escudoTexture = new Texture(Gdx.files.internal("escudo.png"));
        Texture balonHielo = new Texture(Gdx.files.internal("balonHielo.png"));
        Texture snowflake = new Texture(Gdx.files.internal("snowflake.png"));
        Texture balonVeneno = new Texture(Gdx.files.internal("balonVeneno.png"));
        Texture iconoVerde = new Texture(Gdx.files.internal("poisonicon.png"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // Inicializar Arco usando el patrón Builder
        arco = new Arco.Builder()
            .setBucketImage(bucketTexture)
            .setSonidoHerido(hurtSound)
            .setVidas(5)
            .setPuntos(100)
            .setVelx(300)
            .setTiempoHeridoMax(50)
            .build();

        // Inicializar Balones
        balones = new Balones(balonNormal, balonRojo, balonDorado, escudoTexture, balonHielo, snowflake, balonVeneno, iconoVerde, dropSound, rainMusic);

        // Configurar cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // Crear objetos
        arco.crear();
        balones.crear();
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla con color negro
        ScreenUtils.clear(0, 0, 0, 1);

        // Actualizar matrices de la cámara
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Dibujar elementos
        batch.begin();
        
        int nivelActual = balones.getTotalGotasGeneradas() / 100 + 1;
        int nivelMaximo = 15; // Nivel máximo de dificultad
        
        if (nivelActual > nivelMaximo) {
            nivelActual = nivelMaximo;
        }
        
        // Dibujar textos
        font.draw(batch, "Puntos totales: " + arco.getPuntos(), 5, 475);
        font.draw(batch, "Nivel: " + nivelActual, 5, 440);
        font.draw(batch, "Vidas : " + arco.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);
        
        if (!arco.estaHerido()) {
            arco.actualizarMovimiento();
            if (!balones.actualizarMovimiento(arco)) {
                // Actualizar HigherScore
                if (game.getHigherScore() < arco.getPuntos())
                    game.setHigherScore(arco.getPuntos());
                // Ir a la ventana de fin de juego y destruir la actual
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }
        
        arco.dibujar(batch);
        balones.dibujar(batch);
        
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        balones.continuar();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        balones.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        arco.destruir();
        balones.destruir();
    }
}
