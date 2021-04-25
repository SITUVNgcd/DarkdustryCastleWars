package CastleWars;

import arc.util.CommandHandler;
import mindustry.mod.Plugin;
import mindustry.gen.Player;
import CastleWars.data.PlayerData;
import CastleWars.game.Logic;
import arc.Events;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.world.Block;

public class Main extends Plugin {

    public static Rules rules;
    public Logic logic;

    @Override
    public void init() {
        rules = new Rules();
        rules.pvp = true;
        rules.canGameOver = false;
        rules.teams.get(Team.sharded).cheat = true;
        rules.teams.get(Team.blue).cheat = true;
        rules.waves = true;
        for (Block block : Vars.content.blocks()) {
            if (block == Blocks.thoriumWall || block == Blocks.thoriumWallLarge) {
                continue;
            }
            rules.bannedBlocks.add(block);
        }

        PlayerData.init();
        logic = new Logic();

        Events.on(EventType.ServerLoadEvent.class, e -> {
            logic.restart();
            Vars.netServer.openServer();
        });

        Events.run(EventType.Trigger.update, () -> {
            logic.update();
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.<Player>register("info", "Info for Castle Wars", (args, player) -> {
            player.sendMessage("[lime]Defender units defend the core.\n"
                    + "[scarlet]Attacker[lime] units attack the [scarlet]enemy[lime] team.\n"
                    + "Income is your money per second [scarlet]don't ever let it go negative.[lime]\n"
                    + "Shoot at units to buy units.\n"
                    + "Why can't I buy this unit? If your income is below the income of the unit and its a defender you can't buy it.");
        });
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("restart", "no thing", (t) -> {
            logic.restart();
        });
    }
}
