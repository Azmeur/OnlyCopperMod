package onlycopper.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;
import mindustry.world.modules.ItemModule;

import static mindustry.Vars.*;

public class OCConsumerDrill extends Drill{
    public float rotateSpeed = 4f;
    public int consumption = 1;

    public OCConsumerDrill(String name) {
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.drills;
        hasLiquids = true;
        liquidCapacity = 5f;
        hasItems = true;
    }
    
    @Override
    public void setBars(){
        super.setBars();

        addBar("drillspeed", (DrillBuild e) ->
             new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * (1 + (float)(tier - e.dominantItem.hardness) / (tier * 2)) * 60 * e.timeScale(), 2)), () -> Pal.ammo, () -> e.warmup));
    }

    @Override
    public float getDrillTime(Item item){
        return ((drillTime + hardnessDrillMultiplier * item.hardness) / drillMultipliers.get(item, 1f)) / (1 + (float)(tier - item.hardness) / (tier * 2));
    }

    public class OCConsumerDrillBuild extends DrillBuild {
        @Override
        public boolean shouldConsume(){
            return items.get(dominantItem) < itemCapacity && enabled;
        }

        @Override
        public boolean shouldAmbientSound(){
            return efficiency > 0.01f && items.get(dominantItem) < itemCapacity;
        }
        
        @Override
        public void updateTile() {
            if(timer(timerDump, dumpTime)){
                if(Items.copper == dominantItem) {
                    if(items.get(Items.copper) > 1) {
                        dump(dominantItem);
                    }
                } else {
                    dump(dominantItem);
                }
            }

            if(dominantItem == null){
                return;
            }

            timeDrilled += warmup * delta();
            float delay = getDrillTime(dominantItem) * (1 + (float)(tier - dominantItem.hardness) / (tier * 2));
            if(items.get(dominantItem) < itemCapacity && dominantItems > 0 && efficiency > 0){
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                lastDrillSpeed = (speed * dominantItems * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * dominantItems * speed * warmup;

                if(Mathf.chanceDelta(updateEffectChance * warmup))
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
            }else{
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if(dominantItems > 0 && progress >= delay && items.get(dominantItem) < itemCapacity){
                for(int i = 0; i < ((tier + 1) - dominantItem.hardness); i++) {
                    if(i == 0) {if (dominantItem == Items.copper) {continue;} else {consume();}}
                    if(i == 1 && consumption == 2 && dominantItem == Items.copper) {continue;}
                    offload(dominantItem);
                }
                progress %= delay;

                if(wasVisible && Mathf.chanceDelta(updateEffectChance * warmup)) 
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
            }
        }
    }

    @Override
    public void init(){
        super.init();
        buildType = OCConsumerDrillBuild::new;
    }
}