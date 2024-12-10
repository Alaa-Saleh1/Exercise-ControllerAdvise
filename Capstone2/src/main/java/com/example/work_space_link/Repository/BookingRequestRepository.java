package com.example.work_space_link.Repository;

import com.example.work_space_link.Model.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Integer> {

    BookingRequest findBookingRequestById(Integer bookingId);

    @Query("SELECT b from BookingRequest b where b.userId=?1 and b.bookingStatus=?2")
    List<BookingRequest> findByUserIdAndBookingStatus(Integer id, String status);

    @Query("select b from BookingRequest b where b.bookingStatus='Panding' and b.requestDate<?1")
    List<BookingRequest> getUnapprovedBookingRequests(LocalDateTime slot);

    @Query("select SUM(b.totalPrice) from BookingRequest b JOIN WorkSpace w on b.workspaceId=w.id where w.companyId=?1 and b.bookingStatus='Completed'")
    Double calculateRevenueOfCompany(Integer companyId);


    @Query("select SUM(b.totalPrice) from BookingRequest b JOIN WorkSpace w on b.workspaceId=w.id where w.companyId=?1 and b.bookingStatus='Completed'" +
            "and b.startDateTime>=?2 and b.endDateTime<=?3")
    Double calculateRevenueOfCompanyMonthly(Integer companyId, LocalDateTime from, LocalDateTime to);


    @Query("select SUM(b.totalPrice) from BookingRequest b WHERE b.workspaceId=?1 and b.bookingStatus='Completed'")
    Double calculateRevenueOfWorkspace(Integer workspaceId);


    @Query("select SUM(b.totalPrice) from BookingRequest b WHERE b.workspaceId=?1 and b.bookingStatus='Completed'" +
            "and b.startDateTime>=?2 and b.endDateTime<=?3")
    Double calculateRevenueOfWorkspaceMonthly(Integer workspaceId, LocalDateTime from, LocalDateTime to);

    List<BookingRequest> findBookingRequestByUserId(Integer userId);
}
