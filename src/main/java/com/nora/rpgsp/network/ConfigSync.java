package com.nora.rpgsp.network;


import com.nora.rpgsp.Rpgsp;
import com.nora.rpgsp.config.ServerConfig;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public  class ConfigSync {
    public static Identifier ID = new Identifier(Rpgsp.MOD_ID, "config_sync");

    public static PacketByteBuf write(ServerConfig config) {
        var gson = new Gson();
        var json = gson.toJson(config);
        var buffer = PacketByteBufs.create();
        buffer.writeString(json);
        return buffer;
    }

    public static ServerConfig read(PacketByteBuf buffer) {
        var gson = new Gson();
        var json = buffer.readString();
        var config = gson.fromJson(json, ServerConfig.class);
        return config;
    }
}