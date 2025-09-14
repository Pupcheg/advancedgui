package me.supcheg.advancedgui.platform.paper.interpret.send;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

enum SendActionTarget {
    CHAT {
        @Override
        void send(Audience audience, Component message) {
            audience.sendMessage(message);
        }
    },
    ACTION_BAR {
        @Override
        void send(Audience audience, Component message) {
            audience.sendActionBar(message);
        }
    };

    abstract void send(Audience audience, Component message);
}
