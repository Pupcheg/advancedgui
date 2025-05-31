package me.supcheg.advancedgui.platform.paper.network.message;

import net.kyori.adventure.key.Keyed;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public interface Message extends Keyed {
    void write(@NotNull FriendlyByteBuf buf);
}
