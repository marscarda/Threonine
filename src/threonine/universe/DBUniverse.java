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
    //**********************************************************************
    public static class DBSubSets {
        static final String TABLE = "subsets";
        static final String SUBSETID = "subsetid";
        static final String UNIVERSEID = "universeid";
        static final String PARENTSUBSET = "parentsubset";
        static final String NAME = "name";
        static final String DESCRIPTION = "description";
        static final String POPULATION = "population";
        static final String WEIGHT = "weight";
        @Deprecated
        static final String MAPRECORDID = "maprecordid";
    }
    //**********************************************************************
}
//**************************************************************************
