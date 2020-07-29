package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {
    public static final String REST_URL = "rest/profile/meals";

    @RequestMapping(method = RequestMethod.GET)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @RequestMapping(value = "/between", method = RequestMethod.GET)
    public List<MealTo> getBetween(
         @RequestParam(value = "startDateTime")
         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
         LocalDateTime startDateTime,
         @RequestParam(value = "endDateTime")
         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
         LocalDateTime endDateTime) {
       return super.getBetween(startDateTime.toLocalDate(), startDateTime.toLocalTime(),
                                endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }

}
