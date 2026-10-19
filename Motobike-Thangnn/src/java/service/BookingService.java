package service;

import dao.CustomerBookingDAO;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service layer for Customer Booking management:
 * Handles business validation rules before delegating to CustomerBookingDAO.
 */
public class BookingService {

    private final CustomerBookingDAO dao;

    public BookingService() {
        this.dao = new CustomerBookingDAO();
    }

    public BookingService(CustomerBookingDAO dao) {
        this.dao = dao;
    }

    /**
     * Business validation for updating booking status:
     * - BookingID must be > 0.
     * - Status cannot be null or empty.
     * - Valid statuses: PENDING, CONFIRMED, REJECTED, CONVERTED.
     * Returns true on success, false on validation/DB error.
     */
    public boolean updateStatus(int bookingID, String status, String rejectReason) {
        if (bookingID <= 0) {
            return false;
        }
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        String s = status.trim().toUpperCase();
        if (!s.equals("PENDING") && !s.equals("CONFIRMED")
                && !s.equals("REJECTED") && !s.equals("CONVERTED")) {
            return false;
        }
        if ("REJECTED".equals(s) && (rejectReason == null || rejectReason.trim().isEmpty())) {
            return false;
        }
        return dao.updateStatus(bookingID, s, rejectReason);
    }

    /**
     * Business validation for marking a booking as converted:
     * - BookingID must be > 0.
     * - RepairOrderID must be > 0.
     * - Requires an existing connection (participates in caller's transaction).
     * Returns true on success, false on validation error.
     * Throws SQLException on DB error.
     */
    public boolean markConverted(Connection conn, int bookingID, int repairOrderID) throws SQLException {
        if (bookingID <= 0 || repairOrderID <= 0) {
            return false;
        }
        if (conn == null) {
            return false;
        }
        return dao.markConverted(conn, bookingID, repairOrderID);
    }
}
