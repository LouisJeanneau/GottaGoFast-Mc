package adentissa.farming;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

@Mod(modid = Farming.MODID, version = Farming.VERSION, name = Farming.NAME)
public class Farming {
    public static final String MODID = "adentissafarming";
    public static final String VERSION = "1.0";
    public static final String NAME = "Mon mod";
    public static ResourceLocation ping = new ResourceLocation("farming", "PING");
    private static int incr = 0;
    private double previousSpeed = 0;
    public static boolean enabled = false;
    public FifteenValueQueue queue;

    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new CustomCommand());
        queue = new FifteenValueQueue();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        incr++;
        incr = incr % 15;
        if (event.phase == TickEvent.Phase.END && incr == 0 && enabled) {
            EntityPlayer player = event.player;
            double speed = MathHelper.sqrt_double(player.motionX * player.motionX + player.motionZ * player.motionZ);
            queue.addValue(speed);
            // Adjust the threshold value according to your needs
            double threshold = 0.01;

            double ave = queue.getAverage();
            if (ave < threshold){
                Minecraft.getMinecraft().thePlayer.playSound(ping.toString(),1.0f,1.0f);
            }


            String message = Double.toString(ave);
            ChatComponentText chatComponent = new ChatComponentText(message);
            chatComponent.getChatStyle().setColor(EnumChatFormatting.GREEN); // Optional: Set the color of the message
            Minecraft.getMinecraft().thePlayer.addChatMessage(chatComponent);
        }
    }



    public class FifteenValueQueue {
        private LinkedList<Double> q;

        public FifteenValueQueue() {
            this.q = new LinkedList<>();
            while (q.size() < 15) {
                q.add(1.0);
            }
        }

        public void addValue(Double value) {
            if (q.size() == 15) {
                q.remove(); // Remove the oldest value
            }
            q.add(value); // Add the new value
        }

        public double getAverage() {
            double sum = 0.0;
            for (Double value : q) {
                sum += value;
            }
            return sum / q.size();
        }
    }

}
