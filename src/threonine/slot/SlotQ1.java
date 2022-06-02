package threonine.slot;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import methionine.sql.SQLInsert;
//**************************************************************************
public class SlotQ1 extends SlotTabs {
    //**********************************************************************
    /**
     * Inserts a new subset allocation into the database.
     * @param allocation
     * @throws Exception 
     */
    protected void insertSlot (SampleSlot allocation) throws Exception {
        SQLInsert insert = new SQLInsert(DBSlot.SampleSlot.TABLE);
        insert.addValue(DBSlot.SampleSlot.UNIVERSEID, allocation.universeid);
        insert.addValue(DBSlot.SampleSlot.SUBSETID, allocation.subsetid);
        insert.addValue(DBSlot.SampleSlot.SAMPLEID, allocation.sampleid);
        insert.addValue(DBSlot.SampleSlot.PROJECTID, allocation.projectid);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new subset slot (vermate)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }    
    //**********************************************************************
}
//**************************************************************************
