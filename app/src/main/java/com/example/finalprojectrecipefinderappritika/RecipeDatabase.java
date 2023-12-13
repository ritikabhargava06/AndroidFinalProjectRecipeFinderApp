package com.example.finalprojectrecipefinderappritika;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(version=1,entities = {Recipe.class})
@TypeConverters({Converters.class})
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao getDao();
}
