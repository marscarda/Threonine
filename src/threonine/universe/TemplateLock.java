package threonine.universe;
//**************************************************************************
import methionine.TabList;
//**************************************************************************
public class TemplateLock extends TemplateQ3 {
    public void lockUniverseTemplate (TabList tabs) { tabs.addTable(databasename, DBUniverse.UniverseTemplate.TABLE); }
    public void lockSubset (TabList tabs) { tabs.addTable(databasename, DBUniverse.SubsetTemplate.TABLE); }
    public void lockMapFeature (TabList tabs) { tabs.addTable(databasename, DBUniverse.SubsetMapFeatureTemplate.TABLE); }
    public void lockLocationPoint (TabList tabs) { tabs.addTable(databasename, DBUniverse.TemplateLocationPoints.TABLE); }    
}
//**************************************************************************
