package com.example.work_space_link.Controller;

import com.example.work_space_link.ApiResponse.ApiResponse;
import com.example.work_space_link.Model.TimeSlot;
import com.example.work_space_link.Service.TimeSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-spase-link/time-slot")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.status(200).body(timeSlots);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTimeSlot(@RequestBody  TimeSlot timeSlot) {
        timeSlotService.addTimeSlot(timeSlot);
        return ResponseEntity.status(200).body(new ApiResponse("TimeSlot added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTimeSlot(@PathVariable Integer id, @RequestBody TimeSlot timeSlot) {

        timeSlotService.updateTimeSlot(id, timeSlot);
        return ResponseEntity.status(200).body(new ApiResponse("TimeSlot updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTimeSlot(@PathVariable Integer id) {
        timeSlotService.deleteTimeSlot(id);
        return ResponseEntity.status(200).body(new ApiResponse("TimeSlot deleted successfully"));
    }

    //endpoint
    //show all availability time slot in one workspace
    @GetMapping("/getAvailability/workspace/{workspaceId}")
    public ResponseEntity<?> getTimeSlotIsAvalibal(@PathVariable Integer workspaceId) {
        List<TimeSlot> listTime = timeSlotService.getAvailableTimeSlots(workspaceId);
        return ResponseEntity.status(200).body(listTime);
    }

}
