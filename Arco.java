package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Arco implements Dibujable {
    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas;
    private int puntos;
    private int velx;
    private boolean herido = false;
    private int tiempoHeridoMax;
    private int tiempoHerido;

    private Arco(Builder builder) {
        this.bucketImage = builder.bucketImage;
        this.sonidoHerido = builder.sonidoHerido;
        this.vidas = builder.vidas;
        this.puntos = builder.puntos;
        this.velx = builder.velx;
        this.tiempoHeridoMax = builder.tiempoHeridoMax;
        crear();
    }
    
    

    public Arco(Texture bucketImage2, Sound sonidoHerido2) {
		// TODO Auto-generated constructor stub
	}



	public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Rectangle getArea() {
        return bucket;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }

    @Override
    public void crear() {
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 32 / 2;
        bucket.y = 20;
        bucket.width = 50;
        bucket.height = 50;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!herido) {
            batch.draw(getBucketImage(), bucket.x, bucket.y, bucket.width, bucket.height);
        } else {
            batch.draw(getBucketImage(), bucket.x, bucket.y + MathUtils.random(-5, 5), bucket.width, bucket.height);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - bucket.width) bucket.x = 800 - bucket.width;
    }

    public void setVelocidad(int nuevaVelocidad) {
        velx = nuevaVelocidad;
    }

    public void aumentarVida() {
        vidas++;
    }

    public void activarCongelacion(int duracionSegundos) {
        setVelocidad(100);
    }

    @Override
    public void destruir() {
        getBucketImage().dispose();
    }

    public boolean estaHerido() {
        return herido;
    }

    public Texture getBucketImage() {
        return bucketImage;
    }

    public static class Builder {
        private Texture bucketImage;
        private Sound sonidoHerido;
        private int vidas = 3;
        private int puntos = 0;
        private int velx = 250;
        private int tiempoHeridoMax = 40;

        public Builder setBucketImage(Texture bucketImage) {
            this.bucketImage = bucketImage;
            return this;
        }

        public Builder setSonidoHerido(Sound sonidoHerido) {
            this.sonidoHerido = sonidoHerido;
            return this;
        }

        public Builder setVidas(int vidas) {
            this.vidas = vidas;
            return this;
        }

        public Builder setPuntos(int puntos) {
            this.puntos = puntos;
            return this;
        }

        public Builder setVelx(int velx) {
            this.velx = velx;
            return this;
        }

        public Builder setTiempoHeridoMax(int tiempoHeridoMax) {
            this.tiempoHeridoMax = tiempoHeridoMax;
            return this;
        }

        public Arco build() {
            return new Arco(this);
        }
    }

	public void setVidas(int vidas2) {
		// TODO Auto-generated method stub
		
	}



	public void setPuntos(int puntos2) {
		// TODO Auto-generated method stub
		
	}
}
