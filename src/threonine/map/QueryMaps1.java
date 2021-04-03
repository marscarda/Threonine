    package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLInsert;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class QueryMaps1 extends QueryMapTabs {
    //**********************************************************************
    /**
     * Inserts a new map folder into the database.
     * @param folder
     * @throws Exception 
     */
    protected void insertMapFolder (MapFolder folder) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.FolderTree.TABLE);
        insert.addValue(DBMaps.FolderTree.FOLDERID, folder.folderid);
        insert.addValue(DBMaps.FolderTree.PROJECTID, folder.projectid);
        insert.addValue(DBMaps.FolderTree.PARENTFOLDER, folder.parentid);
        insert.addValue(DBMaps.FolderTree.FOLDERNAME, folder.name);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new mapfolder\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * Selects and returns a map folder given its ID.
     * @param folderid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapFolder selectMapFolder (long folderid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.PARENTFOLDER);
        select.addItem(DBMaps.FolderTree.FOLDERNAME);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.FOLDERID, "=", folderid));
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
                throw new AppException("Folder not found", AppException.OBJECTNOTFOUND);
            MapFolder folder;
            folder = new MapFolder();
            folder.folderid = rs.getLong(DBMaps.FolderTree.FOLDERID);
            folder.projectid = rs.getLong(DBMaps.FolderTree.PROJECTID);
            folder.parentid = rs.getLong(DBMaps.FolderTree.PARENTFOLDER);
            folder.name = rs.getString(DBMaps.FolderTree.FOLDERNAME);
            return folder;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map folders. Code: hrnfgsa \n");
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
     * Selects and returns a list of mapfolders given their parentid.
     * @param projectid
     * @param parentid
     * @return
     * @throws Exception 
     */
    protected MapFolder[] selectMapFolders (long projectid, long parentid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.PARENTFOLDER);
        select.addItem(DBMaps.FolderTree.FOLDERNAME);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.PARENTFOLDER, "=", parentid));
        if (parentid == 0) whr.addCondition(new SQLCondition(DBMaps.FolderTree.PROJECTID, "=", projectid));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.FolderTree.FOLDERNAME);
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
            List<MapFolder> records = new ArrayList<>();
            MapFolder folder;
            while (rs.next()) {
                folder = new MapFolder();
                folder.folderid = rs.getLong(DBMaps.FolderTree.FOLDERID);
                folder.projectid = rs.getLong(DBMaps.FolderTree.PROJECTID);
                folder.parentid = rs.getLong(DBMaps.FolderTree.PARENTFOLDER);
                folder.name = rs.getString(DBMaps.FolderTree.FOLDERNAME);
                records.add(folder);
            }
            return records.toArray(new MapFolder[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map folders. Code: hrnfgsa \n");
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
     * Inserts a new map record into the database.
     * @param record
     * @throws Exception 
     */
    protected void insertMapRecord (MapRecord record) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.MapRecords.TABLE);
        insert.addValue(DBMaps.MapRecords.RECORDID, record.recordid);
        insert.addValue(DBMaps.MapRecords.PROJECTID, record.projectid);
        insert.addValue(DBMaps.MapRecords.FOLDERID, record.folderid);
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
        select.addItem(DBMaps.MapRecords.PROJECTID);
        select.addItem(DBMaps.MapRecords.FOLDERID);
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
                throw new AppException("Folder not found", AppException.OBJECTNOTFOUND);
            MapRecord record = new MapRecord();
            record.recordid = rs.getLong(DBMaps.MapRecords.RECORDID);
            record.projectid = rs.getLong(DBMaps.MapRecords.PROJECTID);
            record.folderid = rs.getLong(DBMaps.MapRecords.FOLDERID);
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
        select.addItem(DBMaps.MapRecords.PROJECTID);
        select.addItem(DBMaps.MapRecords.FOLDERID);
        select.addItem(DBMaps.MapRecords.NAME);
        select.addItem(DBMaps.MapRecords.EXTRADATA);
        select.addItem(DBMaps.MapRecords.ADMINDIV);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapRecords.FOLDERID, "=", folderid));
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
                record.projectid = rs.getLong(DBMaps.MapRecords.PROJECTID);
                record.folderid = rs.getLong(DBMaps.MapRecords.FOLDERID);
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
    //**********************************************************************
    /**
     * Inserts a new map object in the DB.
     * @param object
     * @throws Exception 
     */
    protected void insertMapObject (MapObject object) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.Objects.TABLE);
        insert.addValue(DBMaps.Objects.OBJECTID, object.objectid);
        insert.addValue(DBMaps.Objects.RECORDID, object.recordid);
        insert.addValue(DBMaps.Objects.OBJTYPE, object.objtype);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
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
     * Inserts a new map point into de DB.
     * @param point
     * @throws Exception 
     */
    protected void insertPointLocation (PointAdd point) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.LocationPoints.TABLE);
        insert.addValue(DBMaps.LocationPoints.RECORDID, point.recordid);
        insert.addValue(DBMaps.LocationPoints.OBJECTID, point.objectid);
        insert.addValue(DBMaps.LocationPoints.POINTINDEX, point.index);
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
     * Selects a MapObject given its ID.
     * @param objectid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapObject selectMapObject (long objectid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.Objects.TABLE);
        select.addItem(DBMaps.Objects.OBJECTID);
        select.addItem(DBMaps.Objects.RECORDID);
        select.addItem(DBMaps.Objects.OBJTYPE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.Objects.OBJECTID, "=", objectid));
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
                throw new AppException("Map Object not found", AppException.OBJECTNOTFOUND);
            MapObject object = new MapObject();
            object.objectid = rs.getLong(DBMaps.Objects.OBJECTID);
            object.recordid = rs.getLong(DBMaps.Objects.RECORDID);
            object.objtype = rs.getInt(DBMaps.Objects.OBJTYPE);
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
    protected MapObject[] selectMapObjects (long recordid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.Objects.TABLE);
        select.addItem(DBMaps.Objects.OBJECTID);
        select.addItem(DBMaps.Objects.RECORDID);
        select.addItem(DBMaps.Objects.OBJTYPE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.Objects.RECORDID, "=", recordid));
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
                object.objectid = rs.getLong(DBMaps.Objects.OBJECTID);
                object.recordid = rs.getLong(DBMaps.Objects.RECORDID);
                object.objtype = rs.getInt(DBMaps.Objects.OBJTYPE);
                objects.add(object);
            }            
            return objects.toArray(new MapObject[0]);
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
     * Selects and return point locations.
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
    /*
    /**
     * Selects and returns an array of strings representing map object codes
     * @param recordid
     * @return
     * @throws Exception 
     */
    /*
    protected String[] selectMapObjectCodes (long recordid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapObjects.TABLE);
        select.addItem(DBMaps.MapObjects.OBJECTCODE);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.MapObjects.RECORDID, "=", recordid));
        sql.addClause(whr);
        SQLGroupBy group = new SQLGroupBy();
        group.addColumn(DBMaps.MapObjects.OBJECTCODE);
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
                codes.add(rs.getString(DBMaps.MapObjects.OBJECTCODE));
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
    */
    
    //**********************************************************************
    /**
     * Selects and returns map points for a given object.
     * @param recordid
     * @param code
     * @return
     * @throws Exception 
     */
    
    /*
    protected PointLocation[] selectLocationPoints (long recordid, String code) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapObjects.TABLE);
        select.addItem(DBMaps.MapObjects.RECORDID);
        select.addItem(DBMaps.MapObjects.OBJECTCODE);
        select.addItem(DBMaps.MapObjects.POINTINDEX);
        select.addItem(DBMaps.MapObjects.LATITUDE);
        select.addItem(DBMaps.MapObjects.LONGITUDE);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        if (recordid != 0) whr.addCondition(new SQLCondition(DBMaps.MapObjects.RECORDID, "=", recordid));
        if (code != null) whr.addCondition(new SQLCondition(DBMaps.MapObjects.OBJECTCODE, "=", code));
        sql.addClause(whr);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.MapObjects.OBJECTCODE);
        order.addColumn(DBMaps.MapObjects.POINTINDEX);
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
                point.recordid = rs.getLong(DBMaps.MapObjects.RECORDID);
                point.objcode = rs.getString(DBMaps.MapObjects.OBJECTCODE);
                point.ptindex = rs.getInt(DBMaps.MapObjects.POINTINDEX);
                point.latitude = rs.getFloat(DBMaps.MapObjects.LATITUDE);
                point.longitude = rs.getFloat(DBMaps.MapObjects.LONGITUDE);
                points.add(point);
            }
            return points.toArray(new PointLocation[0]);
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
    */
    //**********************************************************************
}
//**************************************************************************

