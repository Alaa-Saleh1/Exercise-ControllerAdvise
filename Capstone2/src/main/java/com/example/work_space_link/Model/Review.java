package com.example.work_space_link.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

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

    @NotNull(message = "Rating is required")
    @Min(value = 1,message = "Rating must be at least 1")
    @Max(value = 5,message = "Rating must not exceed 5")
    @Column(columnDefinition = "double not null")
    private Double rating;

    @NotEmpty(message = "Comment is required")
    @Column(columnDefinition = "varchar(250) not null")
    private String comment;

    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
