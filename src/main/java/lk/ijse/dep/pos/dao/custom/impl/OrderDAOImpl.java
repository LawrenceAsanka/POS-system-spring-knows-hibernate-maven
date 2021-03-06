package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAOImpl extends CrudDAOImpl<Order, String> implements OrderDAO {

    public String getLastOrderId() throws Exception {
        List list = getSession().createQuery("SELECT o.id FROM lk.ijse.dep.pos.entity.Order o ORDER BY id DESC").setMaxResults(1).list();
        return list.size() > 0 ? (String) list.get(0) : null;
    }

}
