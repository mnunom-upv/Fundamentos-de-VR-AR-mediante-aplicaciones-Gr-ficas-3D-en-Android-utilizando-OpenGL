package com.upvictoria.pm2023.iti_271086.p1u1.realidadaumentada2023;

public class SystemClock implements Clock {
    @Override
    public long nanoTime() {
        return System.nanoTime();
    }
}
