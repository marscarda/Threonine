package threonine.universe;
//**************************************************************************
import methionine.TabList;
//**************************************************************************
public class UniverseLock extends UniverseQ4 {
    //**********************************************************************
    public void AddLockCreateSubset (TabList tabs) {
        tabs.addTable(databasename, DBUniverse.Universe.TABLE);
        tabs.addTable(databasename, DBUniverse.SubSets.TABLE);
    }
    //**********************************************************************
    /**
     * Lock these three tables individually. Methods Arimasu
     * @param tabs
     * @deprecated
     */
    @Deprecated
    public void AddLockMapRecord (TabList tabs) {
        tabs.addTable(databasename, DBUniverse.SubSets.TABLE);
        tabs.addTable(databasename, DBUniverse.SubsetMapObject.TABLE);
        tabs.addTable(databasename, DBUniverse.LocationPoints.TABLE);
    }
    //**********************************************************************
    public void lockUniverse (TabList tabs) { tabs.addTable(databasename, DBUniverse.Universe.TABLE); }
    public void lockSubset (TabList tabs) { tabs.addTable(databasename, DBUniverse.SubSets.TABLE); }
    public void lockMapObject (TabList tabs) { tabs.addTable(databasename, DBUniverse.SubsetMapObject.TABLE); }
    public void lockLocationPoint (TabList tabs) { tabs.addTable(databasename, DBUniverse.LocationPoints.TABLE); }    
    //**********************************************************************
}
//**************************************************************************
