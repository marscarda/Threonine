package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class MappingQ4 extends QueryMaps3 {
    //**********************************************************************
    protected int selectPublicCount () throws AppException, Exception {
        
        
        return 0;
    }
    //**********************************************************************
    /**
     * 
     * @param searchkey
     * @return
     * @throws Exception 
     */
    protected MapFolder[] selectMapFoldersByPublicName (String searchkey) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBMaps.FolderTree.TABLE);
        select.addItem(DBMaps.FolderTree.FOLDERID);
        select.addItem(DBMaps.FolderTree.PROJECTID);
        select.addItem(DBMaps.FolderTree.PARENTFOLDER);
        select.addItem(DBMaps.FolderTree.FOLDERNAME);
        select.addItem(DBMaps.FolderTree.PUBLICNAME);
        select.addItem(DBMaps.FolderTree.COSTPERUSE);
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
