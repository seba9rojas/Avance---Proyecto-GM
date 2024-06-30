package com.mygdx.game;

public class EstadisticasJuego {
    private static EstadisticasJuego instance;

    private int vidas;
    private int puntaje;
    private int nivel;


    private EstadisticasJuego() {
        this.vidas = 3;
        this.puntaje = 0;
        this.nivel = 1;
    }


    public static EstadisticasJuego getInstance() {
        if (instance == null) {
            instance = new EstadisticasJuego();
        }
        return instance;
    }


    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }


    public void incrementarPuntaje(int puntos) {
        this.puntaje += puntos;
    }

    public void decrementarVidas() {
        this.vidas--;
    }

    public void incrementarNivel() {
        this.nivel++;
    }
}
