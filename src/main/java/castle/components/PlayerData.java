package castle.components;

import arc.struct.Seq;
import castle.CastleRooms;
import mindustry.entities.Units;
import mindustry.gen.Call;
import mindustry.gen.Player;

import java.util.Locale;

import static castle.CastleUtils.*;
import static castle.Main.findLocale;

public class PlayerData {

    public static final Seq<PlayerData> datas = new Seq<>();

    public Player player;

    public int money = 0;
    public int income = 15;

    public Locale locale;

    public PlayerData(Player player) {
        this.handlePlayerJoin(player);
    }

    public static PlayerData getData(String uuid) {
        return datas.find(data -> data.player.uuid().equals(uuid));
    }

    public void update() {
        if (!player.con.isConnected()) return;

        if (player.shooting) CastleRooms.rooms.each(room -> room.check(player.mouseX, player.mouseY) && room.canBuy(this), room -> room.buy(this));

        Call.setHudText(player.con, Bundle.format("ui.hud", locale, money, income, countUnits(player.team()), Units.getCap(player.team()), timer));
    }

    public void updateMoney() {
        if (!player.con.isConnected()) return;
        money += income;
    }

    public void handlePlayerJoin(Player player) {
        this.player = player;
        this.locale = findLocale(player);
    }

    public void reset() {
        this.money = 0;
        this.income = 15;
    }
}
