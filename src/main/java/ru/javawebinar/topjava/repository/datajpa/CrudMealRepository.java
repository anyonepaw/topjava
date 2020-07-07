package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int deleteByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    Meal findByIdAndUser_Id(int id, int userId);

    List<Meal> findAllByUser_Id_OrderByDateTimeDesc(int userId);

    List<Meal> findAllByUser_Id_AndDateTimeIsGreaterThanEqualAndDateTimeIsLessThanOrderByDateTimeDesc(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
