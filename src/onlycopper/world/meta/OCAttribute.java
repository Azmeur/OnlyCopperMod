package onlycopper.world.meta;

import arc.struct.*;
import mindustry.*;
import mindustry.world.meta.Attribute;

public class OCAttribute{
    public static Attribute ice;

    public static void load(){
        ice = Attribute.add("ice");
    }
}
