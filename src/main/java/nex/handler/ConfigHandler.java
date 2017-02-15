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

package nex.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@Config(modid = NetherEx.MOD_ID, name = "NetherEx")
public class ConfigHandler
{
    public static Client client = new Client();
    public static Dimension dimension = new Dimension();
    public static Block block = new Block();
    public static PotionEffects potion_effects = new PotionEffects();
    public static Entity entity = new Entity();
    public static Biome biome = new Biome();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigHandler");

    public static class Client
    {
        public static Graphics graphics = new Graphics();

        public static class Graphics
        {
            public static boolean disableNetherFog = false;
        }
    }

    public static class Dimension
    {
        public static Nether nether = new Nether();

        public static class Nether
        {
            public static boolean generateSoulSand = false;
            public static boolean generateGravel = false;
            public static boolean isLavaInfinite = false;
            public static boolean enablePortalFix = true;
        }
    }

    public static class Block
    {
        public static SoulSand soul_sand = new SoulSand();
        public static Rime rime = new Rime();
        public static Magma magma = new Magma();

        public static class SoulSand
        {
            public static boolean doesNetherwartUseNewGrowthSystem = true;
            public static boolean allowAllHoesToTill = false;
            public static boolean doesRequireIchor = true;
        }

        public static class Magma
        {
            public static boolean turnIntoLava = false;
        }

        public static class Rime
        {
            public static boolean canFreezeWater = true;
            public static boolean canFreezeLava = true;
            public static boolean canFreezeMobs = true;
        }

        public static class Thornstalk
        {
            public static boolean canDestroyItems = false;

            @Config.Comment("Add mobs the Thornstalk shouldn't hurt")
            public static String[] blacklist = new String[]{"minecraft:wither_skeleton", "minecraft:zombie_pigman", "nex:monster_spinout"};
        }
    }

    public static class PotionEffects
    {
        public static Freeze freeze = new Freeze();
        public static Spore spore = new Spore();

        public static class Freeze
        {
            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 2048)
            public static int chanceOfFreezing = 512;

            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 2048)
            public static int chanceOfThawing = 1024;

            @Config.Comment("Add mobs that shouldn't freeze")
            public static String[] blacklist = new String[]{"minecraft:blaze", "minecraft:polar_bear", "nex:monster_wight", "nex:monster_ember", "nex:monster_spinout"};
        }

        public static class Spore
        {
            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 256)
            public static int chanceOfSporeSpawning = 128;

            @Config.Comment("Add mobs that shouldn't spawn Spores")
            public static String[] blacklist = new String[]{"nex:monster_spore_creeper", "nex:monster_spore", "nex:neutral_mogus"};
        }
    }

    public static class Entity
    {
        public static Nethermite nethermite = new Nethermite();
        public static Spinout spinout = new Spinout();
        public static SporeCreeper spore_creeper = new SporeCreeper();
        public static Spore spore = new Spore();
        public static GhastQueen ghast_queen = new GhastQueen();

        public static class Nethermite
        {
            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int chanceOfSpawning = 24;

            @Config.Comment("Add block the Nethermite should spawn from")
            public static String[] whitelist = new String[]{"minecraft:netherrack", "nex:block_netherrack"};
        }

        public static class Spinout
        {
            @Config.Comment({"The lower the number the less time it spins", "The higher the number the more time it spins"})
            @Config.RangeInt(min = 1, max = 512)
            public static int spinTime = 6;

            @Config.Comment({"The lower the number the less time it goes without spinning", "The higher the number the more time it goes without spinning"})
            @Config.RangeInt(min = 1, max = 512)
            public static int spinCooldown = 2;
        }

        public static class SporeCreeper
        {
            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int chanceOfSporeSpawning = 12;
        }

        public static class Spore
        {
            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 512)
            public static int growthTime = 60;

            @Config.Comment({"The lower the number the less Spore Creeper spawn", "The higher the number the more Spore Creeper spawn"})
            @Config.RangeInt(min = 1, max = 64)
            public static int creeperSpawns = 4;

        }

        public static class GhastQueen
        {
            @Config.Comment({"The lower the number the less time it goes without spinning", "The higher the number the more time it goes without spinning"})
            @Config.RangeInt(min = 1, max = 512)
            public static int attackCooldown = 30;

            @Config.Comment({"The lower the number the less Ghast spawn", "The higher the number the more Ghast spawn"})
            @Config.RangeInt(min = 1, max = 64)
            public static int ghastSpawns = 4;
        }
    }

    public static class Biome
    {
        public static Hell hell = new Hell();
        public static RuthlessSands ruthless_sands = new RuthlessSands();
        public static FungiForest fungi_forest = new FungiForest();
        public static TorridWasteland torrid_wasteland = new TorridWasteland();
        public static ArcticAbyss arctic_abyss = new ArcticAbyss();

        public static class Hell
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateBrownMushrooms = true;
            public static boolean generateRedMushrooms = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateVillages = true;
            public static boolean generateGraves = true;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaSpringRarity = 8;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int fireRarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int magmaRarity = 4;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaTrapRarity = 16;

            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int villageRarity = 1;

            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int graveRarity = 24;
        }

        public static class RuthlessSands
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateThornstalk = true;
            public static boolean generateAncientAltars = true;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 8;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaSpringRarity = 8;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaTrapRarity = 16;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int thornstalkRarity = 10;

            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int ancientAltarRarity = 40;
        }

        public static class FungiForest
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateElderMushrooms = true;
            public static boolean generateEnokiMushrooms = true;

            public static int biomeRarity = 4;
            public static int glowstonePass1Rarity = 10;
            public static int glowstonePass2Rarity = 10;
            public static int quartzOreRarity = 16;
            public static int elderMushroomRarity = 32;
            public static int enokiMushroomRarity = 4;
        }

        public static class TorridWasteland
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateBasalt = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateLavaPits = true;
            public static boolean generateBlazingPyramids = true;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 6;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaSpringRarity = 24;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int fireRarity = 32;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int basaltRarity = 12;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int magmaRarity = 12;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaTrapRarity = 48;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int lavaPitRarity = 8;

            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int blazingPyramidRarity = 4;
        }

        public static class ArcticAbyss
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateRimeOre = true;
            public static boolean generateIchorPits = true;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int biomeRarity = 2;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass1Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int glowstonePass2Rarity = 10;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int quartzOreRarity = 16;

            @Config.Comment({"The lower the number the rarer it is", "The higher the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int rimeOreRarity = 16;

            @Config.Comment({"The higher the number the rarer it is", "The lower the number the more common it is"})
            @Config.RangeInt(min = 1, max = 64)
            public static int ichorPitRarity = 16;
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equals(NetherEx.MOD_ID))
        {
            ConfigManager.load(NetherEx.MOD_ID, Config.Type.INSTANCE);
            LOGGER.info("Configuration saved.");
        }
    }
}
