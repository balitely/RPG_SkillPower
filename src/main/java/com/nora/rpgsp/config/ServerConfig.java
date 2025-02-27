package com.nora.rpgsp.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.LinkedHashMap;

@Config(name = "server")
public class ServerConfig implements ConfigData {

    public ServerConfig(){}
    @Comment("Maximum Skillpower Modifier (Default: 1)")
    public  float skillpower = 1;
    @Comment("Additional Base Skillpower (Default: 0)")
    public  float baseskillpower = 0;

    @Comment("Skillpower Regen Modifier (Default: 1)")
    public  float skillpowerregen = 1;

    @Comment("Do not apply Skillpower Costs (and therefore Skillpower Compatibility) to spells matching this regex.")
    public String blacklist_spell_casting_regex = "";

    @Comment("Apply skillpower costs to select spells (Format: 'spellid': 40)")
    public LinkedHashMap<String, Integer> spells = new LinkedHashMap<String, Integer>() {
        {
            this.put("examplemod:examplespell", 40);
        }
    };

}
