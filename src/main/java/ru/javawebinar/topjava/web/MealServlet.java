package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    public final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealStorage storage;

    @Override
    public void init() {
        storage = new MapStorage();
        MealsUtil.getMeals().forEach(storage::create);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("meals: GET request");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        String id = request.getParameter("id");

        switch (action) {
            case "create":
            case "update":
                if (id != null) {
                    Meal meal = storage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                }
                request.getRequestDispatcher("/update-meal.jsp").forward(request, response);
                break;
            case "delete":
                if (id != null) {
                    storage.delete(Integer.parseInt(id));
                    log.debug("delete");
                }
                    response.sendRedirect("meals");
                break;
            default:
                log.debug("read");
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("meals: POST request");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(null, dateTime, description, calories);
        if (id.isEmpty()) {
            Meal createdMeal = storage.create(meal);
            if (createdMeal != null) {
                log.debug("create");
            }
        } else {
            meal.setId(Integer.parseInt(id));
            Meal updatedMeal = storage.update(meal);
            log.debug("update");
        }
        response.sendRedirect("meals");
    }
}
