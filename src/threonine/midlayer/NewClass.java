package threonine.midlayer;
//**************************************************************************

//**************************************************************************
public class NewClass extends MapView {
    //************************************************************
    float targetlatitude = 0;
    float targetlongitude = 0;
    //************************************************************
    
    /**
     * Calculates the geo location from a given cartesian coordinates.
     * @param refx
     * @param refy 
     */
    private void calculateLocationFromPlane (int refx, int refy) {
        //========================================================
        float frompole = (float)(topcanvasy + refy) / (float)scale;
        targetlatitude = 90 - frompole;
        float auxlon = (float)((refx - halfcanvasx) / Math.cos(Math.toRadians(targetlatitude)));
        targetlongitude = clongitude + (auxlon / (float)scale);
        //========================================================
    }
    //************************************************************
    public void qwertyPoint (int ptx, int pty) {
        this.calculateLocationFromPlane(ptx, pty);
        
        System.out.println("Latitude: " + targetlatitude + " Longitude: " + targetlongitude);
        
    }
    //************************************************************
}
//**************************************************************************
