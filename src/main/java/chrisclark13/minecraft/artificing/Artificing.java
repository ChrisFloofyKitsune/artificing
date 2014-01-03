package chrisclark13.minecraft.artificing;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import chrisclark13.minecraft.artificing.block.ModBlocks;
import chrisclark13.minecraft.artificing.core.handler.GuiHandler;
import chrisclark13.minecraft.artificing.core.handler.LivingEventHandler;
import chrisclark13.minecraft.artificing.core.handler.PlayerHandler;
import chrisclark13.minecraft.artificing.core.helper.LocalizationHelper;
import chrisclark13.minecraft.artificing.core.helper.LogHelper;
import chrisclark13.minecraft.artificing.core.helper.RuneHelper;
import chrisclark13.minecraft.artificing.core.proxy.CommonProxy;
import chrisclark13.minecraft.artificing.creativetab.CreativeTabArtificing;
import chrisclark13.minecraft.artificing.enchantment.ModEnchantments;
import chrisclark13.minecraft.artificing.item.ModItems;
import chrisclark13.minecraft.artificing.lib.Reference;
import chrisclark13.minecraft.artificing.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(channels = { Reference.CHANNEL }, packetHandler = PacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class Artificing {

	public static PlayerHandler playerHandler;
	public static LivingEventHandler livingEventHandler;

	// The instance of your mod that Forge uses.
	@Instance(Reference.MOD_ID)
	public static Artificing instance;

	public static CreativeTabs tabArtificing = new CreativeTabArtificing(
			CreativeTabs.getNextID(), Reference.MOD_ID);

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		LogHelper.init();

		LocalizationHelper.loadLanguages();

		ModBlocks.init();

		ModItems.init();

		ModEnchantments.init();

		RuneHelper.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// Register the GUI handler
		NetworkRegistry.instance().registerGuiHandler(instance,
				new GuiHandler());

		// Register TileEntities
		proxy.registerTileEntities();
		proxy.initRendering();
		playerHandler = new PlayerHandler();
		GameRegistry.registerPlayerTracker(playerHandler);
		MinecraftForge.EVENT_BUS.register(playerHandler);

		livingEventHandler = new LivingEventHandler();
		MinecraftForge.EVENT_BUS.register(livingEventHandler);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}
