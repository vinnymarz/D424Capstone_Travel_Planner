package com.example.capstone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.capstone.dao.ExcursionDAO;
import com.example.capstone.dao.VacationDAO;
import com.example.capstone.entities.Excursion;
import com.example.capstone.entities.Vacation;

// Defines a Room database for storing vacation and excursion data
@Database(entities = {Vacation.class, Excursion.class}, version = 11, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {

    // Provides access to the DAO for interacting with Vacation entities
    public abstract VacationDAO vacationDAO();

    // Provides access to the DAO for interacting with Excursion entities
    public abstract ExcursionDAO excursionDAO();

    // Instance of the database builder
    private static volatile VacationDatabaseBuilder INSTANCE;

    // Retrieves the asynchronous instance of the database builder
    static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            // Synchronized block for thread-safety
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    // Builds the Room database instance
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    VacationDatabaseBuilder.class, "MyVacationDatabase.db")
                            // Performs destructive migration on schema mismatch
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
