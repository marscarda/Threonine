package threonine.mapping;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
//**************************************************************************
public class MappingQ2 extends MappingQ1 {
    //**********************************************************************
    /**
     * Inserts a new map record into the database.
     * @param record
     * @throws Exception 
     */
    protected void insertMapRecord (MapRecord record) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.MapRecords.TABLE);
        insert.addValue(DBMaps.MapRecords.RECORDID, record.recordid);
        insert.addValue(DBMaps.MapRecords.LAYERID, record.layerid);
        insert.addValue(DBMaps.MapRecords.NAME, record.name);
        insert.addValue(DBMaps.MapRecords.EXTRADATA, record.extradata);
        insert.addValue(DBMaps.MapRecords.ADMINDIV, record.admindiv);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map record \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
        
    }
    //**********************************************************************
    /**
     * Selects and return a MapRecord given its ID.
     * @param recordid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapRecord selectMapRecord (long recordid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapRecords.TABLE);
        select.addItem(DBMaps.MapRecords.RECORDID);
        select.addItem(DBMaps.MapRecords.LAYERID);
        select.addItem(DBMaps.MapRecords.NAME);
        select.addItem(DBMaps.MapRecords.EXTRADATA);
        select.addItem(DBMaps.MapRecords.ADMINDIV);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapRecords.RECORDID, "=", recordid));
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
            if (!rs.next())
                throw new AppException("Map record not found", MapErrorCodes.MAPRECORDNOTFOUND);
            MapRecord record = new MapRecord();
            record.recordid = rs.getLong(DBMaps.MapRecords.RECORDID);
            record.layerid = rs.getLong(DBMaps.MapRecords.LAYERID);
            record.name = rs.getString(DBMaps.MapRecords.NAME);
            record.extradata = rs.getString(DBMaps.MapRecords.EXTRADATA);
            record.admindiv = rs.getString(DBMaps.MapRecords.ADMINDIV);
            return record;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map record. Code: vtefytrfh\n");
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
     * Selects and returns map records given the folder id
     * @param folderid
     * @return
     * @throws Exception 
     */
    protected MapRecord[] selectMapRecords (long folderid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapRecords.TABLE);
        select.addItem(DBMaps.MapRecords.RECORDID);
        select.addItem(DBMaps.MapRecords.LAYERID);
        select.addItem(DBMaps.MapRecords.NAME);
        select.addItem(DBMaps.MapRecords.EXTRADATA);
        select.addItem(DBMaps.MapRecords.ADMINDIV);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapRecords.LAYERID, "=", folderid));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.MapRecords.NAME);
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
            List<MapRecord> records = new ArrayList<>();
            MapRecord record;
            while (rs.next()) {
                record = new MapRecord();
                record.recordid = rs.getLong(DBMaps.MapRecords.RECORDID);
                record.layerid = rs.getLong(DBMaps.MapRecords.LAYERID);
                record.name = rs.getString(DBMaps.MapRecords.NAME);
                record.extradata = rs.getString(DBMaps.MapRecords.EXTRADATA);
                record.admindiv = rs.getString(DBMaps.MapRecords.ADMINDIV);
                records.add(record);
            }
            return records.toArray(new MapRecord[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map records. Code: qiujhjgh\n");
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
     * 
     * @param folderid
     * @param recordid
     * @throws Exception 
     */
    protected void deleteMapRecord (long folderid, long recordid) throws Exception {
        //---------------------------------------------
        if (folderid == 0 && recordid == 0) return;
        //---------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBMaps.MapRecords.TABLE);
        SQLWhere whr = new SQLWhere();
        if (folderid != 0) whr.addCondition(new SQLCondition(DBMaps.MapRecords.LAYERID, "=", folderid));
        if (recordid != 0) whr.addCondition(new SQLCondition(DBMaps.MapRecords.RECORDID, "=", recordid));
        sql.addClause(delete);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete map record (kandrazj)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    /**
     * Inserts a new map object in the DB.
     * @param object
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws Exception 
     */
    protected void insertMapObject (MapFeature object) throws SQLIntegrityConstraintViolationException, Exception {
        SQLInsert insert = new SQLInsert(DBMaps.MapFeature.TABLE);
        insert.addValue(DBMaps.MapFeature.FEATUREID, object.objectid);
        insert.addValue(DBMaps.MapFeature.RECORDID, object.recordid);
        insert.addValue(DBMaps.MapFeature.OBJTYPE, object.objtype);
        insert.addValue(DBMaps.MapFeature.COST, object.cost);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLIntegrityConstraintViolationException e) { throw e; }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map object \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Deletes from table of map objects by recordid
     * @param recordid
     * @throws Exception 
     */
    protected void deleteMapObjects (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBMaps.MapFeature.TABLE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapFeature.RECORDID, "=", recordid));
        sql.addClause(delete);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete object from table\n");
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
        SQLInsert insert = new SQLInsert(DBMaps.LocationPoints.TABLE);
        insert.addValue(DBMaps.LocationPoints.RECORDID, point.recordid);
        insert.addValue(DBMaps.LocationPoints.OBJECTID, point.objectid);
        insert.addValue(DBMaps.LocationPoints.POINTINDEX, point.ptindex);
        insert.addValue(DBMaps.LocationPoints.LATITUDE, point.latitude);
        insert.addValue(DBMaps.LocationPoints.LONGITUDE, point.longitude);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map object point\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Deletes from table of location points by recordid
     * @param recordid
     * @throws Exception 
     */
    protected void deletePointLocations (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBMaps.LocationPoints.TABLE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.LocationPoints.RECORDID, "=", recordid));
        sql.addClause(delete);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete location points from table\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Selects a MapFeature given its ID.
     * @param objectid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapFeature selectMapObject (long objectid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapFeature.TABLE);
        select.addItem(DBMaps.MapFeature.FEATUREID);
        select.addItem(DBMaps.MapFeature.RECORDID);
        select.addItem(DBMaps.MapFeature.OBJTYPE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapFeature.FEATUREID, "=", objectid));
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
            if (!rs.next())
                throw new AppException("Map Object not found", MapErrorCodes.MAPOBJECTNOTFOUND);
            MapFeature object = new MapFeature();
            object.objectid = rs.getLong(DBMaps.MapFeature.FEATUREID);
            object.recordid = rs.getLong(DBMaps.MapFeature.RECORDID);
            object.objtype = rs.getInt(DBMaps.MapFeature.OBJTYPE);
            return object;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map record. Code: vtefytrfh\n");
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
     * Returns an array of MapObjects given the recordid
     * @param recordid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapFeature[] selectMapObjects (long recordid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapFeature.TABLE);
        select.addItem(DBMaps.MapFeature.FEATUREID);
        select.addItem(DBMaps.MapFeature.RECORDID);
        select.addItem(DBMaps.MapFeature.OBJTYPE);
        select.addItem(DBMaps.MapFeature.COST);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapFeature.RECORDID, "=", recordid));
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
            List<MapFeature> objects = new ArrayList<>();
            MapFeature object;
            while (rs.next()) {
                object = new MapFeature();
                object.objectid = rs.getLong(DBMaps.MapFeature.FEATUREID);
                object.recordid = rs.getLong(DBMaps.MapFeature.RECORDID);
                object.objtype = rs.getInt(DBMaps.MapFeature.OBJTYPE);
                object.cost = rs.getFloat(DBMaps.MapFeature.COST);
                objects.add(object);
            }            
            return objects.toArray(new MapFeature[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select objects map record. Code: vtefyztrfh\n");
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
     * Selects and return point locations for an object.
     * @param objectid
     * @return
     * @throws Exception 
     */
    protected PointLocation[] selectPointLocations (long objectid) throws Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.LocationPoints.TABLE);
        select.addItem(DBMaps.LocationPoints.OBJECTID);
        select.addItem(DBMaps.LocationPoints.RECORDID);
        select.addItem(DBMaps.LocationPoints.POINTINDEX);
        select.addItem(DBMaps.LocationPoints.LATITUDE);
        select.addItem(DBMaps.LocationPoints.LONGITUDE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.LocationPoints.OBJECTID, "=", objectid));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.LocationPoints.POINTINDEX);
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
                point.recordid = rs.getLong(DBMaps.LocationPoints.RECORDID);
                point.ptindex = rs.getInt(DBMaps.LocationPoints.POINTINDEX);
                point.latitude = rs.getFloat(DBMaps.LocationPoints.LATITUDE);
                point.longitude = rs.getFloat(DBMaps.LocationPoints.LONGITUDE);
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
