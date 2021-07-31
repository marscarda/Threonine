package threonine.universe;
//**************************************************************************
class DBSubSets {
    static final String TABLE = "subsets";
    static final String SUBSETID = "subsetid";
    static final String UNIVERSEID = "universeid";
    static final String PARENTSUBSET = "parentsubset";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
    static final String POPULATION = "population";
    static final String WEIGHT = "weight";
    static final String MAPRECORDID = "maprecordid";
}
//**************************************************************************
class DBUniverseUsers {
    static final String TABLE = "universeusers";
    static final String UNIVERSEID = "universeid";
    static final String USERID = "userid";
}
//**************************************************************************
class DBMapRecords {
    static final String TABLE = "maprecords";
    static final String RECORDID = "recordid";
    static final String TAG = "tag";
    static final String CATALOG = "catalog";
    static final String NAME = "name";
}
//**************************************************************************
class DBMapObjects {
    static final String TABLE = "mapobjects";
    static final String RECORDID = "maprecordid";
    static final String OBJECTCODE = "objcode";
    static final String POINTINDEX = "pointindex";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
}
//**************************************************************************
