package com.vetpetmon.wyrmsofnyrus.entity.wyrms;

import com.vetpetmon.wyrmsofnyrus.entity.EntityWyrm;
import com.vetpetmon.wyrmsofnyrus.entity.ability.hexepodContents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashMap;
import java.util.Map;

public class EntityHexePod extends EntityWyrm implements IAnimatable{
    private AnimationFactory factory = new AnimationFactory(this);
    public EntityHexePod(World world) {
        super(world);
        this.casteType = 9;
        setSize(1f, 1f);
        experienceValue = 0;
        enablePersistence();
        setNoAI(false);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof EntityArrow)
            return false;
        if (source.getImmediateSource() instanceof EntityPlayer)
            return false;
        if (source.getImmediateSource() instanceof EntityPotion)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.05D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3D);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.enderdragon_fireball.explode"));
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        int x = (int) this.posX;
        int y = (int) this.posY;
        int z = (int) this.posZ;
        Entity entity = this;
        {
            Map<String, Object> $_dmap = new HashMap<>();
            $_dmap.put("entity", entity);
            $_dmap.put("x", x);
            $_dmap.put("y", y);
            $_dmap.put("z", z);
            $_dmap.put("world", world);
            hexepodContents.doThis($_dmap);
        }
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1F, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hexepod.idle"));
        return PlayState.CONTINUE;
    }
}
