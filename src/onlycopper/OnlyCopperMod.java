package onlycopper;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import onlycopper.content.OCBlocks;
import onlycopper.content.OCTechTree;
import onlycopper.content.OCUnitTypes;
import onlycopper.world.meta.OCAttribute;

public class OnlyCopperMod extends Mod{

    public OnlyCopperMod(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("only-copper-mod-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
        OCAttribute.load();
        
        OCBlocks.load();
        OCUnitTypes.load();
        OCTechTree.load();
    }

}
