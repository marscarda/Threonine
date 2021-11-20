package threonine.universe;
//**************************************************************************
import methionine.TabList;
//**************************************************************************
public class UniverseLock extends QueryUniverse3 {
    //**********************************************************************
    public void AddLockCreateSubset (TabList tabs) {
        tabs.addTable(databasename, DBUniverse.Universe.TABLE);
        tabs.addTable(databasename, DBUniverse.SubSets.TABLE);
    }
    //**********************************************************************
    public void AddLockMapRecord (TabList tabs) {
        tabs.addTable(databasename, DBUniverse.SubSets.TABLE);
        tabs.addTable(databasename, DBUniverse.SubsetMapObject.TABLE);
        tabs.addTable(databasename, DBUniverse.LocationPoints.TABLE);
    }
    //**********************************************************************
}
//**************************************************************************
