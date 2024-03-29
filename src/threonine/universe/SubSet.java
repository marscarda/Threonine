package threonine.universe;
//**************************************************************************
public class SubSet {
    //===============================================
    long subsetid = 0;
    long universeid = 0;
    long parentsubset = 0;
    String name = null;
    int population = 0;
    int weight = 0;
    int mapstatus = 0;
    float subsetcost = 0;
    float mapcost = 0;
    boolean valid = false;
    boolean root = false;
    //===============================================
    public long getSubsetID () { return subsetid; }
    //-----------------------------------------------
    public void setUniverseID (long universeid) { this.universeid = universeid; }
    public void setParentSubset (long parsentsubset) { this.parentsubset = parsentsubset; }
    public void setName (String name) { this.name = name; }
    public void setPopulation (int population) { this.population = population; }
    public void setWeight (int weight) { this.weight = weight; }
    //-----------------------------------------------
    public void setCost (float cost) { this.subsetcost = cost; }
    public void setMapCost (float cost) { this.mapcost = cost; }
    //===============================================
    public long getUniverseID () { return universeid; }
    public long getParentSubSet () { return parentsubset; }
    public int getPopulation () { return population; }
    public int getWeight () { return weight; }
    public float getMapCost () { return mapcost; }
    public boolean mapStatus () { return mapstatus != 0; }
    //-----------------------------------------------
    public String getName () {
        if (name == null) return "";
        return name;
    }
    //===============================================
    public void setValid () { valid = true; }
    public boolean isValid () { return valid; }
    //===============================================
    public void setROOT () { root = true; }
    public boolean isROOT () { return root; }
    //===============================================
}
//**************************************************************************
