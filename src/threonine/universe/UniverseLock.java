package threonine.universe;
//**************************************************************************
import methionine.TabList;
//**************************************************************************
public class UniverseLock extends UniverseQ3 {
    //**********************************************************************
    public void lockUniverse (TabList tabs) { tabs.addTable(databasename, DBUniverse.Universe.TABLE); }
    public void lockSubset (TabList tabs) { tabs.addTable(databasename, DBUniverse.SubSets.TABLE); }
    public void lockMapObject (TabList tabs) { tabs.addTable(databasename, DBUniverse.SubsetMapFeature.TABLE); }
    public void lockLocationPoint (TabList tabs) { tabs.addTable(databasename, DBUniverse.LocationPoints.TABLE); }    
    //**********************************************************************
}
//**************************************************************************
