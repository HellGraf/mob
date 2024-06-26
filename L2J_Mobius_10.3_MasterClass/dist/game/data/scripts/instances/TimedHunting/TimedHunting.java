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
package instances.TimedHunting;

import org.l2jmobius.gameserver.data.xml.TimedHuntingZoneData;
import org.l2jmobius.gameserver.instancemanager.InstanceManager;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.TimedHuntingZoneHolder;
import org.l2jmobius.gameserver.model.instancezone.Instance;

import instances.AbstractInstance;

/**
 * @author Mobius
 */
public class TimedHunting extends AbstractInstance
{
	// NPCs
	private static final int PATROL_TELEPORTER = 34568;
	private static final int PATROL_GUARD = 34569;
	private static final int TELEPORT_SCOUT = 34549;
	// Misc
	private static final int[] TEMPLATE_IDS =
	{
		1001, // Fioren's Crystal Prison
		1006, // Jamoa Camp
		1007, // Pantheon's Museum
		1013, // Devastated Innadril
		1020, // Tower of Insolence
	};
	
	public TimedHunting()
	{
		super(TEMPLATE_IDS);
		addStartNpc(PATROL_TELEPORTER, PATROL_GUARD, TELEPORT_SCOUT);
		addTalkId(PATROL_TELEPORTER, PATROL_GUARD, TELEPORT_SCOUT);
		addFirstTalkId(PATROL_TELEPORTER, PATROL_GUARD, TELEPORT_SCOUT);
	}
	
	@Override
	public String onAdvEvent(String event, Npc npc, Player player)
	{
		if (event.startsWith("ENTER"))
		{
			final int zoneId = Integer.parseInt(event.split(" ")[1]);
			final TimedHuntingZoneHolder huntingZone = TimedHuntingZoneData.getInstance().getHuntingZone(zoneId);
			if (huntingZone == null)
			{
				return null;
			}
			
			if (huntingZone.isSoloInstance())
			{
				enterInstance(player, npc, huntingZone.getInstanceId());
			}
			else
			{
				Instance world = null;
				for (Instance instance : InstanceManager.getInstance().getInstances())
				{
					if (instance.getTemplateId() == huntingZone.getInstanceId())
					{
						world = instance;
						break;
					}
				}
				
				if (world == null)
				{
					world = InstanceManager.getInstance().createInstance(huntingZone.getInstanceId(), player);
				}
				
				player.teleToLocation(huntingZone.getEnterLocation(), world);
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new TimedHunting();
	}
}
