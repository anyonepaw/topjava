package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapStorage implements Storage {

    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();

    public Meal get(Integer id) {
        return map.get(id);
    }

    public Meal create(Meal meal) {
        meal.setId(counter.incrementAndGet());
        return map.putIfAbsent(counter.get(), meal);
    }

    public void delete(Integer id) {
        counter.decrementAndGet();
        map.remove(id);
    }

    public Meal update(Meal meal) {
        return map.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }


}
