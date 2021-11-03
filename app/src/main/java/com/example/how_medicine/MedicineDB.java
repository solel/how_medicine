package com.example.how_medicine;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Medicine.class}, version = 1)
public abstract class MedicineDB extends RoomDatabase {
    private static MedicineDB INSTANCE = null;

    public abstract MedicineDao medicineDao();

    public static MedicineDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, MedicineDB.class, "medicine").build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
