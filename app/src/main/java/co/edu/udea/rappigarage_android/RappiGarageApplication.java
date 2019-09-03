package co.edu.udea.rappigarage_android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class RappiGarageApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
