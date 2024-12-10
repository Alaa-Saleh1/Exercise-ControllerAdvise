package com.example.work_space_link.Repository;

import com.example.work_space_link.Model.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Integer> {

    WorkSpace findWorkSpaceById(int id);

    List<WorkSpace> findWorkSpaceByType(String type);

    @Query("select w from WorkSpace w where w.advantages LIKE %:keyword%")
    List<WorkSpace> findWorkSpaceByKeyword(String keyword);

    @Query("select w from WorkSpace w where w.location LIKE %:city%")
    List<WorkSpace> findWorkSpaceByCity(String city);

    List<WorkSpace> findByCity(String city);


    @Query("select w from WorkSpace w where (select avg(r.rating) from Review r where r.workspaceId=w.id)<=?1")
    List<WorkSpace> getWorkSpacesbyLowRatings(Double num);



}
