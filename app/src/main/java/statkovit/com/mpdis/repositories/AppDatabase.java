package statkovit.com.mpdis.repositories;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import statkovit.com.mpdis.entities.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StudentDao getStudentDao();

}
