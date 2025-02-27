package com.nora.rpgsp.mixin;

import com.nora.rpgsp.api.SpellcostMixinInterface;
import net.spell_engine.api.spell.Spell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Spell.Cost.class)
public class SpellcostMixin implements SpellcostMixinInterface {
    @Unique
    float rpgsp = -1;

    public void setSkillpowerCost(float cost) {
        rpgsp = cost;
    }

    @Override
    public float getSkillpowerCost() {
        return rpgsp == -1 ? 0 : rpgsp;
    }

    @Override
    public boolean calculateSkillpowerCost() {
        return rpgsp == -1;
    }
}
