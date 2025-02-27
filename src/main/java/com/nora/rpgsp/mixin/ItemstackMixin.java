package com.nora.rpgsp.mixin;

import com.nora.rpgsp.Rpgsp;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemstackMixin {

    @ModifyReturnValue(at = @At("TAIL"), method = "getTooltip")
    public List<Text> getTooltiprpgsp(List<Text> tooltip, @Nullable PlayerEntity player, TooltipContext context) {
        if(tooltip.stream().anyMatch(text -> text.toString().contains("rpgsp.skillpower")) ||
                tooltip.stream().anyMatch(text -> text.toString().contains("rpgsp.skillpowercost")) ||
                tooltip.stream().anyMatch(text -> text.toString().contains("rpgsp.skillpowerregen")))  {
            if (tooltip.stream().anyMatch(text -> text.toString().contains("rpgsp.skillpower"))) {
                tooltip.add(Text.translatable("desc.rpgsp.skillpower").formatted(Formatting.GRAY));
            }
            if (tooltip.stream().anyMatch(text -> text.toString().contains("rpgsp.skillpowercost"))) {
                tooltip.add(Text.translatable("desc.rpgsp.skillpowercost").formatted(Formatting.GRAY));
            }
            if (tooltip.stream().anyMatch(text -> text.toString().contains("rpgsp.skillpowerregen"))) {
                tooltip.add(Text.translatable("desc.rpgsp.skillpowerregen").formatted(Formatting.GRAY));

            }
        }
        ItemStack stack = (ItemStack) (Object) this;

        return tooltip;

    }

}
