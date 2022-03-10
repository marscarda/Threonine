package threonine.mapping;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLLimit;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class MappingQ4 extends MappingQ3 {
    //**********************************************************************
    protected int PUBLICCOUNT = 15;
    //**********************************************************************
    /**
     * 
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected int selectPublicCount () throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem("COUNT", "*", "C");
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.SEARCHABLE, "!=", 0));
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
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select public map folders count\n");
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
     * Selects a list of public map folders.
     * @param search
     * @param offset
     * @return
     * @throws Exception 
     */
    protected MapFolder[] selectPublicFolders (String search, int offset) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.PARENTFOLDER);
        select.addItem(DBMaps.FolderTree.FOLDERNAME);
        select.addItem(DBMaps.FolderTree.PUBLICNAME);
        select.addItem(DBMaps.FolderTree.COSTPERUSE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBMaps.FolderTree.SEARCHABLE, "!=", 0));
        if (search != null)
            whr.addCondition(new SQLCondition(DBMaps.FolderTree.PUBLICNAME, "LIKE", "%" + search + "%"));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBMaps.FolderTree.PUBLICNAME);
        //-------------------------------------------------------
        sql.addClause(select);
        sql.addClause(whr);
        sql.addClause(order);
        //-------------------------------------------------------
        if (search == null) {
            SQLLimit limit = new SQLLimit(offset, PUBLICCOUNT);
            sql.addClause(limit);
        }        
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
                folder.costperuse = rs.getFloat(DBMaps.FolderTree.COSTPERUSE);
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
