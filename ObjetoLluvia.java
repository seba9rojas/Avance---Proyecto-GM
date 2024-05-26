package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

abstract class ObjetoLluvia {
    protected Array<Rectangle> rainDropsPos;
    protected Array<Integer> rainDropsType;
    protected long lastDropTime;
    protected int totalGotasGeneradas;
    protected float velocidadGota;

    public ObjetoLluvia(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalGotasGeneradas, float velocidadGota) {
        this.rainDropsPos = rainDropsPos;
        this.rainDropsType = rainDropsType;
        this.lastDropTime = lastDropTime;
        this.totalGotasGeneradas = totalGotasGeneradas;
        this.velocidadGota = velocidadGota;
    }

    public abstract void crearGotaDeLluvia();
    public abstract boolean procesarColision(Tarro tarro, Sound dropSound, EstadoJuego estadoJuego);
}
