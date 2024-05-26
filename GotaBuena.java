package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class GotaBuena extends ObjetoLluvia {
    public GotaBuena(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalGotasGeneradas, float velocidadGota) {
        super(rainDropsPos, rainDropsType, lastDropTime, totalGotasGeneradas, velocidadGota);
    }

    @Override
    public void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);
        rainDropsType.add(2);
    }

    @Override
    public boolean procesarColision(Tarro tarro, Sound dropSound, EstadoJuego estadoJuego) {
        tarro.sumarPuntos(10);
        dropSound.play();
        return true;
    }
}
