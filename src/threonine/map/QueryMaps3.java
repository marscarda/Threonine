package threonine.map;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.sql.SQLInsert;
//**************************************************************************
public class QueryMaps3 extends QueryMaps2 {
    //**********************************************************************
    /**
     * Insert a new folder usage into the table.
     * @param usage
     * @throws Exception 
     */
    protected void insertFolderUsage (FolderUsage usage) throws Exception {
        SQLInsert insert = new SQLInsert(DBMaps.FolderUse.TABLE);
        insert.addValue(DBMaps.FolderUse.PROJECTID, usage.projectid);
        insert.addValue(DBMaps.FolderUse.FOLDERID, usage.folderid);
        insert.addValue(DBMaps.FolderUse.COSTPERUSE, usage.costperuse);
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
}
//**************************************************************************