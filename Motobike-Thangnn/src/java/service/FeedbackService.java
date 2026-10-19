package service;

import dao.FeedbackDAO;

/**
 * Service layer for Feedback management:
 * Handles business validation rules before delegating to FeedbackDAO.
 */
public class FeedbackService {

    private final FeedbackDAO dao;

    public FeedbackService() {
        this.dao = new FeedbackDAO();
    }

    public FeedbackService(FeedbackDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for inserting feedback:
     * - OrderID must be > 0.
     * - CustomerID must be > 0.
     * - Rating must be between 1 and 5.
     * - Only one feedback per order is allowed.
     * Returns generated FeedbackID (> 0) on success, or -1 on validation/DB error.
     */
    public int insert(int orderID, int customerID, int rating, String comment) {
        if (orderID <= 0 || customerID <= 0) {
            return -1;
        }
        if (rating < 1 || rating > 5) {
            return -1;
        }
        if (dao.existsForOrder(orderID)) {
            return -1;
        }
        return dao.insert(orderID, customerID, rating, comment);
    }
}
