package net.countercraft.movecraft.processing.tasks.detection;

import net.countercraft.movecraft.MovecraftLocation;
import net.countercraft.movecraft.craft.CraftType;
import net.countercraft.movecraft.localisation.I18nSupport;
import net.countercraft.movecraft.processing.MovecraftWorld;
import net.countercraft.movecraft.processing.TaskPredicate;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PilotSignValidator implements TaskPredicate<MovecraftLocation> {
    @Override
    public Result validate(@NotNull MovecraftLocation location, @NotNull CraftType type, @NotNull MovecraftWorld world, @Nullable CommandSender player) {
        BlockState state = world.getState(location);
        if (!(state instanceof Sign)) {
            return Result.succeed();
        }
        Sign s = (Sign) state;
        if (!s.getLine(0).equalsIgnoreCase("Pilot:") || player == null) {
            return Result.succeed();
        }
        String playerName = player.getName();
        boolean foundPilot = false;
        for(int line = 1; line<4; line++){
            if(s.getLine(line).equalsIgnoreCase(playerName)){
                foundPilot = true;
                break;
            }
        }
        return foundPilot || (player.hasPermission("movecraft.bypasslock")) ? Result.succeed() : Result.failWithMessage(I18nSupport.getInternationalisedString("Detection - Not Registered Pilot"));
    }
}
