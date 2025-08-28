package com.rosebushes.alchemyexpanded.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface AEPacket {
    public interface PlayToClient extends CustomPacketPayload {
        void execute(IPayloadContext var1);
    }

    public interface PlayToServer extends CustomPacketPayload {
        void execute(IPayloadContext var1, ServerPlayer var2);
    }
}
