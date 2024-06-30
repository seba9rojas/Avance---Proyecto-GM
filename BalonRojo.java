package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

class BalonRojo extends ObjetoBalon {
    public BalonRojo(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalBalonesGenerados, float velocidadBalon) {
        super(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon, velocidadBalon, velocidadBalon);
    }

    @Override
    protected int getTipoBalon() {
        return 1;
    }
    @Override
    public boolean procesarColision(Arco arco, Sound dropSound, EstadoJuego estadoJuego) {
        if (!estadoJuego.isInvencible()) {
            arco.dañar();
            if (arco.getVidas() <= 0) {
                return false;
            }
        }
        return true;
    }
}
