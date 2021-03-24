package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLGroupBy;
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
            StringBuilder msg = new StringBuilder("Failed to select map records. Code: qiujhjgh \n");
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
     * Inserts a new Map Point Location into the database.
     * @param ptloc
     * @throws Exception 
     */
    protected void insertPointLocation (PointLocation ptloc) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.MapObjects.TABLE);
        insert.addValue(DBMaps.MapObjects.RECORDID, ptloc.recordid);
        insert.addValue(DBMaps.MapObjects.OBJECTCODE, ptloc.objcode);
        insert.addValue(DBMaps.MapObjects.POINTINDEX, ptloc.ptindex);
        insert.addValue(DBMaps.MapObjects.LATITUDE, ptloc.latitude);
        insert.addValue(DBMaps.MapObjects.LONGITUDE, ptloc.longitude);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new map point location\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
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
    //**********************************************************************
    /**
     * Selects and returns map points for a given object.
     * @param recordid
     * @param code
     * @return
     * @throws Exception 
     */
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
    //**********************************************************************
}
//**************************************************************************

