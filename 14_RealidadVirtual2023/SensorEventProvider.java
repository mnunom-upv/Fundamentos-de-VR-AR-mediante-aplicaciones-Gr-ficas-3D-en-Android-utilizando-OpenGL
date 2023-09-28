package com.upvictoria.pm2023.iti_271086.p1u1.realidadaumentada2023;

import android.hardware.*;

public interface SensorEventProvider {
    void start();
    
    void stop();
    
    void registerListener(SensorEventListener p0);
    
    void unregisterListener(SensorEventListener p0);
}
