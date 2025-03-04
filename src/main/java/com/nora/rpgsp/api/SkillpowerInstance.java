package com.nora.rpgsp.api;

import net.minecraft.entity.player.PlayerEntity;

public class SkillpowerInstance {
    public double remainingduration = 0;
    public double value = 0;
    public double remainingvalue = 0;
    public double duration = 4;
    public PlayerEntity player = null;
    public SkillpowerInstance(PlayerEntity player, double duration, double value){
        this.player = player;
        this.duration = duration;
        this.value = value;
        this.remainingvalue = value;
        this.remainingduration = duration;
    }
    public void tick(){
        if(this.remainingduration > 0) {
            this.remainingvalue -= (float) (this.value / duration);
            this.remainingduration--;
        }
    }
}
