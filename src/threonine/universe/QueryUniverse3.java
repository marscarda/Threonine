package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLInsert;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
import threonine.map.MapObject;
import threonine.map.PointLocation;
//**************************************************************************
public class QueryUniverse3 extends QueryUniverse2 {
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
     * Selects and returns an array of map objects given a subset id
     * @param subsetid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapObject[] selectMapObjects (long subsetid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.SubsetMapObject.TABLE);
        select.addItem(DBUniverse.SubsetMapObject.OBJECTID);
        select.addItem(DBUniverse.SubsetMapObject.SUBSETID);
        select.addItem(DBUniverse.SubsetMapObject.OBJTYPE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubsetMapObject.SUBSETID, "=", subsetid));
        //-------------------------------------------------------
        sql.addClause(select);
        sql.addClause(whr);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapObject> objects = new ArrayList<>();
            MapObject object;
            while (rs.next()) {
                object = new MapObject();
                object.objectid = rs.getLong(DBUniverse.SubsetMapObject.OBJECTID);
                object.recordid = rs.getLong(DBUniverse.SubsetMapObject.SUBSETID);
                object.objtype = rs.getInt(DBUniverse.SubsetMapObject.OBJTYPE);
                objects.add(object);
            }            
            return objects.toArray(new MapObject[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map objects for subset. Code: vrbcqwdrfh\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
        //-------------------------------------------------------
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
    /**
     * Selects and return point locations for an object.
     * @param objectid
     * @return
     * @throws Exception 
     */
    protected PointLocation[] selectPointLocations (long objectid) throws Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.LocationPoints.TABLE);
        select.addItem(DBUniverse.LocationPoints.OBJECTID);
        select.addItem(DBUniverse.LocationPoints.SUBSETID);
        select.addItem(DBUniverse.LocationPoints.POINTINDEX);
        select.addItem(DBUniverse.LocationPoints.LATITUDE);
        select.addItem(DBUniverse.LocationPoints.LONGITUDE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.LocationPoints.OBJECTID, "=", objectid));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.LocationPoints.POINTINDEX);
        //-------------------------------------------------------
        sql.addClause(select);
        sql.addClause(whr);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<PointLocation> points = new ArrayList<>();
            PointLocation point;
            while (rs.next()) {
                point = new PointLocation();
                point.objectid = objectid;
                point.recordid = rs.getLong(DBUniverse.LocationPoints.SUBSETID);
                point.ptindex = rs.getInt(DBUniverse.LocationPoints.POINTINDEX);
                point.latitude = rs.getFloat(DBUniverse.LocationPoints.LATITUDE);
                point.longitude = rs.getFloat(DBUniverse.LocationPoints.LONGITUDE);
                points.add(point);
            }            
            return points.toArray(new PointLocation[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map point locations. Code: tyhgdytrfh\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
}
//**************************************************************************
