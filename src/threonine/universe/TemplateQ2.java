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
import methionine.sql.SQLInsert;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
//**************************************************************************
public class TemplateQ2 extends TemplateQ1 {
    //**********************************************************************
    /**
     * 
     * @param subset
     * @throws SQLIntegrityConstraintViolationException
     * @throws Exception 
     */
    protected void insertTemplateSubset(SubSet subset) throws SQLIntegrityConstraintViolationException, Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.SubsetTemplate.TABLE);
        insert.addValue(DBUniverse.SubsetTemplate.SUBSETID, subset.subsetid);
        insert.addValue(DBUniverse.SubsetTemplate.UNIVERSEID, subset.universeid);
        insert.addValue(DBUniverse.SubsetTemplate.PARENTSUBSET, subset.parentsubset);
        insert.addValue(DBUniverse.SubsetTemplate.NAME, subset.name);
        insert.addValue(DBUniverse.SubsetTemplate.POPULATION, subset.population);
        insert.addValue(DBUniverse.SubsetTemplate.WEIGHT, subset.weight);
        insert.addValue(DBUniverse.SubsetTemplate.MAPSTATUS, subset.mapstatus);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLIntegrityConstraintViolationException e) { throw e; }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert subset template \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }
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
    protected SubSet selectSubset (long universeid, long subsetid) throws AppException, Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.SubsetTemplate.TABLE);
        select.addItem(DBUniverse.SubsetTemplate.SUBSETID);
        select.addItem(DBUniverse.SubsetTemplate.UNIVERSEID);
        select.addItem(DBUniverse.SubsetTemplate.PARENTSUBSET);
        select.addItem(DBUniverse.SubsetTemplate.NAME);
        select.addItem(DBUniverse.SubsetTemplate.POPULATION);
        select.addItem(DBUniverse.SubsetTemplate.WEIGHT);
        select.addItem(DBUniverse.SubsetTemplate.MAPSTATUS);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubsetTemplate.SUBSETID, "=", subsetid));
        if (universeid != 0) whr.addCondition(new SQLCondition(DBUniverse.SubsetTemplate.UNIVERSEID, "=", universeid));
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
            subset.subsetid = rs.getLong(DBUniverse.SubsetTemplate.SUBSETID);
            subset.universeid = rs.getLong(DBUniverse.SubsetTemplate.UNIVERSEID);
            subset.parentsubset = rs.getLong(DBUniverse.SubsetTemplate.PARENTSUBSET);
            subset.name = rs.getString(DBUniverse.SubsetTemplate.NAME);
            subset.population = rs.getInt(DBUniverse.SubsetTemplate.POPULATION);
            subset.weight = rs.getInt(DBUniverse.SubsetTemplate.WEIGHT);
            subset.mapstatus = rs.getInt(DBUniverse.SubsetTemplate.MAPSTATUS);
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
    //**********************************************************************
    /**
     * Selects an array of subsets.
     * @param universeid
     * @param parentid
     * @return
     * @throws Exception 
     */
    protected SubSet[] selectSubsets (long universeid, long parentid) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.SubsetTemplate.TABLE);
        select.addItem(DBUniverse.SubsetTemplate.SUBSETID);
        select.addItem(DBUniverse.SubsetTemplate.UNIVERSEID);
        select.addItem(DBUniverse.SubsetTemplate.PARENTSUBSET);
        select.addItem(DBUniverse.SubsetTemplate.NAME);
        select.addItem(DBUniverse.SubsetTemplate.POPULATION);
        select.addItem(DBUniverse.SubsetTemplate.WEIGHT);
        select.addItem(DBUniverse.SubsetTemplate.MAPSTATUS);
        sql.addClause(select);
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubsetTemplate.UNIVERSEID, "=", universeid));
        whr.addCondition(new SQLCondition(DBUniverse.SubsetTemplate.PARENTSUBSET, "=", parentid));
        sql.addClause(whr);
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.SubsetTemplate.NAME);
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
                subset.subsetid = rs.getLong(DBUniverse.SubsetTemplate.SUBSETID);
                subset.universeid = rs.getLong(DBUniverse.SubsetTemplate.UNIVERSEID);
                subset.parentsubset = rs.getLong(DBUniverse.SubsetTemplate.PARENTSUBSET);
                subset.name = rs.getString(DBUniverse.SubsetTemplate.NAME);
                subset.population = rs.getInt(DBUniverse.SubsetTemplate.POPULATION);
                subset.weight = rs.getInt(DBUniverse.SubsetTemplate.WEIGHT);
                subset.mapstatus = rs.getInt(DBUniverse.SubsetTemplate.MAPSTATUS);
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
    //**********************************************************************
}
//**************************************************************************
