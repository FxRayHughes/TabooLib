package me.skymc.taboolib.commands.sub.cycle;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import me.skymc.taboolib.Main;
import me.skymc.taboolib.commands.SubCommand;
import me.skymc.taboolib.database.GlobalDataManager;
import me.skymc.taboolib.message.MsgUtils;
import me.skymc.taboolib.other.DateUtils;
import me.skymc.taboolib.timecycle.TimeCycle;
import me.skymc.taboolib.timecycle.TimeCycleEvent;
import me.skymc.taboolib.timecycle.TimeCycleInitializeEvent;
import me.skymc.taboolib.timecycle.TimeCycleManager;

public class CycleInfoCommand extends SubCommand {

	public CycleInfoCommand(CommandSender sender, String[] args) {
		super(sender, args);
		if (args.length < 3) {
			MsgUtils.send(sender, "&c��������ȷ�ļ��������");
			return;
		}
		
		TimeCycle cycle = TimeCycleManager.getTimeCycle(args[2]);
		if (cycle == null) {
			MsgUtils.send(sender, "&c����� &4" + args[2] + " &c������");
			return;
		}
		
		sender.sendMessage("��f");
		sender.sendMessage("��b��l----- ��3��lTimeCycle Info ��b��l-----");
		sender.sendMessage("��f");
		sender.sendMessage(" ��f- ��7ע������: ��f" + asString(cycle.getCycle() / 1000L));
		sender.sendMessage(" ��f- ��7ע����: ��f" + cycle.getPlugin().getName());
		sender.sendMessage("��f");
		sender.sendMessage(" ��f- ��7�ϴ�ˢ��ʱ��: ��f" + DateUtils.CH_ALL.format(TimeCycleManager.getBeforeTimeline(cycle.getName())));
		sender.sendMessage(" ��f- ��7�´�ˢ��ʱ��: ��f" + DateUtils.CH_ALL.format(TimeCycleManager.getAfterTimeline(cycle.getName())));
		sender.sendMessage("��f");
	}
	
	public String asString(long seconds) {
		long day = TimeUnit.SECONDS.toDays(seconds);
	    long hours = TimeUnit.SECONDS.toHours(seconds) - day * 24;
	    long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
	    long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
	    return "��f" + day + "��7 ��, ��f" + hours + "��7 Сʱ, ��f" + minute + "��7 ����, ��f" + second + "��7 ��";
	}

	@Override
	public boolean command() {
		return true;
	}

}