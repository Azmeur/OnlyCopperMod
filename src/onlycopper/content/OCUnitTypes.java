package onlycopper.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.Items;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import onlycopper.OnlyCopperMod;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import static mindustry.content.UnitTypes.mono;

public class OCUnitTypes {
    static {
        EntityMapping.nameMap.put("copper-mono", UnitEntityLegacyMono::create);
    }
    public static UnitType copperMono;

    public static void load(){
        copperMono = new UnitType("copper-mono"){{
            controller = u -> new MinerAI();

            constructor = UnitEntityLegacyMono::create;
            defaultCommand = UnitCommand.mineCommand;

            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.3f;
            health = 70;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            itemCapacity = 10;
            range = 50f;
            isEnemy = false;

            ammoType = new PowerAmmoType(500);

            mineTier = 1;
            mineSpeed = 1.5f;
        }};
    }
}
