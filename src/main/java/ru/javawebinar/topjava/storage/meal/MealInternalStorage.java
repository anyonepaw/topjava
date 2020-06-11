package ru.javawebinar.topjava.storage.meal;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealInternalStorage implements MealStorage {
    private AtomicInteger counter = new AtomicInteger(1);
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();

    public Meal get(Integer id) {
        return map.get(id);
    }

    public Meal create(Meal meal) {
        meal.setId(counter.getAndIncrement());
        return map.compute(meal.getId(), (k, v) -> meal);
    }

    public void delete(Integer id) {
        if (map.containsKey(id)) {
            if (id != map.size()) {
                map.values().stream()
                        .skip(id)
                        .forEach(meal -> {
                            int prevId = meal.getId() - 1;
                            meal.setId(prevId);
                            map.put(prevId, meal);
                        });
            }
            map.remove(map.size());
            counter.set(map.size() == 0 ? 1 : map.size());
        }
    }

    public Meal update(Meal meal) {
        return map.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }
}
