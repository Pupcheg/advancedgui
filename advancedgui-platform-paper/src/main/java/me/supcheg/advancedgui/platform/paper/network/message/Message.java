package me.supcheg.advancedgui.platform.paper.network.message;

import net.kyori.adventure.key.Keyed;
import net.minecraft.network.FriendlyByteBuf;

public interface Message extends Keyed {
    void write(FriendlyByteBuf buf);
}
