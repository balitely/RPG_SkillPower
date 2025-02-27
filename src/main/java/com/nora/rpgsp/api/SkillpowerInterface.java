package com.nora.rpgsp.api;

import com.nora.rpgsp.Rpgsp;
import net.minecraft.entity.player.PlayerEntity;
import net.spell_engine.api.spell.Spell;

import java.util.List;

public interface SkillpowerInterface {

    double getSkillpower();
    double getMaxSkillpower();
    double getSkillpowerRegen();

    double spendSkillpower(double toadd);
    int getTimeFull();

    List<SkillpowerInstance> getSkillpowerInstances();
    Spell getLastSpell();
    void setLastSpell(Spell spell);
}
