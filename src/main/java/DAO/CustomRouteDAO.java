package DAO;

import dto.CustomBus;
import dto.CustomRoute;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import util.HibernateUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nik on 16.05.16.
 */
public class CustomRouteDAO {
    public Collection<CustomRoute> getAllCustomRoutes() throws SQLException {
        Session session = null;
        List<CustomRoute> routes = new ArrayList<CustomRoute>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query sqlQuery = session
                    .createSQLQuery("select {r.*}, {b.*} from bus b join route r on b.route_id = r.id;")
                    //addScalar("myCustomId", StandardBasicTypes.LONG)
                    //addScalar("myCustomNumber", StandardBasicTypes.STRING)
                    .addEntity("r", CustomRoute.class)
                    .addJoin("b", "r.customBusses")
                    .addEntity("r", CustomRoute.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            routes = sqlQuery.list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Ошибка 'getAllCustomBusses'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return routes;
    }
}
