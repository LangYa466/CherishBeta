package cherish.utils.misc;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import cherish.cherish;
import cherish.modules.Module;
import cherish.modules.impl.misc.AntiBot;
import cherish.modules.impl.misc.Teams;
import cherish.modules.impl.player.Blink;
import cherish.modules.impl.player.NoFall;
import cherish.modules.impl.setting.*;
import cherish.utils.wrapper.Wrapper;

public final class EntityUtils implements Wrapper {
    private static boolean getEnable(Class<? extends Module> clazz) {
        return cherish.INSTANCE != null
                && cherish.INSTANCE.getModuleManager() != null
                && cherish.INSTANCE.getModuleManager().getModule(clazz) != null
                && cherish.INSTANCE.getModuleManager().getModule(clazz).isEnabled();
    }

    public static boolean isSelected(final Entity entity, final boolean canAttackCheck) {
        if (entity instanceof LivingEntity && (Target.INSTANCE.dead.getValue() || entity.isAlive()) && !entity.equals(mc.player) && !entity.equals(Blink.fakePlayer)) {

            if (Target.INSTANCE.invisible.getValue() || !entity.isInvisible()) {

                if (Target.INSTANCE.players.getValue() && entity instanceof Player entityPlayer) {

                    if (canAttackCheck) {
                        if (((AntiBot) cherish.INSTANCE.getModuleManager().getModule(AntiBot.class)).isServerBot(entityPlayer))
                            return false;

                        if (NoFall.isPlacing)
                            return false;

                        if (entityPlayer.isSpectator())
                            return false;

                        final Teams teams = (Teams) cherish.INSTANCE.getModuleManager().getModule(Teams.class);
                        return !teams.isEnabled() || !teams.isSameTeam(entityPlayer);
                    }

                    return true;
                }

                return Target.INSTANCE.mobs.getValue() && isMob(entity) || Target.INSTANCE.animals.getValue() && isAnimal(entity);
            }
        }
        return false;
    }

/*    public static boolean isFriend(final IEntity entity) {
        return classProvider.isEntityPlayer(entity) && entity.getName() != null &&
                LiquidBounce.fileManager.friendsConfig.isFriend(ColorUtils.stripColor(entity.getName()));
    }*/

    public static boolean isAnimal(final Entity entity) {
        return entity instanceof Animal || entity instanceof Squid || entity instanceof IronGolem ||
                entity instanceof Bat;
    }

    public static boolean isMob(final Entity entity) {
        return entity instanceof Mob || entity instanceof Villager || entity instanceof Slime
                || entity instanceof Ghast || entity instanceof EnderDragon || entity instanceof Shulker;
    }

    public static int getPing(final Player entityPlayer) {
        if (entityPlayer == null)
            return 0;

        ClientPacketListener connection = mc.getConnection();
        if (connection == null) return 0;

        final PlayerInfo networkPlayerInfo = connection.getPlayerInfo(entityPlayer.getUUID());

        return networkPlayerInfo == null ? 0 : networkPlayerInfo.getLatency();
    }
}
