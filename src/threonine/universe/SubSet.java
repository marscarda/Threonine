package threonine.universe;
//**************************************************************************
public class SubSet {
    //===============================================
    long subsetid = 0;
    long universeid = 0;
    long parentsubset = 0;
    String name = null;
    String description;
    int population = 0;
    int weight = 0;
    long maprecordid = 0;
    boolean valid = false;
    //===============================================
    public long getSubsetID () { return subsetid; }
    //-----------------------------------------------
    public void setUniverseID (long universeid) { this.universeid = universeid; }
    public void setParentSubset (long parsentsubset) { this.parentsubset = parsentsubset; }
    public void setName (String name) { this.name = name; }
    public void setDescription (String description) { this.description = description; }
    public void setPopulation (int population) { this.population = population; }
    public void setWeight (int weight) { this.weight = weight; }
    //===============================================
    public long getUniverseID () { return universeid; }
    public long getParentSubSet () { return parentsubset; }
    public long getPopulation () { return population; }
    public long getWeight () { return weight; }
    public long getMapRecordID () { return maprecordid; }
    //-----------------------------------------------
    public String getName () {
        if (name == null) return "";
        return name;
    }
    //-----------------------------------------------
    public String getDescription () {
        if (description == null) return "";
        return description;
    }
    //===============================================
    public boolean isValid () { return valid; }
    //===============================================
}
//**************************************************************************