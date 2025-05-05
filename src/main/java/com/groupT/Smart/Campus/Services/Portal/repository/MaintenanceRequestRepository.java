package com.groupT.Smart.Campus.Services.Portal.repository;

import com.groupT.Smart.Campus.Services.Portal.entity.MaintenanceRequest;
import com.groupT.Smart.Campus.Services.Portal.util.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    
    List<MaintenanceRequest> findByStatus(MaintenanceStatus status);
    
    List<MaintenanceRequest> findByReportedBy(String username);
    
    List<MaintenanceRequest> findByAssignedTo(String username);
    
    List<MaintenanceRequest> findByCategory(String category);
    
    @Query("SELECT COUNT(m) FROM MaintenanceRequest m WHERE m.status = ?1")
    int countByStatus(MaintenanceStatus status);
    
    @Query("SELECT m.category, COUNT(m) FROM MaintenanceRequest m GROUP BY m.category")
    List<Object[]> countByCategory();
} 