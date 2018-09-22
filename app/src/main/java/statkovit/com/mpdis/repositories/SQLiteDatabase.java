package statkovit.com.mpdis.repositories;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import statkovit.com.mpdis.entities.Student;
import statkovit.com.mpdis.repositories.dao.SQLiteStudentDao;

@Database(entities = {Student.class}, version = 1)
public abstract class SQLiteDatabase extends RoomDatabase {
    public abstract SQLiteStudentDao getSQLiteStudentDao();

}
