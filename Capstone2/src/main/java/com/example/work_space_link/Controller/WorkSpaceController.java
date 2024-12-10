package com.example.work_space_link.Controller;

import com.example.work_space_link.ApiResponse.ApiResponse;
import com.example.work_space_link.Model.WorkSpace;
import com.example.work_space_link.Repository.WorkSpaceRepository;
import com.example.work_space_link.Service.BookingRequestService;
import com.example.work_space_link.Service.WorkSpaceService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-spase-link/work-space")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;
    private final BookingRequestService bookingRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllWorkSpaces() {
        List<WorkSpace> workSpacesList = workSpaceService.findAllWorkSpaces();
        return ResponseEntity.status(200).body(workSpacesList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWorkSpace(@RequestBody  WorkSpace workSpace) {

        workSpaceService.addWorkSpace(workSpace);
        return ResponseEntity.status(200).body(new ApiResponse("Work Space Added Successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWorkSpace(@PathVariable Integer id, @RequestBody WorkSpace workSpace) {

        workSpaceService.updateWorkSpace(id, workSpace);
        return ResponseEntity.status(200).body(new ApiResponse("Work Space Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkSpace(@PathVariable Integer id) {
        workSpaceService.deleteWorkSpace(id);
        return ResponseEntity.status(200).body(new ApiResponse("Work Space Deleted Successfully"));
    }

    //endpoint
    //the user will calculate the total price of any workspace with specific date
    @GetMapping("/calculate-price/workspace/{workspaceId}/from-day/{from}/to-day/{to}")
    public ResponseEntity<?> calculatePrice(@PathVariable Integer workspaceId, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
        Double totalPrice = bookingRequestService.calculateTotalPrice(from,to,workspaceId);
        return ResponseEntity.status(200).body(new ApiResponse("Total Price will be = " + totalPrice));
    }

    //endpoint
    //the user will show all workspace with specific type
    @GetMapping("/all-same-type/{type}")
    public ResponseEntity<?> getWorkSpacesByType(@PathVariable String type) {
        List<WorkSpace> workSpaceList = workSpaceService.findWorkSpacesByType(type);
        return ResponseEntity.status(200).body(workSpaceList);
    }


    //endpoint
    //list of workspace with specific adv
    @GetMapping("/workspace-have/advantage/{advantage}")
    public ResponseEntity<?> getWorkSpacesByAdvantage(@PathVariable String advantage) {
        List<WorkSpace> workSpaceList = workSpaceService.findWorkSpacesByadvantages(advantage);
        return ResponseEntity.status(200).body(workSpaceList);
    }


    //endpoint
    @GetMapping("/revenue-workspace/id/{id}/from-date/{from}/to-date/{to}")
    public ResponseEntity<?> calculateRevenueOfWorkspaceMonthly(@PathVariable Integer id, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to){
        Double revenue = workSpaceService.calculateRevenueOfWorkspaceMonthly(id,from,to);
        return ResponseEntity.status(200).body(new ApiResponse("Calculate the workspace revenue for a specific period  = "+revenue));
    }


    //endpoint
    @GetMapping("/revenue-workspace/{id}")
    public ResponseEntity<?> calculateRevenueOfWorkspace(@PathVariable Integer id){
        Double revenue = workSpaceService.calculateRevenueOfWorkspace(id);
        return ResponseEntity.status(200).body(new ApiResponse("Calculate the company's revenue = "+revenue));
    }

//    //endpoint
//    @GetMapping("/by-city-and-availability/{city}/{from}/{to}")
//    public ResponseEntity<?> getWorkSpacesByCityAndAvailability(@PathVariable String city, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
//        List<WorkSpace> workSpaces=workSpaceService.getWorkSpacesByCityAndAvailability(city,from,to);
//        return ResponseEntity.status(200).body(workSpaces);
//    }

    //endpoint
    @GetMapping("/by-city/{city}")
    public ResponseEntity<?> getWorkSpacesBycity(@PathVariable String city) {
        List<WorkSpace> workSpaceList = workSpaceService.findWorkSpacesByLocation(city);
        return ResponseEntity.status(200).body(workSpaceList);
    }

    //endpoint
    @GetMapping("/low-rated/{num}")
    public ResponseEntity<?> getLowRatedWorkSpaces(@PathVariable Double num) {
        List<WorkSpace> workSpaces= workSpaceService.getWorkSpacesbyLowRatings(num);
        return ResponseEntity.status(200).body(workSpaces);
    }







}
