package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class BalonNormal extends ObjetoBalon {
    public BalonNormal(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalBalonesGenerados, float velocidadBalon) {
        super(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon, velocidadBalon, velocidadBalon);
    }

    @Override
    protected int getTipoBalon() { return 2; }

    @Override
    public boolean procesarColision(Arco tarro, Sound dropSound, EstadoJuego estadoJuego) {
    	tarro.sumarPuntos(10);
        dropSound.play();
        return true;
    }
}
