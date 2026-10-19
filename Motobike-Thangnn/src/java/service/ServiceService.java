package service;

import dao.ServiceDAO;
import java.math.BigDecimal;
import java.util.List;
import model.Service;

/**
 * Service layer for Service management:
 * Handles business validation rules before delegating to ServiceDAO.
 */
public class ServiceService {

    private final ServiceDAO dao;

    public ServiceService() {
        this.dao = new ServiceDAO();
    }

    public ServiceService(ServiceDAO dao) {
        this.dao = dao;
    }

    public List<Service> getAll(boolean activeOnly) {
        return dao.getAll(activeOnly);
    }

    public Service getByID(int serviceID) {
        if (serviceID <= 0) {
            return null;
        }
        return dao.getByID(serviceID);
    }

    /**
     * Business validation for adding a service (sheet addService):
     * - Name cannot be null, cannot exceed 100 characters.
     * - Price cannot be null, cannot be negative (>= 0).
     * Returns generated ID (> 0) on success, or -1 on validation/DB error.
     */
    public int insert(Service s) {
        if (s == null) {
            return -1;
        }
        if (s.getServiceName() == null || s.getServiceName().length() > 100) {
            return -1;
        }
        if (s.getPrice() == null) {
            return -1;
        }
        return dao.insert(s);
    }

    /**
     * Business validation for updating a service (sheet updateService):
     * - ID must be > 0.
     * - Name cannot be null, cannot exceed 100 characters.
     * - Price cannot be null, cannot be negative (>= 0).
     * Returns true on success, false on validation/DB error.
     */
    public boolean update(Service s) {
        if (s == null || s.getServiceID() <= 0) {
            return false;
        }
        if (s.getServiceName() == null || s.getServiceName().length() > 100) {
            return false;
        }
        if (s.getPrice() == null || s.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return dao.update(s);
    }

    /**
     * Business validation for deactivating a service (sheet deactivateService):
     * - ID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean deactivate(int serviceID) {
        if (serviceID <= 0) {
            return false;
        }
        return dao.deactivate(serviceID);
    }
}
