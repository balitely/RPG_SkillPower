package com.nora.rpgsp.api;

public interface SpellcostMixinInterface {
    void setSkillpowerCost(float cost);

    float getSkillpowerCost();
    boolean calculateSkillpowerCost();
}
