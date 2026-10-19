package service;

import dao.RepairOrderDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import model.RepairOrder;

/**
 * Service layer for Repair Order management:
 * Handles business validation rules before delegating to RepairOrderDAO.
 */
public class RepairOrderService {

    private final RepairOrderDAO dao;

    public RepairOrderService() {
        this.dao = new RepairOrderDAO();
    }

    public RepairOrderService(RepairOrderDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for inserting a repair order:
     * - RepairOrder cannot be null.
     * - Description cannot exceed 500 characters.
     * Returns generated OrderID (> 0) on success, or -1 on validation/DB error.
     */
    public int insert(Connection conn, RepairOrder o) throws SQLException {
        if (o == null) {
            return -1;
        }
        if (o.getDescription() != null && o.getDescription().length() > 500) {
            return -1;
        }
        return dao.insert(conn, o);
    }

    /**
     * Business validation for assigning staff to an order:
     * - OrderID must be > 0.
     * - StaffID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean assignStaff(int orderID, int staffID) {
        if (orderID <= 0 || staffID <= 0) {
            return false;
        }
        return dao.assignStaff(orderID, staffID);
    }

    /**
     * Business validation for staff accepting an order:
     * - OrderID must be > 0.
     * - StaffID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean accept(int orderID, int staffID) {
        if (orderID <= 0 || staffID <= 0) {
            return false;
        }
        return dao.accept(orderID, staffID);
    }

    /**
     * Business validation for staff rejecting an order:
     * - OrderID must be > 0.
     * - StaffID must be > 0.
     * - Reason cannot be null or empty.
     * Returns true on success, false on validation/DB error.
     */
    public boolean reject(int orderID, int staffID, String reason) {
        if (orderID <= 0 || staffID <= 0) {
            return false;
        }
        if (reason == null || reason.trim().isEmpty()) {
            return false;
        }
        return dao.reject(orderID, staffID, reason);
    }

    /**
     * Business validation for staff starting processing:
     * - OrderID must be > 0.
     * - StaffID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean startProcessing(int orderID, int staffID) {
        if (orderID <= 0 || staffID <= 0) {
            return false;
        }
        return dao.startProcessing(orderID, staffID);
    }

    /**
     * Business validation for completing an order:
     * - OrderID must be > 0.
     * - StaffID must be > 0.
     * - TotalCost cannot be null or negative.
     * Returns true on success, false on validation/DB error.
     */
    public boolean complete(int orderID, int staffID, BigDecimal totalCost) {
        if (orderID <= 0 || staffID <= 0) {
            return false;
        }
        if (totalCost == null || totalCost.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return dao.complete(orderID, staffID, totalCost);
    }

    /**
     * Business validation for deleting an order:
     * - OrderID must be > 0.
     * Returns true on success, false on validation/DB error (e.g. FK constraint).
     */
    public boolean delete(int orderID) {
        if (orderID <= 0) {
            return false;
        }
        return dao.delete(orderID);
    }
}
