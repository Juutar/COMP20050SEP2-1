package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector3D;

public class Ray {
    
    public Hexagon startHex;
    public Vector3D startDir;
    
    public Ray(Hexagon startHex, Vector3D startDir){
        this.startDir = startDir;
        this.startHex = startHex;
    }

}
