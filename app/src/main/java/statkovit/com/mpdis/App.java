package statkovit.com.mpdis;

import android.app.Application;
import android.arch.persistence.room.Room;

import statkovit.com.mpdis.repositories.SQLiteDatabase;

public class App extends Application {
    public static App instance;

    private SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, SQLiteDatabase.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
