package io.github.goosePatrol;
import com.badlogic.gdx.utils.Array;
public class HiddenEventManager {
    private final Array<HiddenEvent> events;
    private int TriggerCounter = 0;

    public HiddenEventManager(){
        this.events = new Array<>();
    }
            public void update(Player player){
        for (HiddenEvent event : events){
            if(!event.isTriggered()){
                // checks the event area to the players position
                float playerx = player.getX();
                float playery = player.getY();
                if (event.getBounds().contains(playerx, playery)){
                    event.onTrigger(player);
                    TriggerCounter++;
                }
            }
        }
    }
public int getTriggerCounter(){
        return TriggerCounter;

}
public Array<HiddenEvent> getEvents(){
        return events;

}

}
