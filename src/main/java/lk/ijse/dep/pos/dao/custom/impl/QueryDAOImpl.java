package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryDAOImpl implements QueryDAO {

    private  Session session;

    @Override
    public List<CustomEntity> getOrderDetail() throws Exception {

       return session.createNativeQuery("SELECT o.id AS orderId,o.date AS orderDate,c.id AS customerId,c.name AS customerName,(SUM(od.qty*od.unitPrice)) AS total FROM `Order` o\n" +
                "INNER JOIN OrderDetail od on o.id = od.orderId\n" +
                "INNER JOIN Customer c on o.customerId = c.id\n" +
                "GROUP BY o.id").setResultTransformer(Transformers.aliasToBean(CustomEntity.class)).list();

    }

    @Override
    public Session getSession() {
        return null;
    }
}
