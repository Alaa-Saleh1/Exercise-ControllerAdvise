package com.example.work_space_link.Service;

import com.example.work_space_link.ApiResponse.ApiException;
import com.example.work_space_link.Model.Company;
import com.example.work_space_link.Model.WorkSpace;
import com.example.work_space_link.Repository.BookingRequestRepository;
import com.example.work_space_link.Repository.CompanyRepository;
import com.example.work_space_link.Repository.TimeSlotRepository;
import com.example.work_space_link.Repository.WorkSpaceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final CompanyRepository companyRepository;
    private final BookingRequestRepository bookingRequestRepository;
    private final TimeSlotRepository timeSlotRepository;

    public List<WorkSpace> findAllWorkSpaces() {
        return workSpaceRepository.findAll();
    }

    public void addWorkSpace(WorkSpace workSpace) {
        Company company = companyRepository.findCompanyById(workSpace.getCompanyId());
        if (company==null){
            throw new ApiException("*Company not found");
        }
        workSpaceRepository.save(workSpace);
    }

    public void updateWorkSpace(Integer id, WorkSpace workSpace) {
        WorkSpace oldWorkSpace = workSpaceRepository.findWorkSpaceById(id);

        if (oldWorkSpace == null) {
            throw new ApiException("*WorkSpace Not Found*");
        }
        oldWorkSpace.setName(workSpace.getName());
        oldWorkSpace.setDescription(workSpace.getDescription());
        oldWorkSpace.setAdvantages(workSpace.getAdvantages());
        oldWorkSpace.setType(workSpace.getType());
        oldWorkSpace.setPricePerHour(workSpace.getPricePerHour());
        oldWorkSpace.setPricePerDay(workSpace.getPricePerDay());
        oldWorkSpace.setLocation(workSpace.getLocation());
        oldWorkSpace.setCity(workSpace.getCity());
        workSpaceRepository.save(oldWorkSpace);
    }

    public void deleteWorkSpace(Integer id) {
        WorkSpace workSpace = workSpaceRepository.findWorkSpaceById(id);
        if (workSpace == null) {
            throw new ApiException("*WorkSpace not found*");
        }
        workSpaceRepository.delete(workSpace);
    }

    public List<WorkSpace> findWorkSpacesByType(String type) {
        List<WorkSpace> workSpaces = new ArrayList<>();
        if (type.equals("Physical") || type.equals("Virtual") || type.equals("Hybrid")) {
            workSpaces.addAll(workSpaceRepository.findWorkSpaceByType(type));
        }
        return workSpaces;
    }

    public List<WorkSpace> findWorkSpacesByLocation(String city) {
        List<WorkSpace> workSpaces = workSpaceRepository.findWorkSpaceByCity(city);
        return workSpaces;
    }

    public List<WorkSpace> findWorkSpacesByadvantages(String advantage) {
        List<WorkSpace> workSpaces = workSpaceRepository.findWorkSpaceByKeyword(advantage);
        return workSpaces;
    }

    public Double calculateRevenueOfWorkspaceMonthly(Integer workspaceId, LocalDateTime from, LocalDateTime to){
        Double revenue = bookingRequestRepository.calculateRevenueOfWorkspaceMonthly(workspaceId,from,to);
        return revenue;
    }

    public Double calculateRevenueOfWorkspace(Integer workspaceId){
        Double revenue = bookingRequestRepository.calculateRevenueOfWorkspace(workspaceId);
        return revenue;
    }

    public List<WorkSpace> getWorkSpacesbyLowRatings(Double num){
        if (num<=0||num>5){
            throw new ApiException("*wrong number, must be between 0 and 5");
        }
        List<WorkSpace> workSpaces = workSpaceRepository.getWorkSpacesbyLowRatings(num);
        return workSpaces;
    }


}
