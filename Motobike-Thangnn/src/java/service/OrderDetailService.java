package service;

import dao.OrderDetailDAO;
import java.math.BigDecimal;

/**
 * Service layer for Order Detail management:
 * Handles business validation rules before delegating to OrderDetailDAO.
 */
public class OrderDetailService {

    private final OrderDetailDAO dao;

    public OrderDetailService() {
        this.dao = new OrderDetailDAO();
    }

    public OrderDetailService(OrderDetailDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for adding a service line:
     * - OrderID must be > 0.
     * - ServiceID must be > 0.
     * - Quantity must be > 0.
     * - UnitPrice cannot be null or negative.
     * Returns generated OrderDetailID (> 0) on success, or -1 on validation/DB error.
     */
    public int addServiceLine(int orderID, int serviceID, int quantity, BigDecimal unitPrice) {
        if (orderID <= 0 || serviceID <= 0) {
            return -1;
        }
        if (quantity <= 0) {
            return -1;
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            return -1;
        }
        return dao.addServiceLine(orderID, serviceID, quantity, unitPrice);
    }

    /**
     * Business validation for adding a part line:
     * - OrderID must be > 0.
     * - PartID must be > 0.
     * - Quantity must be > 0.
     * - UnitPrice cannot be null or negative.
     * Returns generated OrderDetailID (> 0) on success,
     * -1 if stock is insufficient, -2 if insert failed after deduction.
     */
    public int addPartLine(int orderID, int partID, int quantity, BigDecimal unitPrice) {
        if (orderID <= 0 || partID <= 0) {
            return -1;
        }
        if (quantity <= 0) {
            return -1;
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            return -1;
        }
        return dao.addPartLine(orderID, partID, quantity, unitPrice);
    }

    /**
     * Business validation for deleting a line:
     * - OrderDetailID must be > 0.
     * Returns true on success, false on validation/DB error.
     */
    public boolean deleteLine(int orderDetailID) {
        if (orderDetailID <= 0) {
            return false;
        }
        return dao.deleteLine(orderDetailID);
    }

    /**
     * Business validation for summing total for an order:
     * - OrderID must be > 0.
     * Returns the sum of all line totals, or BigDecimal.ZERO on validation error.
     */
    public BigDecimal sumTotalForOrder(int orderID) {
        if (orderID <= 0) {
            return BigDecimal.ZERO;
        }
        return dao.sumTotalForOrder(orderID);
    }
}
