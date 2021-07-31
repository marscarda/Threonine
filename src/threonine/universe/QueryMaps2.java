package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLGroupBy;
import methionine.sql.SQLInsert;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class QueryMaps2 extends QueryMaps1 {
    //**********************************************************************
    /**
     * Inserts a new map point for a Map Record.
     * @param point
     * @throws Exception 
     */
    protected void insertMapPoint (MapPoint point) throws Exception {
        SQLInsert insert = new SQLInsert(DBMapObjects.TABLE);
        insert.addValue(DBMapObjects.RECORDID, point.recordid);
        insert.addValue(DBMapObjects.OBJECTCODE, point.objcode);
        insert.addValue(DBMapObjects.POINTINDEX, point.ptindex);
        insert.addValue(DBMapObjects.LATITUDE, point.latitude);
        insert.addValue(DBMapObjects.LONGITUDE, point.longitude);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map point\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Selects and return the number of unique objcode given a record ID.
     * @param recordid
     * @return
     * @throws Exception 
     */
    protected int selectMapObjectsCount (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapObjects.TABLE);
        select.addItem("COUNT", "*", "c");
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapObjects.RECORDID, "=", recordid));
        sql.addClause(whr);
        SQLGroupBy group = new SQLGroupBy();
        group.addColumn(DBMapObjects.OBJECTCODE);
        sql.addClause(group);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map objects count\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
    /**
     * Selects and returns an array of strings representing map object codes
     * @param recordid
     * @return
     * @throws Exception 
     */
    protected String[] selectMapObjectCodes (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapObjects.TABLE);
        select.addItem(DBMapObjects.OBJECTCODE);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapObjects.RECORDID, "=", recordid));
        sql.addClause(whr);
        SQLGroupBy group = new SQLGroupBy();
        group.addColumn(DBMapObjects.OBJECTCODE);
        sql.addClause(group);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<String> codes = new ArrayList<>();
            while (rs.next())
                codes.add(rs.getString(DBMapObjects.OBJECTCODE));
            return codes.toArray(new String[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map objects codes\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
    /**
     * Selects and returns map points for a given object.
     * @param recordid
     * @param code
     * @return
     * @throws Exception 
     */
    protected MapPoint[] selectMapPoints (long recordid, String code) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMapObjects.TABLE);
        select.addItem(DBMapObjects.RECORDID);
        select.addItem(DBMapObjects.OBJECTCODE);
        select.addItem(DBMapObjects.POINTINDEX);
        select.addItem(DBMapObjects.LATITUDE);
        select.addItem(DBMapObjects.LONGITUDE);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        if (recordid != 0) whr.addCondition(new SQLCondition(DBMapObjects.RECORDID, "=", recordid));
        if (code != null) whr.addCondition(new SQLCondition(DBMapObjects.OBJECTCODE, "=", code));
        sql.addClause(whr);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMapObjects.OBJECTCODE);
        order.addColumn(DBMapObjects.POINTINDEX);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapPoint> points = new ArrayList<>();
            MapPoint point;
            while (rs.next()) {
                point = new MapPoint();
                point.recordid = rs.getLong(DBMapObjects.RECORDID);
                point.objcode = rs.getString(DBMapObjects.OBJECTCODE);
                point.ptindex = rs.getInt(DBMapObjects.POINTINDEX);
                point.latitude = rs.getFloat(DBMapObjects.LATITUDE);
                point.longitude = rs.getFloat(DBMapObjects.LONGITUDE);
                points.add(point);
            }
            return points.toArray(new MapPoint[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map objects points\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //**********************************************************************
    /**
     * deletes map points given a map record id
     * @param recordid
     * @throws Exception 
     */
    protected void deleteMapPoints (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBMapObjects.TABLE);
        sql.addClause(delete);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMapObjects.RECORDID, "=", recordid));
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete from map points table\n");
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
