package com.example.work_space_link.Service;

import com.example.work_space_link.ApiResponse.ApiException;
import com.example.work_space_link.Model.BookingRequest;
import com.example.work_space_link.Model.TimeSlot;
import com.example.work_space_link.Model.WorkSpace;
import com.example.work_space_link.Repository.BookingRequestRepository;
import com.example.work_space_link.Repository.TimeSlotRepository;
import com.example.work_space_link.Repository.UserRepository;
import com.example.work_space_link.Repository.WorkSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingRequestService {

    private final BookingRequestRepository bookingRequestRepository;
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkSpaceService workSpaceService;
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotService timeSlotService;

    public List<BookingRequest> getAllBookingRequests() {
        return bookingRequestRepository.findAll();
    }

    public void addBookingRequest(BookingRequest bookingRequest) {
        WorkSpace workSpace = workSpaceRepository.findWorkSpaceById(bookingRequest.getWorkspaceId());
        if (workSpace == null) {
            throw new ApiException("*WorkSpace not found*");
        }

        List<TimeSlot> overlappingSlot = timeSlotRepository.findOverlappingTimeSlots(
                bookingRequest.getWorkspaceId(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime()
        );

        if (!overlappingSlot.isEmpty()) {
            throw new ApiException("*Overlapping TimeSlots Error*");
        }


        List<TimeSlot> availbleSlots = timeSlotRepository.getAvailableSlots(
                bookingRequest.getWorkspaceId(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime()
        );

        if (availbleSlots.isEmpty()) {
            throw new ApiException("*No available time slots*");
        }

        for (TimeSlot timeSlot : availbleSlots) {

            if (timeSlot.getStartDateTime().equals(bookingRequest.getStartDateTime())
                    && timeSlot.getEndDateTime().equals(bookingRequest.getEndDateTime())) {
                timeSlot.setIsBooked(true);
                timeSlotRepository.save(timeSlot);
            }
            if (timeSlot.getStartDateTime().isBefore(bookingRequest.getStartDateTime())
                    &&timeSlot.getEndDateTime().isAfter(bookingRequest.getEndDateTime())) {

                TimeSlot beforeSlot = new TimeSlot(null,
                        timeSlot.getWorkspaceId(),
                        timeSlot.getStartDateTime(),
                        bookingRequest.getStartDateTime(),
                        false
                );

                TimeSlot afterSlot = new TimeSlot(null,
                        timeSlot.getWorkspaceId(),
                        bookingRequest.getEndDateTime(),
                        timeSlot.getEndDateTime(),
                        false
                );

                timeSlotRepository.save(beforeSlot);
                timeSlotRepository.save(afterSlot);



                timeSlot.setStartDateTime(bookingRequest.getStartDateTime());
                timeSlot.setEndDateTime(bookingRequest.getEndDateTime());
                timeSlot.setIsBooked(true);
                timeSlotRepository.save(timeSlot);

            }
            else if (timeSlot.getStartDateTime().isBefore(bookingRequest.getStartDateTime())) {
                timeSlot.setEndDateTime(bookingRequest.getStartDateTime());
                timeSlotRepository.save(timeSlot);

            } else if (timeSlot.getEndDateTime().isAfter(bookingRequest.getEndDateTime())) {
                timeSlot.setStartDateTime(bookingRequest.getEndDateTime());
                timeSlotRepository.save(timeSlot);
            }
        }

        Double totalPrice = calculateTotalPrice(bookingRequest.getStartDateTime(),bookingRequest.getEndDateTime(), bookingRequest.getWorkspaceId());
        bookingRequest.setTotalPrice(totalPrice);
        bookingRequest.setBookingStatus("Pending");
        bookingRequest.setRequestDate(LocalDate.now());
        bookingRequestRepository.save(bookingRequest);
    }

    public void updateBookingRequest(Integer bookingId, BookingRequest bookingRequest) {
        WorkSpace workSpace = workSpaceRepository.findWorkSpaceById(bookingRequest.getWorkspaceId());
        BookingRequest oldBookingRequest=bookingRequestRepository.findBookingRequestById(bookingId);
        if (oldBookingRequest == null) {
            throw new ApiException("*BookingRequest not found*");
        }
        if (workSpace == null) {
            throw new ApiException("*WorkSpace not found*");
        }
        releaseTimeSlot(oldBookingRequest.getWorkspaceId(),oldBookingRequest.getStartDateTime(),oldBookingRequest.getEndDateTime());

        List<TimeSlot> overlappingSlot = timeSlotRepository.findOverlappingTimeSlots(
                bookingRequest.getWorkspaceId(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime()
        );

        if (!overlappingSlot.isEmpty()) {
            throw new ApiException("*Overlapping TimeSlots Error*");
        }

        List<TimeSlot> availbleSlots = timeSlotRepository.getAvailableSlots(
                bookingRequest.getWorkspaceId(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime());

        if (availbleSlots.isEmpty()) {
            throw new ApiException("*No available time slots*");
        }
        for (TimeSlot timeSlot : availbleSlots) {
            if (timeSlot.getStartDateTime().isBefore(bookingRequest.getStartDateTime())
                    &&timeSlot.getEndDateTime().isAfter(bookingRequest.getEndDateTime())) {

                TimeSlot beforeSlot = new TimeSlot(null,
                        timeSlot.getWorkspaceId(),
                        timeSlot.getStartDateTime(),
                        bookingRequest.getStartDateTime(),
                        false
                );

                TimeSlot afterSlot = new TimeSlot(null,
                        timeSlot.getWorkspaceId(),
                        bookingRequest.getEndDateTime(),
                        timeSlot.getEndDateTime(),
                        false
                );

                timeSlotRepository.save(beforeSlot);
                timeSlotRepository.save(afterSlot);


                timeSlot.setStartDateTime(bookingRequest.getStartDateTime());
                timeSlot.setEndDateTime(bookingRequest.getEndDateTime());
                timeSlot.setIsBooked(true);
                timeSlotRepository.save(timeSlot);
            }
            else if (timeSlot.getStartDateTime().isBefore(bookingRequest.getStartDateTime())) {
                timeSlot.setEndDateTime(bookingRequest.getStartDateTime());
                timeSlotRepository.save(timeSlot);

            } else if (timeSlot.getEndDateTime().isAfter(bookingRequest.getEndDateTime())) {
                timeSlot.setStartDateTime(bookingRequest.getEndDateTime());
                timeSlotRepository.save(timeSlot);
            }

        }

        Double totalPrice = calculateTotalPrice(bookingRequest.getStartDateTime(),bookingRequest.getEndDateTime(), bookingRequest.getWorkspaceId());
        oldBookingRequest.setTotalPrice(totalPrice);
        oldBookingRequest.setUserId(bookingRequest.getUserId());
        oldBookingRequest.setWorkspaceId(bookingRequest.getWorkspaceId());
        oldBookingRequest.setStartDateTime(bookingRequest.getStartDateTime());
        oldBookingRequest.setEndDateTime(bookingRequest.getEndDateTime());
        oldBookingRequest.setBookingStatus("Pending");
        bookingRequestRepository.save(oldBookingRequest);
    }

    public Double calculateTotalPrice(LocalDateTime from, LocalDateTime to, Integer workSpaceId) {

        WorkSpace workSpace = workSpaceRepository.findWorkSpaceById(workSpaceId);
        if (workSpace == null) {
            throw new ApiException("*Workspace not found*");
        }

        if (from == null || to == null || !from.isBefore(to)) {
            throw new ApiException("*Invalid booking time period*");
        }

        Duration duration = Duration.between(from, to);

        Double days = (double) duration.toDays();

        Double hours = (double) duration.minusDays(days.longValue()).toHours();

        Double totalPrice = 0.0;
        if (days > 0) {
            totalPrice += workSpace.getPricePerDay() * days;
        }
        if (hours > 0) {
            totalPrice += workSpace.getPricePerHour() * hours;
        }

        return totalPrice;
    }

    public void deleteBookingRequest(Integer bookingId) {
        BookingRequest bookingRequest =bookingRequestRepository.findBookingRequestById(bookingId);
        if (bookingRequest == null) {
            throw new ApiException("*BookingRequest not found*");
        }
        bookingRequestRepository.delete(bookingRequest);

    }

    public void releaseTimeSlot(Integer workSpaceId, LocalDateTime startDateTime,LocalDateTime endDateTime) {
        List<TimeSlot> bookedSlots = timeSlotRepository.getBookedSlots(workSpaceId,startDateTime,endDateTime);
        for (TimeSlot timeSlot : bookedSlots) {
            timeSlot.setIsBooked(false);
            timeSlotRepository.save(timeSlot);
        }
    }

    public void cancelBookingRequest(Integer bookingId) {
        BookingRequest bookingRequest =bookingRequestRepository.findBookingRequestById(bookingId);
        if (bookingRequest == null) {
            throw new ApiException("*Booking Request not found*");
        }
        releaseTimeSlot(bookingRequest.getWorkspaceId(),bookingRequest.getStartDateTime(),bookingRequest.getEndDateTime());
        bookingRequest.setBookingStatus("Cancelled");
        bookingRequestRepository.save(bookingRequest);
    }

    public void companyDecision(Integer bookingId, String decision) {
        BookingRequest bookingRequest = bookingRequestRepository.findBookingRequestById(bookingId);

        if (bookingRequest == null) {
            throw new ApiException("*Booking Request not found*");
        }
        if (!decision.equalsIgnoreCase("Reject") && !decision.equalsIgnoreCase("Approve")) {
            throw new ApiException("*Invalid decision value. Must be 'Reject' or 'Approve'*");
        }
        if (decision.equalsIgnoreCase("Reject")) {
            releaseTimeSlot(bookingRequest.getWorkspaceId(), bookingRequest.getStartDateTime(), bookingRequest.getEndDateTime());
            bookingRequest.setBookingStatus("Rejected");
            bookingRequestRepository.save(bookingRequest);
        } else if (decision.equalsIgnoreCase("Approve")) {
            bookingRequest.setBookingStatus("Approved");
            bookingRequestRepository.save(bookingRequest);
        }
    }

    public String bookingRequestStatus(Integer bookingRequestId) {
        BookingRequest bookingRequest = bookingRequestRepository.findBookingRequestById(bookingRequestId);

        if (bookingRequest == null) {
            throw new ApiException("Booking Request not found");
        }

        if ("Approved".equals(bookingRequest.getBookingStatus()) &&
                bookingRequest.getEndDateTime().isBefore(LocalDateTime.now())&&
                bookingRequest.getEndDateTime().toLocalDate().equals(LocalDate.now())) {

            bookingRequest.setBookingStatus("Completed");
            bookingRequestRepository.save(bookingRequest);

            return "Completed";
        }
        return bookingRequest.getBookingStatus();
    }


    public void addBookingWithAvailableTimeSlot(Integer userId, Integer workSpaceId) {
        List<TimeSlot> timeSlotAvailable = timeSlotRepository.findTimeSlotByWorkspaceIdAndIsBooked(workSpaceId,false);
        if (timeSlotAvailable ==null) {
            throw new ApiException("*No available slots to booking*");
        }

        TimeSlot timeSlot = timeSlotAvailable.get(0);
        BookingRequest newBookingRequest = new BookingRequest();
        newBookingRequest.setUserId(userId);
        newBookingRequest.setWorkspaceId(workSpaceId);
        newBookingRequest.setStartDateTime(timeSlot.getStartDateTime());
        newBookingRequest.setEndDateTime(timeSlot.getEndDateTime());
        newBookingRequest.setBookingStatus("Pending");
        Double totalPrice= calculateTotalPrice(timeSlot.getStartDateTime(), timeSlot.getEndDateTime(),workSpaceId);
        newBookingRequest.setTotalPrice(totalPrice);
        newBookingRequest.setRequestDate(LocalDate.now());

        timeSlot.setIsBooked(true);
        timeSlotRepository.save(timeSlot);
        bookingRequestRepository.save(newBookingRequest);
    }


    public List<BookingRequest> getBookingByStatus(Integer userId,String status) {
        List<BookingRequest> listofStatus= bookingRequestRepository.findByUserIdAndBookingStatus(userId,status);
        return listofStatus;
    }

    public void cancelBookingUnapproved(Integer days) {
        LocalDateTime slot = LocalDateTime.now().minusDays(days);
        List<BookingRequest> unApproved=bookingRequestRepository.getUnapprovedBookingRequests(slot);
        for (BookingRequest bookingRequest : unApproved) {
            releaseTimeSlot(bookingRequest.getWorkspaceId(),bookingRequest.getStartDateTime(),bookingRequest.getEndDateTime());
            bookingRequest.setBookingStatus("Cancelled");
            bookingRequestRepository.save(bookingRequest);
        }
    }


    public void cancelAllBookingOfUser(Integer bookingId, Integer userId){
        BookingRequest bookingRequest = bookingRequestRepository.findBookingRequestById(bookingId);
        List<BookingRequest> userBooking = bookingRequestRepository.findBookingRequestByUserId(userId);

        if (!bookingRequest.getBookingStatus().equals("Completed")){
            throw new ApiException("*A user's booking requests can be canceled if they have booked in the past in same company*");
        }

        for(BookingRequest booking : userBooking){
            releaseTimeSlot(booking.getWorkspaceId(),booking.getStartDateTime(),booking.getEndDateTime());
            booking.setBookingStatus("Cancelled");
            bookingRequestRepository.save(booking);
        }
    }


}
