package controller.customer;

import dao.RepairOrderDAO;
import service.FeedbackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.RepairOrder;
import model.User;
import util.ServletUtils;

/** "Rate service" use case (Customer actor). Only allowed on COMPLETED orders, once. */
@WebServlet(name = "CustomerFeedbackServlet", urlPatterns = {"/customer/feedback"})
public class CustomerFeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        int orderID = ServletUtils.getParamInt(req, "orderId", -1);
        int rating = ServletUtils.getParamInt(req, "rating", 0);
        String comment = ServletUtils.getParamTrimmed(req, "comment");

        RepairOrderDAO orderDAO = new RepairOrderDAO();
        RepairOrder order = orderDAO.getByID(orderID);
        FeedbackService feedbackService = new FeedbackService();

        boolean ownsOrder = order != null && order.getCustomerID() != null && order.getCustomerID() == user.getUserID();
        boolean isCompleted = order != null && "COMPLETED".equals(order.getStatus());

        if (!ownsOrder || !isCompleted) {
            resp.sendRedirect(req.getContextPath() + "/customer/order-detail?id=" + orderID + "&error=1");
            return;
        }

        int result = feedbackService.insert(orderID, user.getUserID(), rating, comment);
        if (result > 0) {
            resp.sendRedirect(req.getContextPath() + "/customer/order-detail?id=" + orderID + "&rated=1");
        } else {
            resp.sendRedirect(req.getContextPath() + "/customer/order-detail?id=" + orderID + "&error=1");
        }
    }
}
