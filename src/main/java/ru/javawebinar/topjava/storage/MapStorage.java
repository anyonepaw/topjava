package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapStorage {
    private MapStorage() {
    }

    private static AtomicInteger counter = new AtomicInteger(0);
    private static Map<Integer, Meal> map = new ConcurrentHashMap<>();

    public static MapStorage getSingleton() {
        return new MapStorage();
    }

    public Meal get(Integer id) {
        return map.get(id);
    }

    public void save(Meal meal) {
        meal.setId(counter.incrementAndGet());
        map.putIfAbsent(counter.get(), meal);
    }

    public void delete(Integer id) {
        counter.decrementAndGet();
        map.remove(id);
    }

    public void update(Integer id, Meal meal) {
        meal.setId(id);
        map.computeIfPresent(id, (k, v) -> meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }


}
