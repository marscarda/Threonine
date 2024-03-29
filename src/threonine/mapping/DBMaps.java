package threonine.mapping;
//**************************************************************************
public class DBMaps {
    //**********************************************************************
    public static class MapLayer {
        static final String TABLE = "maplayer";
        static final String LAYERID = "layerid";
        static final String PROJECTID = "projectid";
        static final String LAYERNAME = "layername";
        static final String DESCRIPTION = "description";
    }
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
    public static class LayerUse {
        static final String TABLE = "layeruse";
        static final String PROJECTID = "projectid";
        static final String LAYERID = "layerid";
    }
    //**********************************************************************
    public static class FolderUsage {
        static final String TABLE = "folderusage";
        static final String PROJECTID = "projectid";
        static final String FOLDERID = "folderid";
        static final String COSTPERUSE = "costperuse";
    }
    //**********************************************************************
    public static class MapRecords {
        static final String TABLE = "maprecord";
        static final String RECORDID = "recordid";
        static final String LAYERID = "layerid";
        static final String NAME = "name";
        static final String EXTRADATA = "extradata";
        static final String ADMINDIV = "admindivision";
    }
    //**********************************************************************
    public static class MapFeature {
        static final String TABLE = "mapfeature";
        static final String FEATUREID = "featureid";
        static final String RECORDID = "recordid";
        static final String OBJTYPE = "featuretype";
        static final String COST = "cost";
    }
    //**********************************************************************
    public static class LocationPoints {
        static final String TABLE = "locationpoint";
        static final String RECORDID = "recordid";
        static final String OBJECTID = "featureid";
        static final String POINTINDEX = "pointindex";
        static final String LATITUDE = "latitude";
        static final String LONGITUDE = "longitude";
    }
    //**********************************************************************
}
//**************************************************************************
