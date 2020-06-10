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

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class MealServlet extends HttpServlet {
    public final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MapStorage mapStorage;

    @Override
    public void init() {
        mapStorage = new MapStorage();
        MealsUtil.getMeals().forEach(mapStorage::create);
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
                    request.setAttribute("id", id);
                    Meal meal = mapStorage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                }
                request.getRequestDispatcher("/update-meal.jsp").forward(request, response);
                break;
            case "delete":
                if (id != null) {
                    mapStorage.delete(Integer.parseInt(id));
                }
                response.sendRedirect("meals");
                break;
            default:
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(mapStorage.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("meals: POST request");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), ISO_LOCAL_DATE_TIME);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);
        if (id.isEmpty()) {
            Meal createdMeal = mapStorage.create(meal);
            if (createdMeal != null) {
                log.debug("create meal: id " + createdMeal.getId() + "and description " + createdMeal.getDescription());
            }
        } else {
            meal.setId(Integer.parseInt(id));
            Meal updatedMeal = mapStorage.update(meal);
            log.debug("update meal: id" + updatedMeal.getId() + "and description " + updatedMeal.getDescription());
        }
        response.sendRedirect("meals");
    }

}
