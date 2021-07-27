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
        static final String PUBLICNAME = "publicname";
        static final String SHAREPASS = "sharepass";
        static final String COSTPERUSE = "costperuse";
        static final String SEARCHABLE = "searchable";
    }
    //**********************************************************************
    public static class FolderShare {
        static final String TABLE = "foldershare";
        static final String PROJECTID = "projectid";
        static final String FOLDERID = "folderid";
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
    public static class Objects {
        static final String TABLE = "mapobjects";
        static final String OBJECTID = "objectid";
        static final String RECORDID = "recordid";
        static final String OBJTYPE = "objtype";
    }
    //**********************************************************************
    public static class LocationPoints {
        static final String TABLE = "locationpoints";
        static final String RECORDID = "recordid";
        static final String OBJECTID = "objectid";
        static final String POINTINDEX = "pointindex";
        static final String LATITUDE = "latitude";
        static final String LONGITUDE = "longitude";
    }
    //**********************************************************************
}
//**************************************************************************
