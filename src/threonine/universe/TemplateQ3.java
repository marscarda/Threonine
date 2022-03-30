package threonine.universe;
//**************************************************************************
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import methionine.sql.SQLInsert;
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
