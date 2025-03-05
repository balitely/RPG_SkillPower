package com.nora.rpgsp.client;

import com.nora.rpgsp.Rpgsp;
import com.nora.rpgsp.api.SkillpowerInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.internals.casting.SpellCasterClient;

public class InGameHud  implements HudRenderCallback {
	private static final Identifier spbar = new Identifier(Rpgsp.MOD_ID,"textures/gui/bar_sp.png");
	private static final Identifier spbead = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead.png");
	private static final Identifier spbead2= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead2.png");
	private static final Identifier spbead3 = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead3.png");
	private static final Identifier spbead4= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead4.png");
	private static final Identifier spbead12 = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead12.png");
	private static final Identifier spbead22= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead22.png");
	private static final Identifier spbead32 = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead32.png");
	private static final Identifier spbead42= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead42.png");

	private static final Identifier spbeadneg = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbeadneg.png");
	private static final Identifier spbead2neg= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead2neg.png");
	private static final Identifier spbead3neg = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead3neg.png");
	private static final Identifier spbead4neg= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead4neg.png");
	private static final Identifier spbead12neg = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead12neg.png");
	private static final Identifier spbead22neg= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead22neg.png");
	private static final Identifier spbead32neg = new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead32neg.png");
	private static final Identifier spbead42neg= new Identifier(Rpgsp.MOD_ID,"textures/gui/spbead42neg.png");


	private static final Identifier spbarneg = new Identifier(Rpgsp.MOD_ID,"textures/gui/bar_sp_neg.png");

	private static final Identifier spticks = new Identifier(Rpgsp.MOD_ID,"textures/gui/bar_ticks.png");
	private static final Identifier spback = new Identifier(Rpgsp.MOD_ID,"textures/gui/bar_back.png");


	public void onHudRender(DrawContext drawContext, float tickDelta) {

		PlayerEntity playerEntity = MinecraftClient.getInstance().player;

		if (playerEntity != null) {
			SkillpowerInterface skillpowerInterface = (SkillpowerInterface) playerEntity;
			if (skillpowerInterface.getMaxSkillpower() >= 1 && skillpowerInterface.getTimeFull() < 60 && !(playerEntity instanceof SpellCasterClient client && client.isCastingSpell())) {
				if(!Rpgsp.clientConfig.alt) {

					int scaledHeight = drawContext.getScaledWindowHeight();
					int scaledWidth = drawContext.getScaledWindowWidth();
					scaledHeight -= 7;
					MinecraftClient.getInstance().getProfiler().push("skillpower");
					RenderSystem.enableBlend();
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					double skillpowerprop = (skillpowerInterface.getSkillpower() / skillpowerInterface.getMaxSkillpower());
					int i = (int) (skillpowerprop * (182));
					int k = scaledWidth;

					drawContext.drawTexture(spback, (int) ((k / 2) - 182 / 2), scaledHeight - 5 - 3 - 8, 0, 0, 182, 5, 182, 5);
					if (skillpowerprop > 0) {
						drawContext.drawTexture(spbar, (int) ((k / 2) - 182 / 2), scaledHeight - 5 - 3 - 8, 0, 0, Math.min(i, 182), 5, 182, 5);
					} else {
						drawContext.drawTexture(spbarneg, (int) ((k / 2) - 182 / 2), scaledHeight - 5 - 3 - 8, 0, 0, Math.min(-i, 182), 5, 182, 5);

					}
					drawContext.drawTexture(spticks, (int) ((k / 2) - 182 / 2), scaledHeight - 5 - 3 - 8, 0, 0, Math.min(i, 182), 5, 182, 5);
					RenderSystem.disableBlend();
					RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
				}
				else{

					int x = 0;
					int y = 0;
					MinecraftClient client = MinecraftClient.getInstance();
					if (client != null) {
						int width = client.getWindow().getScaledWidth();
						int height = client.getWindow().getScaledHeight();

						x = width / 2;
						y = height;
					}
					int z = 0;
					if (skillpowerInterface.getTimeFull() > 50) {
						z += 10 * (skillpowerInterface.getTimeFull() - 50) / 10;
					}
					double skillpowerprop = (skillpowerInterface.getSkillpower() / skillpowerInterface.getMaxSkillpower());
					if( skillpowerprop > 0) {
						drawContext.drawTexture(spbead12, x - 8-6 - z, y / 2 -8-6-z, 0, 0, 12, 12, 12,12);

					}
					 if(skillpowerprop >= 0.125 ) {
						 drawContext.drawTexture(spbead, x - 8 - 6 - z, y / 2 - 8 - 6 - z, 0, 0, 12, 12, 12, 12);
					 }
					if (skillpowerprop >= 0.25 ) {
						drawContext.drawTexture(spbead22, x - 8-6-z, y / 2 +8-6+z, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop >= 0.375 ) {
						drawContext.drawTexture(spbead2, x - 8 - 6-z, y / 2 + 8 - 6+z, 0, 0, 12, 12, 12, 12);
					}
					if (skillpowerprop >= 0.5 ) {
						drawContext.drawTexture(spbead32, x + 7-6+z, y / 2 +8-6+z, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop >= 0.625 ) {
						drawContext.drawTexture(spbead3, x + 7 - 6+z, y / 2 + 8 - 6+z	, 0, 0, 12, 12, 12, 12);
					}
					if (skillpowerprop >= 0.75 ) {
						drawContext.drawTexture(spbead42	, x + 7-6+z, y / 2 -8-6-z, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop >= 0.875 ){
						drawContext.drawTexture(spbead4, x + 7-6+z, y / 2 -8-6-z, 0, 0, 12, 12, 12,12);

					}

					if(skillpowerprop < 0) {
						drawContext.drawTexture(spbead12neg, x - 8-6, y / 2 -8-6, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop < -0.125) {
						drawContext.drawTexture(spbeadneg, x - 8-6, y / 2 - 8-6,0, 0, 12, 12, 12, 12);

					}
					if (skillpowerprop < -0.25) {
						drawContext.drawTexture(spbead22neg, x - 8-6, y / 2 +8-6, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop < -0.375) {
						drawContext.drawTexture(spbead2neg, x - 8-6, y / 2 + 8-6, 0, 0, 12, 12, 12, 12);

					}if (skillpowerprop < -0.5) {
						drawContext.drawTexture(spbead32neg, x + 7-6, y / 2 +8-6, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop < -0.625){
						drawContext.drawTexture(spbead3neg, x + 7-6, y / 2 +8-6, 0, 0, 12, 12, 12,12);

					}if (skillpowerprop < -0.75) {
						drawContext.drawTexture(spbead42neg	, x + 7-6, y / 2 -8-6, 0, 0, 12, 12, 12,12);

					}
					if(skillpowerprop < -0.875){
						drawContext.drawTexture(spbead4neg, x+ 7-6, y / 2 -8-6, 0, 0, 12, 12, 12,12);

					}


				}
			}

		}
	}


}