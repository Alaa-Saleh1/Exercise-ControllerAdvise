package com.example.work_space_link.Service;

import com.example.work_space_link.ApiResponse.ApiException;
import com.example.work_space_link.Model.Company;
import com.example.work_space_link.Repository.BookingRequestRepository;
import com.example.work_space_link.Repository.CompanyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final BookingRequestRepository bookingRequestRepository;


    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    public void addCompany(Company company){
        companyRepository.save(company);
    }

    public void updateCompany(Integer id, Company company){
        Company oldCompany = companyRepository.findCompanyById(id);
        if(oldCompany == null){
            throw new ApiException("*Company not found*");
        }

        oldCompany.setName(company.getName());
        oldCompany.setUsername(company.getUsername());
        oldCompany.setPassword(company.getPassword());
        oldCompany.setDescription(company.getDescription());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setPhoneNumber(company.getPhoneNumber());
        oldCompany.setWebsiteAddress(company.getWebsiteAddress());
        companyRepository.save(oldCompany);

    }

    public void deleteCompany(Integer id){
        Company company = companyRepository.findCompanyById(id);
        if(company == null){
            throw new ApiException("*Company not found*");
        }
        companyRepository.delete(company);
    }


    public Double calculateRevenueOfCompany(Integer workspaceId){
        Double revenue = bookingRequestRepository.calculateRevenueOfCompany(workspaceId);
        return revenue;
    }


    public Double calculateRevenueOfCompanyMonthly(Integer workspaceId, LocalDateTime from, LocalDateTime to){
        Double revenue = bookingRequestRepository.calculateRevenueOfCompanyMonthly(workspaceId,from,to);
        return revenue;
    }







}
