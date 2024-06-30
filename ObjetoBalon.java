package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

abstract class ObjetoBalon {
    protected Array<Rectangle> rainDropsPos;
    protected Array<Integer> rainDropsType;
    protected long lastDropTime;
    protected int totalBalonesGenerados;
    protected float velocidadBalon;
    protected float anchoBalon;
    protected float altoBalon;

    public ObjetoBalon(Array<Rectangle> rainDropsPos, Array<Integer> rainDropsType, long lastDropTime, int totalBalonesGenerados, float velocidadBalon, float anchoBalon, float altoBalon) 
    {
        this.rainDropsPos = rainDropsPos;
        this.rainDropsType = rainDropsType;
        this.lastDropTime = lastDropTime;
        this.totalBalonesGenerados = totalBalonesGenerados;
        this.velocidadBalon = velocidadBalon;
        this.anchoBalon = anchoBalon;
        this.altoBalon = altoBalon;
    }

    public void crearBalon() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 32;
        raindrop.height = 32;
        rainDropsPos.add(raindrop);
        rainDropsType.add(getTipoBalon());
    }

    protected float getAnchoBalon() {
        return anchoBalon;
    }
    protected float getAltoBalon() {
        return altoBalon;
    }	
    protected abstract int getTipoBalon();

    public abstract boolean procesarColision(Arco arco, Sound dropSound, EstadoJuego estadoJuego);
}
