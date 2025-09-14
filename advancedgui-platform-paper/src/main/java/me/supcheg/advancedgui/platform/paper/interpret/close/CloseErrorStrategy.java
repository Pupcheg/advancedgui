package me.supcheg.advancedgui.platform.paper.interpret.close;

import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;

@Slf4j
enum CloseErrorStrategy {
    LOG {
        @Override
        void handle(Exception exception) {
            log.error(exception.getMessage(), exception);
        }
    },
    IGNORE {
        @Override
        void handle(Exception exception) {
        }
    },
    FATAL {
        @Override
        void handle(Exception exception) {
            throw Lombok.sneakyThrow(exception);
        }
    };

    abstract void handle(Exception exception);
}
