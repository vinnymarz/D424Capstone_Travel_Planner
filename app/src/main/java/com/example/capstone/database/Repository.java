package com.example.capstone.database;

import android.app.Application;

import com.example.capstone.dao.CarDAO;
import com.example.capstone.dao.ExcursionDAO;
import com.example.capstone.dao.VacationDAO;
import com.example.capstone.entities.Car;
import com.example.capstone.entities.Excursion;
import com.example.capstone.entities.Vacation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Repository class to handle data interactions with the database
public class Repository {
    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;
    private CarDAO mCarDAO;

    // Lists to hold all vacations and excursions
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;
    private List<Car> mAllCars;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
        mCarDAO = db.carDAO();
    }

    public List<Vacation> getmAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }

    public void insert (Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Excursion> getmAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public List<Excursion> getAssociatedExcursions(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationID);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Car> getmAllCars() {
        databaseExecutor.execute(() -> {
            mAllCars = mCarDAO.getAllCars();
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCars;
    }

    public List<Car> getAssociatedCars(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllCars = mCarDAO.getAssociatedCars(vacationID);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCars;
    }

    public List<Car> getAvailableCars(int vacationId) {
        List<Car> availableCarsList = new ArrayList<>();

        // Use the ExecutorService to run the database query on a background thread
        databaseExecutor.execute(() -> {
            // Fetch the vacation by its ID
            Vacation vacation = getVacationById(vacationId);

            if (vacation != null) {
                String vacationStartDate = vacation.getStartDate();
                String vacationEndDate = vacation.getEndDate();

                availableCarsList.add(new Car(1, "KIA Carnival", -1, vacationStartDate + " - " + vacationEndDate));
                availableCarsList.add(new Car(2, "Honda Accord", -1, vacationStartDate + " - " + vacationEndDate));
                availableCarsList.add(new Car(3, "Ford Mustang", -1, vacationStartDate + " - " + vacationEndDate));
                availableCarsList.add(new Car(4, "Dodge 1500", -1, vacationStartDate + " - " + vacationEndDate));
                availableCarsList.add(new Car(5, "Chevrolet Tahoe", -1, vacationStartDate + " - " + vacationEndDate));
                availableCarsList.add(new Car(6, "Ford Transit", -1, vacationStartDate + " - " + vacationEndDate));
            }
        });

        return availableCarsList;
    }

    private Vacation getVacationById(int vacationId) {
        // Use your mVacationDAO to fetch the vacation from the database
        return mVacationDAO.getVacationById(vacationId);
    }

    public void insert(Car car) {
        databaseExecutor.execute(() -> {
            mCarDAO.insert(car);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Car car) {
        databaseExecutor.execute(() -> {
            mCarDAO.update(car);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Car car) {
        databaseExecutor.execute(() -> {
            mCarDAO.delete(car);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Car getCarById(int carId) {
        return mCarDAO.getCarById(carId);
    }
}
