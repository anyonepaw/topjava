package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, ConcurrentHashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("meal repository: save user {} meal {}", userId, meal);
        if(!repository.containsKey(userId)) {
            repository.put(userId, new ConcurrentHashMap<>());
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("meal repository: delete {}", id);
        return repository.remove(found(id, userId)) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("meal repository: get {}", id);
        return repository.get(userId).get(found(id, userId));
    }

    // -1 if not found
    private int found(int id, int userId) {
        return repository.get(userId).computeIfPresent(id,
                (mealId, meal) -> meal.getUserId() == userId ? meal : null) != null ? id : -1;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("meal repository: getAll");
        return repository.get(userId).values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalTime start, LocalTime end) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), start, end))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

