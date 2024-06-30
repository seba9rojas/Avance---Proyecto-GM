package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

public class Estrategia1 implements TipoBalonStrategy {
    @Override
    public int determinarTipoBalon(int totalBalonesGenerados, int nivelActual) {
        float probabilidadBalonesBuenos = 50;
        float probabilidadBalonesMalos = 43;
        float probabilidadBalonDorado = 1;
        float probabilidadBalonHielo = 5;


        for (int i = 0; i < nivelActual - 1; i++) {
            probabilidadBalonesBuenos -= 2;
            probabilidadBalonesMalos += 2;
        }

        float tipoBalonFloat = MathUtils.random(1, 100);
        if (tipoBalonFloat <= probabilidadBalonesMalos)
            return 1; // Balón Rojo
        else if (tipoBalonFloat <= probabilidadBalonesMalos + probabilidadBalonesBuenos)
            return 2; // Balón Normal
        else if (tipoBalonFloat <= probabilidadBalonesMalos + probabilidadBalonesBuenos + probabilidadBalonDorado)
            return 3; // Balón Dorado
        else if (tipoBalonFloat <= probabilidadBalonesMalos + probabilidadBalonesBuenos + probabilidadBalonDorado + probabilidadBalonHielo)
            return 4; // Balón Hielo
        else
            return 5; // Balón Veneno
    }
}
