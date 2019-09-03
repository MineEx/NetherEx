/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.entity;

import logictechcorp.libraryex.utilities.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.hostile.SpinoutEntity;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExEntities
{
    public static final EntityType<SpinoutEntity> SPINOUT = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityType<?>> event)
    {
        registerEntity("spinout", EntityType.Builder.create(SpinoutEntity::new, EntityClassification.MONSTER).size(0.55F, 1.95F).immuneToFire());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        registerEntitySpawn(SPINOUT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpinoutEntity::canSpawn);
    }

    private static <T extends Entity> void registerEntity(String name, EntityType.Builder<T> builder)
    {
        ResourceLocation registryName = new ResourceLocation(NetherEx.MOD_ID, name);
        ForgeRegistries.ENTITIES.register(builder.build(registryName.toString()).setRegistryName(registryName));
    }

    private static <T extends MobEntity> void registerEntitySpawn(EntityType<T> entityType, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<T> placementPredicate)
    {
        EntitySpawnPlacementRegistry.register(entityType, placementType, heightMapType, placementPredicate);
    }
}
