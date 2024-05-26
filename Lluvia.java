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

public class Lluvia {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaDorada;
    private Texture escudoTexture;
    private Texture snowball;
    private Texture snowflake;
    private Texture gotaVerde;
    private Texture iconoVerde;
    private Sound dropSound;
    private Music rainMusic;
    private EstadoJuego estadoJuego;
    private int totalGotasGeneradas;
    private float velocidadGota;
    private static final long DURACION_TRANSFORMACION = 3000000000L; // 3 segundos

    public int getTotalGotasGeneradas() {
        return totalGotasGeneradas;
    }

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaDorada, Texture escudoTexture, Texture snowball, Texture snowflake, Texture gotaVerde, Texture iconoVerde, Sound ss, Music mm) {
        rainMusic = mm;
        dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaDorada = gotaDorada;
        this.escudoTexture = escudoTexture;
        this.snowball = snowball;
        this.snowflake = snowflake;
        this.gotaVerde = gotaVerde;
        this.iconoVerde = iconoVerde;
        this.estadoJuego = new EstadoJuego();
        totalGotasGeneradas = 0;
        velocidadGota = 300;
    }

    public void crear() {
        rainDropsPos = new Array<Rectangle>();
        rainDropsType = new Array<Integer>();
        totalGotasGeneradas = 0;
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private int determinarTipoGota() {
        int nivelActual = totalGotasGeneradas / 100 + 1;
        float probabilidadGotasBuenas = 50;
        float probabilidadGotasMalas = 46;
        for (int i = 0; i < nivelActual - 1; i++) {
            probabilidadGotasBuenas -= 2;
            probabilidadGotasMalas += 2;
        }
        float tipoGotaFloat = MathUtils.random(1, 100);
        if (tipoGotaFloat <= probabilidadGotasMalas)
            return 1;
        else if (tipoGotaFloat <= probabilidadGotasMalas + probabilidadGotasBuenas)
            return 2;
        else if (tipoGotaFloat <= probabilidadGotasMalas + probabilidadGotasBuenas + 1)
            return 3;
        else if (tipoGotaFloat <= probabilidadGotasMalas + probabilidadGotasBuenas + 2)
            return 4;
        else
            return 5;
    }

    private void crearGotaDeLluvia() {
        int tipoGota = determinarTipoGota();
        ObjetoLluvia nuevaGota = null;
        switch (tipoGota) {
            case 1:
                nuevaGota = new GotaMala(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                break;
            case 2:
                nuevaGota = new GotaBuena(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                break;
            case 3:
                nuevaGota = new GotaDorada(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                break;
            case 4:
                nuevaGota = new BolaDeHielo(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                break;
            case 5:
                nuevaGota = new GotaVerde(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                break;
            default:
                break;
        }

        if (nuevaGota != null) {
            nuevaGota.crearGotaDeLluvia();
        }

        lastDropTime = TimeUtils.nanoTime();
        totalGotasGeneradas++;

        int nivelActual = totalGotasGeneradas / 100 + 1;
        if (totalGotasGeneradas % 250 == 0 && nivelActual < 15) {
            nivelActual++;
            if (nivelActual >= 2) {
                float variabilidad = nivelActual * 50;
                velocidadGota = MathUtils.random(250 - variabilidad, 350 + variabilidad);
            }
        }
    }

    public float getVelocidadGota() {
        return velocidadGota;
    }

    public boolean actualizarMovimiento(Tarro tarro) {
        float frecuenciaCreacion = 70 - totalGotasGeneradas / 100;
        if (frecuenciaCreacion < 10) {
            frecuenciaCreacion = 10;
        }
        if (TimeUtils.nanoTime() - lastDropTime > frecuenciaCreacion * 1000000) {
            crearGotaDeLluvia();
        }

        if (TimeUtils.nanoTime() - lastDropTime > 60000000) crearGotaDeLluvia();

        if (estadoJuego.invencible && TimeUtils.nanoTime() - estadoJuego.tiempoInvencibilidad > 3000000000L) {
            estadoJuego.invencible = false;
        }

        if (estadoJuego.tarroLento && TimeUtils.nanoTime() - estadoJuego.tiempoInicioLentitud > 4000000000L) {
            estadoJuego.tarroLento = false;
            tarro.setVelocidad(250);
        }

        if (estadoJuego.gotasTransformadas && TimeUtils.nanoTime() - estadoJuego.tiempoTransformacion > DURACION_TRANSFORMACION) {
            estadoJuego.gotasTransformadas = false;
        }

        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();

            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                continue;
            }

            ObjetoLluvia gota = null;
            switch (rainDropsType.get(i)) {
                case 1:
                    gota = new GotaMala(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                    break;
                case 2:
                    gota = new GotaBuena(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                    break;
                case 3:
                    gota = new GotaDorada(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                    break;
                case 4:
                    gota = new BolaDeHielo(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                    break;
                case 5:
                    gota = new GotaVerde(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
                    break;
            }
            
            if (raindrop.overlaps(tarro.getArea())) {
                if (gota != null) {
                    boolean colision = gota.procesarColision(tarro, dropSound, estadoJuego);
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


    public void dibujar(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (rainDropsType.get(i) == 1) {
                batch.draw(gotaMala, raindrop.x, raindrop.y, 23, 27);
            } else if (rainDropsType.get(i) == 2) {
                if (estadoJuego.gotasTransformadas) {
                    batch.draw(gotaMala, raindrop.x, raindrop.y, 23, 27);
                } else {
                    batch.draw(gotaBuena, raindrop.x, raindrop.y, 22, 22);
                }
            } else if (rainDropsType.get(i) == 3) {
                batch.draw(gotaDorada, raindrop.x, raindrop.y, 12, 22);
            } else if (rainDropsType.get(i) == 4) {
                batch.draw(snowball, raindrop.x, raindrop.y, 32, 32);
            } else if (rainDropsType.get(i) == 5) { 
                batch.draw(gotaVerde, raindrop.x, raindrop.y, 22, 22);
            }
        }

        if (estadoJuego.invencible) {
            batch.draw(escudoTexture, 368, 380, 64, 64);
        }
        if (estadoJuego.tarroLento) {
            batch.draw(snowflake, 368 - 80, 380, 110, 64);
        }
        if (estadoJuego.gotasTransformadas) {
            batch.draw(iconoVerde, 368 + 80, 380, 64, 64);
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
        gotaBuena.dispose();
        gotaMala.dispose();
        gotaDorada.dispose();
        escudoTexture.dispose();
        snowball.dispose();
        snowflake.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
    public void continuar() {
        rainMusic.play();
    }
}
