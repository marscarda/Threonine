package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLColumn;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLInnerJoin;
import methionine.sql.SQLInsert;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class QueryMaps3 extends QueryMaps2 {
    //**********************************************************************
    /**
     * Insert a new folder usage into the table.
     * @param usage
     * @throws Exception 
     */
    protected void insertFolderUsage (FolderUsage usage) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.FolderUsage.TABLE);
        insert.addValue(DBMaps.FolderUsage.PROJECTID, usage.projectid);
        insert.addValue(DBMaps.FolderUsage.FOLDERID, usage.folderid);
        insert.addValue(DBMaps.FolderUsage.COSTPERUSE, usage.costperuse);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new mapfolder usage\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    /**
     * 
     * @param projectid
     * @param folderid
     * @return 
     * @throws methionine.AppException 
     */
    protected FolderUsage selectFolderUsage (long projectid, long folderid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderUsage.TABLE);
        select.addItem(DBMaps.FolderUsage.PROJECTID);
        select.addItem(DBMaps.FolderUsage.FOLDERID);
        select.addItem(DBMaps.FolderUsage.COSTPERUSE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderUsage.PROJECTID, "=", projectid));
        whr.addCondition(new SQLCondition(DBMaps.FolderUsage.FOLDERID, "=", folderid));
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
            FolderUsage usage;
            if (!rs.next())
                throw new AppException("Folder Not used in the project", MapErrorCodes.FOLDERUSEAGENOTFOUND);
            usage = new FolderUsage();
            usage.projectid = rs.getLong(DBMaps.FolderUsage.PROJECTID);
            usage.folderid = rs.getLong(DBMaps.FolderUsage.FOLDERID);
            usage.costperuse = rs.getFloat(DBMaps.FolderUsage.COSTPERUSE);
            return usage;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map folder usage: qnuvxstfh\n");
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
     * Selects and return a set of folder usages given the project id.
     * @param projectid
     * @return
     * @throws Exception 
     */
    protected FolderUsage[] selectFolderUsages (long projectid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderUsage.TABLE);
        select.addItem(DBMaps.FolderUsage.PROJECTID);
        select.addItem(DBMaps.FolderUsage.FOLDERID);
        select.addItem(DBMaps.FolderUsage.COSTPERUSE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderUsage.PROJECTID, "=", projectid));
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
            List<FolderUsage> usages = new ArrayList<>();
            FolderUsage usage;
            while (rs.next()) {
                usage = new FolderUsage();
                usage.projectid = rs.getLong(DBMaps.FolderUsage.PROJECTID);
                usage.folderid = rs.getLong(DBMaps.FolderUsage.FOLDERID);
                usage.costperuse = rs.getFloat(DBMaps.FolderUsage.COSTPERUSE);
                usages.add(usage);
            }
            return usages.toArray(new FolderUsage[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map folder usages: qnuhrwgh\n");
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
     * @param projectid
     * @throws Exception 
     */
    protected void deleteFolderUsage (long folderid, long projectid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBMaps.FolderUsage.TABLE);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderUsage.FOLDERID, "=", folderid));
        if (projectid != 0) whr.addCondition(new SQLCondition(DBMaps.FolderUsage.PROJECTID, "=", projectid));
        sql.addClause(delete);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete map folder usage (mansers)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }
    //**********************************************************************
    /**
     * 
     * @param projectid
     * @return
     * @throws Exception 
     */
    protected MapFolder[] selectFoldersUsedInProject (long projectid) throws Exception {
        //----------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.PUBLICNAME);
        select.addItem(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.COSTPERUSE);
        //-------------------------
        SQLInnerJoin join = new SQLInnerJoin(DBMaps.FolderUsage.TABLE);
        SQLColumn columnfolder, columnusage;
        SQLCondition condition;
        //-------------------------
        columnfolder = new SQLColumn(DBMaps.FolderTree.TABLE, DBMaps.FolderTree.FOLDERID);
        columnusage = new SQLColumn(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.FOLDERID);
        condition = new SQLCondition(columnfolder, "=", columnusage);
        join.addCondition(condition);
        //-------------------------
        columnusage = new SQLColumn(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.PROJECTID);
        condition = new SQLCondition(columnusage, "=", projectid);
        join.addCondition(condition);
        //----------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.FolderTree.PUBLICNAME);
        //----------------------------------------------------------
        sql.addClause(select);
        sql.addClause(join);
        sql.addClause(order);
        //----------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapFolder> records = new ArrayList<>();
            MapFolder folder;
            while (rs.next()) {
                folder = new MapFolder();
                folder.folderid = rs.getLong(1);
                folder.projectid = rs.getLong(2);
                folder.publicname = rs.getString(3);
                folder.costperuse = rs.getFloat(4);
                records.add(folder);
            }
            return records.toArray(new MapFolder[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select folders used in project jrvjuys\n");
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
     * Returns records belonging to folders that are in use in a given project
     * and the name contains de given searchkey.
     * @param projectid
     * @param searchkey
     * @return
     * @throws Exception 
     */
    protected MapRecord[] selectRecordsByKey (long projectid, String searchkey) throws Exception {
        //----------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.MapRecords.TABLE);
        select.addItem(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.RECORDID);
        select.addItem(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.NAME);
        select.addItem(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.EXTRADATA);
        select.addItem(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.ADMINDIV);
        select.addItem(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.FOLDERID);
        select.addItem(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.COSTPERUSE);
        //-------------------------
        SQLInnerJoin join = new SQLInnerJoin(DBMaps.FolderUsage.TABLE);
        SQLColumn columnrecord, columnusage;
        SQLCondition condition;
        //-------------------------
        columnrecord = new SQLColumn(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.FOLDERID);
        columnusage = new SQLColumn(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.FOLDERID);
        condition = new SQLCondition(columnrecord, "=", columnusage);
        join.addCondition(condition);
        //-------------------------
        columnusage = new SQLColumn(DBMaps.FolderUsage.TABLE, DBMaps.FolderUsage.PROJECTID);
        condition = new SQLCondition(columnusage, "=", projectid);
        join.addCondition(condition);
        //-------------------------
        columnrecord = new SQLColumn(DBMaps.MapRecords.TABLE, DBMaps.MapRecords.NAME);
        condition = new SQLCondition(columnrecord, "LIKE", "%" + searchkey + "%");
        join.addCondition(condition);
        //----------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.FolderTree.PUBLICNAME);
        //----------------------------------------------------------
        sql.addClause(select);
        sql.addClause(join);
        sql.addClause(order);
        //----------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<MapRecord> records = new ArrayList<>();
            MapRecord record;
            while (rs.next()) {
                record = new MapRecord();
                record.recordid = rs.getLong(1);
                record.name = rs.getString(2);
                record.extradata = rs.getString(3);
                record.admindiv = rs.getString(4);
                record.folderid = rs.getLong(5);
                records.add(record);
            }
            return records.toArray(new MapRecord[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to used folders used in project jrvjuys\n");
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