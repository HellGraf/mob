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
package org.l2jmobius.gameserver.network.clientpackets;

import org.l2jmobius.gameserver.data.xml.RecipeData;
import org.l2jmobius.gameserver.enums.PrivateStoreType;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.RecipeHolder;
import org.l2jmobius.gameserver.network.SystemMessageId;
import org.l2jmobius.gameserver.network.serverpackets.RecipeBookItemList;

public class RequestRecipeBookDestroy extends ClientPacket
{
	private int _recipeID;
	
	@Override
	protected void readImpl()
	{
		_recipeID = readInt();
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if (player == null)
		{
			return;
		}
		
		if (!getClient().getFloodProtectors().canPerformTransaction())
		{
			return;
		}
		
		if ((player.getPrivateStoreType() == PrivateStoreType.MANUFACTURE) || player.isCrafting())
		{
			player.sendPacket(SystemMessageId.YOU_MAY_NOT_ALTER_YOUR_RECIPE_BOOK_WHILE_ENGAGED_IN_MANUFACTURING);
			return;
		}
		
		final RecipeHolder rp = RecipeData.getInstance().getRecipe(_recipeID);
		if (rp == null)
		{
			player.sendPacket(SystemMessageId.THE_RECIPE_IS_INCORRECT);
			return;
		}
		
		// Remove the recipe from the list.
		player.unregisterRecipeList(_recipeID);
		
		// Send the new recipe book.
		player.sendPacket(new RecipeBookItemList(player, rp.isDwarvenRecipe()));
	}
}