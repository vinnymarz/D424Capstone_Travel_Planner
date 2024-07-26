package com.d424.capstone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.d424.capstone.dao.ExcursionDAO;
import com.d424.capstone.dao.VacationDAO;
import com.d424.capstone.dao.CarDAO;
import com.d424.capstone.entities.Excursion;
import com.d424.capstone.entities.Vacation;
import com.d424.capstone.entities.Car;

// Defines a Room database for storing vacation, excursion and rental data
@Database(entities = {Vacation.class, Excursion.class, Car.class}, version = 22, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {

    // Provides access to the DAO for interacting with Vacation entities
    public abstract VacationDAO vacationDAO();

    // Provides access to the DAO for interacting with Excursion entities
    public abstract ExcursionDAO excursionDAO();

    // Provides access to the DAO for interacting with Rental entities
    public abstract CarDAO carDAO();

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
