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
    private boolean invencible;
    private long tiempoInvencibilidad;
    private boolean tarroLento = false;
    private long tiempoInicioLentitud;
    private int totalGotasGeneradas;
    private float velocidadGota;
    private boolean gotasTransformadas = false;
    private long tiempoTransformacion;
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
        invencible = false;
        tiempoInvencibilidad = 0;
        tarroLento = false;
        tiempoInicioLentitud = 0;
        gotasTransformadas = false;
        tiempoTransformacion = 0;
        totalGotasGeneradas = 0;
        velocidadGota = 300;
    }

    public void crear() {
        rainDropsPos = new Array<Rectangle>();
        rainDropsType = new Array<Integer>();
        totalGotasGeneradas = 0;
        crearGotaDeLluvia();
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    

    private void crearGotaDeLluvia() {
        Rectangle raindrop;
        boolean posicionValida;
        
        
        do {
            raindrop = new Rectangle();
            raindrop.x = MathUtils.random(0, 800 - 32);
            raindrop.y = 480;
            raindrop.width = 32;
            raindrop.height = 32;

            posicionValida = true;
            for (Rectangle existingRaindrop : rainDropsPos) {
                if (existingRaindrop.y < 480 - 100 && existingRaindrop.y > raindrop.y - 32) {
                    if (Math.abs(existingRaindrop.x - raindrop.x) < 250) {
                        posicionValida = false;
                        break;
                    }
                }
            }
        } while (!posicionValida);

        rainDropsPos.add(raindrop);

        int tipoGota;
        int nivelActual = totalGotasGeneradas / 100 + 1;

        // Ajustar la probabilidad de las gotas en función del nivel
        float probabilidadGotasBuenas = 50;
        float probabilidadGotasMalas = 45;
        for (int i = 0; i < nivelActual - 1; i++) {
            probabilidadGotasBuenas -= 2;
            probabilidadGotasMalas += 2;
        }

        float tipoGotaFloat = MathUtils.random(1, 100);
        if (tipoGotaFloat <= probabilidadGotasMalas)
            tipoGota = 1; // gota mala
        else if (tipoGotaFloat <= probabilidadGotasMalas + probabilidadGotasBuenas)
            tipoGota = 2; // gota buena
        else if (tipoGotaFloat <= probabilidadGotasMalas + probabilidadGotasBuenas + 1)
            tipoGota = 3; // gota dorada
        else if (tipoGotaFloat <= probabilidadGotasMalas + probabilidadGotasBuenas + 2)
            tipoGota = 4; // bola de nieve
        else
            tipoGota = 5; // gota verde

        rainDropsType.add(tipoGota);

        lastDropTime = TimeUtils.nanoTime();
        totalGotasGeneradas++;

        if (totalGotasGeneradas % 250 == 0 && nivelActual < 15) {
            nivelActual++;
            // Ajustar la velocidad de la gota en función del nivel
            if (nivelActual >= 2) {
                // Aumentar la variabilidad en la velocidad de caída de las gotas
                float variabilidad = nivelActual * 50;
                velocidadGota = MathUtils.random(250 - variabilidad, 350 + variabilidad);
            }
        }
    }
    
    public float getVelocidadGota() {
        return velocidadGota;
    }

    public boolean actualizarMovimiento(Tarro tarro) {
    	float frecuenciaCreacion = 70 - totalGotasGeneradas / 100; // Frecuencia inicial - incremento por nivel
        if (frecuenciaCreacion < 10) {
            frecuenciaCreacion = 10; // Limitar la frecuencia mínima de creación de gotas
        }
        if (TimeUtils.nanoTime() - lastDropTime > frecuenciaCreacion * 1000000) {
            crearGotaDeLluvia();
        }
    	
    	if (TimeUtils.nanoTime() - lastDropTime > 60000000) crearGotaDeLluvia();

        if (invencible && TimeUtils.nanoTime() - tiempoInvencibilidad > 3000000000L) {	
            invencible = false;
        }

        if (tarroLento && TimeUtils.nanoTime() - tiempoInicioLentitud > 4000000000L) {
            tarroLento = false;
            tarro.setVelocidad(250);
        }

        if (gotasTransformadas && TimeUtils.nanoTime() - tiempoTransformacion > DURACION_TRANSFORMACION) {
            gotasTransformadas = false;
        }
        

        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();

            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
            }

            if (raindrop.overlaps(tarro.getArea())) {
                if (rainDropsType.get(i) == 1) {
                    if (!invencible) {
                        tarro.dañar();
                        if (tarro.getVidas() <= 0) return false;
                    }
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else if (rainDropsType.get(i) == 2) {
                    tarro.sumarPuntos(10);
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else if (rainDropsType.get(i) == 3) {
                	tarro.aumentarVida();
                	tarro.sumarPuntos(100);
                    dropSound.play();
                    invencible = true;
                    tiempoInvencibilidad = TimeUtils.nanoTime();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else if (rainDropsType.get(i) == 4) {
                    tarroLento = true;
                    tiempoInicioLentitud = TimeUtils.nanoTime();
                    tarro.setVelocidad(100);
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else if (rainDropsType.get(i) == 5) {
                    gotasTransformadas = true;
                    tiempoTransformacion = TimeUtils.nanoTime();
                    // Cambiar gotas buenas transformadas a gotas malas
                    for (int j = 0; j < rainDropsType.size; j++) {
                        if (rainDropsType.get(j) == 2) { // Si es una gota buena
                            rainDropsType.set(j, 1); // Cambiar a gota mala
                        }
                    }
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
            }
        }

        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (rainDropsType.get(i) == 1) {
                batch.draw(gotaMala, raindrop.x, raindrop.y, 23, 27);
            } else if (rainDropsType.get(i) == 2) {
                if (gotasTransformadas) {
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

        if (invencible) {
            batch.draw(escudoTexture, 368, 380, 64, 64);
        }
        if (tarroLento) {
            batch.draw(snowflake, 368 - 80, 380, 110, 64);
        }
        if (gotasTransformadas) {
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
