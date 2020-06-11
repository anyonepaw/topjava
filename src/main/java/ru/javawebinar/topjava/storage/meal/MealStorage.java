package ru.javawebinar.topjava.storage.meal;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    Meal create(Meal meal);

    Meal update(Meal meal);

    Meal get(Integer id);

    void delete(Integer id);

    List<Meal> getAll();
}
