package lk.ijse.dep.pos.dao.custom.impl;

import java.util.List;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {



  public String getLastItemCode() throws Exception {
    List list = getSession().createQuery("SELECT i.code FROM Item i ORDER BY i.code DESC").setMaxResults(1).list();
    return list.size() > 0 ? (String) list.get(0) : null;

  }

}
