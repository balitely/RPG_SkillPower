package com.nora.rpgsp.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "rpgsp")
public class ServerConfigWrapper extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("server")
    @ConfigEntry.Gui.Excluded
    public ServerConfig server = new ServerConfig();
}