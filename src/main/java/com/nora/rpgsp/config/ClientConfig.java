package com.nora.rpgsp.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "client")
public class ClientConfig implements ConfigData {
    public ClientConfig(){}
    @Comment("Alternate Skillpower Bar (Default: false)")
    public  boolean alt = false;
    @Comment("Adjust the x_position of the default SP bar(Default: -91)")
    public int Spbar_x = -91;
    @Comment("Adjust the y_position of the default SP bar(Default: -15)")
    public int Spbar_y = -15;
}
