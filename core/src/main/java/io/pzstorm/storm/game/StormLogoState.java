/*
 * Zomboid Storm - Java modding toolchain for Project Zomboid
 * Copyright (C) 2021 Matthew Cain
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package io.pzstorm.storm.game;

import io.pzstorm.storm.logging.StormLogger;
import io.pzstorm.storm.util.StormUtils;
import zombie.GameTime;
import zombie.core.Core;
import zombie.core.SpriteRenderer;
import zombie.core.textures.Texture;
import zombie.gameStates.GameState;
import zombie.gameStates.GameStateMachine.StateAction;
import zombie.input.GameKeyboard;
import zombie.input.Mouse;
import zombie.ui.UIManager;

import java.io.IOException;

/**
 * This class represents the screen state where Storm logo is rendered before entering the main menu.
 */
public final class StormLogoState extends GameState {

	/**
	 * Path to the Storm logo image resource.
	 */
	private static final String LOGO_RESOURCE_PATH = "storm-logo.png";

	/**
	 * Determines how long the logo stays on screen.
	 */
	private float logoDelay = 60.0F;

	/**
	 * Determines how long it takes for logo to fade out.
	 */
	private float leaveDelay = 40.0F;

	private float alpha, alpha2, targetAlpha;
	private int stage;

	private boolean bNoRender;

	@Override
	public void enter() {

		UIManager.bSuspend = true;
		this.alpha = 0.0F;
		this.targetAlpha = 1.0F;
	}

	@Override
	public void exit() {
		UIManager.bSuspend = false;
	}

	@Override
	public void render() {

		if (this.bNoRender)
		{
			Core.getInstance().StartFrame();
			SpriteRenderer.instance.renderi(null, 0, 0,
					Core.getInstance().getOffscreenWidth(0),
					Core.getInstance().getOffscreenHeight(0),
					0.0F, 0.0F, 0.0F, 1.0F, null
			);
			Core.getInstance().EndFrame();
		}
		else {
			Core.getInstance().StartFrame();
			Core.getInstance().EndFrame();

			boolean lastUseUIFBO = UIManager.useUIFBO;
			UIManager.useUIFBO = false;

			Core.getInstance().StartFrameUI();
			SpriteRenderer.instance.renderi(null, 0, 0,
					Core.getInstance().getOffscreenWidth(0),
					Core.getInstance().getOffscreenHeight(0),
					0.0F, 0.0F, 0.0F, 1.0F, null
			);
			if (this.logoDelay <= 0.0F)
			{
				++this.stage;
				this.targetAlpha = 1.0F;
			}
			ClassLoader classLoader = getClass().getClassLoader();
			Texture texture;
			try {
				texture = StormUtils.getTextureResourceFromStream(LOGO_RESOURCE_PATH, classLoader);
				if (texture == null)
				{
					StormLogger.error("Unable to read 'storm-logo.png' resource from stream.");
					this.bNoRender = true;
					return;
				}
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
			int width = Core.getInstance().getOffscreenWidth(0) / 2 - texture.getWidth() / 2;
			int height = Core.getInstance().getOffscreenHeight(0) / 2 - texture.getHeight() / 2;
			SpriteRenderer.instance.renderi(
					texture, width, height, texture.getWidth(), texture.getHeight(),
					1.0F, 1.0F, 1.0F, this.alpha, null
			);
			Core.getInstance().EndFrameUI();
			UIManager.useUIFBO = lastUseUIFBO;
		}
	}

	@Override
	public StateAction update() {

		float alphaStep = 0.02F;

		if (Mouse.isLeftDown() || GameKeyboard.isKeyDown(28) || GameKeyboard.isKeyDown(57) || GameKeyboard.isKeyDown(1))
		{
			this.targetAlpha = 0.0F;
			this.stage = 2;
		}
		if (this.stage < 2 && this.alpha == 1.0F && this.targetAlpha == 1.0F) {
			--this.logoDelay;
		}
		if (this.stage >= 1)
		{
			this.targetAlpha = 0.0F;
			if (this.leaveDelay > 0) {
				--this.leaveDelay;
			}
			else if (this.alpha == 0.0F)
			{
				Core.getInstance().StartFrame();
				SpriteRenderer.instance.renderi(
						null, 0, 0, Core.getInstance().getOffscreenWidth(0),
						Core.getInstance().getOffscreenHeight(0), 1.0F, 1.0F, 1.0F, 1.0F, null
				);
				this.bNoRender = true;
				return StateAction.Continue;
			}
		}
		if (this.alpha < this.targetAlpha)
		{
			this.alpha += alphaStep * GameTime.getInstance().getMultiplier();
			if (this.alpha > this.targetAlpha) {
				this.alpha = this.targetAlpha;
			}
		}
		else if (this.alpha > this.targetAlpha)
		{
			this.alpha -= alphaStep * GameTime.getInstance().getMultiplier();
			if (this.alpha < this.targetAlpha) {
				this.alpha = this.targetAlpha;
			}
		}
		if (this.alpha2 < this.targetAlpha)
		{
			this.alpha2 += alphaStep;
			if (this.alpha2 > this.targetAlpha) {
				this.alpha2 = this.targetAlpha;
			}
		}
		else if (this.alpha2 > this.targetAlpha)
		{
			this.alpha2 -= alphaStep * 2.0F;
			if (this.alpha2 < this.targetAlpha) {
				this.alpha2 = this.targetAlpha;
			}
		}
		return StateAction.Remain;
	}
}
