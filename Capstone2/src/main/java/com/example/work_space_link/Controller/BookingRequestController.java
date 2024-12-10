package com.example.work_space_link.Controller;

import com.example.work_space_link.ApiResponse.ApiResponse;
import com.example.work_space_link.Model.BookingRequest;
import com.example.work_space_link.Service.BookingRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-spase-link/booking-request")
public class BookingRequestController {

    private final BookingRequestService bookingRequestService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBookingRequests() {
        List<BookingRequest> bookingRequests = bookingRequestService.getAllBookingRequests();
        return ResponseEntity.status(200).body(bookingRequests);
    }

    //endpoint
    @PostMapping("/add")
    public ResponseEntity<?> addBookingRequest(@RequestBody BookingRequest bookingRequest) {
        bookingRequestService.addBookingRequest(bookingRequest);
        return ResponseEntity.status(200).body(bookingRequest);
    }

    //endpoint
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBookingRequest(@PathVariable Integer id, @RequestBody BookingRequest bookingRequest) {
        bookingRequestService.updateBookingRequest(id, bookingRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Booking request updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookingRequest(@PathVariable Integer id) {
        bookingRequestService.deleteBookingRequest(id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking request deleted successfully"));
    }



    //endpoint
    // user can cancel booking
    @PutMapping("/cancel-user-booking/{bookingId}")
    public ResponseEntity<?> cancelBookingRequest(@PathVariable Integer bookingId) {
        bookingRequestService.cancelBookingRequest(bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking request cancelled successfully"));
    }



    // Endpoint
// Based on the company's decision, the booking status update to approve or reject
    @PutMapping("/company/booking-id/{bookingId}/decision/{decision}")
    public ResponseEntity<?> companyDecision(@PathVariable Integer bookingId, @PathVariable String decision) {
       bookingRequestService.companyDecision(bookingId, decision);
        return ResponseEntity.status(200).body(new ApiResponse("Booking request decision updated successfully"));
    }

    //endpoint
    //show status of booking also update status
    @GetMapping("/status-of-booking/{id}")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Integer id) {
        String status = bookingRequestService.bookingRequestStatus(id);
        return ResponseEntity.status(200).body(new ApiResponse(status));
    }

    //endpoint
    //Booking available time slots for the workspace without select
    @PostMapping("/booking-available/user/{userid}/workspace/{workspaceid}")
    public ResponseEntity<?> getBookingRandomTimeSlot(@PathVariable Integer userid, @PathVariable Integer workspaceid) {
        bookingRequestService.addBookingWithAvailableTimeSlot(userid,workspaceid);
        return ResponseEntity.status(200).body(new ApiResponse("Booking request added successfully"));
    }

    //endpoint
    //show list of booking request of user by status
    @GetMapping("/list-booking-status/user/{id}/status/{status}")
    public ResponseEntity<?> getListOfBookingStatus(@PathVariable Integer id, @PathVariable String status) {
        List<BookingRequest> listofStatus=bookingRequestService.getBookingByStatus(id, status);
        return ResponseEntity.status(200).body(listofStatus);
    }

    //endpoint
    //that endpoint will cancel all booking that is not approved until num of days
    @PutMapping("/cancel-unapproved/days/{days}")
    public ResponseEntity<?> cancelBookingUnapproved(@PathVariable Integer days) {
        bookingRequestService.cancelBookingUnapproved(days);
        return ResponseEntity.status(200).body(new ApiResponse("Booking request cancelled successfully"));
    }

    //endpoint
    @PutMapping("/company-cancel-user/booking-id/{bookingid}/user-id/{userid}")
    public ResponseEntity<?> cancelBookingUser(@PathVariable Integer bookingid, @PathVariable Integer userid) {
        bookingRequestService.cancelAllBookingOfUser(bookingid, userid);
        return ResponseEntity.status(200).body(new ApiResponse("Booking requests for specific user cancelled successfully"));
    }

}
