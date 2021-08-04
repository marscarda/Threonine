package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLInsert;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLWhere;
import threonine.map.MapObject;
import threonine.map.PointLocation;
//**************************************************************************
public class QueryUniverse2 extends QueryUniverse1 {
    //**********************************************************************
    /**
     * Inserts a record in the universe map objects table.
     * @param object
     * @throws Exception 
     */
    protected void insertMapObject (MapObject object) throws Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.SubsetMapObject.TABLE);
        insert.addValue(DBUniverse.SubsetMapObject.OBJECTID, object.objectid);
        insert.addValue(DBUniverse.SubsetMapObject.SUBSETID, object.recordid);
        insert.addValue(DBUniverse.SubsetMapObject.OBJTYPE, object.objtype);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map object (universe subset) efghfd\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Deletes map objects belonging to a subset id
     * @param subsetid
     * @throws Exception 
     */
    protected void deleteMapObject (long subsetid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBUniverse.SubsetMapObject.TABLE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubsetMapObject.SUBSETID, "=", subsetid));
        sql.addClause(delete);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete map object for a subset elksffd\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Inserts a new map point into de DB.
     * @param point
     * @throws Exception 
     */
    protected void insertPointLocation (PointLocation point) throws Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.LocationPoints.TABLE);
        insert.addValue(DBUniverse.LocationPoints.SUBSETID, point.recordid);
        insert.addValue(DBUniverse.LocationPoints.OBJECTID, point.objectid);
        insert.addValue(DBUniverse.LocationPoints.POINTINDEX, point.ptindex);
        insert.addValue(DBUniverse.LocationPoints.LATITUDE, point.latitude);
        insert.addValue(DBUniverse.LocationPoints.LONGITUDE, point.longitude);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert location point subset kftdfhd\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Deletes all location points for a given subset
     * @param subsetid
     * @throws Exception 
     */
    protected void deletePointLocations (long subsetid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBUniverse.LocationPoints.TABLE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.LocationPoints.SUBSETID, "=", subsetid));
        sql.addClause(delete);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete location points for a subset qwlksffd\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
}
//**************************************************************************
