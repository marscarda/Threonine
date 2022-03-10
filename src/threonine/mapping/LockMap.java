package threonine.mapping;
//************************************************************************
import methionine.TabList;
//************************************************************************
public class LockMap extends MappingQ4 {
    //********************************************************************
    public void addLockCreateFolder (TabList tabs) {
        tabs.addTable(databasename, DBMaps.FolderUsage.TABLE);
    }
    //********************************************************************
    public void addLockCreateObject (TabList tabs) {
        tabs.addTable(databasename, DBMaps.MapRecords.TABLE);
        tabs.addTable(databasename, DBMaps.MapFeature.TABLE);
        tabs.addTable(databasename, DBMaps.LocationPoints.TABLE);
    }
    //********************************************************************
    public void addLockDeleteFolder (TabList tabs) {
        tabs.addTable(databasename, DBMaps.MapRecords.TABLE);
        tabs.addTable(databasename, DBMaps.MapFeature.TABLE);
        tabs.addTable(databasename, DBMaps.LocationPoints.TABLE);
        tabs.addTable(databasename, DBMaps.FolderUsage.TABLE);
    }
    //********************************************************************
    public void addLockDeleteRecord (TabList tabs) {
        tabs.addTable(databasename, DBMaps.MapRecords.TABLE);
        tabs.addTable(databasename, DBMaps.MapFeature.TABLE);
        tabs.addTable(databasename, DBMaps.LocationPoints.TABLE);
    }
    //********************************************************************
}
//************************************************************************
