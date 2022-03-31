package threonine.universe;
//**************************************************************************
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Random;
import methionine.AppException;
import methionine.Celaeno;
import threonine.mapping.MapFeature;
import threonine.mapping.PointLocation;
//**************************************************************************
public class TemplateAtlas extends TemplateLock {
    //**********************************************************************
    //UNIVERSES
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
    /**
     * Returns a universe given its ID.
     * @param universeid
     * @return
     * @throws AppException
     * @throws Exception UNIVERSENOTFOUND
     */
    public Universe getTemplate (long universeid) throws AppException, Exception {
        //==================================================================
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //==================================================================
        Universe universe = this.selectUniverseTemplate(universeid);
        return universe;
        //==================================================================
    }    
    //**********************************************************************
    /**
     * Returns a list of universe templates.
     * @return
     * @throws Exception 
     */
    public Universe[] getTemplates () throws Exception {
        
        //------------------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //------------------------------------------------------------------
        int templcount = this.selectTemplateCount();
        int offset = 0;
        //------------------------------------------------------------------
        if (templcount > PUBLICCOUNT) {
            Random rand = new Random();
            offset = rand.nextInt(templcount - PUBLICCOUNT);
        }
        //------------------------------------------------------------------
        return selectUniverseTemplates(offset);
        //------------------------------------------------------------------        
    }
    //**********************************************************************
    //** SUBSETS **
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
     * Returns a subset.
     * @param universeid
     * @param subsetid
     * @return
     * @throws AppException
     * @throws Exception 
     */
    public SubSet getSubset (long universeid, long subsetid) throws AppException, Exception {
        //----------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //----------------------------------------------------------        
        SubSet subset = this.selectSubset(universeid, subsetid);
        subset.valid = true;
        return subset;
    }    
    //**********************************************************************
    public SubSet[] getSubsets (long universid, long parentid) throws Exception {
        //----------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //----------------------------------------------------------        
        SubSet[] subsets = this.selectSubsets(universid, parentid);
        for (SubSet subset : subsets)
            subset.valid = true;
        return subsets;
    }    
    //**********************************************************************
    //MAP FEATURES
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
        MapFeature feature = new MapFeature();
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
    /**
     * Returns the maps features of a given subset.
     * @param subsetid
     * @param fillpoints
     * @return
     * @throws Exception 
     */
    public MapFeature[] getFeaturesBySubset (long subsetid, boolean fillpoints) throws Exception {
        //----------------------------------------------------------
        if (rdmainsrv) connection = electra.mainSrvConnection();
        else connection = electra.nearSrvConnection();
        setDataBase();
        //----------------------------------------------------------        
        MapFeature[] features = this.selectMapFeatures(subsetid);
        if (!fillpoints) return features;
        //------------------------------------------------------------------
        for (MapFeature object : features)
            object.points = this.selectPointLocations(object.objectid);
        //------------------------------------------------------------------
        return features;
        //------------------------------------------------------------------
    }
    //**********************************************************************
}
//**************************************************************************
