package com.example.a360foto;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.panoramagl.PLImage;
import com.panoramagl.PLManager;
import com.panoramagl.PLSphericalPanorama;
import com.panoramagl.utils.PLUtils;
import com.panoramagl.transitions.PLTransitionBlend;

public class MainActivity extends AppCompatActivity {

    private FrameLayout flView;
    private PLManager plManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flView = findViewById(R.id.fl_view);
        initialisePlManager();

        PLSphericalPanorama panorama = new PLSphericalPanorama();
        panorama.getCamera().lookAt(30.0f, 90.0f);

        // Aumentar la sensibilidad de la cámara
        panorama.getCamera().setRotationSensitivity(14.0f); // Valor por defecto es 1.0f

        panorama.setImage(new PLImage(PLUtils.getBitmap(this, R.raw.caminosrurales)));
        plManager.setPanorama(panorama);
    }

    private void initialisePlManager() {
        plManager = new PLManager(this);
        plManager.setContentView(flView);
        plManager.onCreate();

        // Configuración de sensibilidad y movimiento
        plManager.setAccelerometerInterval(1); // Intervalo más bajo = actualizaciones más frecuentes
        plManager.setInertiaInterval(1); // Intervalo de inercia más bajo = movimiento más suave

        // Otras configuraciones que afectan la sensación de movimiento
        plManager.setScrollingEnabled(true);
        plManager.setAccelerometerEnabled(false);
        plManager.setZoomEnabled(true);
        plManager.setInertiaEnabled(true);
        plManager.setAcceleratedTouchScrollingEnabled(true); // Cambiado a true para movimiento más rápido

        // Configuraciones adicionales de sensibilidad
        plManager.setMinDistanceToEnableScrolling(1); // Distancia menor para iniciar el movimiento
        plManager.setMinDistanceToEnableDrawing(1); // Distancia menor para iniciar el dibujado
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return plManager.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        plManager.onResume();
    }

    @Override
    protected void onPause() {
        plManager.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        plManager.onDestroy();
        super.onDestroy();
    }
}