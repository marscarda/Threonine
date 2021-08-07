package threonine.universe;
//**************************************************************************
import methionine.TabList;
//**************************************************************************
public class UniverseLock extends QueryUniverse2 {
    //**********************************************************************
    public void AddLockMapRecord (TabList tabs) {
        tabs.addTable(databasename, DBUniverse.SubSets.TABLE);
        tabs.addTable(databasename, DBUniverse.SubsetMapObject.TABLE);
        tabs.addTable(databasename, DBUniverse.LocationPoints.TABLE);
    }
    //**********************************************************************
}
//**************************************************************************
