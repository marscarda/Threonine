package threonine.slot;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLInsert;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLUpdate;
import methionine.sql.SQLWhere;
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
    /**
     * Updates the sample id for a slot.
     * @param allocation
     * @throws Exception 
     */
    protected void updateSampleSlot (SampleSlot allocation) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBSlot.SampleSlot.TABLE);
        update.addSetColumn(DBSlot.SampleSlot.SAMPLEID, allocation.sampleID());
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBSlot.SampleSlot.UNIVERSEID, "=", allocation.universeid));
        whr.addCondition(new SQLCondition(DBSlot.SampleSlot.SUBSETID, "=", allocation.subsetid));
        //-------------------------------------------------------
        sql.addClause(update);
        sql.addClause(whr);
        //-------------------------------------------------------
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update subset allocation (tramto)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
        //-------------------------------------------------------
    }    
    //**********************************************************************
    /**
     * Returns the number of slots allocation for a given subset.
     * @param allocation
     * @return
     * @throws Exception 
     */
    protected int selectSubsetSlotCount (SampleSlot allocation) throws Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBSlot.SampleSlot.TABLE);
        select.addItem("COUNT", "*", "Q");
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBSlot.SampleSlot.UNIVERSEID, "=", allocation.universeid));
        whr.addCondition(new SQLCondition(DBSlot.SampleSlot.SUBSETID, "=", allocation.subsetid));
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
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to count slots. (calende)\n");
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
     * 
     * @param universeid
     * @param subsetid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected SampleSlot selectSlot(long universeid, long subsetid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBSlot.SampleSlot.TABLE);
        select.addItem(DBSlot.SampleSlot.UNIVERSEID);
        select.addItem(DBSlot.SampleSlot.SUBSETID);
        select.addItem(DBSlot.SampleSlot.SAMPLEID);
        SQLWhere whr = new SQLWhere();
        if (universeid != 0) whr.addCondition(new SQLCondition(DBSlot.SampleSlot.UNIVERSEID, "=", universeid));
        whr.addCondition(new SQLCondition(DBSlot.SampleSlot.SUBSETID, "=", subsetid));
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
                throw new AppException("Sample Slot not found", SlotErrorCodes.SAMPLESLOTNOTFOUND);
            SampleSlot slot = new SampleSlot();
            slot.universeid = rs.getLong(DBSlot.SampleSlot.UNIVERSEID);
            slot.subsetid = rs.getLong(DBSlot.SampleSlot.SUBSETID);
            slot.sampleid = rs.getLong(DBSlot.SampleSlot.SAMPLEID);
            return slot;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select sample slot. (fanterre)\n");
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
     * Deletes slots given certain conditions
     * @param universeid
     * @param subsetid
     * @param sampleid
     * @param projectid
     * @throws Exception 
     */
    protected void deleteSlots (long universeid, long subsetid, long sampleid, long projectid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBSlot.SampleSlot.TABLE);
        SQLWhere whr = new SQLWhere();
        if (universeid != 0) whr.addCondition(new SQLCondition(DBSlot.SampleSlot.UNIVERSEID, "=", universeid));
        if (subsetid != 0) whr.addCondition(new SQLCondition(DBSlot.SampleSlot.SUBSETID, "=", subsetid));
        if (sampleid != 0) whr.addCondition(new SQLCondition(DBSlot.SampleSlot.SAMPLEID, "=", sampleid));
        if (projectid != 0) whr.addCondition(new SQLCondition(DBSlot.SampleSlot.PROJECTID, "=", projectid));
        //-------------------------------------------------------
        sql.addClause(delete);
        sql.addClause(whr);
        //-------------------------------------------------------
        PreparedStatement st = null;
        //-------------------------------------------------------        
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete subset slots (gartenwer)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
        
        
        
    }
    //**********************************************************************
}
//**************************************************************************
