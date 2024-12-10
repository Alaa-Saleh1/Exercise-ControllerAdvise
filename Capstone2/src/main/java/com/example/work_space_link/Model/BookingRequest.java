package com.example.work_space_link.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Id of user is required")
    @Positive(message = "Id of user must be Integer")
    @Column(columnDefinition = "int not null")
    private Integer userId;

    @NotNull(message = "Id of WorkSpace is required")
    @Positive(message = "Id of WorkSpace must be Integer")
    @Column(columnDefinition = "int not null")
    private Integer workspaceId;

    @NotNull(message = "Start date and time are required")
    @FutureOrPresent(message = "Start date and time must be now or in the future")
    @Column(nullable = false)
    private LocalDateTime startDateTime;

//    @NotNull(message = "End date and time are required")
    @Future(message = "End date and time must be in the future")
    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Pattern(regexp = "Pending|Approved|Rejected|Completed|Cancelled")
    @Column(columnDefinition = "varchar(10) default 'Pending'")
    private String bookingStatus ="Pending";

    @Column(columnDefinition = " double")
    private Double totalPrice;

    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
    private LocalDate requestDate;

}
