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
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLSelect;
import methionine.sql.SQLUpdate;
import methionine.sql.SQLWhere;
//**************************************************************************
public class QueryUniverse1 extends QueryUniverseTabs {
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
                throw new AppException("Universe not found", AppException.UNIVERSENOTFOUND);
            Universe universe = new Universe();
            universe.univerid = rs.getLong(DBUniverse.Universe.UNIVERSEID);
            universe.projectid = rs.getLong(DBUniverse.Universe.PROJECTID);
            universe.name = rs.getString(DBUniverse.Universe.NAME);
            universe.description = rs.getString(DBUniverse.Universe.DESCRIPTION);
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
                universe.projectid = rs.getLong(DBUniverse.Universe.UNIVERSEID);
                universe.name = rs.getString(DBUniverse.Universe.NAME);
                universe.description = rs.getString(DBUniverse.Universe.DESCRIPTION);
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
    //SUBSETS
    //******************************************************************
    /**
     * Inserts a new subset
     * @param subset
     * @throws Exception 
     */
    protected void insertSubSet (SubSet subset) throws Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.DBSubSets.TABLE);
        insert.addValue(DBUniverse.DBSubSets.SUBSETID, subset.subsetid);
        insert.addValue(DBUniverse.DBSubSets.UNIVERSEID, subset.universeid);
        insert.addValue(DBUniverse.DBSubSets.PARENTSUBSET, subset.parentsubset);
        insert.addValue(DBUniverse.DBSubSets.NAME, subset.name);
        insert.addValue(DBUniverse.DBSubSets.DESCRIPTION, subset.description);
        insert.addValue(DBUniverse.DBSubSets.POPULATION, subset.population);
        insert.addValue(DBUniverse.DBSubSets.WEIGHT, subset.weight);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert new subset \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //******************************************************************
    /**
     * Selects and returns a universe
     * @param universeid
     * @param subsetid
     * @return
     * @throws AppException SUBSETNOTFOUND
     * @throws Exception 
     */
    protected SubSet selectSubset (long universeid, long subsetid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.DBSubSets.TABLE);
        select.addItem(DBUniverse.DBSubSets.SUBSETID);
        select.addItem(DBUniverse.DBSubSets.UNIVERSEID);
        select.addItem(DBUniverse.DBSubSets.PARENTSUBSET);
        select.addItem(DBUniverse.DBSubSets.NAME);
        select.addItem(DBUniverse.DBSubSets.DESCRIPTION);
        select.addItem(DBUniverse.DBSubSets.POPULATION);
        select.addItem(DBUniverse.DBSubSets.WEIGHT);
        select.addItem(DBUniverse.DBSubSets.MAPRECORDID);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        if (universeid != 0) whr.addCondition(new SQLCondition(DBUniverse.DBSubSets.UNIVERSEID, "=", universeid));
        if (subsetid != 0) whr.addCondition(new SQLCondition(DBUniverse.DBSubSets.SUBSETID, "=", subsetid));
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
                throw new AppException("Subset not found", AppException.SUBSETNOTFOUND);
            SubSet subset;
            subset = new SubSet();
            subset.subsetid = rs.getLong(DBUniverse.DBSubSets.SUBSETID);
            subset.universeid = rs.getLong(DBUniverse.DBSubSets.UNIVERSEID);
            subset.parentsubset = rs.getLong(DBUniverse.DBSubSets.PARENTSUBSET);
            subset.name = rs.getString(DBUniverse.DBSubSets.NAME);
            subset.description = rs.getString(DBUniverse.DBSubSets.DESCRIPTION);
            subset.population = rs.getInt(DBUniverse.DBSubSets.POPULATION);
            subset.weight = rs.getInt(DBUniverse.DBSubSets.WEIGHT);
            subset.maprecordid = rs.getLong(DBUniverse.DBSubSets.MAPRECORDID);
            return subset;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select subset \n");
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
     * Selects an array of subsets.
     * @param universeid
     * @param parentid
     * @return
     * @throws Exception 
     */
    protected SubSet[] selectSubsets (long universeid, long parentid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.DBSubSets.TABLE);
        select.addItem(DBUniverse.DBSubSets.SUBSETID);
        select.addItem(DBUniverse.DBSubSets.UNIVERSEID);
        select.addItem(DBUniverse.DBSubSets.PARENTSUBSET);
        select.addItem(DBUniverse.DBSubSets.NAME);
        select.addItem(DBUniverse.DBSubSets.DESCRIPTION);
        select.addItem(DBUniverse.DBSubSets.POPULATION);
        select.addItem(DBUniverse.DBSubSets.WEIGHT);
        select.addItem(DBUniverse.DBSubSets.MAPRECORDID);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.DBSubSets.UNIVERSEID, "=", universeid));
        whr.addCondition(new SQLCondition(DBUniverse.DBSubSets.PARENTSUBSET, "=", parentid));
        sql.addClause(whr);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.DBSubSets.NAME);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<SubSet> subsets = new ArrayList<>();
            SubSet subset;
            while (rs.next()) {
                subset = new SubSet();
                subset.subsetid = rs.getLong(DBUniverse.DBSubSets.SUBSETID);
                subset.universeid = rs.getLong(DBUniverse.DBSubSets.UNIVERSEID);
                subset.parentsubset = rs.getLong(DBUniverse.DBSubSets.PARENTSUBSET);
                subset.name = rs.getString(DBUniverse.DBSubSets.NAME);
                subset.description = rs.getString(DBUniverse.DBSubSets.DESCRIPTION);
                subset.population = rs.getInt(DBUniverse.DBSubSets.POPULATION);
                subset.weight = rs.getInt(DBUniverse.DBSubSets.WEIGHT);
                subset.maprecordid = rs.getLong(DBUniverse.DBSubSets.MAPRECORDID);
                subsets.add(subset);
            }
            return subsets.toArray(new SubSet[0]);
        }        
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select subsets \n");
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
     * Deletes all subset given a universe id
     * @param universeid
     * @throws Exception 
     */
    protected void deleteSubsetsByUniverse (long universeid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLDelete delete = new SQLDelete(DBUniverse.DBSubSets.TABLE);
        sql.addClause(delete);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.DBSubSets.UNIVERSEID, "=", universeid));
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to delete subsets by universe\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //******************************************************************    
    /**
     * Updates the map record ID for the given subsetid
     * @param subsetid
     * @param recorid
     * @throws Exception 
     */
    @Deprecated
    protected void updateSubsetSetMapRecord (long subsetid, long recorid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBUniverse.DBSubSets.TABLE);
        update.addSetColumn(DBUniverse.DBSubSets.MAPRECORDID, recorid);
        sql.addClause(update);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.DBSubSets.SUBSETID, "=", subsetid));
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update map record id\n");
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
