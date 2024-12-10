package com.example.work_space_link.Service;

import com.example.work_space_link.ApiResponse.ApiException;
import com.example.work_space_link.Model.TimeSlot;
import com.example.work_space_link.Model.WorkSpace;
import com.example.work_space_link.Repository.TimeSlotRepository;
import com.example.work_space_link.Repository.WorkSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final WorkSpaceRepository workSpaceRepository;

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        WorkSpace workSpace = workSpaceRepository.findWorkSpaceById(timeSlot.getWorkspaceId());
        if (workSpace == null) {
            throw new ApiException("*Work Space Not Found*");
        }
        timeSlotRepository.save(timeSlot);
    }

    public void updateTimeSlot(Integer id, TimeSlot timeSlot) {
        TimeSlot oldTimeSlot = timeSlotRepository.findTimeSlotById(id);
        WorkSpace workSpace = workSpaceRepository.findWorkSpaceById(timeSlot.getWorkspaceId());

        if (workSpace == null) {
            throw new ApiException("*Work Space Not Found*");
        }
        if (oldTimeSlot == null) {
            throw new ApiException("*TimeSlot not found*");
        }
        if(oldTimeSlot.getIsBooked()) {
            throw new ApiException("*TimeSlot is booked*");
        }
        oldTimeSlot.setStartDateTime(timeSlot.getStartDateTime());
        oldTimeSlot.setEndDateTime(timeSlot.getEndDateTime());
        timeSlotRepository.save(oldTimeSlot);
    }

    public void deleteTimeSlot(Integer timeSlotId) {
        TimeSlot timeSlot = timeSlotRepository.findTimeSlotById(timeSlotId);
        if (timeSlot == null) {
            throw new ApiException("*TimeSlot not found*");
        }
        if (timeSlot.getIsBooked()) {
            throw new ApiException("*TimeSlot is booked*");
        }
        timeSlotRepository.deleteById(timeSlotId);
    }

    public List<TimeSlot> getAvailableTimeSlots(Integer workspaseId) {
        return timeSlotRepository.findByWorkspaceIdAndIsBooked(workspaseId, false);
    }

}
