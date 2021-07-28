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
import methionine.sql.SQLUpdate;
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
        insert.addValue(DBMaps.FolderTree.PUBLICNAME, folder.publicname);
        insert.addValue(DBMaps.FolderTree.SHAREPASS, folder.sharepass);
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
     * Updates a folder public name field to a given map folder id.
     * @param folderid
     * @param value
     * @throws Exception 
     */
    protected void updateMapFolderPublicName (long folderid, String value) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBMaps.FolderTree.TABLE);
        update.addSetColumn(DBMaps.FolderTree.PUBLICNAME, value);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.FOLDERID, "=", folderid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update map folder public name\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //======================================================================
    /**
     * Updates a folder share pass field to a given map folder id.
     * @param folderid
     * @param value
     * @throws Exception 
     */
    protected void updateMapFolderSharePass (long folderid, String value) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBMaps.FolderTree.TABLE);
        update.addSetColumn(DBMaps.FolderTree.SHAREPASS, value);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.FOLDERID, "=", folderid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update map folder share pass\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //======================================================================
    /**
     * Updates a folder searchable field to a given map folder id.
     * @param folderid
     * @param value
     * @throws Exception 
     */
    protected void updateMapFolderCostPerUse (long folderid, int value) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBMaps.FolderTree.TABLE);
        update.addSetColumn(DBMaps.FolderTree.COSTPERUSE, value);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.FOLDERID, "=", folderid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update map folder cosst per use\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //======================================================================
    /**
     * Updates a folder searchable field to a given map folder id.
     * @param folderid
     * @param value
     * @throws Exception 
     */
    protected void updateMapFolderSearchable (long folderid, int value) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBMaps.FolderTree.TABLE);
        update.addSetColumn(DBMaps.FolderTree.SEARCHABLE, value);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.FOLDERID, "=", folderid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update map folder searchable\n");
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
        select.addItem(DBMaps.FolderTree.PUBLICNAME);
        select.addItem(DBMaps.FolderTree.SHAREPASS);
        
        select.addItem(DBMaps.FolderTree.COSTPERUSE);
        select.addItem(DBMaps.FolderTree.SEARCHABLE);
        
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
            folder.publicname = rs.getString(DBMaps.FolderTree.PUBLICNAME);
            folder.sharepass = rs.getString(DBMaps.FolderTree.SHAREPASS);
            folder.costperuse = rs.getInt(DBMaps.FolderTree.COSTPERUSE);
            folder.searcheable = rs.getInt(DBMaps.FolderTree.SEARCHABLE);
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
     * 
     * @param searchkey
     * @return
     * @throws Exception 
     */
    protected MapFolder[] selectMapFoldersByShareID (String searchkey) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.PARENTFOLDER);
        select.addItem(DBMaps.FolderTree.FOLDERNAME);
        select.addItem(DBMaps.FolderTree.PUBLICNAME);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.SEARCHABLE, "!=", 0));
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.PUBLICNAME, "LIKE", "%" + searchkey + "%"));
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
            List<MapFolder> records = new ArrayList<>();
            MapFolder folder;
            while (rs.next()) {
                folder = new MapFolder();
                folder.folderid = rs.getLong(DBMaps.FolderTree.FOLDERID);
                folder.projectid = rs.getLong(DBMaps.FolderTree.PROJECTID);
                folder.parentid = rs.getLong(DBMaps.FolderTree.PARENTFOLDER);
                folder.name = rs.getString(DBMaps.FolderTree.FOLDERNAME);
                folder.publicname = rs.getString(DBMaps.FolderTree.PUBLICNAME);
                records.add(folder);
            }
            return records.toArray(new MapFolder[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map folders. Code: sharidsgert \n");
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
}
//**************************************************************************

