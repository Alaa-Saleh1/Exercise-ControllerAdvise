package com.example.work_space_link.Repository;

import com.example.work_space_link.Model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer>{

    TimeSlot findTimeSlotById(Integer id);

    List<TimeSlot> findTimeSlotByWorkspaceIdAndIsBooked(Integer workspaceId, Boolean isBooked);


    List<TimeSlot> findByWorkspaceIdAndIsBooked(Integer workspaceId, Boolean isBooked);

    @Query("SELECT t from TimeSlot t where t.workspaceId=?1 and t.isBooked= false and "+
           "t.startDateTime<=?3 and t.endDateTime>=?2")
    List<TimeSlot> getAvailableSlots(Integer workspaceId, LocalDateTime start, LocalDateTime end);

    @Query("select t from TimeSlot t where t.workspaceId=?1 and t.isBooked=true and "
            +"t.startDateTime<=?3 and t.endDateTime>=?2")
    List<TimeSlot> getBookedSlots(Integer workspaceId, LocalDateTime start, LocalDateTime end);

    @Query("select t from TimeSlot t where t.workspaceId = ?1 " +
            "and t.isBooked = true " +
            "and ((t.startDateTime < ?3 and t.endDateTime > ?2))")
    List<TimeSlot> findOverlappingTimeSlots(Integer workspaceId, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
