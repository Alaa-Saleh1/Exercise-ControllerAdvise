package com.example.work_space_link.Controller;


import com.example.work_space_link.ApiResponse.ApiResponse;
import com.example.work_space_link.Model.Company;
import com.example.work_space_link.Service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-spase-link/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCompany() {
        List<Company> companyList = companyService.getAllCompany();
        return ResponseEntity.status(200).body(companyList);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
        return ResponseEntity.status(200).body(new ApiResponse("Company added successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Integer id,@RequestBody Company company) {
        companyService.updateCompany(id, company);
        return ResponseEntity.status(200).body(new ApiResponse("Company updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return ResponseEntity.status(200).body(new ApiResponse("Company deleted successfully"));
    }

    //endpoint
    @GetMapping("/revenue-company/{id}")
    public ResponseEntity<?> calculateRevenueOfCompany(@PathVariable Integer id){
        Double revenue = companyService.calculateRevenueOfCompany(id);
        return ResponseEntity.status(200).body(new ApiResponse("Calculate the company's revenue = "+revenue));
    }

    //endpoint
    @GetMapping("/revenue-company/id/{id}/from-date/{from}/to-date/{to}")
    public ResponseEntity<?> calculateRevenueOfWorkspaceMonthly(@PathVariable Integer id, @PathVariable LocalDateTime from, @PathVariable LocalDateTime to){
        Double revenue = companyService.calculateRevenueOfCompanyMonthly(id,from,to);
        return ResponseEntity.status(200).body(new ApiResponse("Calculate the company's revenue for a specific period  = "+revenue));
    }
}
