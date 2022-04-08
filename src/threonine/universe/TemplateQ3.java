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
import threonine.mapping.MapFeature;
import threonine.mapping.PointLocation;
//**************************************************************************
public class TemplateQ3 extends TemplateQ2 {
    //**********************************************************************
    /**
     * 
     * @param feature
     * @throws SQLIntegrityConstraintViolationException
     * @throws Exception 
     */
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
        catch (SQLIntegrityConstraintViolationException e) { throw e; }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to insert map feature (Universe template) mantargan\n");
            msg.append(e.getMessage());
            throw new Exception(msg.toString());
        }
        finally { if (st != null) try {st.close();} catch(Exception e){} }        
    }
    //**********************************************************************
    /**
     * Selects and returns an array of map objects given a subset id
     * @param subsetid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    protected MapFeature[] selectMapFeatures (long subsetid) throws AppException, Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.SubsetMapFeatureTemplate.TABLE);
        select.addItem(DBUniverse.SubsetMapFeatureTemplate.FEATUREID);
        select.addItem(DBUniverse.SubsetMapFeatureTemplate.SUBSETID);
        select.addItem(DBUniverse.SubsetMapFeatureTemplate.FEATURETYPE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.SubsetMapFeatureTemplate.SUBSETID, "=", subsetid));
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
            List<MapFeature> objects = new ArrayList<>();
            MapFeature object;
            while (rs.next()) {
                object = new MapFeature();
                object.objectid = rs.getLong(DBUniverse.SubsetMapFeatureTemplate.FEATUREID);
                object.recordid = rs.getLong(DBUniverse.SubsetMapFeatureTemplate.SUBSETID);
                object.objtype = rs.getInt(DBUniverse.SubsetMapFeatureTemplate.FEATURETYPE);
                objects.add(object);
            }            
            return objects.toArray(new MapFeature[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map objects for subset. Code: vrbcqwdrfh\n");
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
    /**
     * Selects and return point locations for an object.
     * @param objectid
     * @return
     * @throws Exception 
     */
    protected PointLocation[] selectPointLocations (long objectid) throws Exception {
        //-------------------------------------------------------
        SQLQueryCmd sql = new SQLQueryCmd();
        SQLSelect select = new SQLSelect(DBUniverse.TemplateLocationPoints.TABLE);
        select.addItem(DBUniverse.TemplateLocationPoints.FEATUREID);
        select.addItem(DBUniverse.TemplateLocationPoints.SUBSETID);
        select.addItem(DBUniverse.TemplateLocationPoints.POINTINDEX);
        select.addItem(DBUniverse.TemplateLocationPoints.LATITUDE);
        select.addItem(DBUniverse.TemplateLocationPoints.LONGITUDE);
        //-------------------------------------------------------
        SQLWhere whr = new SQLWhere();
        whr.addCondition(new SQLCondition(DBUniverse.TemplateLocationPoints.FEATUREID, "=", objectid));
        //-------------------------------------------------------
        SQLOrderBy order = new SQLOrderBy();
        order.addColumn(DBUniverse.TemplateLocationPoints.POINTINDEX);
        //-------------------------------------------------------
        sql.addClause(select);
        sql.addClause(whr);
        sql.addClause(order);
        //-------------------------------------------------------
        PreparedStatement st = null;
        ResultSet rs = null;
        //-------------------------------------------------------
        try {
            st = connection.prepareStatement(sql.getText());
            sql.setParameters(st, 1);
            rs = st.executeQuery();
            List<PointLocation> points = new ArrayList<>();
            PointLocation point;
            while (rs.next()) {
                point = new PointLocation();
                point.objectid = objectid;
                point.recordid = rs.getLong(DBUniverse.TemplateLocationPoints.SUBSETID);
                point.ptindex = rs.getInt(DBUniverse.TemplateLocationPoints.POINTINDEX);
                point.latitude = rs.getFloat(DBUniverse.TemplateLocationPoints.LATITUDE);
                point.longitude = rs.getFloat(DBUniverse.TemplateLocationPoints.LONGITUDE);
                points.add(point);
            }            
            return points.toArray(new PointLocation[0]);
        }
        catch (SQLException e) {
            StringBuilder msg = new StringBuilder("Failed to select map point locations. Code: tyhgdytrfh\n");
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
