package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import methionine.sql.SQLCondition;
import methionine.sql.SQLInsert;
import methionine.sql.SQLLimit;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
import threonine.mapping.MapObject;
import threonine.mapping.PointLocation;
//**************************************************************************
public class UniverseQ4 extends UniverseQ3 {
    //**********************************************************************
    protected static final int PUBLICCOUNT = 15;
    //**********************************************************************
    //TEMPLATE UNIVERSE
    //**********************************************************************
    /**
     * Inserts a new Universe Template.
     * @param universe
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws Exception 
     */
    protected void insertTemplateUniverse (Universe universe) throws SQLIntegrityConstraintViolationException, Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.UniverseTemplate.TABLE);
        insert.addValue(DBUniverse.UniverseTemplate.UNIVERSEID, universe.univerid);
        insert.addValue(DBUniverse.UniverseTemplate.NAME, universe.name);
        insert.addValue(DBUniverse.UniverseTemplate.DESCRIPTION, universe.description);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLIntegrityConstraintViolationException e) { throw e; }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert universe template \n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }
    //**********************************************************************
    //**********************************************************************
    //TEMPLATE SUBSET
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
    //TEMPLATE MAP FEATURE
    //**********************************************************************
    protected void insertTemplateFeature (MapObject feature) throws SQLIntegrityConstraintViolationException, Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.SubsetMapFeatureTemplate.TABLE);
        insert.addValue(DBUniverse.SubsetMapFeatureTemplate.FEATUREID, feature.objectid);
        insert.addValue(DBUniverse.SubsetMapFeatureTemplate.SUBSETID, feature.recordid);
        insert.addValue(DBUniverse.SubsetMapFeatureTemplate.FEATURETYPE, feature.objtype);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert map feature (Universe template) mantargan\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }
    //**********************************************************************
    /**
     * Inserts a new map point into de DB.
     * @param point
     * @throws Exception 
     */
    protected void insertTemplatePointLocation (PointLocation point) throws Exception {
        SQLInsert insert = new SQLInsert(DBUniverse.TemplateLocationPoints.TABLE);
        insert.addValue(DBUniverse.TemplateLocationPoints.SUBSETID, point.recordid);
        insert.addValue(DBUniverse.TemplateLocationPoints.FEATUREID, point.objectid);
        insert.addValue(DBUniverse.TemplateLocationPoints.POINTINDEX, point.ptindex);
        insert.addValue(DBUniverse.TemplateLocationPoints.LATITUDE, point.latitude);
        insert.addValue(DBUniverse.TemplateLocationPoints.LONGITUDE, point.longitude);
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert.getText());
            insert.setParameters(st, 1);
            st.execute();            
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert location point (Template)\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally {
            if (st != null) try {st.close();} catch(Exception e){}
        }        
    }
    //**********************************************************************
    //**********************************************************************
    //**********************************************************************
    /**
     * 
     * @return
     * @throws Exception 
     */
    @Deprecated
    protected int selectPublicCount () throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.Universe.TABLE);
        select.addItem("COUNT", "*", "C");
        SQLWhere whr = new SQLWhere();
        //whr.addCondition(new SQLCondition(DBUniverse.Universe.PUBLIC, "!=", 0));
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
            StringBuilder msg = new StringBuilder("Failed to select public universe count\n");
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
     * Selects a list of public universes.
     * @param search
     * @param offset
     * @return
     * @throws Exception 
     */
    @Deprecated
    protected Universe[] selectPublicUniverses (String search, int offset) throws Exception {
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.Universe.TABLE);
        select.addItem(DBUniverse.Universe.UNIVERSEID);
        select.addItem(DBUniverse.Universe.PROJECTID);
        select.addItem(DBUniverse.Universe.NAME);
        select.addItem(DBUniverse.Universe.DESCRIPTION);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        if (search != null)
            whr.addCondition(new SQLCondition(DBUniverse.Universe.NAME, "LIKE", "%" + search + "%"));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.Universe.NAME);
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
            List<Universe> universes = new ArrayList<>();
            Universe universe;
            while (rs.next()) {
                universe = new Universe();
                universe.univerid = rs.getLong(DBUniverse.Universe.UNIVERSEID);
                universe.projectid = rs.getLong(DBUniverse.Universe.PROJECTID);
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
    //**********************************************************************
}
//**************************************************************************
