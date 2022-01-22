package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLInsert;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLUpdate;
import methionine.sql.SQLWhere;
//**************************************************************************
public class UniverseQ1 extends QueryUniverseTabs {
    //******************************************************************
    //UNIVERSES
    //******************************************************************
    /**
     * Inserts a new item into the universes table
     * @param universe
     * @throws Exception 
     */
    protected void insertUniverse (Universe universe) throws Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.Universe.TABLE);
        insert.addValue(DBUniverse.Universe.UNIVERSEID, universe.univerid);
        insert.addValue(DBUniverse.Universe.PROJECTID, universe.projectid);
        insert.addValue(DBUniverse.Universe.NAME, universe.name);
        insert.addValue(DBUniverse.Universe.DESCRIPTION, universe.description);
        insert.addValue(DBUniverse.Universe.CHANGETOPUB, universe.edittopub);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new universe \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //******************************************************************
    /**
     * Selects and return a universe given its ID.
     * @param universeid
     * @return
     * @throws AppException UNIVERSENOTFOUND
     * @throws Exception 
     */
    protected Universe selectUniverse (long universeid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.Universe.TABLE);
        select.addItem(DBUniverse.Universe.UNIVERSEID);
        select.addItem(DBUniverse.Universe.PROJECTID);
        select.addItem(DBUniverse.Universe.NAME);
        select.addItem(DBUniverse.Universe.DESCRIPTION);
        select.addItem(DBUniverse.Universe.PUBLIC);
        select.addItem(DBUniverse.Universe.PRICE);
        select.addItem(DBUniverse.Universe.CHANGETOPUB);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.Universe.UNIVERSEID, "=", universeid));
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
                throw new AppException("Universe not found", UniverseErrorCodes.UNIVERSENOTFOUND);
            Universe universe = new Universe();
            universe.univerid = rs.getLong(DBUniverse.Universe.UNIVERSEID);
            universe.projectid = rs.getLong(DBUniverse.Universe.PROJECTID);
            universe.name = rs.getString(DBUniverse.Universe.NAME);
            universe.description = rs.getString(DBUniverse.Universe.DESCRIPTION);
            universe.ispublic = rs.getInt(DBUniverse.Universe.PUBLIC);
            universe.price = rs.getFloat(DBUniverse.Universe.PRICE);
            universe.edittopub = rs.getInt(DBUniverse.Universe.CHANGETOPUB);
            return universe;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select universe \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //******************************************************************
    /**
     * Returns an array of universes.
     * @param projectid
     * @return
     * @throws Exception 
     */
    protected Universe[] selectUniverses (long projectid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.Universe.TABLE);
        select.addItem(DBUniverse.Universe.UNIVERSEID);
        select.addItem(DBUniverse.Universe.PROJECTID);
        select.addItem(DBUniverse.Universe.NAME);
        select.addItem(DBUniverse.Universe.DESCRIPTION);
        select.addItem(DBUniverse.Universe.PUBLIC);
        select.addItem(DBUniverse.Universe.PRICE);
        select.addItem(DBUniverse.Universe.CHANGETOPUB);
        sql.addClause(select);
        if (projectid != 0) {
            SQLWhere whr = new SQLWhere();
            whr.addCondition(new SQLCondition(DBUniverse.Universe.PROJECTID, "=", projectid));
            sql.addClause(whr);
        }
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<Universe> universes = new ArrayList<>();
            Universe universe;
            while (rs.next()) {
                universe = new Universe();
                universe.univerid = rs.getLong(DBUniverse.Universe.UNIVERSEID);
                universe.projectid = rs.getLong(DBUniverse.Universe.PROJECTID);
                universe.name = rs.getString(DBUniverse.Universe.NAME);
                universe.description = rs.getString(DBUniverse.Universe.DESCRIPTION);
                universe.ispublic = rs.getInt(DBUniverse.Universe.PUBLIC);
                universe.price = rs.getFloat(DBUniverse.Universe.PRICE);
                universe.edittopub = rs.getInt(DBUniverse.Universe.CHANGETOPUB);
                universes.add(universe);
            }
            return universes.toArray(new Universe[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select universe \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
            if (rs != null) try {rs.close();} catch(Exception e){}
        }
    }
    //******************************************************************
    /**
     * Updates a universe given its ID.
     * @param universeid
     * @param universe
     * @throws Exception 
     */
    protected void updateUniverse (long universeid, Universe universe) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBUniverse.Universe.TABLE);
        update.addSetColumn(DBUniverse.Universe.NAME, universe.name);
        update.addSetColumn(DBUniverse.Universe.DESCRIPTION, universe.description);
        sql.addClause(update);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.Universe.UNIVERSEID, "=", universeid));
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update universe \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //******************************************************************
    /**
     * Deletes a universe from the database
     * @param universeid
     * @throws Exception 
     */
    protected void deleteUniverse (long universeid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBUniverse.Universe.TABLE);
        sql.addClause(delete);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.Universe.UNIVERSEID, "=", universeid));
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete universe.\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //******************************************************************
}
//**************************************************************************
