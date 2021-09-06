package threonine.midlayer;
//*********************************************
public class MapRatioInfo {
    public int perimeter = 0;
    public int separation = 0;
    public int ptallowed = 0;
    public int ptobject = 0;
    public boolean aproved () { return (ptobject <= ptallowed); }
}
//*********************************************