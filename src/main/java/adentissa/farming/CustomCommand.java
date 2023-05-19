package adentissa.farming;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

// CustomCommand.java
public class CustomCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "f";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/f e|d";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] args) throws CommandException {
        if (args.length != 1) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Wrong usage!"));
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(getCommandUsage(iCommandSender)));
        }

        // enabled
        // Farming.enabled = true;
        Farming.enabled = args[0].equalsIgnoreCase("e");

        // Inform the player about the state change
        String state = Farming.enabled ? "enabled" : "disabled";
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Alarm set to : "+ state +" !"));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}

