package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Balones implements Dibujable {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture balonNormal;
    private Texture balonRojo;
    private Texture balonDorado;
    private Texture escudoTexture;
    private Texture balonHielo;
    private Texture snowflake;
    private Texture balonVeneno;
    private Texture iconoVerde;
    private Sound dropSound;
    private Music rainMusic;
    private EstadoJuego estadoJuego;
    private int totalBalonesGenerados;
    private float velocidadBalon;
    private static final long DURACION_TRANSFORMACION = 3000000000L;
    private TipoBalonStrategy tipoBalonStrategy;
    
    public int getTotalGotasGeneradas() {
        return totalBalonesGenerados;
    }

    public Balones(Texture balonNormal, Texture balonRojo, Texture balonDorado, Texture escudoTexture, Texture balonHielo, Texture snowflake, Texture balonVeneno, Texture iconoVerde, Sound ss, Music mm) {
    	rainMusic = mm;
        dropSound = ss;
        this.balonNormal = balonNormal;
        this.balonRojo = balonRojo;
        this.balonDorado = balonDorado;
        this.escudoTexture = escudoTexture;
        this.balonHielo = balonHielo;
        this.snowflake = snowflake;
        this.balonVeneno = balonVeneno;
        this.iconoVerde = iconoVerde;
        this.estadoJuego = new EstadoJuego();
        totalBalonesGenerados = 0;
        velocidadBalon = 300;

        tipoBalonStrategy = new Estrategia1();
    }

    public void crear() {
        rainDropsPos = new Array<Rectangle>();
        rainDropsType = new Array<Integer>();
        totalBalonesGenerados = 0;
        crearBalon();
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    
    private void seleccionarEstrategia(int nivelActual) {
        if (nivelActual == 4 || nivelActual == 7 || nivelActual == 10 || nivelActual == 13 || nivelActual == 15) {
            int estrategiaSeleccionada = MathUtils.random(1, 3);
            switch (estrategiaSeleccionada) {
                case 2:
                    tipoBalonStrategy = new Estrategia2();
                    break;
                case 3:
                    tipoBalonStrategy = new Estrategia3();
                    break;
                default:
                    tipoBalonStrategy = new Estrategia1();
                    break;
            }
        }
    }



    private void crearBalon() {
        int tipoBalon = tipoBalonStrategy.determinarTipoBalon(totalBalonesGenerados, totalBalonesGenerados / 100 + 1);
        ObjetoBalon nuevoBalon = null;
        switch (tipoBalon) {
            case 1:
                nuevoBalon = new BalonRojo(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                break;
            case 2:
                nuevoBalon = new BalonNormal(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                break;
            case 3:
                nuevoBalon = new BalonDorado(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                break;
            case 4:
                nuevoBalon = new BalonHielo(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                break;
            case 5:
                nuevoBalon = new BalonVeneno(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                break;
            default:
                break;
        }

        if (nuevoBalon != null) {
            nuevoBalon.crearBalon();
        }

        lastDropTime = TimeUtils.nanoTime();
        totalBalonesGenerados++;

        int nivelActual = totalBalonesGenerados / 100 + 1;
        seleccionarEstrategia(nivelActual);

        if (totalBalonesGenerados % 250 == 0 && nivelActual < 15) {
            nivelActual++;
            if (nivelActual >= 2) {
                float variabilidad = nivelActual * 50;
                velocidadBalon = MathUtils.random(250 - variabilidad, 350 + variabilidad);
            }
        }
    }

    public float getVelocidadBalon() {
        return velocidadBalon;
    }

    public boolean actualizarMovimiento(Arco arco) {
        float frecuenciaCreacion = 70 - totalBalonesGenerados / 100;
        if (frecuenciaCreacion < 10) {
            frecuenciaCreacion = 10;
        }
        if (TimeUtils.nanoTime() - lastDropTime > frecuenciaCreacion * 1000000) {
            crearBalon();
        }

        if (TimeUtils.nanoTime() - lastDropTime > 60000000) crearBalon();

        if (estadoJuego.isInvencible() && TimeUtils.nanoTime() - estadoJuego.getTiempoInvencibilidad() > 3000000000L) {
            estadoJuego.setInvencible(false);
        }

        if (estadoJuego.isArcoLento() && TimeUtils.nanoTime() - estadoJuego.getTiempoInicioLentitud() > 4000000000L) {
            estadoJuego.setArcoLento(false);
            arco.setVelocidad(250);
        }

        if (estadoJuego.isBalonesTransformados() && TimeUtils.nanoTime() - estadoJuego.getTiempoTransformacion() > DURACION_TRANSFORMACION) {
            estadoJuego.setBalonesTransformados(false);
        }

        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();

            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                continue;
            }

            ObjetoBalon balon = null;
            switch (rainDropsType.get(i)) {
                case 1:
                    balon = new BalonRojo(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                    break;
                case 2:
                    balon = new BalonNormal(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                    break;
                case 3:
                    balon = new BalonDorado(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                    break;
                case 4:
                    balon = new BalonHielo(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                    break;
                case 5:
                    balon = new BalonVeneno(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon);
                    break;
            }

            if (raindrop.overlaps(arco.getArea())) {
                if (balon != null) {
                    boolean colision = balon.procesarColision(arco, dropSound, estadoJuego);
                    if (!colision) {
                        return false;
                    }
                }
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                continue;
            }
        }
        
        return true;
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (rainDropsType.get(i) == 1) {
                batch.draw(balonRojo, raindrop.x, raindrop.y, 22, 22);
            } else if (rainDropsType.get(i) == 2) {
                if (estadoJuego.isBalonesTransformados()) {
                    batch.draw(balonRojo, raindrop.x, raindrop.y, 22, 22);
                } else {
                    batch.draw(balonNormal, raindrop.x, raindrop.y, 22, 22);
                }
            } else if (rainDropsType.get(i) == 3) {
                batch.draw(balonDorado, raindrop.x, raindrop.y, 22, 22);
            } else if (rainDropsType.get(i) == 4) {
                batch.draw(balonHielo, raindrop.x, raindrop.y, 22, 22);
            } else if (rainDropsType.get(i) == 5) {
                batch.draw(balonVeneno, raindrop.x, raindrop.y, 22, 22);
            }
        }
        
        if (estadoJuego.isInvencible()) {
            batch.draw(escudoTexture, 368, 380, 64, 64);
        }
        if (estadoJuego.isArcoLento()) {
            batch.draw(snowflake, 368 - 80, 380, 110, 64);
        }
        if (estadoJuego.isBalonesTransformados()) {
            batch.draw(iconoVerde, 368 + 80, 380, 64, 64);
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
        balonNormal.dispose();
        balonRojo.dispose();
        balonDorado.dispose();
        escudoTexture.dispose();
        balonHielo.dispose();
        snowflake.dispose();
    }
    

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
