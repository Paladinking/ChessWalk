package Game.levels;

import Game.levels.Tiles.WallTile;

 public class Vision {
    private int px,py;
    public Vision(int x, int y){
     this.px = x;
     this.py = y;
    }

     public void calculateVision() {
        int maxRange = 10;
        lightTile1(py,1,0,maxRange);
        lightTile2(py, -1, 0, maxRange);
        lightTile3(px, -1, 0, maxRange);
        lightTile4(px, 1, 0, maxRange);
        lightTile5(py,1,0,maxRange);
        lightTile6(py,-1,0,maxRange);
        lightTile7(px,-1,0,maxRange);
        lightTile8(px,1,0,maxRange);
    }

    private void lightTile1(int y, double start,double end, int maxRange) {
        if(Math.abs(py-y)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(y-py));
        for (;i<=end*(y-py);i++){
            if(Tilemap.vGetTile(px+i,y)instanceof WallTile) {
                lightTile1(y-1,sSlope,(i-1)/((double)(y-py)),maxRange);
                sSlope = (i+1)/((double)(y-py));
            }  if(Math.abs(py-y)+Math.abs(i)<=maxRange)Tilemap.show(px+i,y);
        }
        if(i==(int)(start*(y-py))) return;
        lightTile1(y-1,sSlope,end,maxRange);
    }
    private void lightTile2(int y,double start,double end, int maxRange){
        if(Math.abs(py-y)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(y-py));
        for(;i>=end*(y-py);i--){
            if(Tilemap.vGetTile(px+i,y) instanceof WallTile){
                lightTile2(y-1,sSlope,(i+1)/(double)(y-py),maxRange);
                sSlope = (i-1)/((double)(y-py));
            }  if(Math.abs(py-y)+Math.abs(i)<=maxRange)Tilemap.show(px+i,y);

        }
        if(i==(int)(start*(y-py))) return;
        lightTile2(y-1,sSlope,end,maxRange);
    }
    private void lightTile3(int x,double start,double end, int maxRange){
        if(Math.abs(px-x)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(x-px));
        for(;i<=end*(x-px);i++){
            if(Tilemap.vGetTile(x,py+i)instanceof WallTile){
                lightTile3(x+1,sSlope,(i-1)/(double)(x-px),maxRange);
                sSlope = (i+1)/((double)(x-px));
            }  if(Math.abs(px-x)+Math.abs(i)<=maxRange)Tilemap.show(x,py+i);

        }
        if(i==(int)(start*(x-px))) return;
        lightTile3(x+1,sSlope,end,maxRange);
    }
    private void lightTile4(int x,double start,double end, int maxRange){
        if(Math.abs(px-x)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(x-px));
        for(;i>=end*(x-px);i--){
            if(Tilemap.vGetTile(x,py+i)instanceof WallTile){
                lightTile4(x+1,sSlope,(i+1)/(double)(x-px),maxRange);
                sSlope = (i-1)/((double)(x-px));
            }  if(Math.abs(px-x)+Math.abs(i)<=maxRange)Tilemap.show(x,py+i);

        }
        if(i==(int)(start*(x-px))) return;
        lightTile4(x+1,sSlope,end,maxRange);
    }
    private void lightTile5(int y, double start,double end, int maxRange) {
        if(Math.abs(py-y)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(y-py));
        for (;i>=end*(y-py);i--){
            if(Tilemap.vGetTile(px+i,y) instanceof WallTile) {
                lightTile5(y+1,sSlope,(i+1)/((double)(y-py)),maxRange);
                sSlope = (i-1)/((double)(y-py));
            }  if(Math.abs(py-y)+Math.abs(i)<=maxRange)Tilemap.show(px+i,y);
        }
        if(i==(int)(start*(y-py))) return;
        lightTile5(y+1,sSlope,end,maxRange);
    }
    private void lightTile6(int y, double start,double end, int maxRange) {
        if(Math.abs(py-y)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(y-py));
        for (;i<=end*(y-py);i++){
            if(Tilemap.vGetTile(px+i,y)instanceof WallTile) {
                lightTile6(y+1,sSlope,(i-1)/((double)(y-py)),maxRange);
                sSlope = (i+1)/((double)(y-py));
            }  if(Math.abs(py-y)+Math.abs(i)<=maxRange)Tilemap.show(px+i,y);
        }
        if(i==(int)(start*(y-py))) return;
        lightTile6(y+1,sSlope,end,maxRange);
    }

    private void lightTile7(int x,double start,double end, int maxRange){
        if(Math.abs(px-x)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(x-px));
        for(;i>=end*(x-px);i--){
            if(Tilemap.vGetTile(x,py+i)instanceof WallTile){
                lightTile7(x-1,sSlope,(i+1)/(double)(x-px),maxRange);
                sSlope = (i-1)/((double)(x-px));
            }  if(Math.abs(px-x)+Math.abs(i)<=maxRange)Tilemap.show(x,py+i);
        }
        if(i==(int)(start*(x-px))) return;
        lightTile7(x-1,sSlope,end,maxRange);
    }
    private void lightTile8(int x,double start,double end, int maxRange){
        if(Math.abs(px-x)>maxRange) return;
        double sSlope = start;
        int i=(int)(start*(x-px));
        for(;i<=end*(x-px);i++){
            if(Tilemap.vGetTile(x,py+i)instanceof WallTile){
                lightTile8(x-1,sSlope,(i-1)/(double)(x-px),maxRange);
                sSlope = (i+1)/((double)(x-px));
            }  if(Math.abs(px-x)+Math.abs(i)<=maxRange)Tilemap.show(x,py+i);

        }
        if(i==(int)(start*(x-px))) return;
        lightTile8(x-1,sSlope,end,maxRange);
    }
}
