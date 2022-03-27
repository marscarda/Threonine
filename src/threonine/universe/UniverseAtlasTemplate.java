package threonine.universe;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import methionine.Celaeno;
import threonine.mapping.MapObject;
import threonine.mapping.PointLocation;
//**************************************************************************
public class UniverseAtlasTemplate extends UniverseLock {
    //**********************************************************************
    public void addTemplateUniverse (Universe universe) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        while (true) {
            try {
                universe.univerid = Celaeno.getUniqueID();
                this.insertTemplateUniverse(universe);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //==================================================================
    }
    //**********************************************************************
    public void templateAddSubset (SubSet subset) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();        
        //==================================================================
        while (true) {
            try {
                subset.subsetid = Celaeno.getUniqueID();
                this.insertTemplateSubset(subset);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //==================================================================
    }
    //**********************************************************************
    /**
     * 
     * @param subsetid
     * @param points
     * @throws Exception 
     */
    public void templateAddFeature (long subsetid, PointLocation[] points) throws Exception {
        //==================================================================
        if (wrmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        MapObject feature = new MapObject();
        feature.recordid = subsetid;
        //------------------------------------------------------------------
        while (true) {
            try {
                feature.objectid = Celaeno.getUniqueID();
                this.insertTemplateFeature(feature);
                break;
            }
            catch (SQLIntegrityConstraintViolationException e) {}
        }
        //------------------------------------------------------------------
        //We insert the points for the object
        for (PointLocation point : points) {
            point.recordid = feature.recordid;
            point.objectid = feature.objectid;
            this.insertTemplatePointLocation(point);
        }
        //------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
