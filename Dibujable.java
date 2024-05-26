package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Dibujable {
    void dibujar(SpriteBatch batch);
    void crear();
    void destruir();
}
