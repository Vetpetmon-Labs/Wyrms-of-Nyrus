package com.vetpetmon.wyrmsofnyrus;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.model.obj.OBJLoader;

import java.io.File;

public class ClientProxywyrmsofnyrus implements IProxywyrmsofnyrus {
	@Override
	public void init(FMLInitializationEvent event) {
	}

	@Override
	public File getDataDir() {
		return Minecraft.getMinecraft().mcDataDir;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		OBJLoader.INSTANCE.addDomain("wyrmsofnyrus");
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
	}
}
