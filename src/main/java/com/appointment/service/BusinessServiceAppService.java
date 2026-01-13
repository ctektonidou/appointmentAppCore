package com.appointment.service;

import com.appointment.dto.services.CreateServiceRequest;
import com.appointment.dto.services.ServiceResponse;
import com.appointment.dto.services.UpdateServiceRequest;
import com.appointment.model.Business;
import com.appointment.model.BusinessService;
import com.appointment.repository.BusinessRepository;
import com.appointment.repository.BusinessServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessServiceAppService {

    private final BusinessServiceRepository serviceRepo;
    private final BusinessRepository businessRepo;

    public BusinessServiceAppService(BusinessServiceRepository serviceRepo, BusinessRepository businessRepo) {
        this.serviceRepo = serviceRepo;
        this.businessRepo = businessRepo;
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> listServices(Long businessId, boolean activeOnly) {
        List<BusinessService> services = activeOnly
                ? serviceRepo.findByBusiness_IdAndIsActiveTrueOrderByNameAsc(businessId)
                : serviceRepo.findByBusiness_IdOrderByNameAsc(businessId);

        return services.stream().map(this::toResponse).toList();
    }

    @Transactional
    public ServiceResponse createService(Long businessId, CreateServiceRequest req) {
        Business business = businessRepo.findById(businessId)
                .orElseThrow(() -> new IllegalArgumentException("Business not found: " + businessId));

        if (serviceRepo.existsByBusiness_IdAndNameIgnoreCase(businessId, req.getName())) {
            throw new IllegalArgumentException("Service name already exists for this business.");
        }

        BusinessService s = new BusinessService();
        s.setBusiness(business);
        s.setName(req.getName());
        s.setDescription(req.getDescription());
        s.setDurationMinutes(req.getDurationMinutes());
        s.setPriceAmount(req.getPriceAmount());
        s.setCurrency(req.getCurrency());
        s.setColorHex(req.getColorHex());
        s.setActive(req.getIsActive() != null ? req.getIsActive() : true);

        BusinessService saved = serviceRepo.save(s);
        return toResponse(saved);
    }

    @Transactional
    public ServiceResponse updateService(Long businessId, Long serviceId, UpdateServiceRequest req) {
        BusinessService s = serviceRepo.findByIdAndBusiness_Id(serviceId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found for this business."));

        // Optional uniqueness check (only if name changes)
        if (!s.getName().equalsIgnoreCase(req.getName())
                && serviceRepo.existsByBusiness_IdAndNameIgnoreCase(businessId, req.getName())) {
            throw new IllegalArgumentException("Service name already exists for this business.");
        }

        s.setName(req.getName());
        s.setDescription(req.getDescription());
        s.setDurationMinutes(req.getDurationMinutes());
        s.setPriceAmount(req.getPriceAmount());
        s.setCurrency(req.getCurrency());
        s.setColorHex(req.getColorHex());
        s.setActive(req.getIsActive());

        return toResponse(serviceRepo.save(s));
    }

    @Transactional
    public void deactivateService(Long businessId, Long serviceId) {
        BusinessService s = serviceRepo.findByIdAndBusiness_Id(serviceId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found for this business."));

        s.setActive(false);
        serviceRepo.save(s);
    }

    private ServiceResponse toResponse(BusinessService s) {
        return new ServiceResponse(
                s.getId(),
                s.getBusiness().getId(),
                s.getName(),
                s.getDescription(),
                s.getDurationMinutes(),
                s.getPriceAmount(),
                s.getCurrency(),
                s.getColorHex(),
                s.getActive()
        );
    }

    @Transactional(readOnly = true)
    public ServiceResponse getService(Long businessId, Long serviceId) {
        BusinessService s = serviceRepo.findByIdAndBusiness_Id(serviceId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found for this business."));
        return toResponse(s);
    }

    @Transactional
    public ServiceResponse setActive(Long businessId, Long serviceId, boolean active) {
        BusinessService s = serviceRepo.findByIdAndBusiness_Id(serviceId, businessId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found for this business."));
        s.setActive(active);
        return toResponse(serviceRepo.save(s));
    }

}
