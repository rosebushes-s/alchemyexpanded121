package com.rosebushes.alchemyexpanded.item.weapon.gun;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SwordGunWeaponItem extends GunWeaponItem {
    private final int meleeDamage;
    private final float attackSpeed;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public SwordGunWeaponItem(Properties properties, int melDmg, float atkSpeed, float damage, double speed, int amount, float spread, int rate, int ammoCap, int ammoConsumption, int reload, boolean infinite, boolean dualWield, Item ammo, @Nullable Item iDouble) {
        super(properties, damage, speed, amount, spread, rate, ammoCap, ammoConsumption, reload, infinite, dualWield, ammo, iDouble);
        this.meleeDamage = melDmg;
        this.attackSpeed = atkSpeed;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.meleeDamage, AttributeModifier.Operation.ADDITION)
        );
        builder.put(
                Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION)
        );
        this.defaultModifiers = builder.build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker instanceof Player) {
            target.hurt(attacker.damageSources().playerAttack((Player)attacker), meleeDamage);
        }
        else {
            target.hurt(attacker.damageSources().mobAttack(attacker), meleeDamage);
        }
        stack.hurtAndBreak(1, attacker, (PlayerEntity) -> PlayerEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            return state.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(2, miningEntity, PlayerEntity -> miningEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState block) {
        return block.is(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
