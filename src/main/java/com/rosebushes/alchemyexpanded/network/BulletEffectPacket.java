package com.rosebushes.alchemyexpanded.network;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.util.MagicEffect;
import com.mraof.minestuck.network.MSPacket;
import com.mraof.minestuck.network.MSPayloads;
import com.mraof.minestuck.network.MagicRangedEffectPacket;
import com.rosebushes.alchemyexpanded.AlchemyExpanded;
import com.rosebushes.alchemyexpanded.client.util.BulletEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BulletEffectPacket(Vec3 pos, Vec3 lookVec) implements MSPacket.PlayToClient{
    public static final CustomPacketPayload.Type<BulletEffectPacket> ID = new CustomPacketPayload.Type(AlchemyExpanded.id("bullet_effect"));
    public static final StreamCodec<FriendlyByteBuf, BulletEffectPacket> STREAM_CODEC;

    public BulletEffectPacket(Vec3 pos, Vec3 lookVec) {
        this.pos = pos;
        this.lookVec = lookVec;
    }

    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public void execute(IPayloadContext context) {
        BulletEffect.rangedCollisionEffect(ParticleTypes.CRIT, false, Minecraft.getInstance().level, this.pos);
    }

    static {
        STREAM_CODEC = StreamCodec.composite(AEPayloads.VEC3_STREAM_CODEC, BulletEffectPacket::pos, AEPayloads.VEC3_STREAM_CODEC, BulletEffectPacket::lookVec, BulletEffectPacket::new);
    }

    public Vec3 pos() {
        return this.pos;
    }

    public Vec3 lookVec() {
        return this.lookVec;
    }
}
