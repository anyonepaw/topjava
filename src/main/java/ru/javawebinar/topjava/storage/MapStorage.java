package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements MealStorage {
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();

    public Meal get(Integer id) {
        return map.get(id);
    }

    public Meal create(Meal meal) {
        meal.setId(map.size());
        return map.put(meal.getId(), meal);
    }

    public void delete(Integer id) {
        int lastElement = map.size() - 1;
        if(id != lastElement)  {
            map.values().stream()
                    .skip(id + 1)
                    .forEach(meal -> {
                        int prevMealId = meal.getId() - 1;
                        meal.setId(prevMealId);
                        map.put(prevMealId, meal);
                    });
        }
        map.remove(lastElement);
    }

    public Meal update(Meal meal) {
        return map.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}
