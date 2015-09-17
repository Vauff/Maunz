package com.vauff.maunz.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.output.OutputIRC;

import com.vauff.maunz.core.Listener;
import com.vauff.maunz.core.Main;
import com.vauff.maunz.core.Util;

@SuppressWarnings("rawtypes")
public class Restart implements ICommand<MessageEvent,PrivateMessageEvent>
{
	@Override
	public void exeChan(MessageEvent event) throws Exception 
	{
		if(event.getUser().getNick().equals("Vauff"))
		{
			OutputIRC irc = new OutputIRC(Main.esperBot);

			for (String p : Listener.channels) 
			{
			Main.esperBot.sendIRC().message(p, "I have been ordered to restart by " + event.getUser().getNick());
			}
			Listener.channels.clear();
			irc.quitServer();
			Main.createBot();
		}
		else
		{
			event.respond("I'm sorry but you do not have proper permissions to restart me!");
			event.getChannel().send().message("Now nobody can stop me from taking over the world! Mwahahahahaha!");
		}
	}

	@Override
	public void exePrivate(PrivateMessageEvent event) throws Exception
	{
		if(event.getUser().getNick().equals("Vauff"))
		{
			OutputIRC irc = new OutputIRC(Main.esperBot);

			for (String p : Listener.channels) 
			{
			Main.esperBot.sendIRC().message(p, "I have been ordered to restart by " + event.getUser().getNick());
			}
			event.respond("I have been ordered to restart by " + event.getUser().getNick());
			Listener.channels.clear();
			irc.quitServer();
			Main.createBot();
		}
		else
		{
			event.respond("I'm sorry but you do not have proper permissions to restart me!");
			event.respond("Now nobody can stop me from taking over the world! Mwahahahahaha!");
		}
	}
	
	@Override
	public String[] getAliases()
	{
		return new String[]{"*restart"};
	}
}