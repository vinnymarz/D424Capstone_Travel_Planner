package com.d424.capstone.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d424.capstone.dao.CarDAO;
import com.d424.capstone.dao.ExcursionDAO;
import com.d424.capstone.dao.VacationDAO;
import com.d424.capstone.entities.Car;
import com.d424.capstone.entities.Excursion;
import com.d424.capstone.entities.Vacation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Repository class to handle data interactions with the database
public class Repository {
    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;
    private CarDAO mCarDAO;
    private static final String TAG = "CarDetailsActivity";


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
            // Fetch associated cars
            List<Car> associatedCars = mCarDAO.getCarsByVacationId(vacation.getVacationId());

            // Delete associated cars
            for (Car car : associatedCars) {
                mCarDAO.delete(car);
            }

            // Delete the vacation
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // EXCURSIONS

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

    // CARS

    public LiveData<List<Car>> getAllCars() {
        return mCarDAO.getAllCars();
    }

    public LiveData<List<Car>> getAssociatedCars(int vacationID) {
        Log.d(TAG, "getAssociatedCars called for vacationID: " + vacationID);
        return mCarDAO.getAssociatedCars(vacationID);
    }

    public LiveData<List<Car>> getmAllCars() {
        return mCarDAO.getAllCars();
    }

    public LiveData<List<Car>> getAvailableCars(int vacationId) {
        MutableLiveData<List<Car>> availableCarsList = new MutableLiveData<>();

        databaseExecutor.execute(() -> {
            List<Car> cars = new ArrayList<>();
            Vacation vacation = getVacationById(vacationId);

            if (vacation != null) {
                String vacationStartDate = vacation.getStartDate();
                String vacationEndDate = vacation.getEndDate();

                cars.add(new Car(1, "KIA Carnival", -1, vacationStartDate + " - " + vacationEndDate));
                cars.add(new Car(2, "Honda Accord", -1, vacationStartDate + " - " + vacationEndDate));
                cars.add(new Car(3, "Ford Mustang", -1, vacationStartDate + " - " + vacationEndDate));
                cars.add(new Car(4, "Dodge 1500", -1, vacationStartDate + " - " + vacationEndDate));
                cars.add(new Car(5, "Chevrolet Tahoe", -1, vacationStartDate + " - " + vacationEndDate));
                cars.add(new Car(6, "Ford Transit", -1, vacationStartDate + " - " + vacationEndDate));
            }

            availableCarsList.postValue(cars);
        });

        return availableCarsList;
    }

    private Vacation getVacationById(int vacationId) {
        // Use your mVacationDAO to fetch the vacation from the database
        return mVacationDAO.getVacationById(vacationId);
    }

    public void insert(Car car)
    {
        databaseExecutor.execute(() -> mCarDAO.insert(car));
        Log.d(TAG, "Inserting car: " + car.toString());
    }

    public void update(Car car) {
        databaseExecutor.execute(() -> mCarDAO.update(car));
    }

    public void delete(Car car) {
        databaseExecutor.execute(() -> mCarDAO.delete(car));
    }

    public Car getCarById(int carId) {
        return mCarDAO.getCarById(carId);
    }

    public void saveCar(Car car) {
        databaseExecutor.execute(() -> {
            Car existingCar = mCarDAO.getCarById(car.getCarID());
            if (existingCar == null) {
                mCarDAO.insert(car);
            } else {
                mCarDAO.update(car);
            }
        });
    }
}
