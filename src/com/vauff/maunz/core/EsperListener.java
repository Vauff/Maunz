package com.vauff.maunz.core;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.lang3.time.StopWatch;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NickAlreadyInUseEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.QuitEvent;

import com.vauff.maunz.commands.*;
import com.vauff.maunz.core.ICommand;
import com.vauff.maunz.features.Intelligence;

public class EsperListener extends ListenerAdapter
{
	private LinkedList<ICommand<MessageEvent, PrivateMessageEvent>> commands = new LinkedList<ICommand<MessageEvent, PrivateMessageEvent>>();
	public static StopWatch uptime = new StopWatch();

	public EsperListener()
	{
		commands.add(new About());
		commands.add(new AccInfo());
		commands.add(new Benchmark());
		commands.add(new BlogDebug());
		commands.add(new BulliedMe());
		commands.add(new Changelog());
		commands.add(new Chans());
		commands.add(new Disable());
		commands.add(new Enable());
		commands.add(new Help());
		commands.add(new Intelligence());
		commands.add(new Join());
		commands.add(new Leave());
		commands.add(new Map());
		commands.add(new Nick());
		commands.add(new Notify());
		commands.add(new Ping());
		commands.add(new Quote());
		commands.add(new Reddit());
		commands.add(new Reset());
		commands.add(new Restart());
		commands.add(new Say());
		commands.add(new Source());
		commands.add(new Steam());
		commands.add(new Stop());
		commands.add(new Trello());
		commands.add(new Update());
		commands.add(new WhoSay());
	}

	public void onMessage(MessageEvent event) throws Exception
	{
		try
		{
			String cmdName = event.getMessage().split(" ")[0];

			for (ICommand<MessageEvent, PrivateMessageEvent> cmd : commands)
			{
				if (Util.isEnabled || cmd instanceof Enable || cmd instanceof Disable)
				{
					for (String s : cmd.getAliases())
					{
						if (cmdName.equalsIgnoreCase(s))
						{
							cmd.exeChan(event);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			Logger.log.error("", e);
		}
	}

	public void onPrivateMessage(PrivateMessageEvent event) throws Exception
	{
		try
		{
			String cmdName = event.getMessage().split(" ")[0];

			for (ICommand<MessageEvent, PrivateMessageEvent> cmd : commands)
			{
				if (Util.isEnabled || cmd instanceof Enable || cmd instanceof Disable)
				{
					for (String s : cmd.getAliases())
					{
						if (cmdName.equalsIgnoreCase(s))
						{
							cmd.exePrivate(event);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			Logger.log.error("", e);
		}
	}

	public void onConnect(ConnectEvent event) throws IOException
	{
		try
		{
			Main.esperBot = Main.manager.getBotById(0);
			Main.freenodeBot = Main.manager.getBotById(1);
			uptime.start();

			for (String chan : Util.getFileContents())
			{
				if (chan.equals("#extruders"))
				{
					Main.esperBot.sendIRC().joinChannel(chan, Passwords.extruders);
				}
				else if (chan.equals("#TaskController") || chan.equals("#TaCoTest"))
				{
					Main.esperBot.sendIRC().joinChannel(chan, Passwords.taskController);
				}
				else
				{
					Main.esperBot.sendIRC().joinChannel(chan);
				}
			}
		}
		catch (Exception e)
		{
			Logger.log.error("", e);
		}
	}

	public void onQuit(QuitEvent event)
	{
		if (event.getReason().contains("GHOST command used by ") && event.getUser().getNick().equals(Main.esperBot.getNick()))
		{
			Util.isGhosted = true;
			Logger.log.info("Maunz is stopping...");
			Main.manager.stop("Stopping");
			System.exit(0);
		}
	}

	public void onNickAlreadyInUse(NickAlreadyInUseEvent event)
	{
		try
		{
			if (event.getUsedNick().equals("Maunz") && Util.devMode == false)
			{
				Thread.sleep(5000);
				Main.esperBot.sendIRC().message("NickServ", "GHOST Maunz " + Passwords.esperNickServ);
				Main.esperBot.sendIRC().changeNick("Maunz");
				Main.esperBot.sendIRC().message("NickServ", "IDENTIFY " + Passwords.esperNickServ);
			}
			else
			{
				Nick.isNickUsed = true;
			}
		}
		catch (Exception e)
		{
			Logger.log.error("", e);
		}
	}
}