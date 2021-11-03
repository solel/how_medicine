package com.example.how_medicine;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicineDao {
    @Query("SELECT * FROM takemedicine")
    List<Medicine> getAll();

    @Query("SELECT * FROM takemedicine WHERE id IN (:medicineIds)")
    List<Medicine> loadAllByIds(int[] medicineIds);

    @Insert
    void insertAll(Medicine... medicines);

    @Delete
    void delete(Medicine medicine);
}
