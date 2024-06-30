package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

class BalonVeneno extends ObjetoBalon {
    public BalonVeneno(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalBalonesGenerados, float velocidadBalon) {
        super(rainDropsPos, rainDropsType, lastDropTime, totalBalonesGenerados, velocidadBalon, velocidadBalon, velocidadBalon);
    }
    
    @Override
    protected int getTipoBalon() {
        return 5;
    }
    
    @Override
    public boolean procesarColision(Arco arco, Sound dropSound, EstadoJuego estadoJuego) {
        estadoJuego.setBalonesTransformados(true);
        estadoJuego.setTiempoTransformacion(TimeUtils.nanoTime());
        for (int j = 0; j < rainDropsType.size; j++) {
            if (rainDropsType.get(j) == 2) {
                rainDropsType.set(j, 1);
            }
        }
        dropSound.play();
        return true;
    }
}
