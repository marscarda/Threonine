package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import methionine.AppException;
import methionine.sql.SQLCondition;
import methionine.sql.SQLDelete;
import methionine.sql.SQLInsert;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLUpdate;
import methionine.sql.SQLWhere;
//**************************************************************************
public class UniverseQ2 extends UniverseQ1 {
    //******************************************************************
    //SUBSETS
    //******************************************************************
    /**
     * Inserts a new subset
     * @param subset
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws Exception 
     */
    protected void insertSubSet (SubSet subset) throws SQLIntegrityConstraintViolationException, Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.SubSets.TABLE);
        insert.addValue(DBUniverse.SubSets.SUBSETID, subset.subsetid);
        insert.addValue(DBUniverse.SubSets.UNIVERSEID, subset.universeid);
        insert.addValue(DBUniverse.SubSets.PARENTSUBSET, subset.parentsubset);
        insert.addValue(DBUniverse.SubSets.NAME, subset.name);
        insert.addValue(DBUniverse.SubSets.DESCRIPTION, subset.description);
        insert.addValue(DBUniverse.SubSets.POPULATION, subset.population);
        insert.addValue(DBUniverse.SubSets.WEIGHT, subset.weight);
        insert.addValue(DBUniverse.SubSets.MAPSTATUS, subset.mapstatus);
        insert.addValue(DBUniverse.SubSets.SUBSETCOST, subset.subsetcost);
        insert.addValue(DBUniverse.SubSets.MAPCOST, subset.mapcost);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLIntegrityConstraintViolationException e) { throw e; }
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
     * Selects the root subset for a given universe.
     * @param universeid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected SubSet selectTopSubset (long universeid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.SubSets.TABLE);
        select.addItem(DBUniverse.SubSets.SUBSETID);
        select.addItem(DBUniverse.SubSets.UNIVERSEID);
        select.addItem(DBUniverse.SubSets.PARENTSUBSET);
        select.addItem(DBUniverse.SubSets.NAME);
        select.addItem(DBUniverse.SubSets.DESCRIPTION);
        select.addItem(DBUniverse.SubSets.POPULATION);
        select.addItem(DBUniverse.SubSets.WEIGHT);
        select.addItem(DBUniverse.SubSets.MAPSTATUS);
        select.addItem(DBUniverse.SubSets.SUBSETCOST);
        select.addItem(DBUniverse.SubSets.MAPCOST);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.PARENTSUBSET, "=", 0));
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.UNIVERSEID, "=", universeid));
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
                throw new AppException("Subset not found", UniverseErrorCodes.SUBSETNOTFOUND);
            SubSet subset;
            subset = new SubSet();
            subset.subsetid = rs.getLong(DBUniverse.SubSets.SUBSETID);
            subset.universeid = rs.getLong(DBUniverse.SubSets.UNIVERSEID);
            subset.parentsubset = rs.getLong(DBUniverse.SubSets.PARENTSUBSET);
            subset.name = rs.getString(DBUniverse.SubSets.NAME);
            subset.description = rs.getString(DBUniverse.SubSets.DESCRIPTION);
            subset.population = rs.getInt(DBUniverse.SubSets.POPULATION);
            subset.weight = rs.getInt(DBUniverse.SubSets.WEIGHT);
            subset.mapstatus = rs.getInt(DBUniverse.SubSets.MAPSTATUS);
            subset.subsetcost = rs.getFloat(DBUniverse.SubSets.SUBSETCOST);
            subset.mapcost = rs.getFloat(DBUniverse.SubSets.MAPCOST);
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
     * Selects and returns a universe
     * @param universeid
     * @param subsetid
     * @return
     * @throws AppException SUBSETNOTFOUND
     * @throws Exception 
     */
    protected SubSet selectSubset (long universeid, long subsetid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.SubSets.TABLE);
        select.addItem(DBUniverse.SubSets.SUBSETID);
        select.addItem(DBUniverse.SubSets.UNIVERSEID);
        select.addItem(DBUniverse.SubSets.PARENTSUBSET);
        select.addItem(DBUniverse.SubSets.NAME);
        select.addItem(DBUniverse.SubSets.DESCRIPTION);
        select.addItem(DBUniverse.SubSets.POPULATION);
        select.addItem(DBUniverse.SubSets.WEIGHT);
        select.addItem(DBUniverse.SubSets.MAPSTATUS);
        select.addItem(DBUniverse.SubSets.SUBSETCOST);
        select.addItem(DBUniverse.SubSets.MAPCOST);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.SUBSETID, "=", subsetid));
        if (universeid != 0) whr.addCondition(new SQLCondition(DBUniverse.SubSets.UNIVERSEID, "=", universeid));
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
                throw new AppException("Subset not found", UniverseErrorCodes.SUBSETNOTFOUND);
            SubSet subset;
            subset = new SubSet();
            subset.subsetid = rs.getLong(DBUniverse.SubSets.SUBSETID);
            subset.universeid = rs.getLong(DBUniverse.SubSets.UNIVERSEID);
            subset.parentsubset = rs.getLong(DBUniverse.SubSets.PARENTSUBSET);
            subset.name = rs.getString(DBUniverse.SubSets.NAME);
            subset.description = rs.getString(DBUniverse.SubSets.DESCRIPTION);
            subset.population = rs.getInt(DBUniverse.SubSets.POPULATION);
            subset.weight = rs.getInt(DBUniverse.SubSets.WEIGHT);
            subset.mapstatus = rs.getInt(DBUniverse.SubSets.MAPSTATUS);
            subset.subsetcost = rs.getFloat(DBUniverse.SubSets.SUBSETCOST);
            subset.mapcost = rs.getFloat(DBUniverse.SubSets.MAPCOST);
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
        SQLSelect select = new SQLSelect(DBUniverse.SubSets.TABLE);
        select.addItem(DBUniverse.SubSets.SUBSETID);
        select.addItem(DBUniverse.SubSets.UNIVERSEID);
        select.addItem(DBUniverse.SubSets.PARENTSUBSET);
        select.addItem(DBUniverse.SubSets.NAME);
        select.addItem(DBUniverse.SubSets.DESCRIPTION);
        select.addItem(DBUniverse.SubSets.POPULATION);
        select.addItem(DBUniverse.SubSets.WEIGHT);
        select.addItem(DBUniverse.SubSets.MAPSTATUS);
        select.addItem(DBUniverse.SubSets.SUBSETCOST);
        select.addItem(DBUniverse.SubSets.MAPCOST);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.UNIVERSEID, "=", universeid));
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.PARENTSUBSET, "=", parentid));
        sql.addClause(whr);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.SubSets.NAME);
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
                subset.subsetid = rs.getLong(DBUniverse.SubSets.SUBSETID);
                subset.universeid = rs.getLong(DBUniverse.SubSets.UNIVERSEID);
                subset.parentsubset = rs.getLong(DBUniverse.SubSets.PARENTSUBSET);
                subset.name = rs.getString(DBUniverse.SubSets.NAME);
                subset.description = rs.getString(DBUniverse.SubSets.DESCRIPTION);
                subset.population = rs.getInt(DBUniverse.SubSets.POPULATION);
                subset.weight = rs.getInt(DBUniverse.SubSets.WEIGHT);
                subset.mapstatus = rs.getInt(DBUniverse.SubSets.MAPSTATUS);
                subset.subsetcost = rs.getFloat(DBUniverse.SubSets.SUBSETCOST);
                subset.mapcost = rs.getFloat(DBUniverse.SubSets.MAPCOST);
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
        SQLDelete delete = new SQLDelete(DBUniverse.SubSets.TABLE);
        sql.addClause(delete);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.UNIVERSEID, "=", universeid));
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
    //SUBSET UPDATES
    //******************************************************************
    /**
     * Updates population for a subset.
     * @param universeid
     * @param subsetid
     * @param population
     * @throws Exception 
     */
    protected void updateSubsetPopulation (long universeid, long subsetid, int population) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBUniverse.SubSets.TABLE);
        update.addSetColumn(DBUniverse.SubSets.POPULATION, population);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.UNIVERSEID, "=", universeid));
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.SUBSETID, "=", subsetid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update subsets population\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }
    }
    //******************************************************************
    protected void updateSubsetMapCost (long universeid, long subsetid, float cost) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBUniverse.SubSets.TABLE);
        update.addSetColumn(DBUniverse.SubSets.MAPCOST, cost);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.UNIVERSEID, "=", universeid));
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.SUBSETID, "=", subsetid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update subsets map cost\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }
    }
    //******************************************************************
    /**
     * Updates the map status for a subset.
     * @param universeid
     * @param subsetid
     * @param mapstatus
     * @throws Exception 
     */
    protected void updateSubsetMapStatus (long subsetid, int mapstatus) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLUpdate update = new SQLUpdate(DBUniverse.SubSets.TABLE);
        update.addSetColumn(DBUniverse.SubSets.MAPSTATUS, mapstatus);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubSets.SUBSETID, "=", subsetid));
        sql.addClause(update);
        sql.addClause(whr);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to update subsets map cost\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }
    //******************************************************************
}
//**************************************************************************
