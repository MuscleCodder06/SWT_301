package service;

import dao.PartDAO;
import java.math.BigDecimal;
import java.util.List;
import model.Part;

/**
 * Service layer for Spare Part management:
 * Handles business validation rules before delegating to PartDAO.
 */
public class PartService {

    private final PartDAO dao;

    public PartService() {
        this.dao = new PartDAO();
    }

    public PartService(PartDAO dao) {
        this.dao = dao;
    }

    public List<Part> getAll(boolean activeOnly) {
        return dao.getAll(activeOnly);
    }

    public Part getByID(int partID) {
        if (partID <= 0) {
            return null;
        }
        return dao.getByID(partID);
    }

    /**
     * Business validation for adding a part (sheet addPart):
     * - Name cannot be null, cannot exceed 100 characters.
     * - Unit cannot exceed 20 characters.
     * - Quantity cannot be negative (>= 0).
     * - Price cannot be null, cannot be negative (>= 0).
     * Returns generated ID (> 0) on success, or -1 on validation/DB error.
     */
    public int insert(Part p) {
        if (p == null) {
            return -1;
        }
        if (p.getPartName() == null || p.getPartName().length() > 100) {
            return -1;
        }
        if (p.getUnit() != null && p.getUnit().length() > 20) {
            return -1;
        }
        if (p.getPrice() == null) {
            return -1;
        }
        return dao.insert(p);
    }

    /**
     * Business validation for updating a part (sheet updatePart):
     * - ID must be > 0.
     * - Name cannot be null, cannot exceed 100 characters.
     * - Unit cannot exceed 20 characters.
     * - Quantity cannot be negative (>= 0).
     * - Price cannot be null, cannot be negative (>= 0).
     * Returns true on success, false on validation/DB error.
     */
    public boolean update(Part p) {
        if (p == null || p.getPartID() <= 0) {
            return false;
        }
        if (p.getPartName() == null || p.getPartName().length() > 100) {
            return false;
        }
        if (p.getUnit() != null && p.getUnit().length() > 20) {
            return false;
        }
        if (p.getQuantity() < 0) {
            return false;
        }
        if (p.getPrice() == null || p.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        return dao.update(p);
    }

    /**
     * Business validation for deactivating a part (sheet deactivatePart):
     * - ID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean deactivate(int partID) {
        if (partID <= 0) {
            return false;
        }
        return dao.deactivate(partID);
    }

    /**
     * Business validation for adding stock (admin restock):
     * - PartID must be > 0.
     * - Amount must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean addStock(int partID, int amount) {
        if (partID <= 0) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        return dao.addStock(partID, amount);
    }

    /**
     * Business validation for deducting stock:
     * - PartID must be > 0.
     * - Amount must be > 0.
     * Returns true if deduction succeeded (enough stock), false otherwise.
     */
    public boolean deductStock(java.sql.Connection conn, int partID, int amount)
            throws java.sql.SQLException {
        if (partID <= 0 || amount <= 0) {
            return false;
        }
        if (conn == null) {
            return false;
        }
        return dao.deductStock(conn, partID, amount);
    }
}
