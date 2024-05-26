package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

class GotaDorada extends ObjetoLluvia {
    public GotaDorada(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalGotasGeneradas, float velocidadGota) {
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
        rainDropsType.add(3);
    }


    @Override
    public boolean procesarColision(Tarro tarro, Sound dropSound, EstadoJuego estadoJuego) {
        tarro.aumentarVida();
        tarro.sumarPuntos(100);
        dropSound.play();
        estadoJuego.invencible = true;
        estadoJuego.tiempoInvencibilidad = TimeUtils.nanoTime();
        return true;
    }
}
