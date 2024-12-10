package com.example.work_space_link.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Id of WorkSpace is required")
    @Positive(message = "Id of WorkSpace must be Integer")
    @Column(columnDefinition = "int not null")
    private Integer workspaceId;

    @NotNull(message = "Start date and time are required")
    @FutureOrPresent(message = "Start date and time must be now or in the future")
    @Column(nullable = false)
    private LocalDateTime startDateTime;

   // @NotNull(message = "End date and time are required")
   @FutureOrPresent(message = "Start date and time must be now or in the future")
   @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private Boolean isBooked;
}
