package pw.tehkitti.maunz.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.output.OutputIRC;

import pw.tehkitti.maunz.core.Listener;
import pw.tehkitti.maunz.core.Main;

@SuppressWarnings("rawtypes")
public class Stop implements ICommand<MessageEvent,PrivateMessageEvent>
{
	@Override
	public void exeChan(MessageEvent event) throws Exception
	{
		if(event.getUser().getNick().equals("TehKitti"))
		{
			OutputIRC irc = new OutputIRC(Main.bot);
			for (String p : Listener.channels) 
			{
				Main.bot.sendIRC().message(p, "I have been ordered to stop by " + event.getUser().getNick());
			}
			irc.quitServer();
		}
		else
		{
			event.respond("I'm sorry but you do not have proper permissions to stop me!");
			event.getChannel().send().message("Now nobody can stop me from taking over the world! Mwahahahahaha!");
		}
	}

	@Override
	public void exePrivate(PrivateMessageEvent event) throws Exception
	{
		if(event.getUser().getNick().equals("TehKitti"))
		{
			OutputIRC irc = new OutputIRC(Main.bot);

			for (String p : Listener.channels) 
			{
				Main.bot.sendIRC().message(p, "I have been ordered to stop by " + event.getUser().getNick());
			}
			event.respond("I have been ordered to stop by " + event.getUser().getNick());
			irc.quitServer();
		}
		else
		{
			event.respond("I'm sorry but you do not have proper permissions to stop me!");
			event.respond("Now nobody can stop me from taking over the world! Mwahahahahaha!");
		}
	}

	@Override
	public String[] getAliases()
	{
		return new String[]{"*stop"};
	}
}