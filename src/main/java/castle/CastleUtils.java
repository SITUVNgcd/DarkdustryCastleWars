package castle;

import mindustry.content.Planets;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.gen.Teamc;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.*;

public class CastleUtils {

    public static void applyRules(Rules rules) {
        rules.waveTimer = rules.waves = rules.waveSending = false;
        rules.pvp = rules.attackMode = true;

        rules.unitCap = 500;
        rules.unitCapVariable = false;
        rules.dropZoneRadius = 48f;

        rules.polygonCoreProtection = true;
        rules.buildSpeedMultiplier = 0.5f;

        rules.modeName = "Castle Wars";

        rules.teams.get(Team.sharded).cheat = true;
        rules.teams.get(Team.blue).cheat = true;

        rules.weather.clear();
        rules.bannedBlocks.addAll(content.blocks().select(block -> block instanceof CoreBlock || block instanceof UnitFactory || block.group == BlockGroup.turrets || block.group == BlockGroup.drills || block.group == BlockGroup.logic));
    }

    public static boolean isSerpulo() {
        return state.rules.env == Planets.serpulo.defaultEnv || state.rules.hiddenBuildItems.equals(Planets.serpulo.hiddenItems.asSet());
    }

    public static boolean isBreak() {
        return world.isGenerating() || state.gameOver || state.isPaused();
    }

    public static boolean onEnemySide(Teamc teamc) {
        return (teamc.team() == Team.sharded && teamc.y() > world.unitHeight() / 2f) ||
                (teamc.team() == Team.blue && teamc.y() < world.unitHeight() / 2f);
    }
}