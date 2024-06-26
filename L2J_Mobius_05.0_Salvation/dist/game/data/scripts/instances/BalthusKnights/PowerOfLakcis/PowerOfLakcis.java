/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package instances.BalthusKnights.PowerOfLakcis;

import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.instancezone.Instance;
import org.l2jmobius.gameserver.model.quest.QuestState;

import instances.AbstractInstance;
import quests.Q10556_ForgottenPowerStartOfFate.Q10556_ForgottenPowerStartOfFate;

/**
 * Power of Lakcis instance zone.
 * @author Kazumi
 */
public final class PowerOfLakcis extends AbstractInstance
{
	// NPCs
	private static final int MASTER_HEALER = 34393;
	// Misc
	private static final int TEMPLATE_ID = 279;
	
	public PowerOfLakcis()
	{
		super(TEMPLATE_ID);
		addFirstTalkId(MASTER_HEALER);
	}
	
	@Override
	public String onFirstTalk(Npc npc, Player player)
	{
		final Instance instance = npc.getInstanceWorld();
		if (instance != null)
		{
			switch (player.getRace())
			{
				case HUMAN:
				{
					return "awakening_symbol8179.htm";
				}
				case ELF:
				{
					return "awakening_symbol8180.htm";
				}
				case DARK_ELF:
				{
					return "awakening_symbol8181.htm";
				}
			}
		}
		return null;
	}
	
	@Override
	public String onAdvEvent(String event, Npc npc, Player player)
	{
		String htmltext = null;
		if (event.equals("enterInstance"))
		{
			final QuestState qs = player.getQuestState(Q10556_ForgottenPowerStartOfFate.class.getSimpleName());
			if ((qs != null) && qs.isStarted())
			{
				enterInstance(player, npc, TEMPLATE_ID);
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new PowerOfLakcis();
	}
}