package com.example.work_space_link.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "type ='Physical' or type ='Virtual' or type ='Hybrid'")
public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Company Id is required")
    @Positive(message = "Company Id of user must be Integer")
    @Column(columnDefinition = "int not null")
    private Integer companyId;

    @NotEmpty(message = "Name of WorkSpace is required")
    @Size(min = 8, message = "Name must be at least 8 characters long")
    @Column(columnDefinition = "varchar(40) NOT NULL")
    private String name;

    @NotEmpty(message = "WorkSpase description is required")
    @Column(columnDefinition = "varchar(250) not null")
    private String description;

    @NotEmpty(message = "Advantages of WorkSpace are required")
    @Column(columnDefinition = "varchar(250) not null")
    private String advantages;

    @NotEmpty(message = "WorkSpace type is required")
    @Pattern(regexp = "Physical|Virtual|Hybrid",message = "Type must be on of : Physical, Virtual or Hybrid.")
    @Column(columnDefinition = "varchar(10) not null")
    private String type;

    @Positive(message = "Price must be greater than 0")
    @Column(columnDefinition = "double")
    private Double pricePerHour;

    @Positive(message = "Price must be greater than 0")
    @Column(columnDefinition = "double")
    private Double pricePerDay;

    @Column(columnDefinition = "varchar(20)")
    private String city;

    @Column(columnDefinition = "varchar(60)")
    private String location;

}
