package threonine.map;
//************************************************************************
import methionine.TabList;
//************************************************************************
public class LockMap extends QueryMaps3 {
    //********************************************************************
    public void addLockCreateObject (TabList tabs) {
        tabs.addTable(databasename, DBMaps.MapRecords.TABLE);
        tabs.addTable(databasename, DBMaps.Objects.TABLE);
        tabs.addTable(databasename, DBMaps.LocationPoints.TABLE);
    }
    //********************************************************************
}
//************************************************************************
