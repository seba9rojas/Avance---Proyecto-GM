package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class ArcoBuilder {
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 250;

    public ArcoBuilder setBucketImage(Texture bucketImage) {
        this.bucketImage = bucketImage;
        return this;
    }

    public ArcoBuilder setSonidoHerido(Sound sonidoHerido) {
        this.sonidoHerido = sonidoHerido;
        return this;
    }

    public ArcoBuilder setVidas(int vidas) {
        this.vidas = vidas;
        return this;
    }

    public ArcoBuilder setPuntos(int puntos) {
        this.puntos = puntos;
        return this;
    }

    public ArcoBuilder setVelx(int velx) {
        this.velx = velx;
        return this;
    }

    public Arco build() {
        Arco arco = new Arco(bucketImage, sonidoHerido);
        arco.setVidas(vidas);
        arco.setPuntos(puntos);
        arco.setVelocidad(velx);
        arco.crear();
        return arco;
    }
}
