package onlycopper.content;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import onlycopper.world.blocks.production.OCConsumerDrill;
import onlycopper.world.meta.OCAttribute;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import static mindustry.content.Blocks.ice;
import static mindustry.content.Blocks.iceSnow;
import static mindustry.content.Blocks.snow;
import static mindustry.type.ItemStack.*;

public class OCBlocks {
    public static Block
        //defense
        copperHail,
        //drill
        copperChipDrill, copperShatterDrill, deepWaterExtractor,
        //power
        copperSolarPanel,
        //production
        cryofluidFreezingPress,
        //unit
        copperAirFactory,
        //wall
        denseCopperWall, denseCopperWallLarge;
    
    public static void load(){
        //environment
        snow.attributes.set(OCAttribute.ice, 0.4f);
        ice.attributes.set(OCAttribute.ice, 0.8f);
        iceSnow.attributes.set(OCAttribute.ice, 0.6f);

        //defense
        copperHail = new ItemTurret("copper-hail"){{
            requirements(Category.defense, with(Items.copper, 57));
            ammo(
                Items.copper, new ArtilleryBulletType(3f, 8){{
                    knockback = 0.4f;
                    lifetime = 80f;
                    width = height = 11f;
                    collidesTiles = false;
                    splashDamageRadius = 15f * 0.75f;
                    splashDamage = 5f;
                }}
            );
            targetAir = false;
            reload = 60f;
            recoil = 2f;
            range = 245f;
            inaccuracy = 1f;
            shootCone = 10f;
            health = 230;
            shootSound = Sounds.bang;
            coolant = consumeCoolant(0.1f);
            limitRange(0f);
        }};

        //drill
        copperChipDrill = new OCConsumerDrill("copper-chip-drill"){{
            requirements(Category.production, with(Items.copper, 28));
            size = 2;
            health = 280;
            drillTime = 670;
            liquidCapacity = 5;
            itemCapacity = 10;
            tier = 3;
            
            consumeItems(ItemStack.with(Items.copper, 1));
            consumeLiquid(Liquids.water, 0.06f).boost();
        }};
        copperShatterDrill = new OCConsumerDrill("copper-shatter-drill"){{
            requirements(Category.production, with(Items.copper, 115));
            size = 3;
            health = 350;
            drillTime = 380;
            liquidCapacity = 5;
            itemCapacity = 10;
            consumption = 2;
            tier = 4;
            hasPower = true;
            updateEffect = Fx.pulverizeMedium;
            drillEffect = Fx.mineBig;

            consumePower(1.25f);
            consumeItems(ItemStack.with(Items.copper, 2));
            consumeLiquid(Liquids.water, 0.08f).boost();
        }};
        deepWaterExtractor = new SolidPump("deep-water-extractor"){{
            requirements(Category.production, with(Items.copper, 120));
            result = Liquids.water;
            pumpAmount = 0.05f;
            health = 110;
            size = 2;
            liquidCapacity = 30f;
            rotateSpeed = 1.4f;
            attribute = Attribute.water;
            envRequired |= Env.groundWater;

            consumePower(1.75f);
        }};
        
        //power
        copperSolarPanel = new SolarGenerator("copper-solar-panel"){{
            requirements(Category.power, with(Items.copper, 25));
            powerProduction = 0.05f;
        }};

        //production
        cryofluidFreezingPress = new AttributeCrafter("cryofluid-freezing-press"){{
            requirements(Category.crafting, with(Items.copper, 165));
            outputLiquid = new LiquidStack(Liquids.cryofluid, 0.5f / 60f);
            size = 2;
            hasPower = true;
            hasLiquids = true;
            rotate = false;
            solid = true;
            outputsLiquid = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawDefault());
            liquidCapacity = 24f;
            craftTime = 90;
            lightLiquid = Liquids.cryofluid;
            boostScale = 0.15f;
            attribute = OCAttribute.ice;

            consumePower(2f);
            consumeLiquid(Liquids.water, 6f / 60f);
        }};

        //unit
        copperAirFactory = new UnitFactory("copper-air-factory"){{
            requirements(Category.units, with(Items.copper, 130));
            plans = Seq.with(
                new UnitPlan(OCUnitTypes.copperMono, 60f * 35, with(Items.copper, 45))
            );
            size = 3;
            consumePower(0.9f);
        }};

        //wall
        denseCopperWall = new Wall("dense-copper-wall"){{
            requirements(Category.defense, with(Items.copper, 11));
            size = 1;
            scaledHealth = 420;
        }};
        denseCopperWallLarge = new Wall("dense-copper-wall-large"){{
            requirements(Category.defense, with(Items.copper, 11));
            size = 2;
            scaledHealth = 420;
        }};
    }

}
