package threonine.map;
//**************************************************************************
public class DBMaps {
    //**********************************************************************
    public static class FolderTree {
        static final String TABLE = "mapfoldertree";
        static final String FOLDERID = "folderid";
        static final String PROJECTID = "projectid";
        static final String PARENTFOLDER = "parentfolder";
        static final String FOLDERNAME = "foldername";
    }
    //**********************************************************************
    public static class MapRecords {
        static final String TABLE = "maprecords";
        static final String RECORDID = "recordid";
        static final String PROJECTID = "projectid";
        static final String FOLDERID = "folderid";
        static final String NAME = "name";
        static final String EXTRADATA = "extradata";
        static final String ADMINDIV = "admindivision";
    }
    //**********************************************************************
    public static class MapObjects {
        static final String TABLE = "mapobjects";
        static final String RECORDID = "recordid";
        static final String OBJECTCODE = "objcode";
        static final String POINTINDEX = "pointindex";
        static final String LATITUDE = "latitude";
        static final String LONGITUDE = "longitude";
    }
    //**********************************************************************
}
//**************************************************************************
