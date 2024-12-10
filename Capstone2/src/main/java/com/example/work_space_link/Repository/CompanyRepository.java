package com.example.work_space_link.Repository;

import com.example.work_space_link.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyById(int id);
}
