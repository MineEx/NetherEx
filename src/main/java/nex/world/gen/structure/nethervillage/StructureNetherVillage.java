/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.world.gen.structure.nethervillage;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.entity.passive.EntityPigtificate;
import nex.util.WorldGenUtil;
import nex.village.Pigtificate;
import nex.world.gen.structure.StructureNetherEx;

import java.util.List;
import java.util.Random;

public abstract class StructureNetherVillage extends StructureNetherEx
{
    protected int averageGroundLvl = -1;
    private int villagersSpawned;
    protected int structureType;
    protected boolean isZombieInfested;
    protected StructureNetherVillageWell.Controller controller;

    public StructureNetherVillage()
    {
    }

    protected StructureNetherVillage(StructureNetherVillageWell.Controller controllerIn, int componentType)
    {
        super(componentType);

        if(controllerIn != null)
        {
            structureType = controllerIn.structureType;
            isZombieInfested = controllerIn.isZombieInfested;
            controller = controllerIn;
        }
    }

    public static void registerPieces()
    {
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageWell.Controller.class, "village_nether_controller");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageWell.class, "village_nether_well");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillagePath.class, "village_nether_path");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageLampPost.class, "village_nether_lamp_post");
        MapGenStructureIO.registerStructureComponent(StructureHunterHut.class, "village_nether_hut_hunter");
        MapGenStructureIO.registerStructureComponent(StructureGathererHut.class, "village_nether_hut_gatherer");
        MapGenStructureIO.registerStructureComponent(StructureScavengerHut.class, "village_nether_hut_scavenger");
        MapGenStructureIO.registerStructureComponent(StructureArmorsmithHut.class, "village_nether_hut_armorsmith");
        MapGenStructureIO.registerStructureComponent(StructureToolsmithHut.class, "village_nether_hut_toolsmith");
        MapGenStructureIO.registerStructureComponent(StructureBrewerHut.class, "village_nether_hut_brewer");
    }

    public static List<Piece> getStructureVillagePieceList(Random rand, int size)
    {
        List<Piece> list = Lists.newArrayList();
        list.add(new Piece(StructureHunterHut.class, 3, MathHelper.getInt(rand, size + 2, (size * 3) + 5)));
        list.add(new Piece(StructureGathererHut.class, 3, MathHelper.getInt(rand, size + 2, (size * 3) + 5)));
        list.add(new Piece(StructureScavengerHut.class, 3, MathHelper.getInt(rand, size + 2, (size * 3) + 5)));
        list.add(new Piece(StructureArmorsmithHut.class, 3, MathHelper.getInt(rand, size + 2, (size * 3) + 5)));
        list.add(new Piece(StructureToolsmithHut.class, 3, MathHelper.getInt(rand, size + 2, (size * 3) + 5)));
        list.add(new Piece(StructureBrewerHut.class, 3, MathHelper.getInt(rand, size + 2, (size * 3) + 5)));
        list.removeIf(piece -> (piece).getSpawnLimit() == 0);
        return list;
    }

    private static int updatePieceWeight(List<Piece> pieces)
    {
        boolean flag = false;
        int i = 0;

        for(Piece piece : pieces)
        {
            if(piece.getSpawnLimit() > 0 && piece.getAmountSpawned() < piece.getSpawnLimit())
            {
                flag = true;
            }

            i += piece.getWeight();
        }

        return flag ? i : -1;
    }

    private static StructureNetherVillage createComponent(StructureNetherVillageWell.Controller controller, Piece piece, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        Class<? extends StructureNetherVillage> structureNetherVillageCls = piece.getCls();

        if(structureNetherVillageCls == StructureHunterHut.class)
        {
            return StructureHunterHut.createComponent(controller, components, rand, minX, minY, minZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureGathererHut.class)
        {
            return StructureGathererHut.createComponent(controller, components, rand, minX, minY, minZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureScavengerHut.class)
        {
            return StructureScavengerHut.createComponent(controller, components, rand, minX, minY, minZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureArmorsmithHut.class)
        {
            return StructureArmorsmithHut.createComponent(controller, components, rand, minX, minY, minZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureToolsmithHut.class)
        {
            return StructureToolsmithHut.createComponent(controller, components, rand, minX, minY, minZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureBrewerHut.class)
        {
            return StructureBrewerHut.createComponent(controller, components, rand, minX, minY, minZ, facing, componentType);
        }
        else
        {
            return null;
        }
    }

    private static StructureNetherVillage generateComponent(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        int i = updatePieceWeight(controller.getPieces());

        if(i <= 0)
        {
            return null;
        }
        else
        {
            int j = 0;

            while(j < 5)
            {
                j++;
                int k = rand.nextInt(i);

                for(Piece piece : controller.getPieces())
                {
                    k -= piece.getWeight();

                    if(k < 0)
                    {
                        if(!piece.canSpawnMoreVillagePieces() || piece == controller.getPiece() && controller.getPieces().size() > 1)
                        {
                            break;
                        }

                        StructureNetherVillage structureNetherVillage = createComponent(controller, piece, components, rand, minX, minY, minZ, facing, componentType);

                        if(structureNetherVillage != null)
                        {
                            piece.incrementAmountSpawned();
                            controller.setPiece(piece);

                            if(!piece.canSpawnMoreVillagePieces())
                            {
                                controller.getPieces().remove(piece);
                            }

                            return structureNetherVillage;
                        }
                    }
                }
            }

            StructureBoundingBox boundingBox = StructureNetherVillageLampPost.findPieceBox(controller, components, rand, minX, minY, minZ, facing);

            if(boundingBox != null)
            {
                return new StructureNetherVillageLampPost(controller, componentType, rand, boundingBox, facing);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructureComponent generateAndAddComponent(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        if(componentType > 50)
        {
            return null;
        }
        else if(Math.abs(minX - controller.getBoundingBox().minX) <= 112 && Math.abs(minZ - controller.getBoundingBox().minZ) <= 112)
        {
            StructureComponent component = generateComponent(controller, components, rand, minX, minY, minZ, facing, componentType + 1);

            if(component != null)
            {
                components.add(component);
                controller.getPendingHouses().add(component);
                return component;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public static StructureComponent generateAndAddRoadPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        if(componentType > 3 + controller.getVillageSize())
        {
            return null;
        }
        else if(Math.abs(minX - controller.getBoundingBox().minX) <= 112 && Math.abs(minZ - controller.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox boundingBox = StructureNetherVillagePath.findPieceBox(controller, components, rand, minX, minY, minZ, facing);

            if(boundingBox != null && boundingBox.minY > 10)
            {
                StructureComponent structurecomponent = new StructureNetherVillagePath(controller, componentType, rand, boundingBox, facing);
                components.add(structurecomponent);
                controller.getPendingRoads().add(structurecomponent);
                return structurecomponent;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("HorizontalPosition", averageGroundLvl);
        tagCompound.setInteger("PigtificateCount", villagersSpawned);
        tagCompound.setInteger("Type", structureType);
        tagCompound.setBoolean("Zombie", isZombieInfested);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        averageGroundLvl = tagCompound.getInteger("HorizontalPosition");
        villagersSpawned = tagCompound.getInteger("PigtificateCount");
        structureType = tagCompound.getInteger("Type");
        isZombieInfested = tagCompound.getBoolean("Zombie");
    }

    @Override
    public abstract boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn);

    protected StructureComponent getNextComponentNN(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minY, int minXZ)
    {
        EnumFacing facing = getCoordBaseMode();

        if(facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX - 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.WEST, getComponentType());
                case SOUTH:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX - 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.WEST, getComponentType());
                case WEST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
                case EAST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
            }
        }
        else
        {
            return null;
        }
    }

    protected StructureComponent getNextComponentPP(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minY, int minXZ)
    {
        EnumFacing facing = getCoordBaseMode();

        if(facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    return generateAndAddComponent(controller, components, rand, boundingBox.maxX + 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.EAST, getComponentType());
                case SOUTH:
                    return generateAndAddComponent(controller, components, rand, boundingBox.maxX + 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.EAST, getComponentType());
                case WEST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
                case EAST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
            }
        }
        else
        {
            return null;
        }
    }

    protected int findAverageGroundLevel(World world, StructureBoundingBox boundingBoxIn)
    {
        int i = 0;
        int j = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
        {
            for(int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
            {
                mutableBlockPos.setPos(l, 32, k);

                if(boundingBoxIn.isVecInside(mutableBlockPos))
                {
                    i += Math.max(WorldGenUtil.getSolidBlockBelow(world, mutableBlockPos, 125).getY(), world.getSeaLevel() + 1);
                    j++;
                }
            }
        }

        if(j == 0)
        {
            return -1;
        }
        else
        {
            return i / j;
        }
    }

    protected static boolean canVillageGoDeeper(StructureBoundingBox boundingBoxIn)
    {
        return boundingBoxIn != null && boundingBoxIn.minY > 10;
    }

    protected void spawnPigtificates(World world, StructureBoundingBox boundingBox, int x, int y, int z, Pigtificate.Career career, int count, ItemStack heldStack)
    {
        for(int i = villagersSpawned; i < count; i++)
        {
            int j = getXWithOffset(x + i, z);
            int k = getYWithOffset(y);
            int l = getZWithOffset(x + i, z);

            if(!boundingBox.isVecInside(new BlockPos(j, k, l)))
            {
                break;
            }

            villagersSpawned++;

            if(isZombieInfested)
            {
                EntityPigZombie pigZombie = new EntityPigZombie(world);
                pigZombie.setLocationAndAngles((double) j + 0.5D, (double) k, (double) l + 0.5D, 0.0F, 0.0F);
                pigZombie.setHeldItem(EnumHand.MAIN_HAND, heldStack);
                pigZombie.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(pigZombie)), null);
                pigZombie.enablePersistence();
                world.spawnEntity(pigZombie);
            }
            else
            {
                EntityPigtificate pigtificate = new EntityPigtificate(world, career);
                pigtificate.setLocationAndAngles((double) j + 0.5D, (double) k, (double) l + 0.5D, 0.0F, 0.0F);
                pigtificate.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(pigtificate)), null);
                pigtificate.setHeldItem(EnumHand.MAIN_HAND, heldStack);
                world.spawnEntity(pigtificate);
            }
        }
    }

    protected void setFenceGate(World world, IBlockState state, int x, int y, int z, EnumFacing facing, StructureBoundingBox boundingBox)
    {
        if(!isZombieInfested)
        {
            setBlockState(world, state.withProperty(BlockFenceGate.FACING, facing), x, y, z, boundingBox);
        }
    }

    protected void replaceAirAndLiquidDownwards(World world, IBlockState state, int x, int y, int z, StructureBoundingBox boundingBox)
    {
        super.replaceAirAndLiquidDownwards(world, state, x, y, z, boundingBox);
    }

    protected void setStructureType(int structureTypeIn)
    {
        structureType = structureTypeIn;
    }

    public static class Piece
    {
        private Class<? extends StructureNetherVillage> cls;
        private final int weight;
        private int amountSpawned;
        private int spawnLimit;

        public Piece(Class<? extends StructureNetherVillage> clsIn, int weightIn, int spawnLimitIn)
        {
            cls = clsIn;
            weight = weightIn;
            spawnLimit = spawnLimitIn;
        }

        public boolean canSpawnMoreVillagePieces()
        {
            return spawnLimit == 0 || amountSpawned < spawnLimit;
        }

        public Class<? extends StructureNetherVillage> getCls()
        {
            return cls;
        }

        public int getWeight()
        {
            return weight;
        }

        public int getAmountSpawned()
        {
            return amountSpawned;
        }

        public int getSpawnLimit()
        {
            return spawnLimit;
        }

        public void incrementAmountSpawned()
        {
            amountSpawned++;
        }
    }

    public abstract static class Road extends StructureNetherVillage
    {
        public Road()
        {
        }

        protected Road(StructureNetherVillageWell.Controller controller, int componentType)
        {
            super(controller, componentType);
        }
    }
}
