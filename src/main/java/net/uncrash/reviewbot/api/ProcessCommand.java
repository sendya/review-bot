package net.uncrash.reviewbot.api;

public interface ProcessCommand extends Command {

    void join();

    void leave();
}
