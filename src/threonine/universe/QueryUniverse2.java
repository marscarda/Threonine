package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.sql.SQLInsert;
import threonine.map.MapObject;
//**************************************************************************
public class QueryUniverse2 extends QueryUniverse1 {
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
            StringBuilder msg = new StringBuilder("Failed to insert new map object (universe subset)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    //**********************************************************************
}
//**************************************************************************
