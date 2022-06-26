package com.vetpetmon.wyrmsofnyrus.evo;

import com.vetpetmon.wyrmsofnyrus.AutoReg;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.synapselib.RNG;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * A collection of functions used to mess with invasion points.
 * I made this specifically because I hated writing so much code every
 * time I wanted to add points to the invasion
 *
 * Even worse, there's no dedicated getter. I'm fixing that as we speak.
 */
@AutoReg.ModElement.Tag
public class evoPoints extends AutoReg.ModElement {
    public evoPoints(AutoReg instance) {
        super(instance, 206);
    }

    public static int minEvoCap = 0;
    public static int get(World w) {return wyrmVariables.WorldVariables.get(w).wyrmEvo;}
    public static void set(World w, int i) {wyrmVariables.WorldVariables.get(w).wyrmEvo = i;}

    /**
     * Adds invasion points to the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void add(World w, int i){
        if (get(w) < minEvoCap) {
            set(w,minEvoCap);
        }
        wyrmVariables.WorldVariables.get(w).wyrmEvo += i;
        sync(w);
    }

    public static void minimum(){
        if (Loader.isModLoaded("draconicevolution")) {minEvoCap += 100;}
        if (Loader.isModLoaded("hbm")) {minEvoCap += 80;}
        if (Loader.isModLoaded("techguns")) {minEvoCap += 50;}
        if (Loader.isModLoaded("immersiveintelligence")) {minEvoCap += 50;}
        if (Loader.isModLoaded("srparasites")) {minEvoCap += 20;}
    }

    /**
     * Removes invasion points from the world.
     * Syncs automatically. Now you can do this in one line.
     * @param w World
     * @param i Value to add
     */
    public static void subtract(World w, int i){
        wyrmVariables.WorldVariables.get(w).wyrmEvo -= i;
        if (get(w) < minEvoCap) {
            set(w,minEvoCap);
        }
        sync(w);
    }

    public static void decay(World w) {
        if (RNG.dBase(3000) == 5) {
            subtract(w, (int) (1 * Evo.evoFactor));
        }
    }

    /**
     * Syncs the variable change to the world.
     */
    private static void sync(World w) {
        wyrmVariables.WorldVariables.get(w).syncData(w);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        World world = event.world;
        decay(world);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
