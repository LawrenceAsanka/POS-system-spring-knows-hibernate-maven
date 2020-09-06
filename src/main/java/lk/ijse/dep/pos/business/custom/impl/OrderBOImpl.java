package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.custom.*;
import lk.ijse.dep.pos.entity.CustomEntity;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import lk.ijse.dep.pos.entity.OrderDetail;
import lk.ijse.dep.pos.util.OrderDetailTM;
import lk.ijse.dep.pos.util.OrderTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class OrderBOImpl implements OrderBO { // , Temp
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private QueryDAO queryDAO;

    @Transactional(readOnly = true)
    public String getNewOrderId() throws Exception {
        String lastOrderId = orderDAO.getLastOrderId();
        if (lastOrderId == null) {
            return "OD001";
        } else {
            int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public void placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception {
        orderDAO.save(new Order(order.getOrderId(),
                (Date) order.getOrderDate(),
                customerDAO.find(order.getCustomerId())));

        for (OrderDetailTM orderDetail : orderDetails) {
            orderDetailDAO.save(new OrderDetail(
                    order.getOrderId(), orderDetail.getCode(),
                    orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
            ));
            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            itemDAO.update(item);

        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderTM> getAllOrders() throws Exception {
        List<CustomEntity> odl = queryDAO.getOrderDetail();
        List<OrderTM> orderDetailsList = new ArrayList<>();
        for (CustomEntity orderDetails : odl) {
            BigDecimal total = orderDetails.getTotal();

            orderDetailsList.add(new OrderTM(orderDetails.getOrderId(), orderDetails.getOrderDate(), orderDetails.getCustomerId(),
                    orderDetails.getCustomerName(), Double.parseDouble(total.toString())));
        }
        return orderDetailsList;
    }
}
