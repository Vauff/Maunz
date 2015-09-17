package com.vauff.maunz.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import com.vauff.maunz.core.Listener;
import com.vauff.maunz.core.Main;
import com.vauff.maunz.core.Util;

public class Disable implements ICommand<MessageEvent,PrivateMessageEvent>
{

	@Override
	public void exeChan(MessageEvent event) throws Exception
	{
		if(event.getUser().getNick().equals("Vauff"))
		{
			if(Util.isEnabled)
			{
				for (String p : Listener.channels) 
				{
				Main.esperBot.sendIRC().message(p, "I have been disabled by " + event.getUser().getNick());
				}
				Util.isEnabled = false;
			}
			else
				event.respond("You silly, I was already disabled!");
		}
		else
		{
			event.respond("I'm sorry but you do not have proper permissions to disable me!");
			event.getChannel().send().message("Now nobody can stop me from taking over the world! Mwahahahahaha!");
		}
	}

	@Override
	public void exePrivate(PrivateMessageEvent event) throws Exception
	{
		if(event.getUser().getNick().equals("Vauff"))
		{
			if(Util.isEnabled)
			{
				for (String p : Listener.channels) 
				{
				Main.esperBot.sendIRC().message(p, "I have been disabled by " + event.getUser().getNick());
				}
				event.respond("I have been disabled by " + event.getUser().getNick());
				Util.isEnabled = false;
			}
			else
				event.respond("You silly, I was already disabled!");
		}
		else
		{
			event.respond("I'm sorry but you do not have proper permissions to disable me!");
			event.getUser().send().message("Now nobody can stop me from taking over the world! Mwahahahahaha!");
		}
	}

	@Override
	public String[] getAliases()
	{
		return new String[]{"*disable"};
	}
}