package service;

import dao.MotorbikeDAO;
import model.Motorbike;

/**
 * Service layer for Motorbike management:
 * Handles business validation rules before delegating to MotorbikeDAO.
 */
public class MotorbikeService {

    private final MotorbikeDAO dao;

    public MotorbikeService() {
        this.dao = new MotorbikeDAO();
    }

    public MotorbikeService(MotorbikeDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for inserting a motorbike:
     * - CustomerID must be > 0.
     * - LicensePlate cannot be null or empty, cannot exceed 20 characters.
     * - LicensePlate must not already exist.
     * Returns generated MotorbikeID (> 0) on success, or -1 on validation/DB error.
     */
    public int insert(Motorbike m) {
        if (m == null) {
            return -1;
        }
        if (m.getCustomerID() <= 0) {
            return -1;
        }
        if (m.getLicensePlate() == null || m.getLicensePlate().trim().isEmpty()
                || m.getLicensePlate().length() > 20) {
            return -1;
        }
        if (dao.existsPlate(m.getLicensePlate().trim())) {
            return -1;
        }
        return dao.insert(m);
    }
}
