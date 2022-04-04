package threonine.universe;
//**************************************************************************
public class DBUniverse {
    //**********************************************************************
    public static class Universe {
        static final String TABLE = "universe";
        static final String UNIVERSEID = "universeid";
        static final String PROJECTID = "projectid";
        static final String NAME = "univname";
        static final String DESCRIPTION = "description";
        static final String WEIGHTED = "weighted";
    }
    //======================================================================
    public static class UniverseTemplate {
        static final String TABLE = "template";
        static final String UNIVERSEID = "universeid";
        static final String NAME = "name";
        static final String DESCRIPTION = "description";
        static final String WEIGHTED = "weighted";
    }    
    //**********************************************************************
    public static class SubSets {
        static final String TABLE = "subset";
        static final String SUBSETID = "subsetid";
        static final String UNIVERSEID = "universeid";
        static final String PARENTSUBSET = "parentsubset";
        static final String NAME = "name";
        static final String POPULATION = "population";
        static final String WEIGHT = "weight";
        static final String MAPSTATUS = "mapstatus";
        static final String SUBSETCOST = "subsetcost";
        static final String MAPCOST = "mapcost";
    }
    //======================================================================
    public static class SubsetTemplate {
        static final String TABLE = "tsubset";
        static final String SUBSETID = "subsetid";
        static final String UNIVERSEID = "universeid";
        static final String PARENTSUBSET = "parentsubset";
        static final String NAME = "name";
        static final String POPULATION = "population";
        static final String WEIGHT = "weight";
        static final String MAPSTATUS = "mapstatus";
    }
    //**********************************************************************
    public static class SubsetMapFeature {
        static final String TABLE = "umapfeature";
        static final String FEATUREID = "featureid";
        static final String SUBSETID = "subsetid";
        static final String OBJTYPE = "featuretype";
    }
    //======================================================================
    public static class SubsetMapFeatureTemplate {
        static final String TABLE = "tmapfeature";
        static final String FEATUREID = "featureid";
        static final String SUBSETID = "subsetid";
        static final String FEATURETYPE = "featuretype";
    }    
    //**********************************************************************
    public static class LocationPoints {
        static final String TABLE = "ulocationpoint";
        static final String SUBSETID = "subsetid";
        static final String OBJECTID = "objectid";
        static final String POINTINDEX = "pointindex";
        static final String LATITUDE = "latitude";
        static final String LONGITUDE = "longitude";
    }
    //======================================================================
    public static class TemplateLocationPoints {
        static final String TABLE = "tlocationpoint";
        static final String SUBSETID = "subsetid";
        static final String FEATUREID = "featureid";
        static final String POINTINDEX = "pointindex";
        static final String LATITUDE = "latitude";
        static final String LONGITUDE = "longitude";
    }
    //**********************************************************************
}
//**************************************************************************
