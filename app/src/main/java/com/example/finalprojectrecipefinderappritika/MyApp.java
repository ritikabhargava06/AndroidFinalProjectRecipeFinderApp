package com.example.finalprojectrecipefinderappritika;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    static Handler handler = new Handler(Looper.getMainLooper());
    static ExecutorService executorService = Executors.newFixedThreadPool(4);
   NetworkingManager networkingManager;
   DatabaseManager dataBaseManager;
   FireStoreManager fireStoreManager;

    public FireStoreManager getFireStoreManager() {
        if(fireStoreManager==null)
            fireStoreManager = new FireStoreManager();
        return fireStoreManager;
    }
    int dataSource;   //1 - Room DB
                    //2 - fire store

    public int getDataSource() {
        return dataSource;
    }

    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }

    static JsonManager jsonManager = new JsonManager();

    public DatabaseManager getDataBaseManager() {
        if (dataBaseManager==null)
            dataBaseManager = new DatabaseManager();
        return dataBaseManager;
    }

    public NetworkingManager getNetworkingManager() {
        if (networkingManager==null)
            networkingManager = new NetworkingManager();
        return networkingManager;
    }
}
