package Game.entities.actions;

import Game.Items.Item;
import Game.entities.Entity;

public class ItemUse extends Action {
    private Item item;
    private int dx,dy;

    public static boolean readyItemUse(Item item,int dx,int dy, Entity entity){
        return item.reUse(dx,dy,entity);
    }
    public ItemUse(Item item, int dx, int dy, Entity entity){
        this.item = item;
        this.dx = dx;
        this.dy = dy;
        this.addEntity(entity);
    }
    @Override
    public int tickLength() {
        int t = item.getUseTime();
        if(t==-1) t = (int)(10*Math.sqrt(dx*dx+dy*dy));
        return t;
    }

    @Override
    public void beforeAction() {
        item.beforeUse();
    }
    @Override
    public void preformAction(){
        item.use(ticks);
        super.preformAction();
    }
    @Override
    public void afterAction(){
        item.afterUse();
        super.afterAction();
    }
}
