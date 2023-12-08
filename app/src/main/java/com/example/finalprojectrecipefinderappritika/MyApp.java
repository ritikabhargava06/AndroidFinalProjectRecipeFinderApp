package com.example.finalprojectrecipefinderappritika;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MyApp extends Application {

    static Handler handler = new Handler(Looper.getMainLooper());
    static ExecutorService executorService = Executors.newFixedThreadPool(4);
    NetworkingManager networkingManager;

    public NetworkingManager getNetworkingManager() {
        if (networkingManager==null)
            networkingManager = new NetworkingManager();
        return networkingManager;
    }
}
