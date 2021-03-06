package net.novaplay.bcproxy.command.defaults;

import net.novaplay.bcproxy.client.ProxyClient;
import net.novaplay.bcproxy.command.Command;
import net.novaplay.bcproxy.command.CommandSender;
import net.novaplay.bcproxy.player.Player;
import net.novaplay.bcproxy.server.Server;

public class ListCommand extends Command {

	public ListCommand(String name) {
		super(name);
		this.usage = "list";
		this.description = "Shows players list";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(args.length == 0) {
			String list = "";
			int c = 0;
			for(Player p : Server.getInstance().getOnlinePlayers().values()) {
				list += p.getName() + ", ";
				c++;
			}
			sender.sendMessage("§6Players online ("+ Server.getInstance().getOnlinePlayers().size()+"): §a" +list);
		} else {
			if(args[0] != null) {
				String server = args[0];
				ProxyClient client = Server.getInstance().getOnlineClientByName(server);
				if(client != null) {
					String list = "";
					int c = 0;
					for(Player p : client.getOnlinePlayers().values()) {
						list += p.getName() + ", ";
						c++;
					}
					sender.sendMessage("§6Players online on client §b"+server+" §6("+ client.getOnlinePlayers().size()+"): §a" +list);
				} else {
					sender.sendMessage("§cClient §b"+server+" §cis offline");	
				}
			}
		}
		return false;
	}

}
