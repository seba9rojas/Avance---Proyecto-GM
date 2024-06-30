package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

class BalonHielo extends ObjetoBalon {
    public BalonHielo(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalBalonesGenerados, float velocidadBalon) {
        super(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon, velocidadBalon, velocidadBalon);
    }

    @Override
    protected int getTipoBalon() {
        return 4;
    }
    @Override
    public boolean procesarColision(Arco arco, Sound dropSound, EstadoJuego estadoJuego) {
        estadoJuego.setArcoLento(true);
        estadoJuego.setTiempoInicioLentitud(TimeUtils.nanoTime());
        arco.setVelocidad(100);
        dropSound.play();
        return true;
    }
}
