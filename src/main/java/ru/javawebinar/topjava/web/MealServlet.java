package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    public final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final MapStorage MAP_STORAGE = MapStorage.getSingleton();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("meals: GET request");

        String action = request.getParameter("action");
        if (action == null) action = "";

        String id = request.getParameter("id");

        switch (action) {
            case "delete":
                MAP_STORAGE.delete(Integer.parseInt(id));
                break;
            case "create":
            case "update":
                if (id != null) {
                    request.setAttribute("id", id);
                    Meal meal = MAP_STORAGE.get(Integer.parseInt(id));
                    request.setAttribute("dateTime", meal.getDateTime());
                    request.setAttribute("description", meal.getDescription());
                    request.setAttribute("calories", meal.getCalories());
                }
                request.getRequestDispatcher("/update-meal.jsp").forward(request, response);
                break;
        }
        request.setAttribute( "caloriesPerDay", MealsUtil.CALORIES_PER_DAY);
        request.setAttribute("meals",
                MealsUtil.filteredByStreams(MAP_STORAGE.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("meals: POST request");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime")
                .replace("T", " "), DATE_TIME_FORMATTER);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);
        if (id.isEmpty()) {
            MAP_STORAGE.save(meal);
            log.debug("save");
        } else {
            MAP_STORAGE.update(Integer.parseInt(id), meal);
            log.debug("update");
        }
        doGet(request, response);
    }

}
