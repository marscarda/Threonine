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
import methionine.sql.SQLLimit;
import methionine.sql.SQLOrderBy;
import methionine.sql.SQLQueryCmd;
import methionine.sql.SQLSelect;
import methionine.sql.SQLWhere;
import threonine.mapping.MapFeature;
import threonine.mapping.PointLocation;
//**************************************************************************
public class TemplateQ1 extends TabsUniverse {
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
    /**
     * Returns the count of univese templates
     * @return
     * @throws Exception 
     */
    protected int selectTemplateCount () throws Exception {
        SQLSelect select = new SQLSelect(DBUniverse.UniverseTemplate.TABLE);
        select.addItem("COUNT", "*", "C");
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(select.getText());
            select.setParameters(st, 1);
            rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select universe template count\n");
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
     * 
     * @param offset
     * @return
     * @throws Exception 
     */
    protected Universe[] selectUniverseTemplates (int offset) throws Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.UniverseTemplate.TABLE);
        select.addItem(DBUniverse.UniverseTemplate.UNIVERSEID);
        select.addItem(DBUniverse.UniverseTemplate.NAME);
        select.addItem(DBUniverse.UniverseTemplate.DESCRIPTION);
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.UniverseTemplate.NAME);
        //-------------------------------------------------------
        SQLLimit limit = new SQLLimit(offset, PUBLICCOUNT);
        //-------------------------------------------------------
        sql.addClause(select);
        sql.addClause(order);
        sql.addClause(limit);
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
                universe.univerid = rs.getLong(DBUniverse.UniverseTemplate.UNIVERSEID);
                universe.name = rs.getString(DBUniverse.UniverseTemplate.NAME);
                universe.description = rs.getString(DBUniverse.UniverseTemplate.DESCRIPTION);
                universes.add(universe);
            }
            return universes.toArray(new Universe[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select universe templates \n");
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
     * @param templateid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected Universe selectUniverseTemplate (long templateid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.UniverseTemplate.TABLE);
        select.addItem(DBUniverse.UniverseTemplate.UNIVERSEID);
        select.addItem(DBUniverse.UniverseTemplate.NAME);
        select.addItem(DBUniverse.UniverseTemplate.DESCRIPTION);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.UniverseTemplate.UNIVERSEID, "=", templateid));
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
                throw new AppException("Template not found", UniverseErrorCodes.UNIVERSENOTFOUND);
            Universe universe;
            universe = new Universe();
            universe.univerid = rs.getLong(DBUniverse.UniverseTemplate.UNIVERSEID);
            universe.name = rs.getString(DBUniverse.UniverseTemplate.NAME);
            universe.description = rs.getString(DBUniverse.UniverseTemplate.DESCRIPTION);
            return universe;
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select universe templates \n");
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
    protected void insertTemplateFeature (MapFeature feature) throws SQLIntegrityConstraintViolationException, Exception {
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
}
//**************************************************************************
