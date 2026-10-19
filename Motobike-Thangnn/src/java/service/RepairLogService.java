package service;

import dao.RepairLogDAO;

/**
 * Service layer for Repair Log management:
 * Handles business validation rules before delegating to RepairLogDAO.
 */
public class RepairLogService {

    private final RepairLogDAO dao;

    public RepairLogService() {
        this.dao = new RepairLogDAO();
    }

    public RepairLogService(RepairLogDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for inserting a repair log:
     * - OrderID must be > 0.
     * - StaffID must be > 0.
     * - Note cannot be null or empty.
     * Returns generated LogID (> 0) on success, or -1 on validation/DB error.
     */
    public int insert(int orderID, int staffID, String note) {
        if (orderID <= 0 || staffID <= 0) {
            return -1;
        }
        if (note == null || note.trim().isEmpty()) {
            return -1;
        }
        return dao.insert(orderID, staffID, note);
    }
}
