package me.srrapero720.waterframes.common.network;

import me.srrapero720.waterframes.WaterFrames;
import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import me.srrapero720.waterframes.common.network.packets.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import team.creative.creativecore.common.network.CreativeNetwork;
import team.creative.creativecore.common.network.CreativePacket;

import java.util.List;

import static me.srrapero720.waterframes.WaterFrames.LOGGER;

public class DisplayNetwork {
    public static final CreativeNetwork DATA = new CreativeNetwork(1, LOGGER, new ResourceLocation(WaterFrames.ID, "data"));
    public static final CreativeNetwork CONTROL = new CreativeNetwork(1, LOGGER, new ResourceLocation(WaterFrames.ID, "control"));

    public static void init() {

    }

    public static void sendClient(CreativePacket packet, Level level, BlockPos pos) {
        for (ServerPlayer player: (List<ServerPlayer>) level.players()) {
            DATA.sendToClient(packet, player);
        }
//        DATA.sendToClient(packet, level, pos);
    }

    public static void sendClient(CreativePacket packet, ServerPlayer player) {
        DATA.sendToClient(packet, player);
    }

    public static void sendServer(CreativePacket packet) {
        DATA.sendToServer(packet);
    }

    public static void sendClient(DisplayDataPacket packet, DisplayTile tile) {
        for (ServerPlayer player: (List<ServerPlayer>) tile.getLevel().players()) {
            DATA.sendToClient(packet, player);
        }
    }

    public static void sendServer(DisplayDataPacket packet) {
        DATA.sendToServer(packet);
    }

    public static void sendClient(DisplayControlPacket packet, DisplayTile tile) {
        if (packet.bounce) {
            packet.bounce = false;
            packet.execute(tile, false);
        }
        for (ServerPlayer player: (List<ServerPlayer>) tile.getLevel().players()) {
            CONTROL.sendToClient(packet, player);
        }
//        CONTROL.sendToClient(packet, tile.getLevel().getChunkAt(packet.pos));
    }

    public static void sendServer(DisplayControlPacket packet) {
        CONTROL.sendToServer(packet);
    }
}
