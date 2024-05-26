package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

class GotaVerde extends ObjetoLluvia {
    public GotaVerde(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalGotasGeneradas, float velocidadGota) {
        super(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
    }

    @Override
    public void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 32;
        raindrop.height = 32;
        rainDropsPos.add(raindrop);
        rainDropsType.add(5);
    }


    @Override
    public boolean procesarColision(Tarro tarro, Sound dropSound, EstadoJuego estadoJuego) {
        estadoJuego.gotasTransformadas = true;
        estadoJuego.tiempoTransformacion = TimeUtils.nanoTime();
        // Cambiar gotas buenas transformadas a gotas malas
        for (int j = 0; j < rainDropsType.size; j++) {
            if (rainDropsType.get(j) == 2) { // Si es una gota buena
                rainDropsType.set(j, 1); // Cambiar a gota mala
            }
        }
        dropSound.play();
        return true;
    }
}
