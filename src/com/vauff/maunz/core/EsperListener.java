package com.vauff.maunz.core;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.lang3.time.StopWatch;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NickAlreadyInUseEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

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
		commands.add(new BulliedMe());
		commands.add(new BullyMe());
		commands.add(new Changelog());
		commands.add(new Chans());
		commands.add(new Disable());
		commands.add(new Enable());
		commands.add(new Help());
		commands.add(new Intelligence());
		commands.add(new Join());
		commands.add(new Leave());
		commands.add(new Nick());
		commands.add(new Ping());
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

	public void onPrivateMessage(PrivateMessageEvent event) throws Exception
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

	public void onConnect(ConnectEvent event) throws IOException
	{
		Main.esperBot = Main.manager.getBotById(0);
		Main.freenodeBot = Main.manager.getBotById(1);
		uptime.start();

		for (String chan : Util.getFileContents())
		{
			Main.esperBot.sendIRC().joinChannel(chan);
		}
	}

	public void onNickAlreadyInUse(NickAlreadyInUseEvent event)
	{
		Nick.isNickUsed = true;
	}
}