package br.com.agendaon.service;

import br.com.agendaon.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServicePresenter> findAll() {
        List<ServiceModel> services = this.serviceRepository.findAll();
        List<ServicePresenter> result = new ArrayList<>();
        services.forEach(service -> result.add(new ServicePresenter(service)));
        return result;
    }

    public ServicePresenter findByCode(UUID code) {
        ServiceModel service = this.serviceRepository.findByCode(code).orElse(null);
        if (service == null) return null;
        return new ServicePresenter(service);
    }

    public ServicePresenter create(ServiceDTO serviceDTO) {
        try {
            ServiceModel serviceModel = new ServiceModel();
            serviceModel.setDescription(serviceDTO.getDescription());
            serviceModel.setValue(serviceDTO.getValue());
            serviceModel.setDuration(serviceDTO.getDuration());
            ServiceModel service = this.serviceRepository.save(serviceModel);
            return new ServicePresenter(service);
        } catch (Exception error) {
            System.out.println("Error create service " + error.getMessage());
            return null;
        }
    }

    public ServicePresenter update(ServiceDTO serviceDTO, UUID code) {
        try {
            ServiceModel service = this.serviceRepository.findByCode(code).orElseThrow(() -> null);
            Utils.copyNonNullProperties(serviceDTO, service);
            this.serviceRepository.save(service);
            return this.findByCode(code);
        } catch (Exception error) {
            System.out.println("Error update service " + error.getMessage());
            return null;
        }
    }

    public Boolean delete(UUID code) {
        try {
            ServiceModel service = this.serviceRepository.findByCode(code).orElseThrow(() -> null);
            this.serviceRepository.delete(service);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete service " + error.getMessage());
            return false;
        }
    }
}
