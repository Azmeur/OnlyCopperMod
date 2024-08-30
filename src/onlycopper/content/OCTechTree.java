package onlycopper.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static onlycopper.content.OCBlocks.*;
import static onlycopper.content.OCUnitTypes.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.UnitTypes.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;

public class OCTechTree {
    public static TechNode context = null;
    public static void load() {
        addToNode(Blocks.mechanicalDrill, () -> node(OCBlocks.copperChipDrill, () -> node(OCBlocks.copperShatterDrill)));
        addToNode(OCBlocks.copperChipDrill, () -> node(OCBlocks.copperHail));
        addToNode(OCBlocks.copperChipDrill, () -> node(OCBlocks.deepWaterExtractor));
        addToNode(OCBlocks.copperChipDrill, () -> node(OCBlocks.copperSolarPanel));
        addToNode(OCBlocks.copperChipDrill, () -> node(OCBlocks.cryofluidFreezingPress));
        addToNode(OCBlocks.copperChipDrill, () -> node(OCBlocks.denseCopperWall, () -> node(OCBlocks.denseCopperWallLarge)));
        addToNode(OCBlocks.copperChipDrill, () -> node(OCBlocks.copperAirFactory, () -> node(OCUnitTypes.copperMono)));
    }
    
    public static void addToNode(UnlockableContent p, Runnable c){
        context = TechTree.all.find(t -> t.content == p);
        c.run();
    }
    
    public static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    public static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    public static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
        TechNode node = new TechNode(context, content, requirements);
        if(objectives != null){
            node.objectives.addAll(objectives);
        }

        TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    public static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    public static void node(UnlockableContent block){
        node(block, () -> {});
    }

    public static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.add(new Produce(content)), children);
    }

    public static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, new Seq<>(), children);
    }
}
